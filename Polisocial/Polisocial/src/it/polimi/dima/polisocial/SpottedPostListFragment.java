package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.adapter.SpottedPostAdapter;
import it.polimi.dima.polisocial.customListeners.EndlessScrollListener;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.CollectionResponsePostSpotted;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;
import it.polimi.dima.polisocial.loader.SpottedPostListLoader;
import it.polimi.dima.polisocial.utilClasses.SessionManager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.database.Cursor;
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
public class SpottedPostListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<CollectionResponsePostSpotted> {

	private boolean refreshRequest = false;
	private SpottedPostAdapter mAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private View mProgressView;
	private SessionManager session;
	private ListView mList;
	private String mCursor = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_spotted, null);
		return v;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setHasOptionsMenu(true);
		session = new SessionManager(getActivity().getApplicationContext());
		Long userId = Long.valueOf(session.getUserDetails().get(
				SessionManager.KEY_USERID));
		String name = session.getUserDetails().get(SessionManager.KEY_NAME);
		// Create an empty adapter we will use to display the loaded data.
		mAdapter = new SpottedPostAdapter(getActivity(), userId, name);
		mList = getListView();
		mList.setAdapter(mAdapter);

		mList.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(String cursor, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list

				customLoadMoreDataFromApi(cursor);
				// or customLoadMoreDataFromApi(totalItemsCount);
			}
		});

		// Start out with a progress indicator.
		mProgressView = getView().findViewById(R.id.progress_bar);

		mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(
				R.id.swipe_container);
		mSwipeRefreshLayout.setColorScheme(R.color.color1, R.color.color2,
				R.color.color3, R.color.color4);
		mSwipeRefreshLayout
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						refreshRequest = true;
						mCursor = null;
						initiateRefresh();
					}
				});
		showProgress(true);
		// Prepare the loader. Either re-connect with an existing one,
		// or start a new one.
		Bundle bundle = new Bundle();
		bundle.putString("cursor", mCursor);
		getLoaderManager().initLoader(0, bundle, this);
	}

	// Append more data into the adapter
	public void customLoadMoreDataFromApi(String cursor) {
		//Toast.makeText(getActivity(),
			//	"A questo punto comincia il caricamento dei nuovi post",
				//Toast.LENGTH_LONG).show();

		initiateRefresh();
		

	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.findItem(R.id.action_create_event).setVisible(false);
		menu.findItem(R.id.action_add_restaurant).setVisible(false);
		menu.findItem(R.id.menu_filter_events).setVisible(false);
	}

	private void initiateRefresh() {
		Bundle bundle = new Bundle();
		bundle.putString("cursor", mCursor);
		getLoaderManager().restartLoader(0, bundle, this);
	}

	private void onRefreshComplete() {
		refreshRequest = false;
		mSwipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Insert desired behaviour here.
	}

	@Override
	public Loader<CollectionResponsePostSpotted> onCreateLoader(int arg0,
			Bundle bundle) {
		String cursor = (String) bundle.get("cursor");
		return new SpottedPostListLoader(getActivity(), cursor);
	}

	@Override
	public void onLoadFinished(Loader<CollectionResponsePostSpotted> arg0,
			CollectionResponsePostSpotted data) {
		mCursor = data.getNextPageToken();
		if(data.getItems()!=null){
			
			if (refreshRequest) {
				mAdapter.setData(data.getItems());
				onRefreshComplete();
			}else {
				mAdapter.addAll(data.getItems());
			}
		}
		
		showProgress(false);
		/**
		 * if (isResumed()) { //setListShown(true); } else {
		 * //setListShownNoAnimation(true); }
		 **/
	}

	@Override
	public void onLoaderReset(Loader<CollectionResponsePostSpotted> arg0) {
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
