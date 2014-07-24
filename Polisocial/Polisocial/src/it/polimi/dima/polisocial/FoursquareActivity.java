package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.foursquare.foursquareendpoint.Foursquareendpoint;
import it.polimi.dima.polisocial.foursquare.foursquareendpoint.model.StringCollection;

import java.io.IOException;
import java.util.List;

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
import android.widget.Button;
import android.widget.TextView;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class FoursquareActivity extends Activity {

	private TextView resultVenues ;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foursquare);
		
		/*if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new SearchFragment()).commit();
		}*/
		
		Button button= (Button)findViewById(R.id.buttonSearch);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				resultVenues = (TextView) findViewById(R.id.resultVenues);
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
	
	private class SearchVenuesNearPoliTask extends AsyncTask<Void, Void, StringCollection> {

		@Override
		protected StringCollection doInBackground(Void... params) {
			Foursquareendpoint.Builder builder = new Foursquareendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
			builder = CloudEndpointUtils.updateBuilder(builder);
			Foursquareendpoint endpoint = builder.build();
			
			StringCollection result;
			String ll = "45.478178,9.228031"; 
			try {
				 result = endpoint.searchVenues(ll).execute();
			} catch (IOException e) {

				e.printStackTrace();
				result = null;
			}
			return result;
		}
		
		@Override
	    @SuppressWarnings("null")
	    protected void onPostExecute(StringCollection result) {
			
			
					

	      if (result == null || result.getItems() == null || result.getItems().size() < 1) {
	        if (result == null) {
	        	resultVenues.setText("Retrieving venues failed.");
	        	//i.putExtra("polisocial.venues.result", "Retrieving venues failed.");
	        	//startActivity(i);
	        	
	        } else {
	        	resultVenues.setText("No venues found.");
	        	//i.putExtra("polisocial.venues.result", "No venues found.");
	        	//startActivity(i);
	        }
	        
	      }

	      List<String> venuesName = result.getItems();
	      StringBuffer venuesFound = new StringBuffer();
	      
	      for (String venue : venuesName){
	        venuesFound.append(venue+ "\r\n");
	      }
	      
	      resultVenues.setText(venuesFound.toString());
	      //i.putExtra("polisocial.venues.result", venuesFound.toString());
      	  //startActivity(i);
	      
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