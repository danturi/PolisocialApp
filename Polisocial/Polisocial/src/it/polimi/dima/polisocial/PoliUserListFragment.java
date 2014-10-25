package it.polimi.dima.polisocial;

import java.util.ArrayList;
import java.util.List;

import it.polimi.dima.polisocial.adapter.UserAdapter;
import it.polimi.dima.polisocial.customListeners.EndlessScrollListener;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.CollectionResponseUserDTO;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.UserDTO;
import it.polimi.dima.polisocial.loader.UserListLoader;
import it.polimi.dima.polisocial.utilClasses.ShowProgress;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

public class PoliUserListFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<CollectionResponseUserDTO> {

	private ListView mList;
	private UserAdapter mAdapter;
	private String mCursor = null;
	private String username = null;


	private View mProgressView;
	private boolean refreshRequest = false;
	private boolean firstRequest = true;
	private boolean origList = false;
	private List<UserDTO> listInitialUsers = new ArrayList<UserDTO>();
	private String listInitialCursor = null;

	final private OnQueryTextListener queryListener = new OnQueryTextListener() {

		@Override
		public boolean onQueryTextChange(String newText) {
			TextView statusMsg = (TextView) getView()
					.findViewById(R.id.no_user);
			statusMsg.setVisibility(View.GONE);
			mList.setVisibility(View.VISIBLE);
			if (TextUtils.isEmpty(newText)) {
				origList=true;
				getActivity().getActionBar().setSubtitle("Users");
				if (getLoaderManager().hasRunningLoaders()) {
					getLoaderManager().destroyLoader(0);
				}
				mAdapter.setData(listInitialUsers);
				mCursor= listInitialCursor;
				username=null;

				// mList.clearTextFilter();
			} else {
				getActivity().getActionBar().setSubtitle(
						"Users - Searching for: " + newText);
				refreshRequest = true;
				username = newText;
				mCursor = null;
				origList=false;
				if (getLoaderManager().hasRunningLoaders()) {
					getLoaderManager().destroyLoader(0);
				}
				restartPoliUserLoader();
				// mList.setFilterText(newText);

			}

			return true;
		}

		@Override
		public boolean onQueryTextSubmit(String query) {
			Toast.makeText(getActivity(), "Searching for: " + query + "...",
					Toast.LENGTH_SHORT).show();
			refreshRequest = true;
			mCursor = null;
			username = query;
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
		SearchView searchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();
		searchView.setOnQueryTextListener(queryListener);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		super.onActivityCreated(savedInstanceState);
		firstRequest = true;
		mAdapter = new UserAdapter(getActivity());
		mList = getListView();
		mList.setAdapter(mAdapter);
		mList.setTextFilterEnabled(true);
		mList.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore() {
				// Triggered only when new data needs to be appended to the list

				addListPoliUserLoader();
			}

		});

		// Start out with a progress indicator.
		mProgressView = getView().findViewById(R.id.progress_bar);
		ShowProgress.showProgress(true, mProgressView, mList, getActivity());

		Bundle bundle = new Bundle();
		bundle.putString("cursor", mCursor);
		refreshRequest = true;
		getLoaderManager().initLoader(0, bundle, this);

	}


	private void addListPoliUserLoader() {
		ShowProgress.showProgress(true, mProgressView, mList, getActivity());
		Bundle bundle = new Bundle();
		bundle.putString("cursor", mCursor);
		bundle.putString("username", username);
		getLoaderManager().restartLoader(0, bundle, this);
	}

	private void restartPoliUserLoader() {
		ShowProgress.showProgress(true, mProgressView, mList, getActivity());
		Bundle bundle = new Bundle();
		bundle.putString("username", username);
		getLoaderManager().restartLoader(0, bundle, this);
	}

	@Override
	public Loader<CollectionResponseUserDTO> onCreateLoader(int arg0,
			Bundle bundle) {
		String cursor = (String) bundle.get("cursor");
		String username = (String) bundle.get("username");
		return new UserListLoader(getActivity(), cursor, username);

	}

	@Override
	public void onLoadFinished(Loader<CollectionResponseUserDTO> arg0,
			CollectionResponseUserDTO data) {
		
		mCursor = data.getNextPageToken();
		TextView statusMsg = (TextView) getView().findViewById(R.id.no_user);
		//ci sono nuovi user
		if (data.getItems() != null) {
			statusMsg.setVisibility(View.GONE);
			mList.setVisibility(View.VISIBLE);

			//se è una richiesta nuova 
			if (refreshRequest) {
				if (firstRequest) {
					listInitialUsers = data.getItems();
					listInitialCursor= mCursor;
					firstRequest = false;
				}
				mAdapter.setData(data.getItems());
				refreshRequest = false;
			}//se è una richiesta di aggiunta utenti nella lista 
			else {
				// se ci troviamo nella pagina utenti iniziale
				if(origList){
					listInitialUsers.addAll(data.getItems());
					listInitialCursor=mCursor;
				}
				mAdapter.addAll(data.getItems());
			}
		} else {
			if (refreshRequest && !origList) {
				statusMsg.setVisibility(View.VISIBLE);
				mList.setVisibility(View.GONE);
			} 
		}

		ShowProgress.showProgress(false, mProgressView, mList, getActivity());

	}

	@Override
	public void onLoaderReset(Loader<CollectionResponseUserDTO> arg0) {
		mAdapter.setData(null);

	}

	

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		UserDTO userDTO = (UserDTO) getListView().getItemAtPosition(position);

		Intent intent = new Intent(getActivity(), ProfileActivity.class);
		intent.putExtra("userToRetrieveId", userDTO.getUserId());
		startActivity(intent);
	}

}
