package com.codepath.imagesearch;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Spinner;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	public void onSave(View sv) {
		Spinner spnrSize = (Spinner)findViewById(R.id.spnrSize);
		Spinner spnrColor = (Spinner)findViewById(R.id.spnrColor);
		Spinner spnrType = (Spinner)findViewById(R.id.SpnrType); 
		
		//String image_size = spnrSize.getSelectedItem().toString();
		//String image_color = spnrColor.getSelectedItem().toString();
		//String image_type = spnrType.getSelectedItem().toString();
		//Toast.makeText(this, "Image Properties " + image_size + image_color + image_type, Toast.LENGTH_SHORT).show();
		Log.d("DEBUG", "Inside onSave");
		Intent data = new Intent();
		data.putExtra("imSize", spnrSize.getSelectedItem().toString());
		data.putExtra("imColor", spnrColor.getSelectedItem().toString());
		data.putExtra("imType", spnrType.getSelectedItem().toString());
		setResult(RESULT_OK,data);
		Log.d("DEBUG", "Inside onSave1");
		finish();
		
	}

}
