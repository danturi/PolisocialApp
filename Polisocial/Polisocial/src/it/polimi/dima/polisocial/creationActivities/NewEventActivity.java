package it.polimi.dima.polisocial.creationActivities;

import it.polimi.dima.polisocial.CloudEndpointUtils;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.entity.initiativeendpoint.Initiativeendpoint;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.postimageendpoint.Postimageendpoint;
import it.polimi.dima.polisocial.entity.postimageendpoint.model.PostImage;
import it.polimi.dima.polisocial.utilClasses.PictureEditing;
import it.polimi.dima.polisocial.utilClasses.SessionManager;
import it.polimi.dima.polisocial.utilClasses.ShowProgress;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class NewEventActivity extends Activity {

	private static final int RESULT_LOAD_PICTURE = 1;

	private byte[] pictureInBytes;
	private CreateNewEventTask mCreateEventTask = null;
	private EditText mTitleView;
	private EditText mLocationView;
	private EditText mEventDescriptionView;
	private EditText mEventDateView;
	private EditText mEventTimeView;
	private Spinner mSpinnerView;
	private View mProgressView;
	private View mEventCreationForm;

	private int mYear, mMonth, mDay;
	private int mHour, mMinute;

	private SessionManager sessionManager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);
		getActionBar().setIcon(R.drawable.logo_login);
		sessionManager = new SessionManager(getApplicationContext());
		mTitleView = (EditText) findViewById(R.id.title);
		mLocationView = (EditText) findViewById(R.id.location);
		mEventDescriptionView = (EditText) findViewById(R.id.event_description);
		mEventDateView = (EditText) findViewById(R.id.start_date);
		mEventTimeView = (EditText) findViewById(R.id.start_time);
		mSpinnerView = (Spinner) findViewById(R.id.spinnerCategory);
		mEventCreationForm = findViewById(R.id.event_creation_form);
		mProgressView = findViewById(R.id.progress);

		mEventDateView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar mcurrentDate = Calendar.getInstance();
				int year = mcurrentDate.get(Calendar.YEAR);
				int month = mcurrentDate.get(Calendar.MONTH);
				int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog mDatePicker = new DatePickerDialog(
						NewEventActivity.this, new OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								mEventDateView.setText(dayOfMonth + "/"
										+ (++monthOfYear) + "/" + year);
								mYear=year;
								mMonth=monthOfYear;
								mDay=dayOfMonth;
							}
						}, year, month, day);
				mDatePicker.setTitle("Start date:");
				mDatePicker.show();
			}
		});
		mEventTimeView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Calendar mcurrentDate = Calendar.getInstance();
				int hour = mcurrentDate.get(Calendar.HOUR);
				int minute = mcurrentDate.get(Calendar.MINUTE);
				TimePickerDialog mTimePicker = new TimePickerDialog(
						NewEventActivity.this, new OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view, int hour,
									int minute) {
								mEventTimeView.setText(hour + ":" + minute);
								mHour = hour;
								mMinute = minute;
							}
						}, hour, minute, true);
				mTimePicker.setTitle("Start time:");
				mTimePicker.show();
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
			String title = mTitleView.getText().toString();
			if (TextUtils.isEmpty(title)) {
				mTitleView.setError(getString(R.string.error_field_required));
				focusView = mTitleView;
				cancel = true;
			}
			String location = mLocationView.getText().toString();
			if (TextUtils.isEmpty(title)) {
				mLocationView
						.setError(getString(R.string.error_field_required));
				focusView = mLocationView;
				cancel = true;
			}

			String date = mEventDateView.getText().toString();
			if (TextUtils.isEmpty(date)) {
				mEventDateView
						.setError(getString(R.string.error_field_required));
				focusView = mEventDateView;
				cancel = true;
			}
			
			String time = mEventTimeView.getText().toString();
			if (TextUtils.isEmpty(time)) {
				mEventTimeView
						.setError(getString(R.string.error_field_required));
				focusView = mEventTimeView;
				cancel = true;
			}

			String description = mEventDescriptionView.getText().toString();
			if (TextUtils.isEmpty(title)) {
				mEventDescriptionView
						.setError(getString(R.string.error_field_required));
				focusView = mEventDescriptionView;
				cancel = true;
			}

			String category = mSpinnerView.getSelectedItem().toString();
			DateTime dateAndTime = new DateTime(new GregorianCalendar(mYear, mMonth-1, mDay,
					mHour+1, mMinute).getTime());
			if (cancel) {
				focusView.requestFocus();
			} else {
				// Show a progress spinner, and kick off a background task
				ShowProgress.showProgress(true, mProgressView,
						mEventCreationForm, getApplicationContext());

				try {
					if (pictureInBytes == null) {
						mCreateEventTask = new CreateNewEventTask(title,
								location, category, dateAndTime, description);
					} else {
						mCreateEventTask = new CreateNewEventTask(title,
								location, category, dateAndTime, description,
								pictureInBytes);
					}
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mCreateEventTask.execute();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_PICTURE && resultCode == RESULT_OK
				&& data != null) {
			Uri selectedImage = data.getData();
			String[] filePath = { MediaStore.Images.Media.DATA };
			Cursor c = getContentResolver().query(selectedImage, filePath,
					null, null, null);
			c.moveToFirst();
			int columnIndex = c.getColumnIndex(filePath[0]);
			String picturePath = c.getString(columnIndex);
			c.close();
			pictureInBytes = PictureEditing.compressPicture(picturePath);
			showPicture();
			setPictureTakenText(picturePath);
		}
	}

	private void showPicture() {
		ImageView pic = (ImageView) findViewById(R.id.pic);
		pic.setVisibility(View.VISIBLE);
		Bitmap bitmap = BitmapFactory.decodeByteArray(pictureInBytes, 0,
				pictureInBytes.length);
		pic.setImageBitmap(bitmap);
	}

	private void setPictureTakenText(String picturePath) {
		TextView pictureTakenText = (TextView) findViewById(R.id.picture_taken_text);
		pictureTakenText.setVisibility(View.VISIBLE);
		StringTokenizer st = new StringTokenizer(picturePath, "/");
		int n = st.countTokens();
		for (int i = 0; i < n - 1; i++) {
			st.nextToken();
		}

		pictureTakenText.setText(st.nextToken() + " added"
				+ pictureInBytes.length);
		pictureTakenText.setTextColor(Color.RED);
	}

	/**
	 * Represents an asynchronous task used to create a new post and send it to
	 * the server
	 */

	public class CreateNewEventTask extends AsyncTask<Void, Void, Boolean> {

		private long mUserId;
		private final String mTitle;
		private final String mLocation;
		private final String mCategory;
		private final String mDescription;
		private String pic;
		private DateTime mDateAndTime;
		Initiative newEventPost;
		PostImage newPostImage;

		CreateNewEventTask(String title, String location, String category,
				DateTime dateAndTime, String description, byte[] picture)
				throws NoSuchAlgorithmException, UnsupportedEncodingException {
			mTitle = title;
			mLocation = location;
			mCategory = category;
			mDescription = description;
			mDateAndTime = dateAndTime;
			pic = Base64.encodeToString(picture, Base64.DEFAULT);
		}

		CreateNewEventTask(String title, String location, String category,
				DateTime dateAndTime, String description)
				throws NoSuchAlgorithmException, UnsupportedEncodingException {
			mTitle = title;
			mLocation = location;
			mCategory = category;
			mDescription = description;
			mDateAndTime = dateAndTime;
			pic = null;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			newEventPost = new Initiative();
			newEventPost.setTitle(mTitle);
			newEventPost.setLocation(mLocation);
			newEventPost.setText(mDescription);
			newEventPost.setCategory(mCategory);
			newEventPost.setBeginningDate(mDateAndTime);

			String id = sessionManager.getUserDetails().get(
					SessionManager.KEY_USERID);
			mUserId = Long.valueOf(id);
			newEventPost.setUserId(mUserId);
			Calendar calendar = Calendar.getInstance();
			Date now = calendar.getTime();
			newEventPost.setTimestamp(new DateTime(now));
			newEventPost.setNumOfGoing(0);
			newEventPost.setNumOfLikes(0);

			if (pic != null) {
				newEventPost.setHavePicture(true);
			} else {
				newEventPost.setHavePicture(false);
			}

			Initiativeendpoint.Builder builder = new Initiativeendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Initiativeendpoint endpoint = builder.setApplicationName(
					"polimisocial").build();

			try {
				newEventPost = endpoint.insertInitiative(newEventPost)
						.execute();
			} catch (IOException e2) {
				System.out.println(e2.getMessage());
				return false;
			}

			if (pic != null) {
				newPostImage = new PostImage();
				newPostImage.setPostId(newEventPost.getId());
				newPostImage.setImage(pic);
				Postimageendpoint.Builder imageBuilder = new Postimageendpoint.Builder(
						AndroidHttp.newCompatibleTransport(),
						new JacksonFactory(), null);

				imageBuilder = CloudEndpointUtils.updateBuilder(imageBuilder);

				Postimageendpoint imageEndpoint = imageBuilder
						.setApplicationName("polimisocial").build();

				try {
					imageEndpoint.insertPostImage(newPostImage).execute();
				} catch (IOException e2) {
					System.out.println(e2.getMessage());
					return false;
				}
			}

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			ShowProgress.showProgress(false, mProgressView, mEventCreationForm,
					getApplicationContext());
			if (result) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"DONE! New event created", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_VERTICAL,
						Gravity.CENTER_HORIZONTAL, 0);
				toast.show();
				finish();
			} else {
				Toast toast = Toast.makeText(getApplicationContext(),
						"Can't perform operation. Please retry",
						Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER_VERTICAL,
						Gravity.CENTER_HORIZONTAL, 0);
				toast.show();
			}

		}

		@Override
		protected void onCancelled() {
			mCreateEventTask = null;
			ShowProgress.showProgress(false, mProgressView, mEventCreationForm,
					getApplicationContext());
		}
	}

}
