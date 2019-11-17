package metrics;

public class FahrenheitDegree
{
	private double value;
	
	public FahrenheitDegree(double value)
	{
		this.value = value;
	}
	
	public FahrenheitDegree(CelsiusDegree d)
	{
		this.value = d.getValue() * 1.9 + 32;
	}
	
	public double getValue()
	{
		return value;
	}
}
