package com.kitchen.scrape.mastersswim;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Kevin Chen
 */
public class Main
{
	private static final String BASE_URL = "http://www.usms.org/comp/tt/toptenlist.php";

	public static void main( String[] args )
	{
		File output = new File( "output.txt" );
		File errorLog = new File( "error.log" );
		scrape( "1971", "1", "M", "9", output, errorLog );
	}

	private static void scrapeAll( File output, File errorLog )
	{
		List<String> years = IntStream.range( 1971, 2017 ).mapToObj( Integer::toString ).collect( Collectors.toList() );
		List<String> courseIDs = Arrays.asList( "1", "2", "3" );
		List<String> sexes = Arrays.asList( "M", "W" );
		List<String> ageGroupIDs = IntStream.range( 1, 18 ).mapToObj( Integer::toString ).collect( Collectors.toList() );

		for ( String year : years )
		{
			for ( String courseID : courseIDs )
			{
				for ( String sex : sexes )
				{
					for ( String ageGroupID : ageGroupIDs )
					{
						scrape( year, courseID, sex, ageGroupID, output, errorLog );
					}
				}
			}
		}
	}

	private static void scrape( String year, String courseID, String sex, String ageGroupID, File output, File errorLog )
	{
		try
		{
			String args = String.format(
				"CourseID=%s&Year=%s&Sex=%s&AgeGroupID=%s",
				courseID, year, sex, ageGroupID
			);
			Document doc = Jsoup.connect( BASE_URL + "?" + args ).get();
			String text = doc.text();
			if ( text.contains( "No Top 10 data was found" ) )
			{
				System.out.println( "No data found for: " + args );
			}
			else
			{
				System.out.println( "FOUND DATA FOR: " + args );

				List<String> headers =
					doc.select( "h3[id^=Event]" )
						.stream()
						.map( Element::text )
						.map( String::trim )
						.collect( Collectors.toList() );

				Elements tables = doc.select( "table" );

				for ( int i = 0; i < tables.size(); i++ )
				{
					Element table = tables.get( i );
					String header = headers.get( i );
					System.out.println( header );
					for ( Element row : table.select( "tr" ) )
					{
						Elements tds = row.select( "td" );
						// skip headers
						if ( tds.size() == 0 )
						{
							continue;
						}

						if ( tds.size() != 6 )
						{
							String errorMessage = String.format( "Expected 6 columns, found %d", tds.size() );
							FileUtils.writeStringToFile( errorLog, errorMessage, StandardCharsets.UTF_8 );
						}
						else
						{
							tds.forEach( td -> System.out.print( td.text() + " | " ) );
						}
						System.out.println( "\n" );
					}
					System.out.println( "\n\n" );
				}
			}
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
	}
}
