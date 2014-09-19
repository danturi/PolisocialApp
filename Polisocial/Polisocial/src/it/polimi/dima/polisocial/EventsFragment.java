package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class EventsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Initiative>> {
    
	private boolean refreshRequest=false;
	private EventAdapter mAdapter;	
	private SwipeRefreshLayout mSwipeRefreshLayout;
	
	@Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
  	  View v = inflater.inflate(R.layout.fragment_events, null);       
  	  return v;       
    }
    
    @SuppressWarnings("deprecation")
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
        mSwipeRefreshLayout.setColorScheme(R.color.color1,R.color.color2,R.color.color3,R.color.color4);
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
    public Loader<List<Initiative>> onCreateLoader(int arg0, Bundle arg1) {
        return new EventListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Initiative>> arg0, List<Initiative> data) {
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
    public void onLoaderReset(Loader<List<Initiative>> arg0) {
        mAdapter.setData(null);
    }
}

