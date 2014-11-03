package it.polimi.dima.polisocial.creationActivities;

import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.RegistrationActivity;
import it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental;
import it.polimi.dima.polisocial.utilClasses.SessionManager;
import it.polimi.dima.polisocial.utilClasses.ShowProgress;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.internal.mc;
import com.google.android.gms.internal.md;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Sleeper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.test.RenamingDelegatingContext;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;

public class NewRentalActivity extends Activity {

	private static final int RESULT_LOAD_PICTURE = 1;
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
			.permitAll().build();

	boolean isAutoCompChoice = false;
	private AutoCompleteTextView autoCompView;
	private EditText mTitle;
	private EditText mPriceView;
	private EditText mTypeView;
	private EditText mSquareMeterView;;
	private EditText mAvailabilityView;
	private EditText mContactView;
	private ProgressBar mProgressViewFullScreen;
	private View mRentalCreation;
	private SessionManager sessionManager;
	private EditText mDescriptionView;
	private EditText mStreetNumView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_rental);

		StrictMode.setThreadPolicy(policy);
		sessionManager = new SessionManager(getApplicationContext());
		
		autoCompView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
		autoCompView.setAdapter(new PlacesAutoCompleteAdapter(this,
				R.layout.list_item));
		autoCompView.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				isAutoCompChoice = false;

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		autoCompView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				isAutoCompChoice = true;

			}
		});
		autoCompView.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!isAutoCompChoice && !hasFocus) {
					autoCompView
							.setError(getString(R.string.error_autocomplete));
					
				}

			}
		});
		
		
		autoCompView.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				 EditText eText = (EditText)v;

		            // remove error sign
		            eText.setError(null);
		            
				return false;
			}
	    });
		
		mTitle = (EditText) findViewById(R.id.rental_title);
		mPriceView = (EditText) findViewById(R.id.rental_price);
		mTypeView = (EditText) findViewById(R.id.rental_type);
		mSquareMeterView = (EditText) findViewById(R.id.rental_meter);
		mAvailabilityView = (EditText) findViewById(R.id.rental_date);
		mContactView = (EditText) findViewById(R.id.rental_contact);
		mRentalCreation = findViewById(R.id.rental_creation_form);
		mDescriptionView = (EditText) findViewById(R.id.rental_description);
		mStreetNumView = (EditText) findViewById(R.id.rental_street_number);

		mAvailabilityView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// To show current date in the datepicker
				Calendar mcurrentDate = Calendar.getInstance();
				int mYear = mcurrentDate.get(Calendar.YEAR);
				int mMonth = mcurrentDate.get(Calendar.MONTH);
				int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog mDatePicker = new DatePickerDialog(
						NewRentalActivity.this, new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								mAvailabilityView.setText(dayOfMonth + "/"
										+ (++monthOfYear) + "/" + year);

							}
						}, mYear, mMonth, mDay);
				mDatePicker.setTitle("Available from:");
				mDatePicker.show();
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_spotted_post, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_post_post) {
			View focusView = null;
			boolean cancel = false;

			String streetNumber = mStreetNumView.getText().toString();
			if(TextUtils.isEmpty(streetNumber)){
				mStreetNumView.setError(getString(R.string.error_field_required));
				focusView = mStreetNumView;
				cancel = true;
			}
			
			String title = mTitle.getText().toString();
			if(TextUtils.isEmpty(title)){
				mTitle.setError(getString(R.string.error_field_required));
				focusView = mTitle;
				cancel = true;
			}
			
			// price control
			String priceString = mPriceView.getText().toString();
			if (TextUtils.isEmpty(priceString)) {
				mPriceView.setError(getString(R.string.error_field_required));
				focusView = mPriceView;
				cancel = true;
			} else if (!priceString.matches("[0-9]+")) {
				mPriceView.setError(getString(R.string.error_invalid_price));
				focusView = mPriceView;
				cancel = true;
			}
			// type control
			String type = mTypeView.getText().toString();
			if (TextUtils.isEmpty(type)) {
				mTypeView.setError(getString(R.string.error_field_required));
				focusView = mTypeView;
				cancel = true;
			}

			String meters = mSquareMeterView.getText().toString();
			if (TextUtils.isEmpty(type)) {
				mSquareMeterView
						.setError(getString(R.string.error_field_required));
				focusView = mTypeView;
				cancel = true;
			} else if (!meters.matches("[0-9]+")) {
				mSquareMeterView
						.setError(getString(R.string.error_invalid_meters));
				focusView = mSquareMeterView;
				cancel = true;
			}

			String date = mAvailabilityView.getText().toString();
			if (TextUtils.isEmpty(date)) {
				mAvailabilityView
						.setError(getString(R.string.error_field_required));
				focusView = mAvailabilityView;
				cancel = true;
			}

			String contact = mContactView.getText().toString();
			if (TextUtils.isEmpty(contact)) {
				mContactView.setError(getString(R.string.error_field_required));
				focusView = mContactView;
				cancel = true;
			}
			if (cancel) {
				focusView.requestFocus();
			} else {
				// Show a progress spinner, and kick off a background task
				ShowProgress.showProgress(true, mProgressViewFullScreen,
						mRentalCreation, getApplicationContext());
			
				
				Integer squareMeters = Integer.valueOf(meters);
				Integer streetNum = Integer.valueOf(streetNumber);
				Double price = Double.valueOf(priceString);
				Date availableDate = null;
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
					availableDate = dateFormat.parse(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				
				String description = mDescriptionView.getText().toString();
				if (description.isEmpty()){
					description = null;
				}
				new CreateNewRentalTask(title,autoCompView.getText().toString(),streetNum,price,squareMeters,contact,availableDate,type,description).execute();
			}
			return true;
		}
		if (id == R.id.action_take_picture) {
			Intent i = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

			startActivityForResult(i, RESULT_LOAD_PICTURE);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class PlacesAutoCompleteAdapter extends ArrayAdapter<String>
			implements Filterable {
		private ArrayList<String> resultList;

		public PlacesAutoCompleteAdapter(Context context, int textViewResourceId) {
			super(context, textViewResourceId);
		}

		@Override
		public int getCount() {
			return resultList.size();
		}

		@Override
		public String getItem(int index) {
			return resultList.get(index);
		}

		@Override
		public Filter getFilter() {
			Filter filter = new Filter() {
				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					FilterResults filterResults = new FilterResults();
					if (constraint != null) {
						// Retrieve the autocomplete results.
						resultList = autocomplete(constraint.toString());

						// Assign the data to the FilterResults
						filterResults.values = resultList;
						filterResults.count = resultList.size();
					}
					return filterResults;
				}

				@Override
				protected void publishResults(CharSequence constraint,
						FilterResults results) {
					if (results != null && results.count > 0) {
						notifyDataSetChanged();
					} else {
						notifyDataSetInvalidated();
					}
				}
			};
			return filter;
		}
	}

	private static final String LOG_TAG = "place";

	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";

	private static final String API_KEY = "AIzaSyB9RTmSC7-i3bc2fmRSfs1qNit-1gBFaIs";

	private ArrayList<String> autocomplete(String input) {
		ArrayList<String> resultList = null;

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try {
			StringBuilder sb = new StringBuilder(PLACES_API_BASE
					+ TYPE_AUTOCOMPLETE + OUT_JSON);
			sb.append("?key=" + API_KEY);
			sb.append("&components=country:it");
			sb.append("&location=45.478178,9.228031");
			sb.append("&radius=10000");
			sb.append("&types=address");
			sb.append("&input=" + URLEncoder.encode(input, "utf8"));

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			// Load the results into a StringBuilder
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}
		} catch (MalformedURLException e) {
			Log.e(LOG_TAG, "Error processing Places API URL", e);
			return resultList;
		} catch (IOException e) {
			Log.e(LOG_TAG, "Error connecting to Places API", e);
			return resultList;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

			// Extract the Place descriptions from the results
			resultList = new ArrayList<String>(predsJsonArray.length());
			for (int i = 0; i < predsJsonArray.length(); i++) {
				resultList.add(predsJsonArray.getJSONObject(i).getString(
						"description"));

			}
		} catch (JSONException e) {
			Log.e(LOG_TAG, "Cannot process JSON results", e);
		}
		return resultList;
	}

	private class CreateNewRentalTask extends AsyncTask<Void, Void, Boolean> {

		private long mUserId;
		private final String address;
		private final String title;
		private final String description;
		private final Double price;
		private final Integer squareMeters;
		private final Date availabilityDate;
		private final String type;
		private final String contact;
		private final Integer streetNum;

		public CreateNewRentalTask(String title, String address, Integer streetNum, Double price,
				Integer squareMeters, String contact, Date availableDate, String type, String description) {
			this.address = address;
			this.title = title;
			this.description = description;
			this.squareMeters = squareMeters;
			this.price = price;
			this.contact = contact;
			this.availabilityDate = availableDate;
			this.type = type;
			this.streetNum = streetNum;
			
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			
			String id = sessionManager.getUserDetails().get(
					SessionManager.KEY_USERID);
			mUserId = Long.valueOf(id);
			
			StringBuilder addressCompat = new StringBuilder();
			addressCompat.append(streetNum);
			addressCompat.append(","+address);
			Rental rental = new Rental();
			rental.setPrice(price);
			rental.setText(description);
			rental.setTitle(title);
			rental.setUserId(mUserId);
			Calendar calendar = Calendar.getInstance();
			Date now = calendar.getTime();
			rental.setTimestamp(new DateTime(now));
			//rental.setPicture();
			
			//TODO chiamare metodo per trovare coordinate
			//endpoint.find...
			
			return null;
		}
	}

}
