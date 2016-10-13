package com.kitchen.scrape.mastersswim;

/**
 * @author Kevin Chen
 */
public class Event
{
	private final Integer distance;
	private final String name;
	private final String sex;
	private final Integer ageMin;
	private final Integer ageMax;
	private final Integer year;

	public Event( Integer distance, String name, String sex, Integer ageMin, Integer ageMax, Integer year )
	{
		this.distance = distance;
		this.name = name;
		this.sex = sex;
		this.ageMin = ageMin;
		this.ageMax = ageMax;
		this.year = year;
	}

	public Integer getDistance()
	{
		return distance;
	}

	public String getName()
	{
		return name;
	}

	public String getSex()
	{
		return sex;
	}

	public Integer getAgeMin()
	{
		return ageMin;
	}

	public Integer getAgeMax()
	{
		return ageMax;
	}

	public Integer getYear()
	{
		return year;
	}

	@Override
	public String toString()
	{
		return "Event{" +
			"distance=" + distance +
			", name='" + name + '\'' +
			", sex='" + sex + '\'' +
			", ageMin=" + ageMin +
			", ageMax=" + ageMax +
			", year=" + year +
			'}';
	}
}
