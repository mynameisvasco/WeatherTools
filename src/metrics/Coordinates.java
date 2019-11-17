package metrics;

public class Coordinates 
{
	private double latitude;
	private double longitude;
	private double elevation;
	
	public Coordinates(double latitude, double longitude, double elevation)
	{
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = elevation;
	}

	public double getLatitude() 
	{
		return latitude;
	}
	
	public double getLongitude() 
	{
		return longitude;
	}
	
	public double getElevation() 
	{
		return elevation;
	}
	
	public String toString()
	{
		return "[" + this.getLatitude() + "," + this.getLongitude() + "," + this.getElevation() + ",]";
	}
}

