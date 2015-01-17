package com.alminendi.eareader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings.Global;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DodavanjeStranice extends Activity{
	String filename = "list";
	public static final String NASLOV_STRANICE="neki naslov";
	public static final String URL_STRANICE="neki url";
	private EditText naslovStranice;
	private EditText urlStranice;
	private Button btnDodaj;	
	
	
	
	
	
	
	 @SuppressLint("WorldReadableFiles")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stranica_dodavanje);
		
		naslovStranice=(EditText)findViewById(R.id.upis_naslov_stranice);
		urlStranice=(EditText)findViewById(R.id.upis_url_stranice);
		
		btnDodaj=(Button)findViewById(R.id.dodaj_stranicu);
		btnDodaj.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				Intent intent = getIntent();				 
				intent.putExtra(NASLOV_STRANICE, naslovStranice.getText().toString());
				intent.putExtra(URL_STRANICE, urlStranice.getText().toString());
				setResult(RESULT_OK, intent);
				//for(Object i : MainActivity.listItems)	
				
					//String naslov = String.valueOf(naslovStranice.getText());
					//String url = String.valueOf(urlStranice.getText());
					//RssItem a=  new RssItem(naslov,url);
					//MainActivity.listItems.add(a);
				
				
				
			    //SaveList();
			    Toast.makeText(getApplicationContext(),"List Saved !",Toast.LENGTH_LONG).show();
				//ClearList();
				//ReadList();
				finish();
				
			
			}


			private void ClearList() {
					MainActivity.listItems.clear();
				
				
			}
			private boolean ReadList() {
				
			        try {
			            BufferedReader inputReader = new BufferedReader(new InputStreamReader(openFileInput(filename)));
			            String inputString;
			            //StringBuffer stringBuffer = new StringBuffer(); - never used, commented if needed later
			            //kontrolni brojac da odredimo shta citamo
			            int kontroler = 0;
			            RssItem tempItem = new RssItem(" ", " ");

			            while ((inputString = inputReader.readLine()) != null) {            	
			                kontroler++;	                
			                if (kontroler == 1)
			                	tempItem.title = inputString;
			                if (kontroler == 2)
			                	tempItem.link = inputString;
			                if(kontroler == 3)
			                {
			                	MainActivity.listItems.add(tempItem);
			                tempItem = new RssItem("aaa","aaa");
			                kontroler = 0;
			                }
			                   
			                }
			            
			        } catch (IOException e) {
			            e.printStackTrace();
			            return false;
			        }
			       
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
		});
		
	}
}
