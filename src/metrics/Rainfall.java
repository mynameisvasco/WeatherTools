package metrics;

public class Rainfall
{
	private double value;

	public Rainfall(double value)
	{
		super();
		this.value = value;
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
