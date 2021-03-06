package it.polimi.dima.polisocial.tabactivityAndFragments;

import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.ShowRelatedCommentsActivity;
import it.polimi.dima.polisocial.adapter.SecondHandBookAdapter;
import it.polimi.dima.polisocial.customListeners.EndlessScrollListener;
import it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.CollectionResponseSecondHandBook;
import it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook;
import it.polimi.dima.polisocial.loader.SecondHandBookListLoader;
import it.polimi.dima.polisocial.utilClasses.PostType;
import it.polimi.dima.polisocial.utilClasses.SessionManager;
import it.polimi.dima.polisocial.utilClasses.ShowProgress;
import it.polimi.dima.polisocial.utilClasses.WhatToShow;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SecondHandBookFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<CollectionResponseSecondHandBook> {

	private SecondHandBookAdapter mAdapter;
	private View mProgressView;
	private SessionManager session;
	private ListView mList;
	private String mCursor = null;
	private EndlessScrollListener mEndlessScrollListener;
	private boolean mSuggestion=true;
	private boolean mFirstDataSet=true;
	private String mUserFaculty;
	
	//header components
	private ImageButton mSearchButton;
	private EditText mBookTitle;
	private EditText mBookAuthor;
	
	private TextView mBookResult;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_secondhandbook, null);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
		
		((TabActivity) getActivity()).getActionBar().setTitle(getString(R.string.book_fragment_title));

		setHasOptionsMenu(true);
		//retrieve faculty of the current user so that we can show
		// books that may interest him
		
		 session = new SessionManager(getActivity().getApplicationContext());
		 mUserFaculty= session.getUserDetails().get(SessionManager.KEY_FACULTY); 
		
		// Create an empty adapter we will use to display the loaded data.
		mAdapter = new SecondHandBookAdapter(getActivity());
		mList = getListView();
		View header = View.inflate(getActivity(), R.layout.book_header_search, null);
		mSearchButton = (ImageButton) header.findViewById(R.id.search_button);
		mBookTitle = (EditText) header.findViewById(R.id.book_title);
		mBookAuthor = (EditText) header.findViewById(R.id.book_author);
		mSearchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String title = mBookTitle.getText().toString();
				if(TextUtils.isEmpty(title)){
					mBookTitle.setError(getString(R.string.error_field_required));
					mBookTitle.requestFocus();
					return;
				}
				mCursor=null;
				mSuggestion=false;
				mFirstDataSet=true;
				mAdapter.clear();
				mAdapter.setLoading_row(1);
				mAdapter.notifyDataSetChanged();
				loadData();
				mBookResult.setText(getResources().getString(R.string.books_found));
				mBookResult.setPadding(5, 5, 5, 5);
				mBookResult.setTextSize(14);
				mBookResult.setGravity(Gravity.NO_GRAVITY);
				imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
			}
		});
		mList.addHeaderView(header);
		View headerResult = View.inflate(getActivity(), R.layout.book_result_second_header, null);
		mBookResult = (TextView) headerResult.findViewById(R.id.result_header);
		mList.addHeaderView(headerResult);
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
		
		ShowProgress.showProgress(true, mProgressView, mList,
				getActivity());
		// Prepare the loader. Either re-connect with an existing one,
		// or start a new one.
		getLoaderManager().initLoader(0, null, this);

	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.findItem(R.id.action_create_lesson).setVisible(false);
		menu.findItem(R.id.action_create_rental).setVisible(false);
		menu.findItem(R.id.action_create_event).setVisible(false);
		menu.findItem(R.id.action_add_restaurant).setVisible(false);
		//menu.findItem(R.id.menu_filter_events).setVisible(false);
		menu.findItem(R.id.action_write_spotted_post).setVisible(false);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		SecondHandBook secondHandBookClicked = (SecondHandBook) getListView()
				.getItemAtPosition(position);
		long bookId = secondHandBookClicked.getId();
		Intent showRelatedCommentsIntent = new Intent(getActivity(),
				ShowRelatedCommentsActivity.class);
		
		showRelatedCommentsIntent.putExtra("postId", bookId);
		showRelatedCommentsIntent.putExtra("type",
				PostType.SECOND_HAND_BOOK
						.toString());
		showRelatedCommentsIntent.putExtra(
				"notificationCategory",
				WhatToShow.DETAILS
						.toString());
		startActivity(showRelatedCommentsIntent);
	}

	private void loadData() {
		if(!mSuggestion){
			if(mBookTitle.getText().toString().isEmpty()){
				return;
			}
		}
		getLoaderManager().restartLoader(0, null, this);
	}


	@Override
	public Loader<CollectionResponseSecondHandBook> onCreateLoader(int arg0,
			Bundle arg1) {
		mEndlessScrollListener.setLoading(true);
		if(!mSuggestion){
			return new SecondHandBookListLoader(getActivity(), mCursor, mBookTitle.getText().toString(), mBookAuthor.getText().toString(), mSuggestion);
		}else{
			return new SecondHandBookListLoader(getActivity(), mCursor, mUserFaculty, mSuggestion);
		}
	}

	@Override
	public void onLoadFinished(Loader<CollectionResponseSecondHandBook> arg0,
			CollectionResponseSecondHandBook data) {
		mCursor = data.getNextPageToken();
		if (data.getItems() != null) {
			if (mFirstDataSet) {
				mAdapter.setData(data.getItems());
				mAdapter.notifyDataSetChanged();
				
			} else {
				mAdapter.addAll(data.getItems());
			}
		
			if(data.getItems().size()==10){
				mEndlessScrollListener.setLoading(false);
				mAdapter.setLoading_row(1);
				mAdapter.notifyDataSetChanged();
			}else{
				mEndlessScrollListener.setLoading(true);
				mAdapter.setLoading_row(0);
				mAdapter.notifyDataSetChanged();
			}
			
			// case in which there are no more data to retrieve from server
		} else {
			// tell the adapter to dismiss the progress bar cause there are no
			// more data
			mBookResult.setPadding(5, 30, 5, 30);
			mBookResult.setTextSize(20);
			mBookResult.setGravity(Gravity.CENTER);
			mBookResult.setText(getResources().getString(R.string.no_books_found));
			mAdapter.setLoading_row(0);
			mAdapter.notifyDataSetChanged();
		}
		ShowProgress.showProgress(false, mProgressView, mList,
				getActivity());

	}

	@Override
	public void onLoaderReset(Loader<CollectionResponseSecondHandBook> arg0) {
		mAdapter.setData(null);
	}

}
