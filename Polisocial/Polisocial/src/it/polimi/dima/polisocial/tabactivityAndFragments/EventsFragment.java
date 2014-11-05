package it.polimi.dima.polisocial.tabactivityAndFragments;

import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.ShowRelatedCommentsActivity;
import it.polimi.dima.polisocial.adapter.EventAdapter;
import it.polimi.dima.polisocial.customListeners.EndlessScrollListener;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.CollectionResponseInitiative;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.loader.EventListLoader;
import it.polimi.dima.polisocial.utilClasses.PostType;
import it.polimi.dima.polisocial.utilClasses.WhatToShow;
import android.content.Intent;
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
	//private View mProgressView;
	private String mCursor = null;
	private EndlessScrollListener mEndlessScrollListener;

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

		// Create an empty adapter we will use to display the loaded data.
		mAdapter = new EventAdapter(getActivity());
		setListAdapter(mAdapter);
		
		setListAdapter(mAdapter);

		mEndlessScrollListener = new EndlessScrollListener() {
			@Override
			public void onLoadMore() {
				// Triggered only when new data needs to be appended to the list
				loadData();
			}
		};
		getListView().setOnScrollListener(mEndlessScrollListener);
		// Start out with a progress indicator.
		//mProgressView = getView().findViewById(R.id.progress_bar);

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
		// ShowProgress.showProgress(true, mProgressView, mSwipeRefreshLayout,
		// getActivity());
		// Prepare the loader. Either re-connect with an existing one,
		getLoaderManager().initLoader(0, null, this);

	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.findItem(R.id.action_create_lesson).setVisible(false);
		menu.findItem(R.id.action_create_rental).setVisible(false);
		menu.findItem(R.id.action_create_book).setVisible(false);
		menu.findItem(R.id.action_add_restaurant).setVisible(false);
		menu.findItem(R.id.action_write_spotted_post).setVisible(false);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Initiative eventClicked = (Initiative) getListView().getItemAtPosition(
				position);
		long postId = eventClicked.getId();
		String postType = PostType.EVENT.toString();

		Intent showRelativeCommentsIntent = new Intent(getActivity(),
				ShowRelatedCommentsActivity.class);
		showRelativeCommentsIntent.putExtra("postId", postId);
		showRelativeCommentsIntent.putExtra("notificationCategory",
				WhatToShow.DETAILS.toString());
		showRelativeCommentsIntent.putExtra("type", postType);
		startActivity(showRelativeCommentsIntent);
	}

	private void loadData() {
		getLoaderManager().restartLoader(0, null, this);
	}

	private void onRefreshComplete() {
		refreshRequest = false;
		mSwipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public Loader<CollectionResponseInitiative> onCreateLoader(int arg0,
			Bundle bundle) {
		mEndlessScrollListener.setLoading(true);
		return new EventListLoader(getActivity(), mCursor);
	}

	@Override
	public void onLoadFinished(Loader<CollectionResponseInitiative> arg0,
			CollectionResponseInitiative data) {
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

			if (data.getItems().size() == 10) {
				mEndlessScrollListener.setLoading(false);
			} else {
				mAdapter.setLoading_row(0);
				mAdapter.notifyDataSetChanged();
			}
			// case in which there are no more data to retrieve from server
		} else {
			// tell the adapter to dismiss the progress bar cause there are no
			// more data
			mAdapter.setLoading_row(0);
			mAdapter.notifyDataSetChanged();
		}
		// ShowProgress.showProgress(false, mProgressView, mSwipeRefreshLayout,
		// getActivity());
	}

	@Override
	public void onLoaderReset(Loader<CollectionResponseInitiative> arg0) {
		mAdapter.setData(null);
	}
}
