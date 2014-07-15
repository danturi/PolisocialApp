package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.CollectionResponsePoliUser;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.Email;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser;

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

public class MainActivity extends Activity {
	
	
	private TextView resultsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
		
		
		new InsertPoliUserTask().execute();
		
		resultsList = (TextView) findViewById(R.id.results);
		
		new ListOfPoliUserAsync().execute();
		
		Button button = (Button) findViewById(R.id.buttonFSQ);
		button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent openFSQPage = new Intent(MainActivity.this, FoursquareActivity.class); 
				startActivity(openFSQPage);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	private class InsertPoliUserTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			
			PoliUser poliUser= new PoliUser();
			Email userEmail = new Email();
			userEmail.setEmail("email@gmail.com");
			poliUser.setNickname("Buzz culo");
			poliUser.setEmail(userEmail);
			
			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
			
			builder = CloudEndpointUtils.updateBuilder(builder);
			
			Poliuserendpoint endpoint = builder.build();
			
			try {
				
				endpoint.insertPoliUser(poliUser).execute();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			return null;
		}
		
	}
	
	private class ListOfPoliUserAsync extends AsyncTask <Void, Void, CollectionResponsePoliUser> {

	    @Override
	    protected CollectionResponsePoliUser doInBackground(Void... params) {


	      Poliuserendpoint.Builder endpointBuilder = new Poliuserendpoint.Builder(
	          AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
	     
	      endpointBuilder = CloudEndpointUtils.updateBuilder(endpointBuilder);


	      CollectionResponsePoliUser result;

	      Poliuserendpoint endpoint = endpointBuilder.build();

	      try {
	        result = endpoint.listPoliUser().execute();
	      } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        result = null;
	      }
	      return result;
	    }

	    @Override
	    @SuppressWarnings("null")
	    protected void onPostExecute(CollectionResponsePoliUser result) {
	      

	      if (result == null || result.getItems() == null || result.getItems().size() < 1) {
	        if (result == null) {
	          resultsList.setText("Retrieving users failed.");
	        } else {
	          resultsList.setText("No users found.");
	        }

	        return;
	      }

	      List<PoliUser> poliUsers = result.getItems();
	      StringBuffer poliUsersFound = new StringBuffer();
	      
	      for (PoliUser user : poliUsers){
	        poliUsersFound.append(user.getNickname() + "\r\n");
	      }
	      
	      resultsList.setText(poliUsersFound.toString());
	     
	    }
	  }
	
	


	
}
