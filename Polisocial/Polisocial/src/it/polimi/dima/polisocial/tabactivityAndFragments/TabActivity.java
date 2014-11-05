package it.polimi.dima.polisocial.tabactivityAndFragments;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.GCMIntentService;
import it.polimi.dima.polisocial.HitOnDialogFragment.HitOnDialogListener;
import it.polimi.dima.polisocial.OAuthAccessActivity;
import it.polimi.dima.polisocial.PreferencesActivity;
import it.polimi.dima.polisocial.ProfileActivity;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.SingleChoiceDialogFragm;
import it.polimi.dima.polisocial.SingleChoiceDialogFragm.FacultyDialogListener;
import it.polimi.dima.polisocial.creationActivities.NewBookActivity;
import it.polimi.dima.polisocial.creationActivities.NewEventActivity;
import it.polimi.dima.polisocial.creationActivities.NewPrivateLessonActivity;
import it.polimi.dima.polisocial.creationActivities.NewRentalActivity;
import it.polimi.dima.polisocial.creationActivities.NewSpottedPostActivity;
import it.polimi.dima.polisocial.entity.hitonendpoint.Hitonendpoint;
import it.polimi.dima.polisocial.entity.hitonendpoint.model.HitOn;
import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser;
import it.polimi.dima.polisocial.foursquare.foursquareendpoint.Foursquareendpoint;
import it.polimi.dima.polisocial.foursquare.foursquareendpoint.model.ResponseObject;
import it.polimi.dima.polisocial.utilClasses.SessionManager;
import it.polimi.dima.polisocial.utilClasses.ShowProgress;
import it.polimi.dima.polisocial.utilClasses.VenueItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Session;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

public class TabActivity extends FragmentActivity implements
		ActionBar.TabListener, HitOnDialogListener, FacultyDialogListener,
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the primary sections of the app. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;
	ViewPager mViewPager;
	PoliUser userSession;
	public SessionManager sessionManager;
	private it.polimi.dima.polisocial.tabactivityAndFragments.TabActivity.GoogleMapFragment.VenuesNearPoliAndPlotMapTask task;
	private LocationClient mLocationClient;
	static Location mCurrentLocation;
	LocationRequest mLocationRequest;
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	private static final int MILLISECONDS_PER_SECOND = 1000;
	private static final int UPDATE_INTERVAL_IN_SECONDS = 5;
	private static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND
			* UPDATE_INTERVAL_IN_SECONDS;
	private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
	private static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND
			* FASTEST_INTERVAL_IN_SECONDS;
	ActionBar actionBar;

	private Boolean gpsAdvice = true;
	private Intent intentGcmNotifica = new Intent();
	public Boolean annoucementFragment = false;

	@Override
	public void onBackPressed() {
		// minimize
		int item = mViewPager.getCurrentItem();
		Fragment currentFragm = mAppSectionsPagerAdapter.mFragmentAtPos2;
		if ((item == 2) && !(currentFragm instanceof AnnouncementsFragment)) {
			mAppSectionsPagerAdapter.mFragmentManager.beginTransaction().remove(currentFragm)
					.commit();
			mAppSectionsPagerAdapter.mFragmentAtPos2 = AnnouncementsFragment.newInstance(mAppSectionsPagerAdapter.listenerAnnouncement);
			mAppSectionsPagerAdapter.notifyDataSetChanged();

		} else {
			moveTaskToBack(true);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		mLocationClient.connect();
	}

	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	public String setActionBarTitle(int position) {
		String title = null;
		switch (position) {
		case 0:
			title = getString(R.string.first_tab_title);
			break;
		case 1:
			title = getString(R.string.second_tab_title);
			break;
		case 2:
			if (mAppSectionsPagerAdapter.mFragmentAtPos2 instanceof SecondHandBookFragment) {
				title = getString(R.string.book_fragment_title);
			} else if (mAppSectionsPagerAdapter.mFragmentAtPos2 instanceof RentalFragment) {
				title = getString(R.string.rental_fragment_title);
			} else if (mAppSectionsPagerAdapter.mFragmentAtPos2 instanceof PrivateLessonFragment) {
				title = getString(R.string.lesson_fragment_title);
			} else {
				title = getString(R.string.third_tab_title);
			}
			break;
		case 3:
			title = getString(R.string.fourth_tab_title);
			break;
		case 4:
			title = getString(R.string.fifth_tab_title);
			break;
		}
		return title;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);

		// ask for faculty at the first user login

		if (getIntent().getBooleanExtra("firstLogin", false)) {
			showNoticeDialog();

		}

		sessionManager = new SessionManager(getApplicationContext());

		servicesConnected();
		mLocationClient = new LocationClient(this, this, this);

		// mCurrentLocation = mLocationClient.getLastLocation();
		mLocationRequest = LocationRequest.create();
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		// Start with updates turned off

		// Create the adapter that will return a fragment for each of the three
		// primary sections
		// of the app.
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(
				getSupportFragmentManager(), this);

		// Set up the action bar.
		actionBar = getActionBar();

		// Specify that the Home/Up button should not be enabled, since there is
		// no hierarchical
		// parent.
		actionBar.setHomeButtonEnabled(false);
		actionBar.setIcon(R.drawable.logo_login);
		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener
		// for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(4); // mantiene memoria delle tab
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between different app sections, select
						// the corresponding tab.
						// We can also use ActionBar.Tab#select() to do this if
						// we have a reference to the
						// Tab.

						actionBar.setSelectedNavigationItem(position);
						actionBar.setTitle(setActionBarTitle(position));
						if (position == 3 && gpsAdvice) {
							LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

							if (locationManager
									.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
								Toast.makeText(getApplicationContext(),
										"GPS is Enabled in your device",
										Toast.LENGTH_SHORT).show();
								mLocationClient.requestLocationUpdates(
										mLocationRequest, TabActivity.this);
							} else {
								showGPSDisabledAlertToUser();
							}
							gpsAdvice = false;
						}
					}

				});

		// For each of the sections in the app, add a tab to the action bar.
		// for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
		// Create a tab with text corresponding to the page title defined by the
		// adapter.
		// Also specify this Activity object, which implements the TabListener
		// interface, as the
		// listener for when this tab is selected.
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.spotted_icon)
		// .setText(mAppSectionsPagerAdapter.getPageTitle(i))
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setIcon(R.drawable.events_icon)
		// .setText(mAppSectionsPagerAdapter.getPageTitle(i))
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab()
				.setIcon(R.drawable.announcements_icon)
				// .setText(mAppSectionsPagerAdapter.getPageTitle(i))
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab()
				.setIcon(R.drawable.restaurants_icon)
				// .setText(mAppSectionsPagerAdapter.getPageTitle(i))
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab()
				.setIcon(R.drawable.notifications_icon)
				// .setText(mAppSectionsPagerAdapter.getPageTitle(i))
				.setTabListener(this));

		if (getIntent().getBooleanExtra("gcmNotification", false)) {
			actionBar.setSelectedNavigationItem(4);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.tab_activity_actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_write_spotted_post:
			startActivity(new Intent(TabActivity.this,
					NewSpottedPostActivity.class));
			return true;
		case R.id.action_add_restaurant:
			if (task != null) {
				task.cancel(true);
			}
			startActivity(new Intent(this, OAuthAccessActivity.class));
			return true;
		case R.id.action_create_event:
			startActivity(new Intent(TabActivity.this, NewEventActivity.class));
			return true;
		case R.id.action_create_book:
			startActivity(new Intent(TabActivity.this, NewBookActivity.class));
			return true;
		case R.id.action_create_lesson:
			startActivity(new Intent(TabActivity.this,
					NewPrivateLessonActivity.class));
			return true;
		case R.id.action_create_rental:
			startActivity(new Intent(TabActivity.this, NewRentalActivity.class));
			return true;
		case R.id.action_show_profile:
			startActivity(new Intent(TabActivity.this, ProfileActivity.class));
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			return true;
		case R.id.action_show_preferences:
			Intent preferencesIntent = new Intent(this,
					PreferencesActivity.class);
			startActivity(preferencesIntent);
			return true;

		case R.id.action_logout:
			gpsAdvice = true;
			Session session = Session.getActiveSession();
			if (session != null && session.isOpened()) {
				session.close();
				session.closeAndClearTokenInformation();
			}
			sessionManager.logoutUser();
			GCMIntentService.unregister(this);

			/**
			 * case R.id.menu_filter_events_culture: item.setChecked(true); //
			 * showProfile(); case R.id.menu_filter_events_fun:
			 * item.setChecked(true); // showProfile(); case
			 * R.id.menu_filter_events_various: item.setChecked(true); //
			 * showProfile(); return true;
			 **/
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		static final int NUM_ITEMS = 5;
		private final FragmentManager mFragmentManager;
		private Fragment mFragmentAtPos3;
		private Fragment mFragmentAtPos0;
		private Fragment mFragmentAtPos2;
		private Fragment mFragmentAtPos4;
		private Fragment mFragmentAtPos1;
		private SavedState mFragmentAtpos3Map = null;
		private SavedState mFragmentAtpos3List = null;
		private SavedState mFragmentBook = null;
		private SavedState mFragmentRental = null;
		private SavedState mFragmentLesson = null;
		private Boolean update = false;
		private TabActivity activity;

		private final class RestaurantsListener implements
				SwitchFragmentListener {

			public void onSwitchFragment() {

				if (mFragmentAtPos3 instanceof GoogleMapFragment) {

					if (mFragmentAtpos3List == null || update == true) {
						mFragmentManager.beginTransaction()
								.remove(mFragmentAtPos3).commit();
						mFragmentAtPos3 = ListVenuesFragment
								.newInstance(listenerRestaurant);

					} else {
						mFragmentAtpos3Map = mFragmentManager
								.saveFragmentInstanceState(mFragmentAtPos3);
						mFragmentAtPos3 = new ListVenuesFragment();
						mFragmentAtPos3
								.setInitialSavedState(mFragmentAtpos3List);

					}

				} else {

					if (mFragmentAtpos3Map == null || update == true) {
						mFragmentManager.beginTransaction()
								.remove(mFragmentAtPos3).commit();
						mFragmentAtPos3 = GoogleMapFragment
								.newInstance(listenerRestaurant);

					} else {
						mFragmentAtpos3List = mFragmentManager
								.saveFragmentInstanceState(mFragmentAtPos3);
						mFragmentAtPos3 = new GoogleMapFragment();
						mFragmentAtPos3
								.setInitialSavedState(mFragmentAtpos3Map);

					}

				}

				notifyDataSetChanged();
			}

			@Override
			public void onSwitchFragmentName(String newFragment) {
			}
		}

		private final class AnnouncementListener implements
				SwitchFragmentListener {

			@Override
			public void onSwitchFragment() {
			}

			// utilizza il parametro per scegliere quale fragm sostituire
			@Override
			public void onSwitchFragmentName(String newFragmentName) {

				if (newFragmentName.equals("book")) {
					if (mFragmentAtPos2 instanceof PrivateLessonFragment) {
						mFragmentLesson = mFragmentManager
								.saveFragmentInstanceState(mFragmentAtPos2);
					} else {
						mFragmentRental = mFragmentManager
								.saveFragmentInstanceState(mFragmentAtPos2);
					}
					mFragmentManager.beginTransaction().remove(mFragmentAtPos2)
							.commit();
					mFragmentAtPos2 = new SecondHandBookFragment();
					mFragmentAtPos2.setInitialSavedState(mFragmentBook);

				}

				if (newFragmentName.equals("rental")) {
					if (mFragmentAtPos2 instanceof PrivateLessonFragment) {
						mFragmentLesson = mFragmentManager
								.saveFragmentInstanceState(mFragmentAtPos2);
					} else {
						mFragmentBook = mFragmentManager
								.saveFragmentInstanceState(mFragmentAtPos2);
					}
					mFragmentManager.beginTransaction().remove(mFragmentAtPos2)
							.commit();
					mFragmentAtPos2 = RentalFragment
							.newInstance(listenerAnnouncement);
					mFragmentAtPos2.setInitialSavedState(mFragmentRental);
				}
				if (newFragmentName.equals("lesson")) {
					if (mFragmentAtPos2 instanceof SecondHandBookFragment) {
						mFragmentBook = mFragmentManager
								.saveFragmentInstanceState(mFragmentAtPos2);
					} else {
						mFragmentRental = mFragmentManager
								.saveFragmentInstanceState(mFragmentAtPos2);
					}
					mFragmentManager.beginTransaction().remove(mFragmentAtPos2)
							.commit();
					mFragmentAtPos2 = new PrivateLessonFragment();

					mFragmentAtPos2.setInitialSavedState(mFragmentLesson);
				}

				if (newFragmentName.equals("rentalList")) {
					mFragmentManager.beginTransaction().remove(mFragmentAtPos2)
							.commit();
					mFragmentAtPos2 = RentalFragmentList
							.newInstance(listenerAnnouncement);
				}

				if (newFragmentName.equals("announcements")) {
					if (mFragmentAtPos2 instanceof SecondHandBookFragment) {
						mFragmentBook = mFragmentManager
								.saveFragmentInstanceState(mFragmentAtPos2);
					} else if (mFragmentAtPos2 instanceof RentalFragment) {
						mFragmentRental = mFragmentManager
								.saveFragmentInstanceState(mFragmentAtPos2);
					} else if (mFragmentAtPos2 instanceof PrivateLessonFragment) {
						mFragmentLesson = mFragmentManager
								.saveFragmentInstanceState(mFragmentAtPos2);
					}
					mFragmentManager.beginTransaction().remove(mFragmentAtPos2)
							.commit();
					mFragmentAtPos2 = AnnouncementsFragment.newInstance(listenerAnnouncement);
				}

				notifyDataSetChanged();
			}

		}

		RestaurantsListener listenerRestaurant = new RestaurantsListener();
		AnnouncementListener listenerAnnouncement = new AnnouncementListener();

		public AppSectionsPagerAdapter(FragmentManager fm,
				TabActivity tabActivity) {
			super(fm);
			mFragmentManager = fm;
			activity = tabActivity;
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
			case 0:
				if (mFragmentAtPos0 == null) {
					mFragmentAtPos0 = new SpottedPostListFragment();
				}
				return mFragmentAtPos0;
			case 1:
				if (mFragmentAtPos1 == null) {
					mFragmentAtPos1 = new EventsFragment();
				}
				return mFragmentAtPos1;
			case 2:
				if (mFragmentAtPos2 == null) {
					mFragmentAtPos2 = AnnouncementsFragment
							.newInstance(listenerAnnouncement);
				}
				return mFragmentAtPos2;
			case 3:
				if (mFragmentAtPos3 == null) {
					mFragmentAtPos3 = GoogleMapFragment
							.newInstance(listenerRestaurant);
				}
				return mFragmentAtPos3;
			case 4:
				if (mFragmentAtPos4 == null) {
					mFragmentAtPos4 = new NotificationFragment(activity);
				}
				return mFragmentAtPos4;
			default:
				return null;
			}
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "Section " + (position + 1);
		}

		@Override
		public int getItemPosition(Object object) {
			if (object instanceof GoogleMapFragment
					&& mFragmentAtPos3 instanceof ListVenuesFragment)
				return POSITION_NONE;
			if (object instanceof ListVenuesFragment
					&& mFragmentAtPos3 instanceof GoogleMapFragment)
				return POSITION_NONE;
			if (object instanceof SecondHandBookFragment
					&& !(mFragmentAtPos2 instanceof SecondHandBookFragment))
				return POSITION_NONE;
			if (object instanceof PrivateLessonFragment
					&& !(mFragmentAtPos2 instanceof PrivateLessonFragment))
				return POSITION_NONE;
			if (object instanceof RentalFragment
					&& !(mFragmentAtPos2 instanceof RentalFragment))
				return POSITION_NONE;
			if (object instanceof AnnouncementsFragment
					&& !(mFragmentAtPos2 instanceof AnnouncementsFragment))
				return POSITION_NONE;

			return POSITION_UNCHANGED;

		}

	}

	public interface SwitchFragmentListener {
		void onSwitchFragment();

		void onSwitchFragmentName(String newFragment);
	}

	public static class GoogleMapFragment extends Fragment {

		GoogleMap map;
		MapView mapView;
		ArrayList<String> listVenuesName = new ArrayList<String>();
		static SwitchFragmentListener listener;
		VenuesNearPoliAndPlotMapTask task;

		public GoogleMapFragment() {
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			setHasOptionsMenu(true);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			super.onCreateView(inflater, container, savedInstanceState);
			View v = inflater.inflate(R.layout.fragment_venues_map, container,
					false);

			// location =
			// lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

			// Gets the MapView from the XML layout and creates it
			mapView = (MapView) v.findViewById(R.id.map);
			mapView.onCreate(savedInstanceState);
			map = mapView.getMap();
			task = new VenuesNearPoliAndPlotMapTask(this, map);
			task.execute();
			TextView buttonView = (TextView) v.findViewById(R.id.button_view);
			buttonView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (task != null) {
						task.cancel(true);
					}

					listener.onSwitchFragment();

				}
			});
			return v;
		}

		@Override
		public void onPrepareOptionsMenu(Menu menu) {
			super.onPrepareOptionsMenu(menu);
			menu.findItem(R.id.action_create_lesson).setVisible(false);
			menu.findItem(R.id.action_create_rental).setVisible(false);
			menu.findItem(R.id.action_create_book).setVisible(false);
			menu.findItem(R.id.action_create_event).setVisible(false);
			// menu.findItem(R.id.menu_filter_events).setVisible(false);
			menu.findItem(R.id.action_write_spotted_post).setVisible(false);
		}

		public static GoogleMapFragment newInstance(
				SwitchFragmentListener googleMapFragmentListener) {
			GoogleMapFragment googleMapFrgm = new GoogleMapFragment();
			listener = googleMapFragmentListener;
			return googleMapFrgm;
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

		private class VenuesNearPoliAndPlotMapTask extends
				AsyncTask<Void, Void, ResponseObject> {

			GoogleMapFragment mapFrag;
			GoogleMap mapIn;

			public VenuesNearPoliAndPlotMapTask(GoogleMapFragment mapFrag,
					GoogleMap map) {
				this.mapFrag = mapFrag;
				mapIn = map;

			}

			@Override
			protected ResponseObject doInBackground(Void... params) {

				Foursquareendpoint.Builder builder = new Foursquareendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);
				builder = CloudEndpointUtils.updateBuilder(builder);
				Foursquareendpoint endpoint = builder.setApplicationName(
						"polimisocial").build();

				ResponseObject result = new ResponseObject();

				String ll = "45.478178,9.228031";
				try {
					result = endpoint.searchVenues(ll).execute();
				} catch (IOException e) {
					System.out.println(e.getMessage());
					result = null;
				}
				return result;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void onPostExecute(ResponseObject result) {

				if (task.isCancelled())
					return;
				if (result == null) {
					Toast.makeText(getActivity(),
							"Retrieving venues failed.Connection error.",
							Toast.LENGTH_SHORT).show();
				} else if (result.getObject() == null) {
					Toast.makeText(getActivity(), result.getException(),
							Toast.LENGTH_SHORT).show();
				} else {
					final ArrayList<ArrayList<String>> venues = ((ArrayList<ArrayList<String>>) result
							.getObject());
					if ((venues.size() < 1) || (venues.isEmpty())) {
						Toast.makeText(getActivity(), "No venues found",
								Toast.LENGTH_SHORT).show();
					} else {

						if (task.isCancelled())
							return;
						MapsInitializer.initialize(mapFrag.getActivity());
						LatLng poli = new LatLng(45.478178, 9.228031);
						CameraUpdate cameraUpdate = CameraUpdateFactory
								.newLatLngZoom(poli, 14);
						mapIn.animateCamera(cameraUpdate);

						int nameIndex = 1;
						int coordIndex = 2;

						String venueName = "";
						String coordinates = "";
						double latitude = 0;
						double longitude = 0;

						Iterator<ArrayList<String>> iterator = venues
								.iterator();

						while (iterator.hasNext()) {
							ArrayList<String> venue = iterator.next();
							venueName = venue.get(nameIndex);// prendo il nome
																// di ogni venue
							coordinates = venue.get(coordIndex); // coordinates
							StringTokenizer coordTok = new StringTokenizer(
									coordinates, ",");

							latitude = Double.parseDouble(coordTok.nextToken()); // latitude
							longitude = Double
									.parseDouble(coordTok.nextToken()); // longitude

							MarkerOptions marker = new MarkerOptions()
									.position(new LatLng(latitude, longitude))
									.title(venueName)
									.icon(BitmapDescriptorFactory
											.fromResource(R.drawable.restaurant));
							mapIn.addMarker(marker);
							listVenuesName.add(venueName);

						}
						if (mCurrentLocation != null) {
							MarkerOptions userMarker = new MarkerOptions()
									.position(
											new LatLng(mCurrentLocation
													.getLatitude(),
													mCurrentLocation
															.getLongitude()))
									.title("You are here")
									.icon(BitmapDescriptorFactory
											.fromResource(R.drawable.youarehere));
							mapIn.addMarker(userMarker);
						}
						mapIn.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

							@Override
							public void onInfoWindowClick(Marker marker) {

								String idVenueFsq = "";
								for (ArrayList<String> venueArrayList : venues) {

									if (venueArrayList.contains(marker
											.getTitle())) {
										idVenueFsq = venueArrayList.get(0);
										break;
									}

								}

								String baseUrl = "http://foursquare.com/venue/";
								StringBuilder urlBuilder = new StringBuilder(
										baseUrl);
								urlBuilder.append(idVenueFsq);
								String url = urlBuilder.toString();
								Intent intent = new Intent(Intent.ACTION_VIEW,
										Uri.parse(url));
								startActivity(intent);

							}
						});

					}
				}
			}
		}

	}

	public static class ListVenuesFragment extends Fragment {

		ListView listVenues;
		ProgressBar progressBar;
		ArrayAdapter<ArrayList<String>> adapter = null;
		ArrayList<String> listVenuesName = new ArrayList<String>();
		VenuesNearPoliTask task;
		static SwitchFragmentListener listener;

		public ListVenuesFragment() {
		}

		public static ListVenuesFragment newInstance(
				SwitchFragmentListener listVenuesFragmentListener) {
			ListVenuesFragment listVenuesFrgm = new ListVenuesFragment();
			listener = listVenuesFragmentListener;
			return listVenuesFrgm;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			setHasOptionsMenu(true);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View v = inflater.inflate(R.layout.fragment_listvenues, container,
					false);

			listVenues = (ListView) v.findViewById(R.id.listViewVenues);
			progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
			task = new VenuesNearPoliTask();
			ShowProgress.showProgress(true, progressBar, listVenues,
					getActivity());
			task.execute();

			TextView buttonView = (TextView) v
					.findViewById(R.id.button_changeToMap);
			buttonView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					task.cancel(true);
					ShowProgress.showProgress(false, progressBar, listVenues,
							getActivity());
					listener.onSwitchFragment();

				}
			});
			return v;
		}

		@Override
		public void onPrepareOptionsMenu(Menu menu) {
			super.onPrepareOptionsMenu(menu);
			menu.findItem(R.id.action_create_event).setVisible(false);
			// menu.findItem(R.id.menu_filter_events).setVisible(false);
			menu.findItem(R.id.action_write_spotted_post).setVisible(false);
		}

		private class VenuesNearPoliTask extends
				AsyncTask<Void, Void, ResponseObject> {

			@Override
			protected ResponseObject doInBackground(Void... params) {
				Foursquareendpoint.Builder builder = new Foursquareendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);
				builder = CloudEndpointUtils.updateBuilder(builder);
				Foursquareendpoint endpoint = builder.setApplicationName(
						"polimisocial").build();

				ResponseObject result = new ResponseObject();

				String ll = "45.478178,9.228031";

				try {
					result = endpoint.searchVenues(ll).execute();
				} catch (IOException e) {
					System.out.println(e.getMessage());
					result = null;
				}
				return result;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void onPostExecute(ResponseObject result) {

				if (task.isCancelled())
					return;
				if (result == null) {
					Toast.makeText(getActivity(),
							"Retrieving venues failed.Connection error.",
							Toast.LENGTH_SHORT).show();
				} else if (result.getObject() == null) {
					Toast.makeText(getActivity(), result.getException(),
							Toast.LENGTH_SHORT).show();
				} else {
					final ArrayList<ArrayList<String>> venues = ((ArrayList<ArrayList<String>>) result
							.getObject());
					if ((venues.size() < 1) || (venues.isEmpty())) {
						Toast.makeText(getActivity(), "No venues found",
								Toast.LENGTH_SHORT).show();
					} else {

						if (task.isCancelled())
							return;
						new RetrieveDistanceVenues().execute(venues);
						/*
						 * int name = 1; Iterator<ArrayList<String>> iterator =
						 * venues .iterator(); while (iterator.hasNext()){
						 * ArrayList<String> venue = iterator.next();
						 * 
						 * listVenuesName.add(venue.get(name));
						 * 
						 * 
						 * }
						 * 
						 * 
						 * adapter = new ArrayAdapter<String>(getActivity(),
						 * android.R.layout.simple_list_item_1, listVenuesName);
						 * listVenues.setAdapter(adapter);
						 * 
						 * listVenues .setOnItemClickListener(new
						 * AdapterView.OnItemClickListener() {
						 * 
						 * @Override public void onItemClick( AdapterView<?>
						 * adapt, final View componente, int pos, long id) {
						 * String idVenueFsq = venues.get(pos) .get(0); String
						 * baseUrl = "http://foursquare.com/venue/";
						 * StringBuilder urlBuilder = new StringBuilder(
						 * baseUrl); urlBuilder.append(idVenueFsq); String url =
						 * urlBuilder.toString(); Intent intent = new Intent(
						 * Intent.ACTION_VIEW, Uri .parse(url));
						 * startActivity(intent);
						 * 
						 * } });
						 */

					}
				}

			}
		}

		public class RetrieveDistanceVenues extends
				AsyncTask<ArrayList<ArrayList<String>>, Void, Void> {

			ArrayList<ArrayList<String>> listVenuesInfo;
			VenueItem venue;
			List<VenueItem> list = new ArrayList<VenueItem>();
			Collection<VenueItem> listItem = new ArrayList<VenueItem>();

			@Override
			protected Void doInBackground(
					ArrayList<ArrayList<String>>... params) {

				listVenuesInfo = params[0];
				Foursquareendpoint.Builder builder = new Foursquareendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);
				builder = CloudEndpointUtils.updateBuilder(builder);
				Foursquareendpoint endpoint = builder.setApplicationName(
						"polimisocial").build();

				if (mCurrentLocation != null) {
					ResponseObject response = null;

					StringBuilder venuesAttribute = new StringBuilder();

					Iterator<ArrayList<String>> iter = listVenuesInfo
							.iterator();
					ArrayList<String> venueArray = new ArrayList<String>();

					if (iter.hasNext()) {
						venueArray = (ArrayList<String>) iter.next();
						venue = new VenueItem(venueArray.get(0),
								venueArray.get(1), venueArray.get(2));
						listItem.add(venue);
						venuesAttribute.append(venue.getCoord());
					}

					while (iter.hasNext()) {

						venueArray = (ArrayList<String>) iter.next();
						venue = new VenueItem(venueArray.get(0),
								venueArray.get(1), venueArray.get(2));
						listItem.add(venue);
						venuesAttribute.append(",");
						venuesAttribute.append(venue.getCoord());
					}

					try {
						response = endpoint.findDistanceAndWalkingDuration(
								mCurrentLocation.getLatitude(),
								mCurrentLocation.getLongitude(),
								venuesAttribute.toString()).execute();

						ArrayList<ArrayList<String>> venuesAttr;
						venuesAttr = (ArrayList<ArrayList<String>>) response
								.getObject();
						Iterator<ArrayList<String>> attrIter = venuesAttr
								.iterator();
						Iterator<VenueItem> venueIter = listItem.iterator();

						while (attrIter.hasNext()) {
							venue = venueIter.next();
							ArrayList<String> attr = attrIter.next();
							venue.setDistanceInMeter(Integer.valueOf(attr
									.get(0)));
							venue.setDistance(attr.get(1));

						}

					} catch (IOException e) {
						e.printStackTrace();
					}

				}

				return null;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void onPostExecute(Void result) {

				list = (List<VenueItem>) listItem;
				Collections.sort(list, new Comparator<VenueItem>() {
					public int compare(VenueItem o1, VenueItem o2) {
						if (o1.getDistanceInMeter() != null
								&& o2.getDistanceInMeter() != null) {
							return o1.getDistanceInMeter()
									- o2.getDistanceInMeter();
						}
						return 0;
					}
				});
				adapter = new ArrayAdapter(getActivity(),
						android.R.layout.simple_list_item_2,
						android.R.id.text1, list) {
					@Override
					public View getView(int position, View convertView,
							ViewGroup parent) {
						View view = super
								.getView(position, convertView, parent);
						TextView text1 = (TextView) view
								.findViewById(android.R.id.text1);
						TextView text2 = (TextView) view
								.findViewById(android.R.id.text2);

						text1.setText(list.get(position).getName());
						text2.setText(list.get(position).getDistance());
						return view;
					}
				};
				ShowProgress.showProgress(false, progressBar, listVenues,
						getActivity());
				listVenues.setAdapter(adapter);

				listVenues
						.setOnItemClickListener(new AdapterView.OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> adapt,
									final View componente, int pos, long id) {
								String idVenueFsq = list.get(pos).getVenueId();
								String baseUrl = "http://foursquare.com/venue/";
								StringBuilder urlBuilder = new StringBuilder(
										baseUrl);
								urlBuilder.append(idVenueFsq);
								String url = urlBuilder.toString();
								Intent intent = new Intent(Intent.ACTION_VIEW,
										Uri.parse(url));
								startActivity(intent);

							}
						});

				super.onPostExecute(result);
			}

		}
	}

	// faculty dialog
	public void showNoticeDialog() {
		// Create an instance of the dialog fragment and show it
		DialogFragment dialog = new SingleChoiceDialogFragm();
		dialog.show(getFragmentManager(), "SingleChoiceDialogFragm");
	}

	// dialog faculty
	@Override
	public void onFacultyDialogPositiveClick(String faculty) {
		new UpdateFacultyPoliUser(Long.valueOf(sessionManager.getUserDetails()
				.get(SessionManager.KEY_USERID))).execute(faculty);

	}

	// dialog HitOn
	@Override
	public void onHitOnDialogPositiveClick(String message, Bundle bundle) {
		long userId = bundle.getLong("userId");
		long postId = bundle.getLong("postId");
		String name = bundle.getString("name");
		new InsertHitOn(name, message).execute(userId, postId);

	}

	public class UpdateFacultyPoliUser extends AsyncTask<String, Void, String> {

		private Long userId;

		public UpdateFacultyPoliUser(Long userId) {
			this.userId = userId;
		}

		@Override
		protected String doInBackground(String... params) {

			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);
			Poliuserendpoint endpoint = builder.setApplicationName(
					"polimisocial").build();

			try {
				endpoint.updateFacultyPoliUser(params[0], userId).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return params[0];
		}

		@Override
		protected void onPostExecute(String faculty) {
			super.onPostExecute(faculty);
			sessionManager.setFaculty(faculty);
		}

	}

	// creo hitOn e lo inserisco nel db
	public class InsertHitOn extends AsyncTask<Long, Void, Boolean> {

		private Hitonendpoint end;
		private String seducerName;
		private String message;
		HitOn hitOn;

		public InsertHitOn(String name, String message) {
			seducerName = name;
			this.message = message;
		}

		@Override
		protected Boolean doInBackground(Long... params) {

			Hitonendpoint.Builder builder = new Hitonendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);
			builder = CloudEndpointUtils.updateBuilder(builder);
			end = builder.setApplicationName("polimisocial").build();
			hitOn = new HitOn();
			hitOn.setSeducerId(params[0]);
			hitOn.setPostId(params[1]);
			hitOn.setAuthorName(seducerName);
			Calendar calendar = Calendar.getInstance();
			Date now = calendar.getTime();
			hitOn.setTimestamp(new DateTime(now));
			hitOn.setContact(message);
			Boolean response = true;
			try {
				end.insertHitOn(hitOn).execute();
			} catch (IOException e) {
				e.printStackTrace();
				response = false;
			}
			return response;
		}

		// on post execute Toast message
		@Override
		protected void onPostExecute(Boolean result) {
			if (result) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"DONE! You have sent a message", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_VERTICAL,
						Gravity.CENTER_HORIZONTAL, 0);
				toast.show();
				new SendHitOnNotification().execute(hitOn);
			} else {
				Toast toast = Toast.makeText(getApplicationContext(),
						"FAILED! Error connection", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_VERTICAL,
						Gravity.CENTER_HORIZONTAL, 0);
				toast.show();
			}

			super.onPostExecute(result);

		}
	}

	// mando notifica per hitOn
	public class SendHitOnNotification extends AsyncTask<HitOn, Void, Void> {

		@Override
		protected Void doInBackground(HitOn... params) {

			Hitonendpoint.Builder builder = new Hitonendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);
			builder = CloudEndpointUtils.updateBuilder(builder);
			Hitonendpoint endHitOn = builder.setApplicationName("polimisocial")
					.build();

			try {
				endHitOn.sendHitOnNotification(params[0]).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}
	}

	// Define a DialogFragment that displays the error dialog
	public static class ErrorDialogFragment extends DialogFragment {
		// Global field to contain the error dialog
		private Dialog mDialog;

		// Default constructor. Sets the dialog field to null
		public ErrorDialogFragment() {
			super();
			mDialog = null;
		}

		// Set the dialog to display
		public void setDialog(Dialog dialog) {
			mDialog = dialog;
		}

		// Return a Dialog to the DialogFragment.
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			return mDialog;
		}
	}

	/*
	 * Handle results returned to the FragmentActivity by Google Play services
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Decide what to do based on the original request code
		switch (requestCode) {

		case CONNECTION_FAILURE_RESOLUTION_REQUEST:
			/*
			 * If the result code is Activity.RESULT_OK, try to connect again
			 */
			switch (resultCode) {
			case Activity.RESULT_OK:
				/*
				 * Try the request again
				 */
				mLocationClient.connect();
				break;
			}

		}
	}

	private boolean servicesConnected() {
		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d("Location Updates", "Google Play services is available.");
			// Continue
			return true;
			// Google Play services was not available for some reason.
			// resultCode holds the error code.
		} else {
			// Get the error dialog from Google Play services
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
					resultCode, this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

			// If Google Play services can provide an error dialog
			if (errorDialog != null) {
				// Create a new DialogFragment for the error dialog
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				// Set the dialog in the DialogFragment
				errorFragment.setDialog(errorDialog);
				// Show the error dialog in the DialogFragment
				errorFragment.show(getFragmentManager(), "Location Updates");
			}
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this,
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			/*
			 * If no resolution is available, display a dialog to the user with
			 * the error.
			 */
			showDialog(connectionResult.getErrorCode());
		}

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// Display the connection status
		mCurrentLocation = mLocationClient.getLastLocation();
		if (mCurrentLocation == null)
			mLocationClient.requestLocationUpdates(mLocationRequest, this);
		/*
		 * else Toast.makeText( this, "Location1: " +
		 * mCurrentLocation.getLatitude() + ", " +
		 * mCurrentLocation.getLongitude(), Toast.LENGTH_SHORT) .show();
		 */
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLocationChanged(Location location) {
		mLocationClient.removeLocationUpdates(this);
		mCurrentLocation = mLocationClient.getLastLocation();
		/*
		 * Toast.makeText( this, "Location2: " + mCurrentLocation.getLatitude()
		 * + ", " + mCurrentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
		 */

	}

	@Override
	protected void onNewIntent(Intent intent) {
		intentGcmNotifica = intent;
		if (intent.getBooleanExtra("gcmNotification", false)) {
			actionBar.setSelectedNavigationItem(4);
		}
		super.onNewIntent(intent);
	}

	private void showGPSDisabledAlertToUser() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage(
						"GPS is disabled in your device.Would you like to enable it?")
				.setCancelable(false)
				.setPositiveButton("Goto Settings Page To Enable GPS",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent callGPSSettingIntent = new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
								startActivity(callGPSSettingIntent);
							}
						});
		alertDialogBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

	public Intent getNotificationIntent() {
		return intentGcmNotifica;
	}

	public void setNotificationIntent(Intent intent) {
		intentGcmNotifica = intent;
	}

}