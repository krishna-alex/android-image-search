package com.codepath.imagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ImageSearchActivity extends Activity {
	
	Button btnSearch;
	GridView gvResults;
	TextView etQuery;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	String imageSize;
	String imageColor;
	String imageType;
	private final int REQUEST_CODE = 20;
	EndlessScrollListener scrollListener;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_search);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
												
		});
		this.scrollListener = new EndlessScrollListener( ) {
			@Override
		    public void onLoadMore( int start ) {
				Log.d("DEBUG", "Inside listener");
	        	googleImageSearch( gvResults.getCount() ); 
		    }
        };        
		gvResults.setOnScrollListener( this.scrollListener );
	}
	
	public void onSettingsClick(MenuItem mi) {
	//	Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(getBaseContext(), SettingsActivity.class);
		startActivityForResult(i, REQUEST_CODE);
	
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("DEBUG", "Inside onActivityResult");
	  if (resultCode == RESULT_OK) {
	     imageSize = data.getExtras().getString("imSize");
	     imageColor = data.getExtras().getString("imColor");
	     imageType = data.getExtras().getString("imType");
	     Toast.makeText(this, "Toast" + imageSize + imageColor + imageType, Toast.LENGTH_SHORT).show();
	  }
	} 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_search, menu);
		return true;
	}
	
	public void setupViews() {
		btnSearch = (Button) findViewById(R.id.btnSearch);
		gvResults = (GridView) findViewById(R.id.gvResults);
		etQuery = (TextView) findViewById(R.id.etQuery); 
	}
	
	public void googleImageSearch(int start) {
		String query = etQuery.getText().toString();
		//Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		Log.d("DEBUG", "before client");
		client.get("https://ajax.googleapis.com/ajax/services/search/images?" + 
		"rsz=8&start=" + start + "&imgcolor=" + imageColor + "&imgsz=" + imageSize +
		"&imgtype=" + imageType +"&v=1.0&q=" + Uri.encode(query),
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONObject response) {
						JSONArray imageJsonResults = null;
						Log.d("DEBUG", "inside onsuccess before try");
						Log.d("DEBUG", response.toString());
						try {
							int total = response.getJSONObject("responseData"
									).getJSONObject("cursor").getInt("estimatedResultCount");
							scrollListener.setTotal( total );
							imageJsonResults = response.getJSONObject(
									"responseData").getJSONArray("results");
							imageAdapter.addAll(ImageResult.
									fromJSONArray(imageJsonResults));
							Log.d("DEBUG", "Image Result" + imageResults.toString());
						} catch (JSONException e) {
							Log.d("DEBUG", "inside catch" + e.toString());
							e.printStackTrace();
						}		
					}

					@Override
					public void onFailure(Throwable arg0, JSONArray arg1) {
						Log.d("DEBUG", arg1.toString());
						super.onFailure(arg0, arg1);
					}

					@Override
					public void onFailure(Throwable arg0, JSONObject arg1) {
						Log.d("DEBUG", arg1.toString());
						super.onFailure(arg0, arg1);
					}
			});
				
	}
	public void onImageSearch(View v) {
		imageResults.clear();
		googleImageSearch( 0 );		
	}
} 


