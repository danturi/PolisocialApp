package it.polimi.dima.polisocial.tabactivityAndFragments;

import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.adapter.EventAdapter;
import it.polimi.dima.polisocial.customListeners.EndlessScrollListener;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.CollectionResponseInitiative;
import it.polimi.dima.polisocial.loader.EventListLoader;
import it.polimi.dima.polisocial.utilClasses.SessionManager;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class EventsFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<CollectionResponseInitiative> {

	private boolean refreshRequest = false;
	private EventAdapter mAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	// TODO private View mProgressView;
	private SessionManager session;
	private ListView mList;
	private String mCursor = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_events, null);
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
		mAdapter = new EventAdapter(getActivity());
		setListAdapter(mAdapter);

		mList = getListView();
		mList.setAdapter(mAdapter);

		mList.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore() {
				// Triggered only when new data needs to be appended to the list

				initiateRefresh();
				// or customLoadMoreDataFromApi(totalItemsCount);
			}
		});
		// Start out with a progress indicator.
		// TODO mProgressView = getView().findViewById(R.id.progress_bar);

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
		// showProgress(true);
		// Prepare the loader. Either re-connect with an existing one,
		// or start a new one.
		Bundle bundle = new Bundle();
		bundle.putString("cursor", mCursor);
		getLoaderManager().initLoader(0, bundle, this);

	}


	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.findItem(R.id.action_add_restaurant).setVisible(false);
		menu.findItem(R.id.action_write_spotted_post).setVisible(false);
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
	public Loader<CollectionResponseInitiative> onCreateLoader(int arg0,
			Bundle bundle) {
		String cursor = (String)bundle.get("cursor");
		return new EventListLoader(getActivity(), null);
	}

	@Override
	public void onLoadFinished(Loader<CollectionResponseInitiative> arg0,
			CollectionResponseInitiative data) {
		mCursor = data.getNextPageToken();
		if (data.getItems() != null) {

			if (refreshRequest) {
				mAdapter.setData(data.getItems());
				onRefreshComplete();
			} else {
				mAdapter.addAll(data.getItems());
			}
		}

		// showProgress(false);
	}

	@Override
	public void onLoaderReset(Loader<CollectionResponseInitiative> arg0) {
		mAdapter.setData(null);
	}
}
