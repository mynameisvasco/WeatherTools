package weather;

import java.util.LinkedList;

import metrics.Coordinates;

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
	
	public boolean equals(Station s)
	{
		if(this.getName().equals(s.getName())) return true;
		return false;
	}
	
	public String toString()
	{
		return "Name -> " + this.getName() + "\n"
				+ "Location -> " + this.getLocation();
	}
}
