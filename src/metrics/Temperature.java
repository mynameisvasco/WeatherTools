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
	
	public String toString()
	{
		return this.celsius + " °C";
	}
}