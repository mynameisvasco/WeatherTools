import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
		MinHash minHash = new MinHash(500, dataset.getMaxStationId());

		
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
				if (s == s1);
				double similarity = minHash.similarity(s.getId(), s1.getId());
				if(temperatures[s.getId()].length == 0 && temperatures[s1.getId()].length == 0) continue;
				if(similarity > MIN_SIMILARITY)
				{					
					System.out.println(s.getName() + " and " + s1.getName() + " -> MinHash: " + similarity +
							" | Jaccard Similarity " + (jaccardSimilarity(temperatures[s.getId()], temperatures[s1.getId()])));
				}
			}
		}
		System.out.println("\n" + dataset.getStations().size() + " stations compared in " + ( (System.currentTimeMillis() - beforeTime) * 0.001) + " seconds");	
	}
	
	static private double jaccardSimilarity(Double[] a, Double[] b) {

	    Set<Double> s1 = new HashSet<Double>();
	    for (int i = 0; i < a.length; i++) {
	        s1.add(a[i]);
	    }
	    Set<Double> s2 = new HashSet<Double>();
	    for (int i = 0; i < b.length; i++) {
	        s2.add(b[i]);
	    }

	    final int sa = s1.size();
	    final int sb = s2.size();
	    s1.retainAll(s2);
	    final int intersection = s1.size();
	    return 1d / (sa + sb - intersection) * intersection;
	}

}