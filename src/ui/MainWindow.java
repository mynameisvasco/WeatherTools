package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;


import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.tilesources.OfflineOsmTileSource;

import files.Dataset;
import weather.Station;

import javax.swing.JFrame;
public class MainWindow
{
	
	private JFrame mainFrame;
	private JMapViewer map;
	private Dataset dataset;
	
	public MainWindow() throws IOException
	{
		dataset = new Dataset();
		this.importDatasets();
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame = new JFrame("WeatherTools - Map");
		mainFrame.setSize(1280,720);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
		
		this.initMap();
		this.initStationsMarkers();
		
		mainFrame.setVisible(true);
	}
	
	public void initMap()
	{
		map = new JMapViewer();
		map.setTileSource(new OfflineOsmTileSource(new File("resources/mapTiles").toURI().toString(),1,6));
		mainFrame.add(map);
		System.out.println("LOG> Map initialzied with succes");
	}
	
	public void initStationsMarkers()
	{
		for(Station s : dataset.getStations())
		{
			map.addMapMarker(new MapMarkerDot(Color.RED, s.getLocation().getLatitude(), s.getLocation().getLongitude()));
		}
	}
	
	public void importDatasets() throws IOException
	{
		dataset.importFiles();
		dataset.importStations();
		System.out.println("LOG> Datasets imported with success");
	}
}
