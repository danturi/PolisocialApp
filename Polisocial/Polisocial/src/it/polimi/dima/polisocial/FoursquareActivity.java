package it.polimi.dima.polisocial;

import java.io.IOException;
import java.util.ArrayList;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import it.polimi.dima.polisocial.foursquare.foursquareendpoint.Foursquareendpoint;
import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FoursquareActivity extends Activity {

	
	private TextView resultsList; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foursquare);
		
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements android.view.View.OnClickListener {

		
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_foursquare,
					container, false);
			Button button= (Button) rootView.findViewById(R.id.buttonLogin);
			button.setOnClickListener(this);

			return rootView;
		}

		@Override
		public void onClick(View v) {

			new SearchVenuesNearPoliTask().execute();
		}

		private class SearchVenuesNearPoliTask extends AsyncTask<Void, Void, ArrayList<String>> {

			@Override
			protected ArrayList<String> doInBackground(Void... params) {
				Foursquareendpoint.Builder builder = new Foursquareendpoint.Builder(
						AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
				builder = CloudEndpointUtils.updateBuilder(builder);
				Foursquareendpoint endpoint = builder.build();
				
				ArrayList<String> results = new ArrayList<String>();
				String ll = "45.478178,9.228031"; 
				try {
					 endpoint.searchVenues(ll);
				} catch (IOException e) {

					e.printStackTrace();
				}
				return null;
			}

		}


	}


}
