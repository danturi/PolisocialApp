package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.adapter.UserAdapter;
import it.polimi.dima.polisocial.customListeners.EndlessScrollListener;
import it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.CollectionResponseUserDTO;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.UserDTO;
import it.polimi.dima.polisocial.loader.UserListLoader;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

public class PoliUserListFragment extends ListFragment implements
LoaderManager.LoaderCallbacks<CollectionResponseUserDTO> {

	private ListView mList;
	private UserAdapter adapter;
	private String mCursor= null;
	private String username=null;
	public PoliUserListFragment() {};
	private View mProgressView;
	private boolean refreshRequest=false;
	
	
	final private OnQueryTextListener queryListener = new OnQueryTextListener() {       

		
		
	    @Override
	    public boolean onQueryTextChange(String newText) {
	    	if (TextUtils.isEmpty(newText)) {
	            getActivity().getActionBar().setSubtitle("Users");               
	            mList.clearTextFilter();
	        } else {
	            getActivity().getActionBar().setSubtitle("Users - Searching for: " + newText);
	            mList.setFilterText(newText);

	        }   
	        
	        return true;
	    }

	    @Override
	    public boolean onQueryTextSubmit(String query) {            
	        Toast.makeText(getActivity(), "Searching for: " + query + "...", Toast.LENGTH_SHORT).show();
	        refreshRequest=true;
	        mCursor=null;
	        username=query;
	        restartPoliUserLoader();
	        return false;
	    }
	};
	

	

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_poliuser, container,
				false);
		return rootView;
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.search, menu); 
	    SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
	    searchView.setOnQueryTextListener(queryListener);
		super.onCreateOptionsMenu(menu, inflater);
	}

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onActivityCreated(savedInstanceState);
		adapter = new UserAdapter(getActivity());
		mList = getListView();
		mList.setAdapter(adapter);
		mList.setTextFilterEnabled(true);
		mList.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(String cursor, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
				if(mCursor!=null){
				customLoadMoreDataFromApi(cursor);
				}
			}

			
		});
		

		// Start out with a progress indicator.
		mProgressView = getView().findViewById(R.id.progress_bar);
		showProgress(true);
		
		Bundle bundle = new Bundle();
		bundle.putString("cursor", mCursor);
		refreshRequest=true;
		getLoaderManager().initLoader(0, bundle, this);
		
	}
	
	private void customLoadMoreDataFromApi(String cursor) {
		restartPoliUserLoader();
		
	}
	
	private void restartPoliUserLoader() {
		showProgress(true);
		Bundle bundle = new Bundle();
		bundle.putString("cursor", mCursor);
		bundle.putString("username", username);
		getLoaderManager().restartLoader(0, bundle, this);
	}
	
	@Override
	public Loader<CollectionResponseUserDTO> onCreateLoader(int arg0, Bundle bundle) {
		String cursor = (String) bundle.get("cursor");
		String username = (String) bundle.get("username");
		return new UserListLoader(getActivity(), cursor,username);
		
	}

	@Override
	public void onLoadFinished(Loader<CollectionResponseUserDTO> arg0,
			CollectionResponseUserDTO data) {
		mCursor = data.getNextPageToken();
			if(data.getItems()!=null){
			
			if (refreshRequest) {
				adapter.setData(data.getItems());
				refreshRequest=false;
			}else {
				adapter.addAll(data.getItems());
				adapter.addToOrigData(data.getItems());
			}
		}
		
		showProgress(false);
		
	}

	@Override
	public void onLoaderReset(Loader<CollectionResponseUserDTO> arg0) {
		adapter.setData(null);
		
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
		}
	}


	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		UserDTO userDTO = (UserDTO) getListView()
				.getItemAtPosition(position);
		
		Intent intent = new Intent(getActivity(),
				ProfileActivity.class);
		intent.putExtra("userToRetrieveId",userDTO.getUserId());
		startActivity(intent);
	}

}
