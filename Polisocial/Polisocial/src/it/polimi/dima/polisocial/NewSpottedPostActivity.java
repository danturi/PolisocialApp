package it.polimi.dima.polisocial;


import it.polimi.dima.polisocial.entity.postspottedendpoint.Postspottedendpoint;
import it.polimi.dima.polisocial.entity.postspottedendpoint.model.PostSpotted;
import it.polimi.dima.polisocial.utilClasses.PictureEditing;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.StringTokenizer;

import com.facebook.Session.NewPermissionsRequest;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewSpottedPostActivity extends Activity {

	private static final int RESULT_LOAD_PICTURE = 1;
	
	private byte[] pictureInBytes;
	private CreateNewPostTask mCreatePostTask=null;
	private EditText mLocationAndTitleView;
	private EditText mPostTextView;
	private Spinner mSpinnerView;
	private View mProgressView;
	private View mPostCreationForm;
	private SessionManager sessionManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_spotted_post);
		getActionBar().setIcon(R.drawable.logo_login);
		sessionManager = new SessionManager(getApplicationContext());
		
		mLocationAndTitleView = (EditText) findViewById(R.id.location_and_title);
		mPostTextView = (EditText) findViewById(R.id.post_text);
		mSpinnerView = (Spinner) findViewById(R.id.spinnerCategory);
		mPostCreationForm = findViewById(R.id.post_creation_form);
		mProgressView = findViewById(R.id.progress);
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
			boolean cancel=false;
			String locationAndTitle = mLocationAndTitleView.getText().toString();
			if(TextUtils.isEmpty(locationAndTitle)){
				mLocationAndTitleView.setError(getString(R.string.error_field_required));
				focusView = mLocationAndTitleView;
				cancel = true;
			}
			
			String postText=mPostTextView.getText().toString();
			if(TextUtils.isEmpty(postText)){
				mPostTextView.setError(getString(R.string.error_field_required));
				focusView = mPostTextView;
				cancel = true;
			}
			
			String category = mSpinnerView.getSelectedItem().toString();
			if (cancel) {
				focusView.requestFocus();
			} else {
				// Show a progress spinner, and kick off a background task 
				showProgress(true);
				try {
					if(pictureInBytes==null)
						mCreatePostTask=new CreateNewPostTask(locationAndTitle, category, postText);
					else
						mCreatePostTask=new CreateNewPostTask(locationAndTitle, category, postText, pictureInBytes);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mCreatePostTask.execute();
			}
			return true;
		}
		if (id == R.id.action_take_picture) {
			Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					 
			startActivityForResult(i, RESULT_LOAD_PICTURE);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	      
	     if (requestCode == RESULT_LOAD_PICTURE && resultCode == RESULT_OK && data != null) {
             Uri selectedImage = data.getData();
             String[] filePath = { MediaStore.Images.Media.DATA };
             Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
             c.moveToFirst();
             int columnIndex = c.getColumnIndex(filePath[0]);
             String picturePath = c.getString(columnIndex);
             c.close();
             pictureInBytes=PictureEditing.compressPicture(picturePath);
             showPicture();
             setPictureTakenText(picturePath);
	     }
	}
	
	private void showPicture(){
		ImageView pic = (ImageView) findViewById(R.id.pic);
		pic.setVisibility(View.VISIBLE);
		Bitmap bitmap = BitmapFactory.decodeByteArray(pictureInBytes , 0, pictureInBytes.length);
		pic.setImageBitmap(bitmap);
	}
	
	private void setPictureTakenText(String picturePath){
		TextView pictureTakenText = (TextView)findViewById(R.id.picture_taken_text);
		pictureTakenText.setVisibility(View.VISIBLE);
		 StringTokenizer st = new StringTokenizer(picturePath, "/");
		 int n = st.countTokens();
		 for(int i=0;i<n-1;i++) {
		     st.nextToken();
		 }
		
		pictureTakenText.setText(st.nextToken()+" added"+pictureInBytes.length);
		pictureTakenText.setTextColor(Color.RED);
	}
	
	
	/**
	 * Represents an asynchronous task used to create a new post and send it to the server
	 */

	public class CreateNewPostTask extends AsyncTask<Void, Void, Boolean> {

		private long mUserId;
		private final String mLocationAndTitle;
		private final String mCategory;
		private final String mText;
		private byte[] mPicture;
		private String pic;
		PostSpotted newSpottedPost;
		

		CreateNewPostTask(String locationAndTitle,String category, String text,byte[] picture) throws NoSuchAlgorithmException, UnsupportedEncodingException {
			mLocationAndTitle=locationAndTitle;
			mCategory = category;
			mText = text;
			pic = Base64.encodeToString(picture, Base64.DEFAULT);

			
		}
		
		public CreateNewPostTask(String locationAndTitle, String category,
				String postText) {
			mLocationAndTitle=locationAndTitle;
			mCategory = category;
			mText = postText;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			
	
			newSpottedPost = new PostSpotted();
			if(pic!=null)
			//newSpottedPost.encodePicture(mPicture);
			newSpottedPost.setPicture(pic);
			newSpottedPost.setTitle(mLocationAndTitle);
			newSpottedPost.setText(mText);
			newSpottedPost.setPostCategory(mCategory);
			String id = sessionManager.getUserDetails().get(SessionManager.KEY_USERID);
			mUserId=Long.valueOf(id);
			newSpottedPost.setUserId(mUserId);
			Calendar calendar = Calendar.getInstance();
			Date now = calendar.getTime();
			
			//Timestamp currentTimestamp = new Timestamp(now.getTime());
			newSpottedPost.setTimestamp(new DateTime(now));
			Postspottedendpoint.Builder builder = new Postspottedendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
			
			builder = CloudEndpointUtils.updateBuilder(builder);
			
			Postspottedendpoint endpoint = builder.setApplicationName("polimisocial").build();
			
			try {
				endpoint.insertPostSpotted(newSpottedPost).execute();			
			} catch (IOException e2) {
				 System.out.println(e2.getMessage());
				return false;
			}
				return true;
			}

		@Override
		protected void onPostExecute(Boolean result) {
			showProgress(false);
			if(result){
				Toast toast = Toast.makeText(getApplicationContext(), "DONE! You have just insterted a new post", Toast.LENGTH_SHORT);
				toast.show();
				finish();
			}else{
				Toast toast = Toast.makeText(getApplicationContext(), "Can't perform operation. Please retry", Toast.LENGTH_SHORT);
				toast.show();
			}

		}

		@Override
		protected void onCancelled() {
			mCreatePostTask = null;
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

		mPostCreationForm.setVisibility(show ? View.GONE : View.VISIBLE);
		mPostCreationForm.animate().setDuration(shortAnimTime)
				.alpha(show ? 0 : 1)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {
						mPostCreationForm.setVisibility(show ? View.GONE
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
		mPostCreationForm.setVisibility(show ? View.GONE : View.VISIBLE);
	}
}


}

