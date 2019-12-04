import java.io.IOException;
import files.Dataset;
import probability.MinHash;
import weather.RegisteredWeather;
import weather.Station;

public class TestMinHash 
{
	public static double MIN_SIMILARITY = 0.5;
	public static int YEAR = 2018;
	public static void main(String[] args) throws IOException
	{	
		
		
		Dataset dataset = new Dataset();
		dataset.importFiles();
		dataset.importStations();
		dataset.importRegisteredWeathers();
		
		Double[][] temperatures = new Double[dataset.getMaxStationId()][12];
		MinHash minHash = new MinHash(100, dataset.getMaxStationId());

		
		for(Station s : dataset.getStations())
		{

			Double[] array = new Double[12];
			int i = 0;
			if(s.getRegisteredWeathers(YEAR).size() >= 12)
			{				
				for(RegisteredWeather rw : s.getRegisteredWeathers(YEAR))
				{
					array[i] = rw.getAverageTemperature().getValue();
					i++;
				}
			}
			temperatures[s.getId()] = array;
		}

		long beforeTime = System.currentTimeMillis();
		minHash.initSignatureTable(temperatures);
		System.out.println("\n" + dataset.getStations().size() + " stations signed in " + ( (System.currentTimeMillis() - beforeTime) * 0.001) + " seconds");			
		
		
		beforeTime = System.currentTimeMillis();
		for(Station s : dataset.getStations())
		{
			for(Station s1 : dataset.getStations())
			{
				if (s == s1) continue;
				System.out.println(s.getName() + " and " + s1.getName() + " have a jaccard distance of " + (1-minHash.similarity(s.getId(), s1.getId())));
			}
		}
		System.out.println("\n" + dataset.getStations().size() + " stations compared in " + ( (System.currentTimeMillis() - beforeTime) * 0.001) + " seconds");	
	}
}