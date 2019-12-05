package weather;

import java.util.LinkedList;

import metrics.Coordinates;
import metrics.Date;
import probability.BloomFilter;

public class Station
{
	private int id;
	private String name;
	private Coordinates location;
	private LinkedList<RegisteredWeather> registeredWeathers;
	private BloomFilter registeredWeathersBloomFilter;

	public Station(int id, String name, Coordinates location)
	{
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.registeredWeathers = new LinkedList<RegisteredWeather>();
		this.registeredWeathersBloomFilter = new BloomFilter(1000, 0.01);

	}

	public String getName()
	{
		return name;
	}
	
	public Coordinates getLocation()
	{
		return location;
	}
	
	public LinkedList<RegisteredWeather> getRegisteredWeathers()
	{
		return registeredWeathers;
	}
	
	public LinkedList<RegisteredWeather> getRegisteredWeathers(int year)
	{
		LinkedList<RegisteredWeather> registeredWeathers = new LinkedList<RegisteredWeather>();
		for(RegisteredWeather rw : this.registeredWeathers)
		{
			if(rw.getDate().getYear() == year)
			{
				registeredWeathers.add(rw);
			}
		}
		return registeredWeathers;
	}
	
	public void addRegisteredWeather(RegisteredWeather rw)
	{
		this.registeredWeathers.add(rw);
		this.registeredWeathersBloomFilter.add(rw.generateHash());
	}
	
	public boolean equals(Station s)
	{
		if(this.getName().equals(s.getName())) return true;
		return false;
	}
		
	public RegisteredWeather findWeather(Date date)
	{
		for(RegisteredWeather rw : this.registeredWeathers)
		{
			if(rw.getDate().equals(date))
			{
				return rw;
			}
		}
		
		return null;
	}
	
	public String toString()
	{
		return "Name -> " + this.getName() + "\n"
				+ "Location -> " + this.getLocation();
	}
	
	public boolean containsWeather(int year)
	{
		for(RegisteredWeather rw : this.registeredWeathers)
		{
			if(rw.getDate().getYear() == year)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean containsWeather(String s)
	{
		return this.registeredWeathersBloomFilter.contains(s);
	}
	public boolean containsWeather(Date d)
	{
		for(RegisteredWeather rw : this.registeredWeathers)
		{
			if(rw.getDate().equals(d))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void get999()
	{
		for(RegisteredWeather rw : this.getRegisteredWeathers(2018))
		{
			System.out.print(rw.getAverageTemperature().getValue() + " ,");
		}
		System.out.println("");
	}
	
	public boolean isValid(Coordinates c)
	{		
		c = c.decreaseAccuracy();
		
		if(c.isInRange(this.location))
		{			
			return true;
		}
		else
		{
			return false;
		}
	}

	public int getId() 
	{
		return this.id;
	}
	
}
