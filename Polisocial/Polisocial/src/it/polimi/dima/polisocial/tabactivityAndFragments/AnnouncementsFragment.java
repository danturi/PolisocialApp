package it.polimi.dima.polisocial.tabactivityAndFragments;

import it.polimi.dima.polisocial.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class AnnouncementsFragment extends Fragment {

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
  	  View v = inflater.inflate(R.layout.fragment_announcements, null);       
  	  return v;       
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
