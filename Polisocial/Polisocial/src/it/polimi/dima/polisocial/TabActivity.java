package it.polimi.dima.polisocial;



import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser;
import it.polimi.dima.polisocial.foursquare.foursquareendpoint.Foursquareendpoint;
import it.polimi.dima.polisocial.foursquare.foursquareendpoint.model.ResponseObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.Session;
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
	 * primary sections of the app. We use a {@link android.support.v4.app.FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;
	ViewPager mViewPager;
	PoliUser userSession;
	private String email;
	SessionManager sessionManager; 
	

	@Override
	public void onBackPressed() {
		//minimize
		moveTaskToBack(true);
	}

	public String setActionBarTitle(int position){
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
		
		sessionManager = new SessionManager(getApplicationContext());
		email=sessionManager.getUserDetails().get(SessionManager.KEY_EMAIL);
		boolean firstLogin = getIntent().getBooleanExtra("firstLogin",false);
		
		
		//controllo primo login dell'utente caso facebook
		if(firstLogin){
		
			//lancio dialog facoltà
			//showNoticeDialog();
			
			//setto id nel sessionManager e attivo notifiche
			new setUserIdAndRegisterTask().execute();
			
		}

		// Create the adapter that will return a fragment for each of the three primary sections
		// of the app.
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();

		// Specify that the Home/Up button should not be enabled, since there is no hierarchical
		// parent.
		actionBar.setHomeButtonEnabled(false);
		actionBar.setIcon(R.drawable.logo_login);
		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager.setOffscreenPageLimit(5);  	//mantiene memoria delle tab
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When swiping between different app sections, select the corresponding tab.
				// We can also use ActionBar.Tab#select() to do this if we have a reference to the
				// Tab.
				actionBar.setSelectedNavigationItem(position);
				actionBar.setTitle(setActionBarTitle(position));
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
		//TODO da fare controllo per icona notifiche...
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
	        	startActivity(new Intent(TabActivity.this, NewSpottedPostActivity.class));
	        	return true;
	        case R.id.action_add_restaurant:
	            //showProfile();
	            return true;
	        case R.id.action_create_event:
	            //showProfile();
	            return true;
	        case R.id.action_show_profile:
	            //showProfile();
	            return true;
	        case R.id.action_show_preferences:
	        	Intent preferencesIntent = new Intent(this, PreferencesActivity.class);
				startActivity(preferencesIntent);
	            return true;
	      
	        case R.id.action_logout:
	        	Session session = Session.getActiveSession();
	    		if (session != null && session.isOpened()) {
	    			session.close();
	    			session.closeAndClearTokenInformation();
	    		}
	        	sessionManager.logoutUser();
	            
	        case R.id.menu_filter_events_culture:
	        	 item.setChecked(true);
	        	 //showProfile();
	        case R.id.menu_filter_events_fun:
	        	 item.setChecked(true);
	        	 //showProfile();
	        case R.id.menu_filter_events_various:
	        	 item.setChecked(true);
	        	 //showProfile();
	            return true;
	           
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

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
		private Fragment mFragmentAtPos0;
		private Fragment mFragmentAtPos2;
		private Fragment mFragmentAtPos4;
		private Fragment mFragmentAtPos1;
		private SavedState mFragmentAtpos3Map=null;
		private SavedState mFragmentAtpos3List=null;
		private Boolean update = false;


		private final class RestaurantsListener implements RestaurantsFragmentListener{

			public void onSwitchFragment(){
				
				
				if (mFragmentAtPos3 instanceof GoogleMapFragment){
	
					if ( mFragmentAtpos3List == null || update == true){   //TODO controllare che non si sia aggiunto un nuovo locale,in quel caso ricreare fragment
						mFragmentManager.beginTransaction().remove(mFragmentAtPos3).commit();
						mFragmentAtPos3 = ListVenuesFragment.newInstance(listener);

					}else{
						mFragmentAtpos3Map= mFragmentManager.saveFragmentInstanceState(mFragmentAtPos3);
						mFragmentAtPos3 = new ListVenuesFragment();
						mFragmentAtPos3.setInitialSavedState(mFragmentAtpos3List);
				
					}
					
				}else {
					
					if ( mFragmentAtpos3Map == null || update == true){
						mFragmentManager.beginTransaction().remove(mFragmentAtPos3).commit();
						mFragmentAtPos3 = GoogleMapFragment.newInstance(listener);
						
					}else {
						mFragmentAtpos3List= mFragmentManager.saveFragmentInstanceState(mFragmentAtPos3);
						mFragmentAtPos3 = new GoogleMapFragment();
						mFragmentAtPos3.setInitialSavedState(mFragmentAtpos3Map);
					

					}
					
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
				if (mFragmentAtPos0 == null){
					mFragmentAtPos0 = new SpottedPostListFragment();
					}
					return mFragmentAtPos0;
			case 1:
				if (mFragmentAtPos1 == null){
					mFragmentAtPos1 = new EventsFragment();
					}
					return mFragmentAtPos1;
			case 2:
				if (mFragmentAtPos2 == null){
					mFragmentAtPos2 = new AnnouncementsFragment();
					}
					return mFragmentAtPos2;
			case 3:
				if (mFragmentAtPos3 == null){
					mFragmentAtPos3= GoogleMapFragment.newInstance(listener);
				}
				return mFragmentAtPos3;
			case 4:
				if (mFragmentAtPos4 == null){
					mFragmentAtPos4 = new NotificationFragment();
					}
					return mFragmentAtPos4;
			default: return null;
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


	public static class GoogleMapFragment extends Fragment {

		GoogleMap map;
		MapView mapView;
		ArrayList<String> listVenuesName = new ArrayList<String>();
		static RestaurantsFragmentListener listener;
		VenuesNearPoliAndPlotMapTask task;


		public GoogleMapFragment() {
		}

	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);

	        setHasOptionsMenu(true);
	    }
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

			super.onCreateView(inflater, container, savedInstanceState);
			View v = inflater.inflate(R.layout.fragment_map, container, false);
			// Gets the MapView from the XML layout and creates it
			
			mapView = (MapView) v.findViewById(R.id.map);
			mapView.onCreate(savedInstanceState);
			map = mapView.getMap();
			task = new VenuesNearPoliAndPlotMapTask(this,map);
			task.execute();
			Button buttonView= (Button) v.findViewById(R.id.button_view);
			buttonView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					task.cancel(true);
					listener.onSwitchFragment();
					
				}
			});
			return v;
		}

	    @Override
	    public void onPrepareOptionsMenu(Menu menu) {
	        super.onPrepareOptionsMenu(menu);
	        menu.findItem(R.id.action_create_event).setVisible(false);
	        menu.findItem(R.id.menu_filter_events).setVisible(false);
	        menu.findItem(R.id.action_write_spotted_post).setVisible(false);
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

				if(task.isCancelled())return;
				if (result == null) {
					Toast.makeText(getActivity(), "Retrieving venues failed.Connection error.",Toast.LENGTH_SHORT).show();
				} else if ( result.getObject()== null){
					Toast.makeText(getActivity(), result.getException(),Toast.LENGTH_SHORT).show();
				} else {
					final ArrayList<ArrayList<String>> venues = ((ArrayList<ArrayList<String>>) result.getObject());
					if ( (venues.size() < 1)   ||   (venues.isEmpty()) ) { 
						Toast.makeText(getActivity(), "No venues found",Toast.LENGTH_SHORT).show();
					} else {

						if(task.isCancelled())return;
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
		VenuesNearPoliTask task;
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
			task = new VenuesNearPoliTask();
			task.execute();
			

			Button buttonView= (Button) v.findViewById(R.id.button_changeToMap);
			buttonView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					task.cancel(true);
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

				if(task.isCancelled())return;
				if (result == null) {
					Toast.makeText(getActivity(), "Retrieving venues failed.Connection error.",Toast.LENGTH_SHORT).show();
				} else if ( result.getObject()== null){
					Toast.makeText(getActivity(), result.getException(),Toast.LENGTH_SHORT).show();
				} else {
					final ArrayList<ArrayList<String>> venues = ((ArrayList<ArrayList<String>>) result.getObject());
					if ( (venues.size() < 1)   ||   (venues.isEmpty()) ) { 
						Toast.makeText(getActivity(), "No venues found",Toast.LENGTH_SHORT).show();
					} else {

						if(task.isCancelled())return;
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

	/*
	@Override
	public void onDialogPositiveClick(String faculty) {
		
		this.faculty = faculty;
		//new updateUserTask().execute();
		
	}*/
		
	private class setUserIdAndRegisterTask extends AsyncTask<Void, Void, Long>  {


		Long id;
		
		@Override
		protected Long doInBackground(Void... params) {
			
			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);

			builder = CloudEndpointUtils.updateBuilder(builder);
			Poliuserendpoint endpoint = builder.setApplicationName("polimisocial").build();
			
			try {

				userSession = endpoint.checkForDuplicateEmail(email).execute();
				id = userSession.getUserId();
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return id;
		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(Long result) {
			super.onPostExecute(result);
			String s = Long.toString(result);
			sessionManager.setId(s);
			GCMIntentService.register(getApplicationContext());
		}
		
		
	}

	 public void showNoticeDialog() {
	        // Create an instance of the dialog fragment and show it
	        DialogFragment dialog = new SingleChoiceDialogFragm();
	        dialog.show(getFragmentManager(), "SingleChoiceDialogFragm");
	    }

}
