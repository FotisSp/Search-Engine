package com.search_engine.application.client;

import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.search_engine.application.click_handlers.ChartClickHandler;
import com.search_engine.application.shared.Review;

public class PopUpDialogs 
{
	private static final Double TOTALPROCESSES = 2435672.0;
	private static final Double PERCENTAGE = 100.0;
	
	private static Button closeButton = new Button("X");
	private static final HTML serverResponseLabel = new HTML();
	private DialogBox progressBox;
	private FlowPanel stepBusiness;
	private FlowPanel stepCheckIn;
	private FlowPanel stepIndex;
	private FlowPanel stepReviews;
	private FlowPanel stepDone;
	private HTML percent;
	private FlowPanel percentHTML = new FlowPanel();

	public void createErrorBox(String message, String title, String header)
	{
		serverResponseLabel.addStyleName("serverResponseLabelError");
		serverResponseLabel.setHTML(message);

		final DialogBox errorDialogBox = new DialogBox();
		errorDialogBox.setStyleName("gwt-DialogBox");
		errorDialogBox.setText(title);
		errorDialogBox.setAnimationEnabled(true);
		errorDialogBox.setGlassEnabled(true);

		closeButton.getElement().setId("closeButton");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.setStyleName("dialogVPanel"); 
		dialogVPanel.add(new HTML("<br><b>"+header+"</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		errorDialogBox.setWidget(dialogVPanel);
		errorDialogBox.center();
		closeButton.setFocus(true);

		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				errorDialogBox.hide();
			}
		});
	}
	
	public void createProgressBox()
	{
		progressBox = new DialogBox();
		progressBox.setStyleName("ProgressBox");
		
		FlowPanel percentageContainer = new FlowPanel();
		percentageContainer.setStyleName("percentageContainer");
		percent = new HTML("0%");
		percent.setStyleName("percent");
		percentHTML.add(percent);
		percentHTML.setStyleName("percentHTML");
		percentageContainer.add(new HTML("<div class=\"loader\">"
				+"<svg class=\"circular\" viewBox=\"25 25 50 50\">"
				+"<circle class=\"path\" cx=\"50\" cy=\"50\" r=\"20\" fill=\"none\" stroke-width=\"2\" stroke-miterlimit=\"10\"/>"
				+"</svg>"
				+"</div>"));
		percentageContainer.add(percentHTML);
		
		FlowPanel progressPanel = new FlowPanel();
		progressPanel.setStyleName("progressPanel");
		
		stepBusiness = new FlowPanel();
		stepBusiness.setStyleName("step business");
		HTML business = new HTML("<div class=\"step-progress\"></div>"
			    +"<div class=\"icon-wrapper\">"
			     +"<svg class=\"icon icon-checkmark\" viewBox=\"0 0 32 32\"><path class=\"path1\" d=\"M27 4l-15 15-7-7-5 5 12 12 20-20z\"></path>  </svg>"
			      +"<div class=\"step-text\">Business File Parsed</div>"
			    +"</div>");
		stepBusiness.add(business);
		
		stepCheckIn = new FlowPanel();
		stepCheckIn.setStyleName("step checkin");
		HTML checkIns = new HTML("<div class=\"step-progress\"></div>"
			    +"<div class=\"icon-wrapper\">"
			      +"<svg class=\"icon icon-checkmark\" viewBox=\"0 0 32 32\"><path class=\"path1\" d=\"M27 4l-15 15-7-7-5 5 12 12 20-20z\"></path>  </svg>"
			      +"<div class=\"step-text\">Check-Ins File Parsed</div>"
			    +"</div>");
		stepCheckIn.add(checkIns);

		stepIndex = new FlowPanel();
		stepIndex.setStyleName("step index");
		HTML index = new HTML("<div class=\"step-progress\"></div>"
			    +"<div class=\"icon-wrapper\">"
			      +"<svg class=\"icon icon-checkmark\" viewBox=\"0 0 32 32\"><path class=\"path1\" d=\"M27 4l-15 15-7-7-5 5 12 12 20-20z\"></path>  </svg>"
			      +"<div class=\"step-text\">Index Created</div>"
			    +"</div>");
		stepIndex.add(index);

		stepReviews = new FlowPanel();
		stepReviews.setStyleName("step reviews");
		HTML reviews = new HTML("<div class=\"step-progress\"></div>"
			    +"<div class=\"icon-wrapper\">"
			      +"<svg class=\"icon icon-checkmark\" viewBox=\"0 0 32 32\"><path class=\"path1\" d=\"M27 4l-15 15-7-7-5 5 12 12 20-20z\"></path>  </svg>"
			      +"<div class=\"step-text\">Reviews File Parsed</div>"
			    +"</div>");
		stepReviews.add(reviews);

		stepDone = new FlowPanel();
		stepDone.setStyleName("step donedone");
		HTML done = new HTML("<div class=\"icon-wrapper\">"
			      +"<svg class=\"icon icon-checkmark\" viewBox=\"0 0 32 32\"><path class=\"path1\" d=\"M27 4l-15 15-7-7-5 5 12 12 20-20z\"></path>  </svg>"
			      +"<div class=\"step-text\">Finalizing</div>"
			    +"</div>");
		stepDone.add(done);
		
		progressPanel.add(stepBusiness);
		progressPanel.add(stepCheckIn);
		progressPanel.add(stepIndex);
		progressPanel.add(stepReviews);
		progressPanel.add(stepDone);
		percentageContainer.add(progressPanel);
		progressBox.add(percentageContainer);
		progressBox.center();
	}
	
	public void setPercentage(int value)
	{
		Double percentage = value/TOTALPROCESSES * PERCENTAGE;
		percent = new HTML(percentage.intValue()+"%");
		percentHTML.clear();
		percentHTML.add(percent);
	}
	
	public void setBusinessProgress()
	{
		String name = stepBusiness.getStyleName();
		stepBusiness.setStyleName(name+" done");
	}
	
	public void setCheckInProgress()
	{
		String name = stepCheckIn.getStyleName();
		stepCheckIn.setStyleName(name+" done");
	}
	
	public void setIndexProgress()
	{
		String name = stepIndex.getStyleName();
		stepIndex.setStyleName(name+" done");
	}
	
	public void setReviewsProgress()
	{
		String name = stepReviews.getStyleName();
		stepReviews.setStyleName(name+" done");
	}
	
	public void setDoneProgress()
	{
		String name = stepDone.getStyleName();
		stepDone.setStyleName(name+" done");
	}
	
	public void destroyProgress()
	{
		progressBox.clear();
		progressBox.hide();
	}
	
	public void createDialogBox(HashMap<Integer, Review> result, String checkIns)
	{
		HTMLBuilder builder = new HTMLBuilder();
		builder.buildReviewResults(result);

		final DialogBox dialogBox = new DialogBox();
		dialogBox.setStyleName("successDialogPanel");
		dialogBox.setGlassEnabled(true);	
		
		closeButton.getElement().setId("closeButton");
		VerticalPanel dialogVPanel = new VerticalPanel();
		
		if(!checkIns.isEmpty())
		{
			FlowPanel ckeckInChart = new FlowPanel();	
			final ChartClickHandler chartClkHandler = new ChartClickHandler(checkIns,ckeckInChart);

			final ListBox lb = new ListBox();
			lb.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					chartClkHandler.getChart(lb.getSelectedIndex());
					chartClkHandler.setPage(lb.getSelectedIndex());
				}
			});
			lb.addItem("Sunday");
			lb.addItem("Monday");
			lb.addItem("Tuesday");
			lb.addItem("Wednesday");
			lb.addItem("Thursday");
			lb.addItem("Friday");
			lb.addItem("Saturday");
			lb.setSelectedIndex(1);

			Button leftChartButton = new Button("",new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					int page = chartClkHandler.showPreviousChart();
					if(page < 7)
					{
						lb.setSelectedIndex(page);
					}
				}
			});
			Button rightChartButton = new Button("",new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					int page = chartClkHandler.showNextChart();
					if(page < 7)
					{
						lb.setSelectedIndex(page);
					}
				}
			});

			leftChartButton.setStyleName("leftChartButton");
			rightChartButton.setStyleName("rightChartButton");
			HorizontalPanel hPanel = new HorizontalPanel();
			hPanel.add(leftChartButton);
			hPanel.add(ckeckInChart);
			hPanel.add(rightChartButton);
			hPanel.setStyleName("topResultsPanel");
			dialogVPanel.add(lb);
			dialogVPanel.add(hPanel);
		}

		FlowPanel results = new FlowPanel();
		results.setStyleName("reviewResults");
		for(Integer id : result.keySet()) {
			HTML box = builder.reviewsToHTML(id);
			box.setStyleName("resultBox hvr-grow-shadow");
			results.add(box);
		}
		dialogVPanel.setSize("100%", "100%");
		dialogVPanel.add(results);
		
		ScrollPanel panel = new ScrollPanel();
		panel.add(dialogVPanel);

		VerticalPanel vPanel = new VerticalPanel();
		
		vPanel.add(panel);
		vPanel.add(closeButton);
		
		dialogBox.add(vPanel);
		dialogBox.center();
		closeButton.setFocus(true);

		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.setAnimationEnabled(true);
				dialogBox.hide();
			}
		});
	}
}
