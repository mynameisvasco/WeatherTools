package weather;

import java.util.LinkedList;

import metrics.Coordinates;
import metrics.Date;

public class Station
{
	private String name;
	private Coordinates location;
	private LinkedList<RegisteredWeather> registeredWeathers;
	
	public Station(String name, Coordinates location)
	{
		super();
		this.name = name;
		this.location = location;
		this.registeredWeathers = new LinkedList<RegisteredWeather>();
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
	
	public void addRegisteredWeather(RegisteredWeather rw)
	{
		this.registeredWeathers.add(rw);
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
	
	public boolean containsRegisteredWeatherInDate(Date d)
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
}
