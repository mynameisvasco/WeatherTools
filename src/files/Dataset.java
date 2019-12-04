package files;

import weather.*;
import metrics.*;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Dataset
{
	private LinkedList<Path> filesPath;
	private LinkedList<Station> stations;
	private int maxStationId;
	private String datasetsLocation = "resources/datasets";

	public Dataset()
	{
		filesPath = new LinkedList<Path>();
		stations = new LinkedList<Station>();
	}
	
	public LinkedList<Station> getStations()
	{
		return this.stations;
	}
	
	public void importFiles()
	{
		File datasetsDir = new File(datasetsLocation);
		File[] datasets = datasetsDir.listFiles(new FileExtensionFilter("csv"));
		for(File f : datasets)
		{
			filesPath.add(Paths.get(f.getAbsolutePath()));
		}
	}
	
	public LinkedList<Station> importStations() throws IOException
	{
		int stationId = 0;
		for(Path path : this.filesPath)
		{
	        Reader reader = Files.newBufferedReader(path);
	        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
	        
	        mainLoop:
	        for (CSVRecord csvRecord : csvParser) 
	        {
	        	for(Station s : stations)
	        	{
	        		if(s.getName().equals(csvRecord.get(1)))
					{
						continue mainLoop;
					}
	        	}
        		this.stations.add(new Station(stationId,
        				csvRecord.get(1), new Coordinates(
        				Double.parseDouble(csvRecord.get(2)), 
        				Double.parseDouble(csvRecord.get(3)), 
        				Double.parseDouble(csvRecord.get(4))
				)));
        		stationId++;
        		this.maxStationId = stationId;
	        }
	        
	        csvParser.close();
		}    
		return this.stations;
	}
	
	public LinkedList<RegisteredWeather> importRegisteredWeathers() throws IOException
	{
		LinkedList<RegisteredWeather> rw = null;
		for(Path path : this.filesPath)
		{
			Reader reader = Files.newBufferedReader(path);
	        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
			rw = new LinkedList<RegisteredWeather>();
		
			for (CSVRecord csvRecord : csvParser) 
	        {
	        	for(int i = 0; i < this.stations.size(); i++)
	        	{
	        		if(this.stations.get(i).getName().equals(csvRecord.get(1)))
					{
						try
						{
							int dateDay = 1;
							int dateMonth = Integer.parseInt(csvRecord.get("DATE").split("-")[1]);
							int dateYear = Integer.parseInt(csvRecord.get("DATE").split("-")[0]);
							Date date = new Date(dateDay, dateMonth, dateYear);
							Temperature tMin = new Temperature(new FahrenheitDegree(Double.parseDouble(csvRecord.get("TMIN"))));
							Temperature tMax = new Temperature(new FahrenheitDegree(Double.parseDouble(csvRecord.get("TMAX"))));
							Temperature tAverage = new Temperature(new FahrenheitDegree(Double.parseDouble(csvRecord.get("TAVG"))));
							Rainfall rainfall = new Rainfall(Double.parseDouble(csvRecord.get("PRCP")));							
							this.stations.get(i).addRegisteredWeather(new RegisteredWeather(date, tMax, tMin, tAverage, rainfall));
						}
						catch(NumberFormatException e) // Means that one of the values above is null so we need to remove that station
						{
							this.stations.remove(this.stations.get(i));
						}
					}
	        	}
	        }
	        csvParser.close();
		}
		return rw;
	}
	
	public int getMaxStationId()
	{
		return this.maxStationId;
	}
}
