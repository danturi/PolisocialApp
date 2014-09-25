package it.polimi.dima.polisocial.loader;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.entity.commentendpoint.Commentendpoint;
import it.polimi.dima.polisocial.entity.commentendpoint.model.ResponseObject;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.initiativeendpoint.Initiativeendpoint;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.CollectionResponseInitiative;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class EventListLoader extends AsyncTaskLoader<List<Initiative>> {
	   
    List<Initiative> mEventItems;

    public EventListLoader(Context context) {
        super(context);
    }

    @Override
    public List<Initiative> loadInBackground() {
    	//GET DEI POST DA APP ENGINE
        Initiativeendpoint.Builder builder = new Initiativeendpoint.Builder(AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
        builder = CloudEndpointUtils.updateBuilder(builder);
		Initiativeendpoint endpoint = builder.setApplicationName("polimisocial").build();
		
		Commentendpoint.Builder build = new Commentendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);

		build= CloudEndpointUtils.updateBuilder(build);
		Commentendpoint commEndpoint = build.setApplicationName("polimisocial").build();
		
		List<Initiative> entries = new ArrayList<Initiative>();
		try {
			CollectionResponseInitiative list = endpoint.listInitiative().setLimit(10).execute();

			if(list.getItems()!=null){
				for(Initiative eventPost : list.getItems()){	
					ResponseObject count = commEndpoint.getNumbPostComments(eventPost.getId()).execute();
					eventPost.setNumOfComments(Integer.valueOf((String) count.getObject()));
					entries.add(eventPost);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        return entries;
    }
     
    /**
     * Called when there is new data to deliver to the client.  The
     * super class will take care of delivering it; the implementation
     * here just adds a little more logic.
     */
    @Override public void deliverResult(List<Initiative> listOfData) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (listOfData != null) {
                onReleaseResources(listOfData);
            }
        }
        List<Initiative> oldApps = listOfData;
        mEventItems = listOfData;

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
        if (mEventItems != null) {
            // If we currently have a result available, deliver it
            // immediately.
            deliverResult(mEventItems);
        }


        if (takeContentChanged() || mEventItems == null) {
            // If the data has changed since the last time it was loaded
            // or is not currently available, start a load.
            forceLoad();
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
    @Override public void onCanceled(List<Initiative> apps) {
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
        if (mEventItems != null) {
            onReleaseResources(mEventItems);
            mEventItems = null;
        }
    }

    /**
     * Helper function to take care of releasing resources associated
     * with an actively loaded data set.
     */
    protected void onReleaseResources(List<Initiative> apps) {}
     

}
