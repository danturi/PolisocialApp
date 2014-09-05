package it.polimi.dima.polisocial;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

//class representing the fragment at position 0 (spotted section)
	public class SpottedPostListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<PostItem>> {
      
      PostAdapter mAdapter;	
			
      @Override
      public void onActivityCreated(Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);

          setHasOptionsMenu(true);
          // Initially there is no data 
          setEmptyText("No Data");

          // Create an empty adapter we will use to display the loaded data.
          mAdapter = new PostAdapter(getActivity());
          setListAdapter(mAdapter);

          // Start out with a progress indicator.
          setListShown(false);
          
          
          // Prepare the loader.  Either re-connect with an existing one,
          // or start a new one.
          getLoaderManager().initLoader(0, null, this);
      }

      @Override
      public void onPrepareOptionsMenu(Menu menu) {
          super.onPrepareOptionsMenu(menu);
          menu.findItem(R.id.action_create_event).setVisible(false);
          menu.findItem(R.id.action_add_restaurant).setVisible(false);
          menu.findItem(R.id.action_write_announcement).setVisible(false);
          menu.findItem(R.id.menu_filter_events).setVisible(false);
      }

      @Override
      public void onListItemClick(ListView l, View v, int position, long id) {
          // Insert desired behaviour here.
      }

      @Override
      public Loader<List<PostItem>> onCreateLoader(int arg0, Bundle arg1) {
          return new SpottedPostListLoader(getActivity());
      }

      @Override
      public void onLoadFinished(Loader<List<PostItem>> arg0, List<PostItem> data) {
          mAdapter.setData(data);
          // The list should now be shown.
          if (isResumed()) {
              setListShown(true);
          } else {
              setListShownNoAnimation(true);
          }
      }

      @Override
      public void onLoaderReset(Loader<List<PostItem>> arg0) {
          mAdapter.setData(null);
      }
  }
   
  