package metrics;

public class Temperature
{
	public double celsius;
	
	public Temperature(CelsiusDegree c)
	{
		
		this.celsius = Math.round(c.getValue() * 1);
		this.celsius = this.celsius / 1;
	}
	
	public Temperature(FahrenheitDegree f)
	{
		this.celsius = new CelsiusDegree(f).getValue();
		this.celsius = Math.round(this.celsius * 1);
		this.celsius = this.celsius / 1;
	}
	
	public double getValue()
	{
		return this.celsius;
	}
	
	
	public String toString()
	{
		return this.celsius + " ºC";
	}
}