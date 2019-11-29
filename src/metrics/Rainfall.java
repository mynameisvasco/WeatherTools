package metrics;

public class Rainfall
{
	private double value;

	public Rainfall(double value)
	{
		super();
		value = Math.round(value * 10);
		this.value = value / 10;
	}
	
	public double getValue()
	{
		return value;
	}
	
	public String toString()
	{
		return this.value + " mm";
	}
}
