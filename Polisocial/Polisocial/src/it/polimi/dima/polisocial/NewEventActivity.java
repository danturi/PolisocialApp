package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.entity.initiativeendpoint.Initiativeendpoint;
import it.polimi.dima.polisocial.entity.initiativeendpoint.model.Initiative;
import it.polimi.dima.polisocial.entity.postimageendpoint.Postimageendpoint;
import it.polimi.dima.polisocial.entity.postimageendpoint.model.PostImage;
import it.polimi.dima.polisocial.utilClasses.PictureEditing;
import it.polimi.dima.polisocial.utilClasses.SessionManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
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
	private static final int DATE_DIALOG_ID = 2;
	private static final int TIME_DIALOG_ID = 3;

	private byte[] pictureInBytes;
	private CreateNewEventTask mCreateEventTask = null;
	private EditText mTitleView;
	private EditText mLocationView;
	private EditText mEventDescriptionView;
	private TextView mEventDateView;
	private TextView mEventTimeView;
	private Spinner mSpinnerView;
	private View mProgressView;
	private View mEventCreationForm;

	private int year, month, day;
	private int hour, minute;

	private SessionManager sessionManager;

	private String INITIAL_TEXT_DATE = "Tap to set event date";
	private String INITIAL_TEXT_TIME = "Tap to set event time";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);
		getActionBar().setIcon(R.drawable.logo_login);
		sessionManager = new SessionManager(getApplicationContext());
		setCurrentDate();
		mTitleView = (EditText) findViewById(R.id.title);
		mLocationView = (EditText) findViewById(R.id.location);
		mEventDescriptionView = (EditText) findViewById(R.id.event_description);
		mEventDateView = (TextView) findViewById(R.id.start_date);
		mEventTimeView = (TextView) findViewById(R.id.start_time);
		mSpinnerView = (Spinner) findViewById(R.id.spinnerCategory);
		mEventCreationForm = findViewById(R.id.event_creation_form);
		mProgressView = findViewById(R.id.progress);

		mEventDateView.setText(INITIAL_TEXT_DATE);
		mEventTimeView.setText(INITIAL_TEXT_TIME);

		mEventDateView.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		mEventTimeView.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});
	}

	// display current date both on the text view and the Date Picker
	public void setCurrentDate() {
		final Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timePickerListener, 12, 0, true);
		}
		// set time picker a
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear - 1900;
			month = selectedMonth;
			day = selectedDay;

			// set selected date into Text View
			mEventDateView.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));
		}
	};

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

			mEventTimeView.setText(new StringBuilder().append(hour)
					.append(" : ").append(minute));

		}
	};

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
			if (date.equals(INITIAL_TEXT_DATE)) {
				mEventDateView
						.setError(getString(R.string.error_field_required));
				focusView = mEventDateView;
				cancel = true;
			}

			String time = mEventTimeView.getText().toString();
			if (time.equals(INITIAL_TEXT_TIME)) {
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
			@SuppressWarnings("deprecation")
			DateTime dateAndTime = new DateTime(new Date(year, month, day,
					hour, minute));
			if (cancel) {
				focusView.requestFocus();
			} else {
				// Show a progress spinner, and kick off a background task
				showProgress(true);
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
			showProgress(false);
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
			showProgress(false);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mEventCreationForm.setVisibility(show ? View.GONE : View.VISIBLE);
			mEventCreationForm.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mEventCreationForm.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProgressView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mEventCreationForm.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
}
