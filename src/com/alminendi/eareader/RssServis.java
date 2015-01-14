package com.alminendi.eareader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;
import android.widget.Toast;

public class RssServis extends IntentService {
	public static String rssurl ;
	
	public static final String RSS_LINK = rssurl;//"
	public static final String ITEMS = "items";
	public static final String RECEIVER = "receiver";

	public RssServis() {
		super("RssServis");
	}
    
	@Override
	public void onHandleIntent(Intent intent) {
		String url = intent.getStringExtra(RSS_LINK);
		Log.i("šta je url", url);
		List<RssItem> rssObjekti = null;
		try {
		   PcWorldRssParser parser = new PcWorldRssParser();
		   rssObjekti = parser.parse(getInputStream(url));
		   Log.i("Service URL:",url);
		   Log.i("rssitems","" + rssObjekti.size());
		} catch (XmlPullParserException e) {
			Log.w(e.getMessage(), e);
		} catch (IOException e) {
			Log.w(e.getMessage(), e);
		}
		Bundle bundle = new Bundle();
		bundle.putSerializable(ITEMS, (Serializable) rssObjekti);
		ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
		receiver.send(0, bundle);
	}

	public InputStream getInputStream(String link) {
	
		try {
			URL url = new URL(link);			
			return url.openConnection().getInputStream();
			
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(),"Url koji pokušavate otvoriti nije taèan,unesite taèan url!", 
	                Toast.LENGTH_SHORT).show();
			Log.i("Return null", link);
			return null;
		}
	}
}
