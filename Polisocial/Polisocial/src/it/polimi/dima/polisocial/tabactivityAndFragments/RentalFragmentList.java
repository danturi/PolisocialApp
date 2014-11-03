package it.polimi.dima.polisocial.tabactivityAndFragments;

import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.tabactivityAndFragments.TabActivity.SwitchFragmentListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RentalFragmentList extends Fragment {

	
	private static SwitchFragmentListener listener;

	public RentalFragmentList() {}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_rental_list, container,
				false);
		return v;
	}
	
	public static Fragment newInstance(SwitchFragmentListener announcementsFragmentListener) {
		RentalFragmentList rentalFragList = new RentalFragmentList();
		listener=announcementsFragmentListener;
		return rentalFragList;
	}

}
