package it.polimi.dima.polisocial.tabactivityAndFragments;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.ShowRelatedCommentsActivity;
import it.polimi.dima.polisocial.entity.rentalendpoint.Rentalendpoint;
import it.polimi.dima.polisocial.entity.rentalendpoint.model.CollectionResponseRental;
import it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental;
import it.polimi.dima.polisocial.tabactivityAndFragments.TabActivity.SwitchFragmentListener;
import it.polimi.dima.polisocial.utilClasses.PostType;
import it.polimi.dima.polisocial.utilClasses.WhatToShow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class RentalFragment extends Fragment {

	private static SwitchFragmentListener listener;
	GoogleMap map;
	MapView mapView;
	private RentalNearPoliAndPlotMapTask task;
	private List<Rental> rentals = new ArrayList<>();
	private TextView listButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_rental_map, container,
				false);

		listButton = (TextView) v.findViewById(R.id.button_view);

		mapView = (MapView) v.findViewById(R.id.map);
		mapView.onCreate(savedInstanceState);
		map = mapView.getMap();
		task = new RentalNearPoliAndPlotMapTask(this, map);
		task.execute();
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((TabActivity) getActivity()).getActionBar().setTitle(
				getString(R.string.rental_fragment_title));
		setHasOptionsMenu(true);
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
	public void onResume() {
		mapView.onResume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}

	public static RentalFragment newInstance(
			SwitchFragmentListener announcementsFragmentListener) {
		RentalFragment rentalFragment = new RentalFragment();
		listener = announcementsFragmentListener;
		return rentalFragment;
	}

	public class RentalNearPoliAndPlotMapTask extends
			AsyncTask<Void, Void, CollectionResponseRental> {

		RentalFragment rentFrag;
		GoogleMap mapIn;
		CollectionResponseRental collection = null;

		public RentalNearPoliAndPlotMapTask(RentalFragment rentalFragment,
				GoogleMap map) {
			this.rentFrag = rentalFragment;
			mapIn = map;
		}

		@Override
		protected CollectionResponseRental doInBackground(Void... params) {

			Rentalendpoint.Builder builder = new Rentalendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);
			builder = CloudEndpointUtils.updateBuilder(builder);
			Rentalendpoint endpoint = builder
					.setApplicationName("polimisocial").build();

			try {
				collection = endpoint.listRental().execute();

			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			return collection;
		}

		@Override
		protected void onPostExecute(CollectionResponseRental result) {
			super.onPostExecute(result);

			if (task.isCancelled())
				return;
			if (result == null) {
				Toast.makeText(getActivity(),
						"Retrieving rentals failed.Connection error.",
						Toast.LENGTH_SHORT).show();

			} else {
				if ((result.size() < 1) || (result.isEmpty())) {
					Toast.makeText(getActivity(), "No rental found",
							Toast.LENGTH_SHORT).show();
				} else {

					if (task.isCancelled())
						return;
					MapsInitializer.initialize(rentFrag.getActivity());
					LatLng milano = new LatLng(45.464178, 9.189583);
					CameraUpdate cameraUpdate = CameraUpdateFactory
							.newLatLngZoom(milano, 10);
					mapIn.animateCamera(cameraUpdate);

					if (task.isCancelled())
						return;
					if (!(result.getItems() == null)) {
						
						//fill the List to pass to the listRentalFragment
						rentals = result.getItems();
						//only after data have been passed to listRentalFragment set onclicklistener, otherwise if the user click the textview
						//before, there will be an empty list
						listButton.setVisibility(View.VISIBLE);
						listButton.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								if (task != null) {
									task.cancel(true);
								}
								listener.onSwitchFragmentRental("rentalList", rentals);

							}
						});

						Iterator<Rental> iterator = result.getItems()
								.iterator();
						Rental rental = null;
						while (iterator.hasNext()) {
							rental = iterator.next();
							MarkerOptions marker = new MarkerOptions()
									.position(
											new LatLng(rental.getLatitude(),
													rental.getLongitude()))
													.title(rental.getType()).snippet(rental.getPrice()+"€")
									.icon(BitmapDescriptorFactory
											.fromResource(R.drawable.renticon));

							mapIn.addMarker(marker);

						}

						mapIn.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
							
							@Override
							public void onInfoWindowClick(Marker marker) {
								LatLng pos = marker.getPosition();
								for (Rental rentHouse : collection.getItems()) {
									if (pos.equals(new LatLng(rentHouse
											.getLatitude(), rentHouse
											.getLongitude()))) {
										
										Intent showRelativeCommentsIntent = new Intent(getActivity(),
												ShowRelatedCommentsActivity.class);
										showRelativeCommentsIntent.putExtra("postId", rentHouse.getId());
										showRelativeCommentsIntent.putExtra("notificationCategory",
												WhatToShow.DETAILS.toString());
										showRelativeCommentsIntent.putExtra("type", PostType.RENTAL.toString());
										startActivity(showRelativeCommentsIntent);
										
									}
								}
								
							}
						});
					}
				}

			}

		}
	}
}
