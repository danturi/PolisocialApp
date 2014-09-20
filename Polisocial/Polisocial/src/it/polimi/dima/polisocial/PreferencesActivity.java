package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser;

import java.io.IOException;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class PreferencesActivity extends PreferenceActivity {
    
	
	private PoliUser user;
	private Boolean spotted;
	private Boolean announc;
	private Boolean events;
	SessionManager session;
	
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.activity_preferences);
        session = new SessionManager(getApplicationContext());
    	
        
    }   
    
    // Boolean  
    public static boolean ReadBoolean(Context context, final String key, final boolean defaultValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }
 
    public static void WriteBoolean(Context context, final String key, final boolean value) {
          SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
          SharedPreferences.Editor editor = settings.edit();
          editor.putBoolean(key, value);
          editor.commit();        
    }
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	//leggere le checkbox e impostare i valori nel server prima che l'attivit� venga chiusa
    	spotted = ReadBoolean(this, "spotted", false);
    	announc = ReadBoolean(this, "announcements", false);
    	events = ReadBoolean(this, "events", false);
    	
    	new UpdateNotifyUser().execute();

    }
    
    
    public class UpdateNotifyUser extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			
			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);

			builder = CloudEndpointUtils.updateBuilder(builder);
			Poliuserendpoint endpoint = builder.setApplicationName("polimisocial").build();
			
			
			try {
				user=endpoint.getPoliUser(Long.valueOf(session.getUserDetails().get(SessionManager.KEY_USERID))).execute();
				
				//update user only when there is at least a different option
				if(!((spotted == user.getNotifySpotted()) && (announc == user.getNotifyAnnouncement()) && 
		    			(events == user.getNotifyEvent()))) {
					user.setNotifyAnnouncement(announc);
					user.setNotifyEvent(events);
					user.setNotifySpotted(spotted);
					endpoint.updatePoliUser(user).execute();
				}
				
				
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			
			//attivo o disattivo la registrazione a gcm
	    	if (!spotted && !announc && !events)
	    		GCMIntentService.unregister(getApplicationContext());
	    	
	    	if (spotted || announc || events)
	    		GCMIntentService.register(getApplicationContext());
		}
		
		
    	
    }
    
    
}
