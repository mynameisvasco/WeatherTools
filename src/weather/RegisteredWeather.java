package weather;

import metrics.Rainfall;
import metrics.Temperature;
import metrics.Date;


public class RegisteredWeather
{
	/*	This class represents each monthly mean weather record */
	private Date date;
	private Temperature maxTemperature;
	private Temperature minTemperature;
	private Temperature averageTemperature;
	private Rainfall averageRainfall;

	public RegisteredWeather(Date date, Temperature maxTemperature, Temperature minTemperature,
			Temperature averageTemperature, Rainfall averageRainfall)
	{
		super();
		this.date = date;
		this.maxTemperature = maxTemperature;
		this.minTemperature = minTemperature;
		this.averageTemperature = averageTemperature;
		this.averageRainfall = averageRainfall;
	}

	public Date getDate()
	{
		return date;
	}
	
	public Temperature getMaxTemperature()
	{
		return maxTemperature;
	}
	
	public Temperature getMinTemperature()
	{
		return minTemperature;
	}
	
	public Temperature getAverageTemperature()
	{
		return averageTemperature;
	}
	
	public Rainfall getAverageRainfall()
	{
		return averageRainfall;
	}
	
	public String toString()
	{
		return "Max Temperature: " + this.maxTemperature + "\n"
				+ "Min Temperature: " + this.minTemperature + "\n"
				+ "Average Temperature: " + this.averageTemperature + "\n"
				+ "Average Rainfall: " + this.averageRainfall + "\n";
	}
}
