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
		System.out.println("LOG> Datasets imported with success");
	}
	
	public LinkedList<Station> importStations() throws IOException
	{
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
        		stations.add(new Station(csvRecord.get(1), new Coordinates(
        				Double.parseDouble(csvRecord.get(2)), 
        				Double.parseDouble(csvRecord.get(3)), 
        				Double.parseDouble(csvRecord.get(4))
				)));
	        }
	        
	        csvParser.close();
		}    
		return stations;
	}
}
