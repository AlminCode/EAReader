package com.alminendi.eareader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.app.backup.SharedPreferencesBackupHelper;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Global;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DodavanjeStranice extends Activity{
	String filename = "Links";
	public static final String NASLOV_STRANICE="neki naslov";
	public static final String URL_STRANICE="neki url";
	private EditText naslovStranice;
	private EditText urlStranice;
	private Button btnDodaj;
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
				SaveList();
				finish();
				
			
			}

			private void SaveList() {
				 FileOutputStream outputStream;
			        //snimanje proizvoda
			        try {
			            outputStream = openFileOutput(filename, getApplicationContext().MODE_PRIVATE);
			            for (int i = 0; i < MainActivity.listItems.size(); i++) {
			                outputStream.write(String.valueOf( MainActivity.listItems.get(i).getTitle() + "\n").getBytes());
			                outputStream.write(String.valueOf( MainActivity.listItems.get(i).getLink() + "\n").getBytes());
			                
			            }
			            outputStream.close();
			        } catch (Exception e) {
			            e.printStackTrace();
			        }

		
				
			}
		});
		
	}
}
