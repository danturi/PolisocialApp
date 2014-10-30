package it.polimi.dima.polisocial.tabactivityAndFragments;

import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.tabactivityAndFragments.TabActivity.SwitchFragmentListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class AnnouncementsFragment extends Fragment {

	private static SwitchFragmentListener listener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_announcements, null);

		// button
		ImageButton lessonButton = (ImageButton) v
				.findViewById(R.id.imageButton1);
		ImageButton bookButton = (ImageButton) v
				.findViewById(R.id.imageButton2);
		ImageButton rentButton = (ImageButton) v
				.findViewById(R.id.imageButton3);

		// listener button
		lessonButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onSwitchFragmentName("lesson");
			}
		});

		bookButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onSwitchFragmentName("book");
			}
		});

		rentButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onSwitchFragmentName("rental");
			}
		});
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
		menu.findItem(R.id.action_create_lesson).setVisible(false);
		menu.findItem(R.id.action_create_rental).setVisible(false);
		menu.findItem(R.id.action_create_book).setVisible(false);
		menu.findItem(R.id.action_create_event).setVisible(false);
		menu.findItem(R.id.action_add_restaurant).setVisible(false);
		//menu.findItem(R.id.menu_filter_events).setVisible(false);
		menu.findItem(R.id.action_write_spotted_post).setVisible(false);
	}

	public static AnnouncementsFragment newInstance(
			SwitchFragmentListener announcementsFragmentListener) {
		AnnouncementsFragment announcementsFragment = new AnnouncementsFragment();
		listener = announcementsFragmentListener;
		return announcementsFragment;
	}
}
