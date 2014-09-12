package it.polimi.dima.polisocial;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class EventsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<EventItem>> {
    
	private boolean refreshRequest=false;
	private EventAdapter mAdapter;	
	private SwipeRefreshLayout mSwipeRefreshLayout;
	
	@Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
  	  View v = inflater.inflate(R.layout.fragment_events, null);       
  	  return v;       
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        
     // Create an empty adapter we will use to display the loaded data.
        mAdapter = new EventAdapter(getActivity());
        setListAdapter(mAdapter);

        // Start out with a progress indicator.
        //setListShown(false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright, 
                android.R.color.holo_green_light, 
                android.R.color.holo_orange_light, 
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
          	  refreshRequest=true;
          	  initiateRefresh();
            }
        });
        

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }
    
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_add_restaurant).setVisible(false);
        menu.findItem(R.id.action_write_spotted_post).setVisible(false);
    }
    
    private void initiateRefresh() {
        getLoaderManager().restartLoader(0, null, this);
    }


    private void onRefreshComplete() {
    	  refreshRequest=false;
    	  mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Insert desired behaviour here.
    }

    @Override
    public Loader<List<EventItem>> onCreateLoader(int arg0, Bundle arg1) {
        return new EventListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<EventItem>> arg0, List<EventItem> data) {
        mAdapter.setData(data);
        // The list should now be shown.
        if(refreshRequest){
      	  onRefreshComplete();
        }
        if (isResumed()) {
            //setListShown(true);
        } else {
            //setListShownNoAnimation(true);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<EventItem>> arg0) {
        mAdapter.setData(null);
    }
}

