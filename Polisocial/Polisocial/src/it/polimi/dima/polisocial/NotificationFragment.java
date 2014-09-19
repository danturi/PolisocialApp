package it.polimi.dima.polisocial;

import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
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

public class NotificationFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<NotificationItem>>  {

	private boolean refreshRequest=false;
    private NotificationAdapter mAdapter;	
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mProgressView;
    
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
  	  View v = inflater.inflate(R.layout.fragment_notification, null);       
  	  return v;       
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);

        // Create an empty adapter we will use to display the loaded data.
        mAdapter = new NotificationAdapter(getActivity());
        setListAdapter(mAdapter);
        
        mProgressView = getView().findViewById(R.id.progress_bar);

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

        // Start out with a progress indicator.
        showProgress(true);
        
        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }

    
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_create_event).setVisible(false);
        menu.findItem(R.id.action_add_restaurant).setVisible(false);
        menu.findItem(R.id.menu_filter_events).setVisible(false);
        menu.findItem(R.id.action_write_spotted_post).setVisible(false);
    }

  
    /**
	 * Shows the progress UI and hides notification list.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mSwipeRefreshLayout.setVisibility(show ? View.GONE : View.VISIBLE);
			mSwipeRefreshLayout.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mSwipeRefreshLayout.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mSwipeRefreshLayout.setVisibility(show ? View.GONE : View.VISIBLE);
		}
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
        super.onListItemClick(l, v, position, id);
        NotificationItem notificationClicked = (NotificationItem) getListView().getItemAtPosition(position);
        long postId = notificationClicked.getId();
        String notificationCategory = notificationClicked.getCategory();
        Intent showRelativeCommentsIntent = new Intent(getActivity(), ShowRelatedCommentsActivity.class);
    	showRelativeCommentsIntent.putExtra("postId",id);
    	showRelativeCommentsIntent.putExtra("notificationCategory",notificationCategory);
    	startActivity(showRelativeCommentsIntent);
        // Insert desired behaviour here.
    }

    @Override
    public Loader<List<NotificationItem>> onCreateLoader(int arg0, Bundle arg1) {
        return new NotificationListLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<NotificationItem>> arg0, List<NotificationItem> data) {
        mAdapter.setData(data);
        // The list should now be shown.
        if(refreshRequest){
      	  onRefreshComplete();
        }
        showProgress(false);
        //if (isResumed()) {
            //setListShown(true);
        //} else {
            //setListShownNoAnimation(true);
        //}
    }

    @Override
    public void onLoaderReset(Loader<List<NotificationItem>> arg0) {
        mAdapter.setData(null);
    }
}
 

	
	
	/**
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
		
		rootView.findViewById(R.id.gcmButton)
		.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), GCMActivity.class);
				startActivity(intent);
			}
		});
		return rootView;
	}
**/
