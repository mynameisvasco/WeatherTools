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
	
	public boolean equals(Coordinates c)
	{
		if(this.latitude == c.getLatitude() && this.longitude == c.getLongitude()) //Ignoring elevation for what we want.
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Coordinates decreaseAccuracy()
	{
		double lat = this.getLatitude();
		double lon = this.getLongitude();
		
		lat = Math.round(lat * 100);
		lat = lat/100;
		
		lon = Math.round(lon * 100);
		lon = lon/100;
		
		return new Coordinates(lat, lon, this.elevation);
	}
	
	public boolean isInRange(Coordinates c)
	{
		if(Math.abs(this.getLongitude() - c.getLongitude()) <= 0.05 && Math.abs(this.getLatitude() - c.getLatitude()) <= 0.05)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	public String toString()
	{
		return "[" + this.getLatitude() + "," + this.getLongitude() + "," + this.getElevation() + ",]";
	}
}

