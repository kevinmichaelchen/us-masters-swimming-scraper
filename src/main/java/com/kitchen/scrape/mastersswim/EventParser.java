package com.kitchen.scrape.mastersswim;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Kevin Chen
 */
public class EventParser
{
	public static void main( String[] args )
	{
		System.out.println( parse( "50 Freestyle SCY Men 60-64 (1971)" ) );
		System.out.println( parse( "50 Freestyle Boom SCY Men 60-64 (1971)" ) );
	}

	public static Event parse( String test )
	{
		Pattern pattern = Pattern.compile( "(\\d+)(\\s+)(.*?)SCY(\\s+)(.*)(\\s+)(\\d+)-(\\d+)(\\s+)\\((\\d+)\\)" );
		Matcher matcher = pattern.matcher( test );
		while ( matcher.find() )
		{
			Integer distance = tryParseToInt( matcher.group( 1 ) );
			String name = matcher.group( 3 );
			String sex = matcher.group( 5 );
			Integer ageMin = tryParseToInt( matcher.group( 7 ) );
			Integer ageMax = tryParseToInt( matcher.group( 8 ) );
			Integer year = tryParseToInt( matcher.group( 10 ) );

			return new Event( distance, name, sex, ageMin, ageMax, year );
		}
		return null;
	}

	private static Integer tryParseToInt( String s )
	{
		try
		{
			return Integer.parseInt( s );
		}
		catch ( Exception e )
		{
			return null;
		}
	}
}
