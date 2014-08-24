package it.polimi.dima.polisocial;



import it.polimi.dima.polisocial.foursquare.foursquareendpoint.Foursquareendpoint;
import it.polimi.dima.polisocial.foursquare.foursquareendpoint.model.ResponseObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class TabActivity extends FragmentActivity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
	 * three primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will display the three primary sections of the app, one at a
	 * time.
	 */
	ViewPager mViewPager;




	public String actionBarTitle(int position){
		String title=null;
		switch(position){
		case 0:
			title = getString(R.string.first_tab_title);
			break;
		case 1:
			title = getString(R.string.second_tab_title);
			break;
		case 2:
			title = getString(R.string.third_tab_title);
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
		Toast.makeText(this,"Ciao "+getIntent().getStringExtra("name")+"!",Toast.LENGTH_LONG).show();
		
		// Create the adapter that will return a fragment for each of the three primary sections
		// of the app.
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();

		// Specify that the Home/Up button should not be enabled, since there is no hierarchical
		// parent.
		actionBar.setHomeButtonEnabled(false);

		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When swiping between different app sections, select the corresponding tab.
				// We can also use ActionBar.Tab#select() to do this if we have a reference to the
				// Tab.
				actionBar.setSelectedNavigationItem(position);
				actionBar.setTitle(actionBarTitle(position));
			}
		});

		// For each of the sections in the app, add a tab to the action bar.
		// for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
		// Create a tab with text corresponding to the page title defined by the adapter.
		// Also specify this Activity object, which implements the TabListener interface, as the
		// listener for when this tab is selected.
		actionBar.addTab(
				actionBar.newTab().setIcon(R.drawable.spotted_icon)
				//.setText(mAppSectionsPagerAdapter.getPageTitle(i))
				.setTabListener(this));
		actionBar.addTab(
				actionBar.newTab().setIcon(R.drawable.events_icon)
				//.setText(mAppSectionsPagerAdapter.getPageTitle(i))
				.setTabListener(this));
		actionBar.addTab(
				actionBar.newTab().setIcon(R.drawable.announcements_icon)
				//.setText(mAppSectionsPagerAdapter.getPageTitle(i))
				.setTabListener(this));
		actionBar.addTab(
				actionBar.newTab().setIcon(R.drawable.restaurants_icon)
				//.setText(mAppSectionsPagerAdapter.getPageTitle(i))
				.setTabListener(this));
		actionBar.addTab(
				actionBar.newTab().setIcon(R.drawable.notifications_icon)
				//.setText(mAppSectionsPagerAdapter.getPageTitle(i))
				.setTabListener(this));
	}
	//}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
	 * sections of the app.
	 */
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		static final int NUM_ITEMS = 5;
		private final FragmentManager mFragmentManager;
		private Fragment mFragmentAtPos3;


		private final class RestaurantsListener implements RestaurantsFragmentListener{

			public void onSwitchFragment(){
				mFragmentManager.beginTransaction().remove(mFragmentAtPos3).commit();
				if (mFragmentAtPos3 instanceof GoogleMapFragment){
					mFragmentAtPos3=ListVenuesFragment.newInstance(listener);
				}else {
					mFragmentAtPos3=GoogleMapFragment.newInstance(listener);
				}
				notifyDataSetChanged();
			}
		}
		RestaurantsListener listener = new RestaurantsListener();

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			mFragmentManager=fm;
		}

		@Override
		public Fragment getItem(int i) {
			switch (i) {
			case 0:
				return new LaunchpadSectionFragment();
			case 3:
				if (mFragmentAtPos3 == null){
					mFragmentAtPos3= GoogleMapFragment.newInstance(listener);
				}
				return mFragmentAtPos3;
			default:
				// The other sections of the app are dummy placeholders.
				Fragment fragment = new DummySectionFragment();
				Bundle args = new Bundle();
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
				fragment.setArguments(args);
				return fragment;
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
			if (object instanceof GoogleMapFragment && mFragmentAtPos3 instanceof ListVenuesFragment)
				return POSITION_NONE;
			if (object instanceof ListVenuesFragment && mFragmentAtPos3 instanceof GoogleMapFragment)
				return POSITION_NONE;
			return POSITION_UNCHANGED;
		}

	}
	public interface RestaurantsFragmentListener {
		void onSwitchFragment();
	}

	/**
	 * A fragment that launches other parts of the demo application.
	 */
	public static class LaunchpadSectionFragment extends Fragment {

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section_launchpad, container, false);

			// Demonstration of a collection-browsing activity.
			rootView.findViewById(R.id.demo_collection_button)
			.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(getActivity(), CollectionDemoActivity.class);
					startActivity(intent);
				}
			});

			// Demonstration of navigating to external activities.
			rootView.findViewById(R.id.demo_external_activity)
			.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					// Create an intent that asks the user to pick a photo, but using
					// FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET, ensures that relaunching
					// the application from the device home screen does not return
					// to the external activity.
					Intent externalActivityIntent = new Intent(Intent.ACTION_PICK);
					externalActivityIntent.setType("image/*");
					externalActivityIntent.addFlags(
							Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
					startActivity(externalActivityIntent);
				}
			});

			return rootView;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {

		public static final String ARG_SECTION_NUMBER = "section_number";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_section_dummy, container, false);
			Bundle args = getArguments();
			((TextView) rootView.findViewById(android.R.id.text1)).setText(
					getString(R.string.dummy_section_text, args.getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

	public static class GoogleMapFragment extends Fragment {

		GoogleMap map;
		MapView mapView;
		ArrayList<String> listVenuesName = new ArrayList<String>();
		static RestaurantsFragmentListener listener;
		ProgressBar progress;


		public GoogleMapFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


			View v = inflater.inflate(R.layout.fragment_map, container, false);



			// Gets the MapView from the XML layout and creates it
			mapView = (MapView) v.findViewById(R.id.map);
			mapView.onCreate(savedInstanceState);
			map = mapView.getMap();
			new VenuesNearPoliAndPlotMapTask(this,map).execute();
			Button buttonView= (Button) v.findViewById(R.id.button_view);
			buttonView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					listener.onSwitchFragment();

				}
			});
			return v;
		}

		public static GoogleMapFragment newInstance(RestaurantsFragmentListener googleMapFragmentListener) {
			GoogleMapFragment googleMapFrgm = new GoogleMapFragment();
			listener=googleMapFragmentListener;
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

		private class VenuesNearPoliAndPlotMapTask extends AsyncTask<Void, Void, ResponseObject> {

			GoogleMapFragment mapFrag;
			GoogleMap mapIn;

			public VenuesNearPoliAndPlotMapTask(GoogleMapFragment mapFrag,GoogleMap map){
				this.mapFrag=mapFrag;
				mapIn=map;
			}

			
			@Override
			protected ResponseObject doInBackground(Void... params) {

				Foursquareendpoint.Builder builder = new Foursquareendpoint.Builder(
						AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
				builder = CloudEndpointUtils.updateBuilder(builder);
				Foursquareendpoint endpoint = builder.setApplicationName("polimisocial").build();


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



				if (result == null) {
					Toast.makeText(getActivity(), "Retrieving venues failed.Connection error.",Toast.LENGTH_SHORT).show();
				} else if ( result.getObject()== null){
					Toast.makeText(getActivity(), result.getException(),Toast.LENGTH_SHORT).show();
				} else {
					final ArrayList<ArrayList<String>> venues = ((ArrayList<ArrayList<String>>) result.getObject());
					if ( (venues.size() < 1)   ||   (venues.isEmpty()) ) { 
						Toast.makeText(getActivity(), "No venues found",Toast.LENGTH_SHORT).show();
					} else {

						MapsInitializer.initialize(mapFrag.getActivity());
						LatLng poli = new LatLng(45.478178,9.228031);
						CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(poli, 14);
						mapIn.animateCamera(cameraUpdate); 

						int nameIndex = 1;
						int coordIndex = 2;


						String venueName = "";
						String coordinates="";
						double latitude=0;
						double longitude=0;

						Iterator<ArrayList<String>> iterator = venues.iterator();
						while (iterator.hasNext()){
							ArrayList<String> venue = iterator.next();  
							venueName=venue.get(nameIndex);// prendo il nome di ogni venue 
							coordinates=venue.get(coordIndex); //coordinates
							StringTokenizer coordTok = new StringTokenizer(coordinates, ","); 

							latitude= Double.parseDouble(coordTok.nextToken()); //latitude
							longitude=Double.parseDouble(coordTok.nextToken()); //longitude

							MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(venueName);
							mapIn.addMarker(marker);
							listVenuesName.add(venueName);

						}


						mapIn.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

							@Override
							public void onInfoWindowClick(Marker marker) {

								String idVenueFsq=""; 
								for(ArrayList<String> venueArrayList : venues){

									if (venueArrayList.contains(marker.getTitle())){
										idVenueFsq=venueArrayList.get(0);
										break;
									}


								}

								String baseUrl = "http://foursquare.com/venue/";
								StringBuilder urlBuilder = new StringBuilder(baseUrl);
								urlBuilder.append(idVenueFsq);
								String url = urlBuilder.toString();
								Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
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
		ArrayAdapter<String> adapter = null;
		ArrayList<String> listVenuesName = new ArrayList<String>();
		static RestaurantsFragmentListener listener;

		public ListVenuesFragment() {
		}

		public static ListVenuesFragment newInstance(RestaurantsFragmentListener listVenuesFragmentListener) {
			ListVenuesFragment listVenuesFrgm = new ListVenuesFragment();
			listener= listVenuesFragmentListener;
			return listVenuesFrgm;
		}


		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


			View v = inflater.inflate(R.layout.fragment_listvenues, container, false);

			listVenues = (ListView) v.findViewById(R.id.listViewVenues);
			new VenuesNearPoliTask().execute();
			

			Button buttonView= (Button) v.findViewById(R.id.button_changeToMap);
			buttonView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					listener.onSwitchFragment();

				}
			});
			return v;
		}

		private class VenuesNearPoliTask extends AsyncTask<Void, Void , ResponseObject>{
			
			
			@Override
			protected ResponseObject doInBackground(Void... params) {
				Foursquareendpoint.Builder builder = new Foursquareendpoint.Builder(
						AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
				builder = CloudEndpointUtils.updateBuilder(builder);
				Foursquareendpoint endpoint = builder.setApplicationName("polimisocial").build();


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

				if (result == null) {
					Toast.makeText(getActivity(), "Retrieving venues failed.Connection error.",Toast.LENGTH_SHORT).show();
				} else if ( result.getObject()== null){
					Toast.makeText(getActivity(), result.getException(),Toast.LENGTH_SHORT).show();
				} else {
					final ArrayList<ArrayList<String>> venues = ((ArrayList<ArrayList<String>>) result.getObject());
					if ( (venues.size() < 1)   ||   (venues.isEmpty()) ) { 
						Toast.makeText(getActivity(), "No venues found",Toast.LENGTH_SHORT).show();
					} else {


						int name = 1;
						Iterator<ArrayList<String>> iterator = venues.iterator();
						while (iterator.hasNext())
							listVenuesName.add(iterator.next().get(name));  // prendo il nome di ogni venue 
						adapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,listVenuesName );
						listVenues.setAdapter(adapter);

						listVenues.setOnItemClickListener(new AdapterView.OnItemClickListener() {  
							@Override  
							public void onItemClick(AdapterView<?> adapt, final View componente, int pos, long id){    
								String idVenueFsq = venues.get(pos).get(0);
								String baseUrl = "http://foursquare.com/venue/";
								StringBuilder urlBuilder = new StringBuilder(baseUrl);
								urlBuilder.append(idVenueFsq);
								String url = urlBuilder.toString();
								Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
								startActivity(intent);

							}         
						});  
							

					}
				}

			}
		}
	}


}
