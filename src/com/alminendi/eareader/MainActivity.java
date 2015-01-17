package com.alminendi.eareader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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
	public static List<RssItem> list = new ArrayList<RssItem>();
	public static List<RssItem> listItems=new ArrayList<RssItem>();
	//RssItem FiksanLink0=new RssItem("Klix vijesti svijet", "http://www.klix.ba/rss/vijesti/svijet");
	//RssItem FiksanLink1=new RssItem("Klix vijesti sport", "http://www.klix.ba/rss/sport");
	//RssItem FiksanLink2=new RssItem("PCWorld", "http://www.pcworld.com/index.rss");
	//Save file
	
	String filename = "list";
	//String string = "Hello world!";
	FileOutputStream outputStream;
	
	public static ListView lv;

	@SuppressWarnings("null")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		//readFromFile();
		//listItems.add(FiksanLink0);		
		//listItems.add(FiksanLink1);
		//listItems.add(FiksanLink2);
	    setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(android.R.id.list);		
		ArrayAdapter<RssItem> adapter = new ArrayAdapter<RssItem>(this,
				android.R.layout.simple_list_item_1, listItems);
		lv.setAdapter(adapter);	
		
		
		ReadList();
		//ReadStuff();
		 lv.setOnItemLongClickListener(new OnItemLongClickListener() {			  
			 
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,int position, long id) {				  
			listItems.remove(position);
			lv.invalidateViews();
			SaveList();
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

	
	
	
	private void ReadStuff() {
		  try {
	            // open the file for reading we have to surround it with a try
	       
	            InputStream instream = openFileInput(filename);//open the text file for reading
	           
	            // if file the available for reading
	            if (instream != null) {               
	               
	              // prepare the file for reading
	              InputStreamReader inputreader = new InputStreamReader(instream);
	              BufferedReader buffreader = new BufferedReader(inputreader);
	              RssItem tempItem = new RssItem("asd", "asd");
					
					int kontroler = 0;
	              String line=null;
	              //We initialize a string "line"
	             
	            while (( line = buffreader.readLine()) != null) {
	                //buffered reader reads only one line at a time, hence we give a while loop to read all till the text is null
		                if (kontroler == 1)
		                	tempItem.title = line;
		                if (kontroler == 2)
		                	tempItem.link = line;
		                kontroler++;	
		                if(kontroler == 3)
		                {
		                listItems.add(tempItem);
		                tempItem = new RssItem("aaa","aaa");
		                kontroler = 0;
		                }
	           
	                   
	              }}}   
	               
	             //now we have to surround it with a catch statement for exceptions
	            catch (IOException e) {
	                e.printStackTrace();
	            }
		
	}

	private Object readFromFile() {
		String ret = "";

	    try {
	        InputStream inputStream = openFileInput("myfile");

	        if ( inputStream != null ) {
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String receiveString = "";
	            StringBuilder stringBuilder = new StringBuilder();

	            while ( (receiveString = bufferedReader.readLine()) != null ) {
	                stringBuilder.append(receiveString);
	            }

	            inputStream.close();
	            ret = stringBuilder.toString();
	        }
	    }
	    catch (FileNotFoundException e) {
	        Log.e("login activity", "File not found: " + e.toString());
	    } catch (IOException e) {
	        Log.e("login activity", "Can not read file: " + e.toString());
	    }
	    
	    return ret;
		
	}


	private boolean ReadList() {
		  
	        try {
	            BufferedReader inputReader = new BufferedReader(new InputStreamReader(openFileInput(filename)));
	            String inputString;
	            //StringBuffer stringBuffer = new StringBuffer(); - never used, commented if needed later
	            //kontrolni brojac da odredimo shta citamo
	            int kontroler = 0;
	            RssItem tempItem = new RssItem("111", "111");

	            while ((inputString = inputReader.readLine()) != null) {            	
	            	 kontroler++;
	            
	                if (kontroler == 1)
	                	tempItem.title = inputString;
	                
	                
	                if (kontroler == 2){  
	                	tempItem.link = inputString;
	                    listItems.add(tempItem);
	                    tempItem = new RssItem("xxx","xxx");
	                    kontroler = 0;
	            }
	                
	                
	                }
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }
	        return true;  
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void SaveList() {
		  FileOutputStream outputStream;
	        //snimanje proizvoda
	        try {
	            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
	            for (int i = 0; i < MainActivity.listItems.size(); i++) {
	                outputStream.write(String.valueOf(MainActivity.listItems.get(i).getTitle() + "\n").getBytes());
	                outputStream.write(String.valueOf(MainActivity.listItems.get(i).getLink() + "\n").getBytes());
	               
	            }
	            outputStream.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }      
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
	  SaveList();
	  }
	} 


	
	
	
}
