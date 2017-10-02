package com.search_engine.application.click_handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.FlowPanel;
import com.googlecode.gwt.charts.client.ChartLoader;
import com.googlecode.gwt.charts.client.ChartPackage;
import com.googlecode.gwt.charts.client.ColumnType;
import com.googlecode.gwt.charts.client.DataTable;
import com.googlecode.gwt.charts.client.corechart.ColumnChart;
import com.googlecode.gwt.charts.client.corechart.ColumnChartOptions;
import com.googlecode.gwt.charts.client.options.Animation;
import com.googlecode.gwt.charts.client.options.AnimationEasing;
import com.googlecode.gwt.charts.client.options.Bar;
import com.googlecode.gwt.charts.client.options.HAxis;
import com.googlecode.gwt.charts.client.options.Legend;
import com.googlecode.gwt.charts.client.options.LegendPosition;
import com.googlecode.gwt.charts.client.options.VAxis;

public class ChartClickHandler
{
	private String myCheckIns;
	private ColumnChart chart;
	private FlowPanel myResultsPanel;
	private int page = 0;
	private final ArrayList<DataTable> charts = new ArrayList<>();
	private final ColumnChartOptions options = ColumnChartOptions.create();
	private final static String[] DAYS = { "Sunday", "Monday", "Tuesday","Wednesday", "Thursday", "Friday", "Saturday"};

	public ChartClickHandler(String checkIns, FlowPanel resultsPanel) {
		this.myCheckIns = checkIns;
		this.myResultsPanel = resultsPanel;

		ChartLoader chartLoader = new ChartLoader(ChartPackage.CORECHART);
		chartLoader.loadApi(new Runnable() {

			@Override
			public void run() {
				chart = new ColumnChart();
				chart.setStyleName("chart");
				myResultsPanel.add(chart);

				HashMap<String,Map<String,String>> temp = new HashMap<>();
				
				String[] splitCheckIns = myCheckIns.replace("[", "").replace("]", "").split("/");
				
				for (int j = 0; j < splitCheckIns.length; j++) {
					String[] dayHour = splitCheckIns[j].split("-");
					String day = dayHour[0];
					String[] hourValue = dayHour[1].split(",");
					HashMap<String,String> perHour = new HashMap<>();
					for (int i = 0; i < hourValue.length; i++) {
						String[] splitEq = hourValue[i].split("=");
					
						String hour = splitEq[0].replace(":00", "").trim();
						perHour.put(hour,splitEq[1]);
					}
					temp.put(day, perHour);
				}
				HashMap<String,Map<String,String>> checkInsByHour = new HashMap<>();
				for(int i=0;i<DAYS.length;i++)
				{
					checkInsByHour.put(DAYS[i],temp.get(DAYS[i]));
				}

				for(String days : checkInsByHour.keySet())
				{
					Map<String,String> hours = checkInsByHour.get(days);
					
					if (hours != null) {
						DataTable colNewData = DataTable.create();
						colNewData.addColumn(ColumnType.STRING, "Name");
						colNewData.addColumn(ColumnType.NUMBER, "Check-ins");
						for (String item : hours.keySet()) {
							colNewData.addRow(item, Integer.parseInt(hours.get(item)));
						}
						charts.add(colNewData);
					}
				}
				
				options.setFontName("Tahoma");
				options.setTitle("Check-ins by Day");
				options.setHAxis(HAxis.create("Hour"));
				options.setVAxis(VAxis.create("Check-ins"));
				Animation animation = Animation.create();
				animation.setDuration(500);
				AnimationEasing easing = AnimationEasing.LINEAR;
				animation.setEasing(easing);;
				options.setAnimation(animation);
				Bar bar = Bar.create();
				bar.setGroupWidth("50%");
				options.setBar(bar);
				options.setDataOpacity(0.7);
				Legend lg = Legend.create();
				lg.setPosition(LegendPosition.NONE);
				options.setLegend(lg);
				showNextChart();
			}
		});
	}
	
	public int showNextChart() {
		page++;
		if(page < charts.size())
		{
			chart.draw(charts.get(page), options);
		}
		else
		{
			page = 0;
			chart.draw(charts.get(page), options);
		}
		return page;
	}
	
	public int showPreviousChart() {
		page--;
		if(page < 0){
			page = charts.size();
			chart.draw(charts.get(page), options);
		}
		if(page < charts.size())
		{
			chart.draw(charts.get(page), options);
		}
		return page;
	}
	
	public void getChart(int day)
	{
		chart.draw(charts.get(day), options);
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int pageNo)
	{
		page = pageNo;
	}
}