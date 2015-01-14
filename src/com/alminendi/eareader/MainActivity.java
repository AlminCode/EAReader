package com.alminendi.eareader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Global;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	//String listItems[] = { "http//www.klix.ba/rss/vijesti/svijet", "https//www.pcworld.com/index.rss", "https//www.reddit.com/r/news/.rss" };
	public static final int DODAJ_STRANICU_REQUEST=0;
	public static final String NEKI_STRING="s";
	public static final String URL_STRANICE="url";
	String filename = "Links";
	public static List<RssItem> listItems=new ArrayList<RssItem>();
	//RssItem FiksanLink0=new RssItem("Klix vijesti svijet", "http://www.klix.ba/rss/vijesti/svijet");
	//RssItem FiksanLink1=new RssItem("Klix vijesti sport", "http://www.klix.ba/rss/sport");
	//RssItem FiksanLink2=new RssItem("PCWorld", "http://www.pcworld.com/index.rss");
	
	public static ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ReadList();
		//listItems.add(FiksanLink0);
		//listItems.add(FiksanLink1);
		//listItems.add(FiksanLink2);
	    setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(android.R.id.list);
		
		ArrayAdapter<RssItem> adapter = new ArrayAdapter<RssItem>(this,
				android.R.layout.simple_list_item_1, listItems);
		lv.setAdapter(adapter);
		 lv.setOnItemLongClickListener(new OnItemLongClickListener() {
		 
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			listItems.remove(position);
			lv.invalidateViews();
				return true;
			}});
		    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
		    Intent intent=new Intent(getApplicationContext(),RssActivity.class);
		    intent.putExtra(NEKI_STRING,listItems.get(position).getLink().toString());
		    startActivity(intent);
			}
			 
		});
	}

	

	private void ReadList() {
		if( listItems != null)
            listItems.clear();
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    openFileInput(filename)));
            String inputString;
            //StringBuffer stringBuffer = new StringBuffer(); - never used, commented if needed later
            //kontrolni brojac da odredimo shta citamo
            int kontroler = 0;
            
            RssItem tempProizvodi = new RssItem("Naslov","Url");
           
            while ((inputString = inputReader.readLine()) != null) {
                kontroler++;
                if (kontroler == 1)
                    tempProizvodi.title = inputString;
                if (kontroler == 2)
                    tempProizvodi.link = inputString;
               
                    listItems.add(tempProizvodi);
                    tempProizvodi = new RssItem("Naslov","Url");
                    kontroler = 0;
                }
            }
            catch (IOException e) {
            e.printStackTrace();
            }
        
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		boolean ItemIzabran=false;
		switch(item.getItemId()){
		case R.id.dodaj_stranicu:
			Intent intent=new Intent(getApplicationContext(), DodavanjeStranice.class);
		//intent.putParcelableArrayListExtra
			startActivityForResult(intent, DODAJ_STRANICU_REQUEST);
			break;
	
		}
		return ItemIzabran;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
	  if (resultCode == RESULT_OK && requestCode == DODAJ_STRANICU_REQUEST) {
	    String naslov1=resultIntent.getStringExtra(DodavanjeStranice.NASLOV_STRANICE);
	    String url1=resultIntent.getStringExtra(DodavanjeStranice.URL_STRANICE);
	    RssItem Novi=new RssItem(naslov1, url1);
	    Toast.makeText(getApplicationContext(),resultIntent.getStringExtra(DodavanjeStranice.NASLOV_STRANICE),
                Toast.LENGTH_SHORT).show();
	    lv.invalidateViews();
	    listItems.add(Novi);
	    
	  }
	} 


	
	
	
}
