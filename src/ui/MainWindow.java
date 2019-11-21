package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.tilesources.OfflineOsmTileSource;

import files.Dataset;
import metrics.Coordinates;
import metrics.Date;
import weather.RegisteredWeather;
import weather.Station;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class MainWindow implements MouseListener, ActionListener
{
	
	private JFrame mainFrame;
	private JFrame stationFrame;
	private JMapViewer map;
	private Dataset dataset;
	private JComboBox<String> dateSelectorMonth;
	private JComboBox<String> dateSelectorYear;
	private Date date;
	
	public MainWindow() throws IOException
	{
		dataset = new Dataset();
		this.importDatasets();
		date = new Date("01/10/2018");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame = new JFrame("WeatherTools - Map");
		mainFrame.setSize(1280,720);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
		mainFrame.setLayout(new BorderLayout());
		this.initDateSelector();
		this.initMap();
		this.initStationsMarkers();
		
		mainFrame.setVisible(true);
	}
	
	public void initMap()
	{
		map = new JMapViewer();
		map.addMouseListener(this);
		map.setTileSource(new OfflineOsmTileSource(new File("resources/mapTiles").toURI().toString(),1,7));
		mainFrame.add(map, BorderLayout.CENTER);
		System.out.println("LOG> Map initialzied with success");
	}
	
	public void updateDate()
	{
		this.date = new Date(1, Date.getMonthInt(this.dateSelectorMonth.getSelectedItem().toString()), Integer.parseInt(this.dateSelectorYear.getSelectedItem().toString()));
	}
	
	public void initStationsMarkers()
	{
		this.map.setMapMarkerList(new ArrayList<MapMarker>());
		
		for(Station s : dataset.getStations())
		{
			if(s.containsRegisteredWeatherInDate(this.date))
			{				
				MapMarkerDot marker = new MapMarkerDot(Color.RED, s.getLocation().getLatitude(), s.getLocation().getLongitude());
				map.addMapMarker(marker);
			}
		}
		
		this.map.revalidate();
		this.map.repaint();
	}
	
	public void initDateSelector()
	{
		dateSelectorMonth = new JComboBox<String>(Date.MONTHS_NAME);
		dateSelectorYear = new JComboBox<String>(Date.yearsUntil2070());
		dateSelectorMonth.setSelectedItem("November");
		dateSelectorYear.setSelectedIndex(18);
		dateSelectorMonth.addActionListener(this);
		dateSelectorYear.addActionListener(this);
		
		JPanel dateSelectorPanel = new JPanel();
		dateSelectorPanel.setLayout(new GridLayout(1, 2));
		dateSelectorPanel.add(dateSelectorMonth);
		dateSelectorPanel.add(dateSelectorYear);
		dateSelectorPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		
		this.mainFrame.add(dateSelectorPanel, BorderLayout.NORTH);
		
		
	}
	
	public void importDatasets() throws IOException
	{
		dataset.importFiles();
		System.out.println("LOG> Datasets files imported with success");
		dataset.importStations();
		System.out.println("LOG> " + this.dataset.getStations().size() + " stations imported with success");
		dataset.importRegisteredWeathers();
		System.out.println("LOG> Registered weathers imported with success " + this.dataset.getStations().size() + " stations are being used.");
	}

	public void mouseClicked(MouseEvent e) 
	{
		if(e.getButton() == MouseEvent.BUTTON1)
		{
			for(Station s : this.dataset.getStations())
			{
				if(s.isValid(new Coordinates(map.getPosition(e.getPoint()).getLat(), map.getPosition(e.getPoint()).getLon(), 0)))
				{
					if(this.stationFrame != null)
					{
						this.stationFrame.revalidate();
						this.stationFrame.repaint();
						this.stationFrame.setVisible(false);
					}
					Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					this.stationFrame = new JFrame(s.getName());
					this.stationFrame.setSize(640,360);
					this.stationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					this.stationFrame.setLocation(dim.width/2-mainFrame.getSize().width/2, dim.height/2-mainFrame.getSize().height/2);
					this.stationFrame.setLayout(new GridLayout(5,1));
					JLabel sName = new JLabel("sName");
					sName.setText("Station name: " + s.getName());
					sName.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
					//Get weather for current date
					RegisteredWeather rw = s.findWeather(this.date);
					JLabel sTMax = new JLabel("sTMax");
					sTMax.setText("Max temperature: " + rw.getMaxTemperature().decreaseAccuracy());
					sTMax.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
					JLabel sTMin = new JLabel("sTMin");
					sTMin.setText("Min temperature: " + rw.getMinTemperature().decreaseAccuracy());
					sTMin.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
					JLabel sTAvg = new JLabel("sTAvg");
					sTAvg.setText("Average temperature: " + rw.getAverageTemperature().decreaseAccuracy());
					sTAvg.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
					JLabel sRain = new JLabel("sRain");
					sRain.setText("Average rainfall: " + rw.getAverageRainfall());
					sRain.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
					
					this.stationFrame.add(sName);
					this.stationFrame.add(sTMax);
					this.stationFrame.add(sTMin);
					this.stationFrame.add(sTAvg);
					this.stationFrame.add(sRain);
					this.stationFrame.setVisible(true);
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.dateSelectorMonth || e.getSource() == this.dateSelectorYear)
		{
			this.updateDate();
			this.initStationsMarkers();
		}
		
	}
}
