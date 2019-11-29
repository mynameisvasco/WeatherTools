import java.io.IOException;

import files.Dataset;
import probability.MinHash;
import weather.RegisteredWeather;

public class TestMinHash 
{
	public static void main(String[] args) throws IOException
	{
		Dataset dataset = new Dataset();
		dataset.importFiles();
		dataset.importStations();
		dataset.importRegisteredWeathers();
		
		MinHash minHash = new MinHash(100);
		
		double[] temperatures1 = new double[] {5.1, 10.2, 9.1, 4.1, 25.4};
		double[] temperatures2 = new double[] {1.1, 0.2, 9.1, 4.1, 25.4};
		
		/*int i = 0;
		if(i < 9)
		{
			for(RegisteredWeather rw : dataset.getStations().get(0).getRegisteredWeathers(2019))
			{
				temperatures1[i] = rw.getAverageTemperature().getValue();
				i++;
			}				
		}
		
		i = 0;
		if(i < 9)
		{
			for(RegisteredWeather rw : dataset.getStations().get(1).getRegisteredWeathers(2019))
			{
				temperatures2[i] = rw.getAverageTemperature().getValue();
				i++;
			}				
		}*/
		
		
		System.out.println(minHash.similarity(temperatures1, temperatures2));
	}
}
