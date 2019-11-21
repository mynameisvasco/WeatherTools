package metrics;

public class Temperature
{
	public double celsius;
	
	public Temperature(CelsiusDegree c)
	{
		this.celsius = c.getValue();
	}
	
	public Temperature(FahrenheitDegree f)
	{
		this.celsius = new CelsiusDegree(f).getValue();
	}
	
	public double getValue()
	{
		return this.celsius;
	}
	
	public Temperature decreaseAccuracy()
	{
		double celsius = this.celsius;
		celsius = Math.round(celsius * 100);
		celsius = celsius/100;
		
		return new Temperature(new CelsiusDegree(celsius));
	}
	
	public String toString()
	{
		return this.celsius + " ºC";
	}
}