package it.polimi.dima.polisocial;




import java.io.IOException;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import it.polimi.dima.polisocial.foursquare.foursquareendpoint.Foursquareendpoint;
import it.polimi.dima.polisocial.utilClasses.SessionManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class AuthorizationFoursquareActivity extends Activity {

	private TokenFoursquareRequestTask tokenTask;
	private SessionManager sessionManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authorization_foursquare);
		sessionManager = new SessionManager(getApplicationContext());
		String code= getIntent().getStringExtra("code");
		String id = sessionManager.getUserDetails().get(SessionManager.KEY_USERID);
		tokenTask= new TokenFoursquareRequestTask(this);
		tokenTask.execute(code,id);
			
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.authorized_foursquare, menu);
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
	
	public void onTokenComplete(String result) {
		
		Intent data = new Intent();
	    data.putExtra("result",result);
	    // Activity finished ok, return the data
	    setResult(RESULT_OK, data);
	    finish();

		
	}
	
	private class TokenFoursquareRequestTask extends AsyncTask <String,Void,Void> {

		private AuthorizationFoursquareActivity mActivity;
		
		public TokenFoursquareRequestTask(
				AuthorizationFoursquareActivity activity) {
			mActivity=activity;
		}

		@Override
		protected Void doInBackground(String... params) {
			String result= "ERROR";
			Foursquareendpoint.Builder builder = new Foursquareendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
			builder = CloudEndpointUtils.updateBuilder(builder);
			Foursquareendpoint endpoint = builder.build();
			
			System.out.println(params[0]);
			System.out.println(params[1]);
            
            try {
				endpoint.performTokenRequest(params[0],Long.valueOf(params[1])).execute();
            	result="OK";
				
			} catch (IOException ex) {
				Log.e("TokenFoursquareRequestTask",ex.getMessage());
			}
            mActivity.onTokenComplete(result);
			return null;
		}

	}

}
	
