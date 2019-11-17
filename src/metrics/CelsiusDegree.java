package metrics;

public class CelsiusDegree
{
	private double value;
	
	public CelsiusDegree(double value)
	{
		this.value = value;
	}
	
	public CelsiusDegree(FahrenheitDegree f)
	{
		this.value = f.getValue() - 32 / 1.8;
	}

	public double getValue()
	{
		return value;
	}
	
}
