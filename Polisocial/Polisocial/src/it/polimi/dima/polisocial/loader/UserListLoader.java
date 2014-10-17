package it.polimi.dima.polisocial.loader;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.CollectionResponseUserDTO;

import java.io.IOException;
import java.util.StringTokenizer;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class UserListLoader extends AsyncTaskLoader<CollectionResponseUserDTO> {

	private String cursor;
	private String username;
	private CollectionResponseUserDTO mUserItems;

	public UserListLoader(Context context, String cursor, String username) {
		super(context);
		this.cursor = cursor;
		this.username=username;
	}

	@Override
	public CollectionResponseUserDTO loadInBackground() {
	     
		//GET USERS DA APP ENGINE
        Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
        builder = CloudEndpointUtils.updateBuilder(builder);
		Poliuserendpoint endpoint = builder.setApplicationName("polimisocial").build();
		
		CollectionResponseUserDTO list = new CollectionResponseUserDTO();
		
		try {
			if(username!=null){
				list = endpoint.searchPoliUser().setLimit(15).setCursor(cursor).setUsername(username).execute();
			//}else if(username!=null){
				//list = endpoint.searchPoliUser().setLimit(10).setUsername(username).execute();
				
			}else {
				list = endpoint.searchPoliUser().setLimit(15).setCursor(cursor).execute();
			}
		}catch (IOException e) {
			if( new StringTokenizer(e.getMessage().toString()).nextToken().equals("404"))
				return list;
		}
		cursor=null;
		username=null;
        return list;
		
	}
	
	 /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override public void deliverResult(CollectionResponseUserDTO listOfData) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (listOfData != null) {
                onReleaseResources(listOfData);
            }
        }
        CollectionResponseUserDTO oldApps = listOfData;
        mUserItems = listOfData; 
        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(listOfData);
        }

        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldApps != null) {
            onReleaseResources(oldApps);
        }
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override protected void onStartLoading() {
    	
    	if (takeContentChanged() || mUserItems == null || cursor==null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
        }
        if (mUserItems != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mUserItems);
        }


        
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    /**
     * Handles a request to cancel a load.
     */
    @Override public void onCanceled(CollectionResponseUserDTO apps) {
        super.onCanceled(apps);

        // At this point we can release the resources associated with 'apps'
        // if needed.
        onReleaseResources(apps);
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        // At this point we can release the resources associated with 'apps'
        // if needed.
        if (mUserItems != null) {
            onReleaseResources(mUserItems);
            mUserItems = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    protected void onReleaseResources(CollectionResponseUserDTO listOfData) {}
     
}
