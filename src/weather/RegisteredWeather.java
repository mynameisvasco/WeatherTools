package weather;

import java.util.Date;

import metrics.Rainfall;
import metrics.Temperature;

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
}
