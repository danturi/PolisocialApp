package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;

import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

//class representing the fragment at position 0 (spotted section)
	public class SpottedPostListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<PostSpotted>> {
      
	  private boolean refreshRequest=false;
      private SpottedPostAdapter mAdapter;	
      private SwipeRefreshLayout mSwipeRefreshLayout;
      private View mProgressView;
      @Override
      public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
    	  View v = inflater.inflate(R.layout.fragment_spotted, null);       
    	  return v;       
      }
      
      @SuppressWarnings("deprecation")
      @Override
      public void onActivityCreated(Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);

          setHasOptionsMenu(true);

          // Create an empty adapter we will use to display the loaded data.
          mAdapter = new SpottedPostAdapter(getActivity());
          setListAdapter(mAdapter);

          // Start out with a progress indicator.
          mProgressView = getView().findViewById(R.id.progress_bar);

          mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipe_container);
          mSwipeRefreshLayout.setColorScheme(R.color.color1,R.color.color2,R.color.color3,R.color.color4);
          mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
              @Override
              public void onRefresh() {
            	  refreshRequest=true;
            	  initiateRefresh();
              }
          });
          showProgress(true);
          // Prepare the loader.  Either re-connect with an existing one,
          // or start a new one.
          getLoaderManager().initLoader(0, null, this);
      }

      
      @Override
      public void onPrepareOptionsMenu(Menu menu) {
          super.onPrepareOptionsMenu(menu);
          menu.findItem(R.id.action_create_event).setVisible(false);
          menu.findItem(R.id.action_add_restaurant).setVisible(false);
          menu.findItem(R.id.menu_filter_events).setVisible(false);
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
      public Loader<List<PostSpotted>> onCreateLoader(int arg0, Bundle arg1) {
          return new SpottedPostListLoader(getActivity());
      }

      @Override
      public void onLoadFinished(Loader<List<PostSpotted>> arg0, List<PostSpotted> data) {
          mAdapter.setData(data);
          // The list should now be shown.
          if(refreshRequest){
        	  onRefreshComplete();
          }
          showProgress(false);
        /**  if (isResumed()) {
              //setListShown(true);
          } else {
              //setListShownNoAnimation(true);
          }**/
      }

      @Override
      public void onLoaderReset(Loader<List<PostSpotted>> arg0) {
          mAdapter.setData(null);
      }
      
      /**
    	 * Shows the progress UI and hides post list.
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
 }
   
  