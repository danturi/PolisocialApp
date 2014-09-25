package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser;
import it.polimi.dima.polisocial.utilClasses.ProfileFieldType;
import it.polimi.dima.polisocial.utilClasses.SessionManager;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditProfileFieldActivity extends Activity {

	
	SessionManager sessionManager;
	View mProgressView;
	View mEditForm;
	String fieldType;
	private EditText editField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile_field);
		getActionBar().setIcon(R.drawable.logo_login);
		mProgressView = findViewById(R.id.progress);
		mEditForm = findViewById(R.id.edit_profile_field_form);
		sessionManager = new SessionManager(getApplicationContext());
		Intent intent = getIntent();
		fieldType = intent.getStringExtra("fieldType");
		String text = intent.getStringExtra("text");
		getActionBar().setTitle(fieldType);

		editField = (EditText) findViewById(R.id.edit_field);
		if (text != null && !text.matches("")) {
			editField.setText(text);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_profile_field, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_save_field) {
			showProgress(true);
			// call asynk task that store the new content in the appropriate field

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mEditForm.setVisibility(show ? View.GONE : View.VISIBLE);
			mEditForm.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mEditForm.setVisibility(show ? View.GONE
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
			mEditForm.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous task used to store profile field new content.
	 */
	public class StoreNewProfileFieldContentTask extends
			AsyncTask<Void, Void, Boolean> {

		private PoliUser poliuser;
		private long userId;

		StoreNewProfileFieldContentTask()
				throws NoSuchAlgorithmException, UnsupportedEncodingException {
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);
			Poliuserendpoint endpoint = builder.setApplicationName(
					"polimisocial").build();

			//depending on what field the user is updating..
			if(fieldType.equals(ProfileFieldType.SELF_SUMMARY)){
				
			}else if(fieldType.equals(ProfileFieldType.WHAT_IM_DOING.toString())){
				
			}else if(fieldType.equals(ProfileFieldType.IM_REALLY_GOOD_AT.toString())){
				
			}else if(fieldType.equals(ProfileFieldType.FAVORITE.toString())){
				
			}else if(fieldType.equals(ProfileFieldType.SIX_THINGS_WITHOUT.toString())){
				
			}
			
			// try {
			// update field poliuser
			return true;
			/*
			 * } catch (IOException e) { if( new
			 * StringTokenizer(e.getMessage().toString
			 * ()).nextToken().equals("404")) return false; } return false;
			 */
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			showProgress(false);

			if (success) {
				Toast toast = Toast.makeText(getApplicationContext(),
						"Field succesfully updated!",
						Toast.LENGTH_SHORT);
				toast.show();
				finish();
				
			} else {
				Toast toast = Toast.makeText(getApplicationContext(),
						"Can't perform operation. Please retry",
						Toast.LENGTH_SHORT);
				toast.show();
			}
		}

		@Override
		protected void onCancelled() {
			showProgress(false);
		}

	}

}
