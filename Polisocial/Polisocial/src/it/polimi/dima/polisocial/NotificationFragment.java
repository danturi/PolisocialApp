package it.polimi.dima.polisocial;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class NotificationFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_notification, container, false);
		
		rootView.findViewById(R.id.gcmButton)
		.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), GCMActivity.class);
				startActivity(intent);
			}
		});
		return rootView;
	}
	
	   @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);

	        setHasOptionsMenu(true);
	    }
	    
	    @Override
	    public void onPrepareOptionsMenu(Menu menu) {
	        super.onPrepareOptionsMenu(menu);
	        menu.findItem(R.id.action_create_event).setVisible(false);
	        menu.findItem(R.id.action_add_restaurant).setVisible(false);
	        menu.findItem(R.id.menu_filter_events).setVisible(false);
	        menu.findItem(R.id.action_write_spotted_post).setVisible(false);
	    }

}
