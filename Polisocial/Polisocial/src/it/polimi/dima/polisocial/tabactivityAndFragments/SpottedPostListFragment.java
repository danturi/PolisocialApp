package it.polimi.dima.polisocial.tabactivityAndFragments;

import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.adapter.SpottedPostAdapter;
import it.polimi.dima.polisocial.customListeners.EndlessScrollListener;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.CollectionResponsePostSpotted;
import it.polimi.dima.polisocial.loader.SpottedPostListLoader;
import it.polimi.dima.polisocial.utilClasses.SessionManager;
import it.polimi.dima.polisocial.utilClasses.ShowProgress;
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
	private EndlessScrollListener mEndlessScrollListener;

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

		mEndlessScrollListener = new EndlessScrollListener() {
			@Override
			public void onLoadMore() {
				// Triggered only when new data needs to be appended to the list
				loadData();
			}
		};
		mList.setOnScrollListener(mEndlessScrollListener);
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
						loadData();
					}
				});
		ShowProgress.showProgress(true, mProgressView, mSwipeRefreshLayout,
				getActivity());
		// Prepare the loader. Either re-connect with an existing one,
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

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Insert desired behaviour here.
	}

	private void loadData() {
		getLoaderManager().restartLoader(0, null, this);
	}

	private void onRefreshComplete() {
		refreshRequest = false;
		mSwipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public Loader<CollectionResponsePostSpotted> onCreateLoader(int arg0,
			Bundle bundle) {
		mEndlessScrollListener.setLoading(true);
		return new SpottedPostListLoader(getActivity(), mCursor);
	}

	@Override
	public void onLoadFinished(Loader<CollectionResponsePostSpotted> arg0,
			CollectionResponsePostSpotted data) {
		mCursor = data.getNextPageToken();
		if (data.getItems() != null) {
			if (refreshRequest) {
				onRefreshComplete();
				mAdapter.setData(data.getItems());
				mAdapter.notifyDataSetChanged();
				mAdapter.setLoading_row(1);
				mAdapter.notifyDataSetChanged();
			} else {
				mAdapter.addAll(data.getItems());
			}
			mEndlessScrollListener.setLoading(false);
			// case in which there are no more data to retrieve from server
		} else {
			// tell the adapter to dismiss the progress bar cause there are no
			// more data
			mAdapter.setLoading_row(0);
			mAdapter.notifyDataSetChanged();
		}
		ShowProgress.showProgress(false, mProgressView, mSwipeRefreshLayout,
				getActivity());
	}

	@Override
	public void onLoaderReset(Loader<CollectionResponsePostSpotted> arg0) {
		mAdapter.setData(null);
	}
}
