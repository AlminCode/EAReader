package com.alminendi.eareader;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.widget.Toast;
 


public class RssActivity extends FragmentActivity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);

		if (savedInstanceState == null) {
			addRssFragment();
		}
	
		//Intent i=getIntent();		
		//String s= i.getStringExtra(MainActivity.NEKI_STRING);
		
		}
	

	private void addRssFragment() {
		Bundle b=new Bundle();		
		b.putString("url-stranice", getIntent().getStringExtra(MainActivity.NEKI_STRING));
		 android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
		 android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
		RssFragment fragment = new RssFragment();
		fragment.urlStranice = getIntent().getStringExtra(MainActivity.NEKI_STRING);
		fragment.setArguments(b);
		transaction.add(R.id.fragment_container, fragment);
		transaction.commit();
	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean("fragment_added", true);
	}
}
