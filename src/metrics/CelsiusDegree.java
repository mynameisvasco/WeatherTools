package metrics;

public class CelsiusDegree
{
	private double value;
	
	public CelsiusDegree(double value)
	{
		this.value =  Math.round(value * 10);
		this.value = this.value/10;
	}
	
	public CelsiusDegree(FahrenheitDegree f)
	{
		this.value = (f.getValue() - 32) / 1.8;
		this.value = Math.round(this.value * 10);
		this.value = this.value/10;
	}

	public double getValue()
	{
		return value;
	}
	
}
