package it.polimi.dima.polisocial.tabactivityAndFragments;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.entity.rentalendpoint.Rentalendpoint;
import it.polimi.dima.polisocial.entity.rentalendpoint.model.CollectionResponseRental;
import it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental;

import java.io.IOException;
import java.util.Iterator;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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

	GoogleMap map;
	MapView mapView;
	private RentalNearPoliAndPlotMapTask task;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.fragment_rental_map, container, false);

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

					if (!result.isEmpty()) {
						Iterator<Rental> iterator = result.getItems()
								.iterator();
						Rental rental = null;
						while (iterator.hasNext()) {
							rental = iterator.next();
							MarkerOptions marker = new MarkerOptions()
									.position(
											new LatLng(rental.getLatitude(),
													rental.getLongitude()))
									.icon(BitmapDescriptorFactory
											.fromResource(R.drawable.renticon));

							mapIn.addMarker(marker);

						}

						mapIn.setOnMarkerClickListener(new OnMarkerClickListener() {

							@Override
							public boolean onMarkerClick(Marker marker) {
								LatLng pos = marker.getPosition();
								for (Rental rentHouse : collection.getItems()) {
									if (pos.equals(new LatLng(rentHouse
											.getLatitude(), rentHouse
											.getLongitude()))) {
										rentHouse.getId();
										// TODO passare id e tipo...
										return true;
									}
								}

								return false;
							}
						});
					}
				}

			}

		}
	}
}
