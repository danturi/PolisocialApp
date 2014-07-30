package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.foursquare.foursquareendpoint.Foursquareendpoint;
import it.polimi.dima.polisocial.foursquare.foursquareendpoint.model.ResponseObject;

import java.io.IOException;
import java.util.ArrayList;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class FoursquareActivity extends Activity {
	
	ListView listVenues;
	ArrayAdapter<String> adapter = null;
	ArrayList<String> list = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foursquare);
		
	 /*	if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new SearchFragment()).commit();
		}*/
		
	    listVenues = (ListView) findViewById(R.id.listViewVenues);
	    
		Button button= (Button)findViewById(R.id.buttonSearch);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new SearchVenuesNearPoliTask().execute();
				
			}
		});

		Button launchOauth = (Button) findViewById(R.id.btn_launch_oauth);
		launchOauth.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				
				startActivity(new Intent().setClass(v.getContext(),OAuthAccessActivity.class));
			
			}
		});
	
		Button clearCredentials = (Button) findViewById(R.id.btn_clear_credentials);
		clearCredentials.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//clearCredentials();
				//new PerformApiCallTask().execute();
			}

		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.foursquare, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class SearchVenuesNearPoliTask extends AsyncTask<Void, Void, ResponseObject> {

		@Override
		protected ResponseObject doInBackground(Void... params) {
			
			Foursquareendpoint.Builder builder = new Foursquareendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
			builder = CloudEndpointUtils.updateBuilder(builder);
			Foursquareendpoint endpoint = builder.setApplicationName("polimisocial").build();
			
			
			ResponseObject result = new ResponseObject();
			
			String ll = "45.478178,9.228031"; 
			
			try {
				 result = endpoint.searchVenues(ll).execute();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				result = null;
			}
			return result;
		}
		
		@SuppressWarnings("unchecked")
		@Override
	    protected void onPostExecute(ResponseObject result) {
			
			TextView resultVenues = (TextView) findViewById(R.id.resultVenues);
			if (result == null) {
				resultVenues.setText("Retrieving venues failed.Connection error.");
			} else if ( result.getObject()== null){
					resultVenues.setText(result.getException());
					} else {
				             list = ((ArrayList<String>) result.getObject());
				             if ( (list.size() < 1)   ||   (list.isEmpty()) ) { 
				            	 resultVenues.setText("No venues found");
				             } else {
				            	 
				            	 adapter= new ArrayAdapter<String>(FoursquareActivity.this, android.R.layout.simple_list_item_1, list);
				            	 listVenues.setAdapter(adapter);
				            
				             }
					}
			}

	}
				            	 
	        
	 /**
	 * A placeholder fragment containing a simple view.
	 */
	public static class SearchFragment extends Fragment {

		
		
		public SearchFragment() {}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootview = inflater.inflate(R.layout.fragment_foursquare,
					container, false);
			

			return rootview;
		}

	}


}