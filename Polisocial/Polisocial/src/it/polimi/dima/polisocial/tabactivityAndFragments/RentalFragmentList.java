package it.polimi.dima.polisocial.tabactivityAndFragments;

import java.util.ArrayList;
import java.util.List;

import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.ShowRelatedCommentsActivity;
import it.polimi.dima.polisocial.adapter.RentalAdapter;
import it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental;
import it.polimi.dima.polisocial.tabactivityAndFragments.TabActivity.SwitchFragmentListener;
import it.polimi.dima.polisocial.utilClasses.PostType;
import it.polimi.dima.polisocial.utilClasses.WhatToShow;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RentalFragmentList extends ListFragment {

	private static SwitchFragmentListener listener;
	private List<Rental> rentals = new ArrayList<>();
	private RentalAdapter mAdapter;

	public RentalFragmentList(List<Rental> rentals) {
		super();
		this.rentals = rentals;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((TabActivity) getActivity()).getActionBar().setTitle(
				getString(R.string.rental_fragment_title));
		setHasOptionsMenu(true);
		mAdapter = new RentalAdapter(getActivity(),0);
		setListAdapter(mAdapter);
		mAdapter.setData(rentals);
		mAdapter.notifyDataSetChanged();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_rental_list, container,
				false);
		return v;
	}
	
	public static Fragment newInstance(SwitchFragmentListener announcementsFragmentListener, List<Rental> rentals) {
		RentalFragmentList rentalFragList = new RentalFragmentList(rentals);
		listener=announcementsFragmentListener;
		return rentalFragList;
	}
	
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.findItem(R.id.action_create_lesson).setVisible(false);
		menu.findItem(R.id.action_create_book).setVisible(false);
		menu.findItem(R.id.action_create_event).setVisible(false);
		menu.findItem(R.id.action_add_restaurant).setVisible(false);
		// menu.findItem(R.id.menu_filter_events).setVisible(false);
		menu.findItem(R.id.action_write_spotted_post).setVisible(false);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Rental rentalClicked = (Rental) getListView().getItemAtPosition(
				position);
		long postId = rentalClicked.getId();
		String postType = PostType.RENTAL.toString();

		Intent showRelativeCommentsIntent = new Intent(getActivity(),
				ShowRelatedCommentsActivity.class);
		showRelativeCommentsIntent.putExtra("postId", postId);
		showRelativeCommentsIntent.putExtra("notificationCategory",
				WhatToShow.DETAILS.toString());
		showRelativeCommentsIntent.putExtra("type", postType);
		startActivity(showRelativeCommentsIntent);
	}
	
	
}
