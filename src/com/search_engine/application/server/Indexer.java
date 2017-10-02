package com.search_engine.application.server;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.json.JSONException;
import org.json.JSONObject;

import com.search_engine.application.shared.Business;
import com.search_engine.application.shared.Review;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class Indexer
{
	private final static String[] DAYS = { "Sunday", "Monday", "Tuesday","Wednesday", "Thursday", "Friday", "Saturday"};

	private Path indexPath;
	private Path reviewPath;	
	private HashMap<String, Business> businessMap;
	private Directory indexDir;
	private Directory reviewDir;
	private IndexWriter indexWriter = null;
	private IndexWriter reviewWriter = null;
	private int progress = 0;

	public void indexer() throws IOException, ParseException, JSONException
	{
		indexPath = Paths.get("index");
		reviewPath = Paths.get("index/reviews");
		if(!Files.exists(indexPath) && !Files.exists(reviewPath))
		{
			long tStart = System.currentTimeMillis();
			
			businessParser("yelp_academic_dataset_business.json");		//77445
			parseCheckIns("yelp_academic_dataset_checkin.json");		//55569
			createIndex();												//77445
			indexWriter.close();
			indexDir.close();
			reviewsParser("yelp_academic_dataset_review.json");			//2225213
			
			long tEnd = System.currentTimeMillis();
			long tDelta = tEnd - tStart;
			double elapsedSeconds = tDelta / 1000.0;
			System.out.println("--- Elapsed time : "+elapsedSeconds+" ---");
																		 //total processes : 2435672
		}
	}
	
	public void businessParser(String businessPath) throws IOException, JSONException {

		InputStream fis = new FileInputStream(businessPath);
		InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		BufferedReader br = new BufferedReader(isr);

		String line;
		businessMap = new HashMap<>();
		while ((line = br.readLine()) != null) {
			progress++;
			
			JSONObject business = new JSONObject(line);
			String name = null;
			String city = null;
			String state = null;
			Double longitude = null;
			Double latitude = null;
			String stars = null;
			int reviewCount = 0;
			String categories = null;
			String businessId = null;

			name = (String) business.get("name");
			name = name.replace("\t", " ");

			String address = (String) business.get("full_address");
			address = address.replace("\n", " ");
			address = address.replace("\r", " ");
			address = address.replace("\t", " ");

			city = (String) business.get("city");

			state = (String) business.get("state");

			longitude = (Double) business.get("longitude");

			latitude = (Double) business.get("latitude");

			stars = business.get("stars").toString();

			reviewCount = (Integer)business.get("review_count");

			categories = business.get("categories").toString();

			businessId = business.get("business_id").toString();
			Business info = new Business();
			info.construct(name, address, city, state, categories, reviewCount, stars, longitude,latitude,businessId, "");
			businessMap.put(businessId, info);
		}
		fis.close();
		isr.close();
		br.close();
	}
	
	public void reviewsParser(String reviewsPath) throws JSONException, IOException
	{
		InputStream fis = new FileInputStream(reviewsPath);
		InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(isr);
		
		reviewDir = FSDirectory.open(new File(reviewPath.toString()).toPath());
		reviewWriter = new IndexWriter(reviewDir, new IndexWriterConfig(new StandardAnalyzer()).setOpenMode(OpenMode.CREATE));
		
		String line;
		while ((line = br.readLine()) != null) {
			progress++;
			JSONObject reviewsObj = new JSONObject(line);
			Review review = new Review();
			
			String date = reviewsObj.get("date").toString();
			String text = reviewsObj.get("text").toString();
			String stars = reviewsObj.get("stars").toString();
			String businessId = reviewsObj.get("business_id").toString();
			
			review.constructReview(text, date, stars, businessId);
			reviewIndexer(review);
		}

		reviewWriter.close();
		reviewDir.close();
	}
	
	public void parseCheckIns(String checkInFile) throws IOException, ParseException, JSONException {
		
		InputStream fis = new FileInputStream(checkInFile);
		InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(isr);
		String day = "";
		
		String line;
		while ((line = br.readLine()) != null) {
			progress++;
			JSONObject checkInObj = new JSONObject(line);
			String businessId = checkInObj.get("business_id").toString();
			String checkIns = checkInObj.get("checkin_info").toString();
			
			checkIns = checkIns.replace("{", "");
			checkIns = checkIns.replace("}", "");
			checkIns = checkIns.replace("\"", "");
			
			String[] split = checkIns.split(",");
			HashMap<String,ArrayList<String>> checkInsByHour = new HashMap<>();

			for(int i =0;i<split.length;i++)
			{			
				ArrayList<String> hoursByDay = new ArrayList<>();
				String[] byHour = split[i].split("-");
				String[] byDay = byHour[1].split(":");
				day = DAYS[Integer.parseInt(byDay[0])];
				
				if(checkInsByHour.containsKey(day))
				{
					hoursByDay = checkInsByHour.get(day);
					hoursByDay.add(byHour[0]+":00="+byDay[1]);
				}
				else
				{
					hoursByDay.add(byHour[0]+":00="+byDay[1]);
				}
				checkInsByHour.put(day, hoursByDay);

			}
			businessMap.get(businessId).addCheckIn(checkInsByHour);
		}
    }
	
	public void reviewIndexer(Review review) throws IOException
	{
		Document doc = new Document();
		doc.add(new TextField("Text", review.getText(), Field.Store.YES));
		doc.add(new TextField("Date", review.getDate(), Field.Store.YES));
		doc.add(new TextField("Stars", review.getStars(), Field.Store.YES));
		doc.add(new TextField("Business Id", review.getBusinessId(), Field.Store.YES));
		
		reviewWriter.addDocument(doc);
	}

	@SuppressWarnings("deprecation")
	public void createIndex() throws IOException
	{
		indexDir = FSDirectory.open(new File(indexPath.toString()).toPath());

		indexWriter = new IndexWriter(indexDir, new IndexWriterConfig(new SimpleAnalyzer()).setOpenMode(OpenMode.CREATE));
		for(String id : businessMap.keySet())
		{
			progress++;
			String name = businessMap.get(id).getName();
			String fullAddress = businessMap.get(id).getFullAddress();
			String city = businessMap.get(id).getCity();
			String state = businessMap.get(id).getState();
			Double longitude = businessMap.get(id).getLongitude();
			Double latitude = businessMap.get(id).getLatitude();
			String stars = businessMap.get(id).getStars();
			int reviewCount = businessMap.get(id).getReviewCount();
			String categories = businessMap.get(id).getCategory();
			String businessId = businessMap.get(id).getBusinessId();
			String checkin = businessMap.get(id).checkInsToString();

			Document doc = new Document();
			doc.add(new TextField("Name", name, Field.Store.YES));
			doc.add(new TextField("Full Address", fullAddress, Field.Store.YES));
			doc.add(new StringField("City", city, Field.Store.YES));
			doc.add(new TextField("State", state, Field.Store.YES));
			doc.add(new StringField("Categories", categories, Field.Store.YES));
			doc.add(new LegacyIntField("Review Count", reviewCount,Field.Store.YES));
			doc.add(new StringField("Stars", stars, Field.Store.YES));
			doc.add(new LegacyDoubleField("Longitude", longitude,Field.Store.YES));
			doc.add(new LegacyDoubleField("Latitude", latitude,Field.Store.YES));
			doc.add(new StringField("Business Id", businessId, Field.Store.YES));
			doc.add(new StringField("CheckIns",checkin,Field.Store.YES));

			String fullSearchableText = name+" "+city+" "+state+" "+fullAddress;
			doc.add(new TextField("content",fullSearchableText, Field.Store.NO));
			indexWriter.addDocument(doc);
		}
	}
	
	public int getProgress()
	{
		if(progress == 0)
		{
			return -1;
		}
		return progress;
	}
}
