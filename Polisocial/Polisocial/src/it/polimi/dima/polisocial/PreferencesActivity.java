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
    
	SessionManager session;
	private PoliUser user;
	private Boolean spotted;
	private Boolean announc;
	private Boolean events;

	
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.activity_preferences);
        
    	
        
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
    	new SetNotificationUser().execute();

    	//attivo o disattivo la registrazione a gcm
    	if (!spotted && !announc && !events)
    		GCMIntentService.unregister(getApplicationContext());
    	
    	if (spotted || announc || events)
    		GCMIntentService.register(getApplicationContext());

    }
    



	public class CheckNotificationUser extends AsyncTask<Void, Void, PoliUser>{
    	
		SessionManager s;
    	Context c ;
    	
    	public CheckNotificationUser(SessionManager session,Context context){
    		s = session;
    		c=context;
    	}

		@Override
		protected PoliUser doInBackground(Void... params) {
			 
			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
		    			AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);

		    	builder = CloudEndpointUtils.updateBuilder(builder);
		    	Poliuserendpoint endpoint = builder.setApplicationName("polimisocial").build();
		    	try {
					user = endpoint.checkForDuplicateEmail(s.getUserDetails().get(SessionManager.KEY_EMAIL)).execute();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    	
			return user;
		}

		@Override
		protected void onPostExecute(PoliUser user) {
			
			super.onPostExecute(user);
			
			if (user.getNotifyAnnouncement() || user.getNotifyAnnouncement()==null) 
				WriteBoolean(c, "announcements", true); 
			else WriteBoolean(c, "announcements", false);
			
			if (user.getNotifyEvent() || user.getNotifyEvent()==null)
				WriteBoolean(c, "events", true); 
			else WriteBoolean(c, "events", false);
			
			if (user.getNotifySpotted() || user.getNotifySpotted()==null)
				WriteBoolean(c, "spotted", true); 
			else WriteBoolean(c, "spotted", false);	
				
		}
    	
    }
    
    public class SetNotificationUser extends AsyncTask<Void, Void, Void>{
    	

    	Context c ;


		@Override
		protected Void doInBackground(Void... params) {
			 
			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
		    			AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);

		    	builder = CloudEndpointUtils.updateBuilder(builder);
		    	Poliuserendpoint endpoint = builder.setApplicationName("polimisocial").build();
		    	
		    	user.setNotifyAnnouncement(announc);
		    	user.setNotifySpotted(spotted);
		    	user.setNotifyEvent(events);
		    	
		    	try {
				endpoint.updatePoliUser(user).execute();	
				} catch (IOException e) {
					e.printStackTrace();
				}
		    	
			return null;
		}
    }
    
    
}
