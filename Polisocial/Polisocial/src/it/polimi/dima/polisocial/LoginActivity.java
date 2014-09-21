package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
/**
 * 
 * A login screen that offers login via email/password and via facebook.
 */
@SuppressLint("NewApi") public class LoginActivity extends Activity implements LoaderCallbacks<Cursor>{


	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// UI references.
	private AutoCompleteTextView mEmailView;
	private EditText mPasswordView;
	private View mProgressView;
	private View mLoginFormView;
	private UiLifecycleHelper uiHelper;
	private SessionManager sessionManager; 

	@Override
	public void onBackPressed() {
		//minimize
		moveTaskToBack(true);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getActionBar().setIcon(R.drawable.logo_login);
		sessionManager = new SessionManager(getApplicationContext());
		
		
		
		//controllo se già loggato
		if(sessionManager.isLoggedIn())
			startActivity(new Intent(LoginActivity.this, TabActivity.class));	
		PreferenceManager.setDefaultValues(this, R.layout.activity_preferences, false);
		
		// Set up the login form.
		mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
		populateAutoComplete();

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							try {
								attemptLogin();
							} catch (NoSuchAlgorithmException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return true;
						}
						return false;
					}
				});

		Button mEmailSignInButton = (Button) findViewById(R.id.standard_login);
		mEmailSignInButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					attemptLogin();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		mLoginFormView = findViewById(R.id.login_form);
		mProgressView = findViewById(R.id.login_progress);
		

		//Login con Facebook


		uiHelper = new UiLifecycleHelper(this,callback);
		//uiHelper.onCreate(savedInstanceState);


		LoginButton authButton = (LoginButton) findViewById(R.id.facebook_login);
		authButton.setReadPermissions(Arrays.asList("user_status","email"));

	}

	@Override
	public void onResume() {
		
		if(sessionManager.isLoggedIn()){
			startActivity(new Intent(LoginActivity.this, TabActivity.class));
			finish();
		}
				
		
		super.onResume();
		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		/*Session session = Session.getActiveSession();
		if (session != null &&
				(session.isOpened() || session.isClosed()) ) {
			onSessionStateChange(session, session.getState(), null);
		}*/

		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
		
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			showProgress(true);
			onSessionStateChange(session, state, exception);
		}
	};

	//dati da inserire per il nuovo utente
	String nickname;
	String email;
	PoliUser poliuser= new PoliUser();

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		if (state.isOpened()) {


			
			if(!sessionManager.isLoggedIn()){	
				Request meRequest=Request.newMeRequest(session, new GraphUserCallback()
				{

					@Override
					public void onCompleted(GraphUser user, Response response)
					{
						if(response.getError()==null)
						{	
							nickname=user.getFirstName()+user.getLastName();
							email = (String) user.getProperty("email");
							poliuser.setEmail(email);
							poliuser.setNickname(nickname);
							poliuser.setFbaccount(user.getLink());
							poliuser.setSelfSummary((String) user.getProperty("bio"));
							poliuser.setNotifyAnnouncement(true);
							poliuser.setNotifyEvent(true);
							poliuser.setNotifySpotted(true);
							poliuser.setNotifiedAnnouncement(false);
							poliuser.setNotifiedEvent(false);
							poliuser.setNotifiedSpotted(false);
							new searchEmailForLoginFBTask().execute();
						}
					}
				});
				meRequest.executeAsync();
			}
			

		} else if (state.isClosed()) {
			showProgress(false);
		}
	}


	public class searchEmailForLoginFBTask extends AsyncTask<Void, Void, Boolean>{

		PoliUser poliUserCheck;
		@Override
		protected Boolean doInBackground(Void... params) {


			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);

			builder = CloudEndpointUtils.updateBuilder(builder);
			Poliuserendpoint endpoint = builder.setApplicationName("polimisocial").build();
			

			//cerco se l'email è già nel database
			try {
				poliUserCheck=endpoint.checkForDuplicateEmail(email).execute();
			} catch (IOException e1) {
					if( new StringTokenizer(e1.getMessage().toString()).nextToken().equals("404"))
						return false;	
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			//super.onPostExecute(result);
			if (!result){
				new InsertPoliUserFBTask().execute();
				
			}else {
				sessionManager.createLoginSession(poliUserCheck.getNickname(), poliUserCheck.getEmail());
				sessionManager.setId(Long.toString(poliUserCheck.getUserId()));
				GCMIntentService.register(getApplicationContext());
				Intent i= new Intent(LoginActivity.this,TabActivity.class);
				startActivity(i);
				finish();
				
			}
		}


	}


	public class InsertPoliUserFBTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);

			builder = CloudEndpointUtils.updateBuilder(builder);
			Poliuserendpoint endpoint = builder.setApplicationName("polimisocial").build();

		
			try {
				endpoint.insertPoliUser(poliuser).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			sessionManager.createLoginSession(nickname, email);
			//prendo user dal database per settare id nel sessionManager
			new GetUserTask().execute(email);
			
		}


	}
        
        
	public void goToRegistrationScreen(View clickedText){
		Intent goToRegistrationIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
		LoginActivity.this.startActivity(goToRegistrationIntent);
		finish();
	}
	
	private void populateAutoComplete() {
		if (VERSION.SDK_INT >= 14) {
			// Use ContactsContract.Profile (API 14+)
			getLoaderManager().initLoader(0, null, this);
		} else if (VERSION.SDK_INT >= 8) {
			// Use AccountManager (API 8+)
			new SetupEmailAutoCompleteTask().execute(null, null);
		}
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public void attemptLogin() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		String email = mEmailView.getText().toString();
		String password = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password, if the user entered one.
		if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!isEmailValid(email)) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			new UserLoginTask(email, password).execute();

		}
	}

	private boolean isEmailValid(String email) {
		// TODO: Replace this with your own logic
		return email.contains("@");
	}

	private boolean isPasswordValid(String password) {
		// TODO: Replace this with your own logic
		return password.length() > 4;
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
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
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
		return new CursorLoader(this,
				// Retrieve data rows for the device user's 'profile' contact.
				Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
						ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
				ProfileQuery.PROJECTION,

				// Select only email addresses.
				ContactsContract.Contacts.Data.MIMETYPE + " = ?",
				new String[] { ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE },

				// Show primary email addresses first. Note that there won't be
				// a primary email address if the user hasn't specified one.
				ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
		List<String> emails = new ArrayList<String>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			emails.add(cursor.getString(ProfileQuery.ADDRESS));
			cursor.moveToNext();
		}

		addEmailsToAutoComplete(emails);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader) {

	}

	private interface ProfileQuery {
		String[] PROJECTION = { ContactsContract.CommonDataKinds.Email.ADDRESS,
				ContactsContract.CommonDataKinds.Email.IS_PRIMARY, };

		int ADDRESS = 0;
		int IS_PRIMARY = 1;
	}

	/**
	 * Use an AsyncTask to fetch the user's email addresses on a background
	 * thread, and update the email text field with results on the main UI
	 * thread.
	 */
	class SetupEmailAutoCompleteTask extends
			AsyncTask<Void, Void, List<String>> {

		@Override
		protected List<String> doInBackground(Void... voids) {
			ArrayList<String> emailAddressCollection = new ArrayList<String>();

			// Get all emails from the user's contacts and copy them to a list.
			ContentResolver cr = getContentResolver();
			Cursor emailCur = cr.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
					null, null, null);
			while (emailCur.moveToNext()) {
				String email = emailCur
						.getString(emailCur
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				emailAddressCollection.add(email);
			}
			emailCur.close();

			return emailAddressCollection;
			
		}

		@Override
		protected void onPostExecute(List<String> emailAddressCollection) {
			addEmailsToAutoComplete(emailAddressCollection);
		}
	}

	private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
		// Create adapter to tell the AutoCompleteTextView what to show in its
		// dropdown list.
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				LoginActivity.this,
				android.R.layout.simple_dropdown_item_1line,
				emailAddressCollection);

		mEmailView.setAdapter(adapter);
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;
		private final String mPassword;
		private PoliUser poliuser;


		UserLoginTask(String email, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
			mEmail = email;
			mPassword = AeSimpleSHA1.SHA1(password);
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
			
			builder = CloudEndpointUtils.updateBuilder(builder);
			Poliuserendpoint endpoint = builder.setApplicationName("polimisocial").build();
			
			
			try {
				poliuser=endpoint.checkCredentials(mEmail, mPassword).execute();
				return true;
				
			} catch (IOException e) {
				if( new StringTokenizer(e.getMessage().toString()).nextToken().equals("404"))
					return false;
			}
			return false;
	

		}
		
		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				sessionManager.createLoginSession(poliuser.getNickname(), poliuser.getEmail());
				sessionManager.setId(Long.toString(poliuser.getUserId()));
				new RegisterUser().execute();
				/*GCMIntentService.register(getApplicationContext());
				Intent loginFinishedIntent = new Intent(LoginActivity.this, TabActivity.class);
				LoginActivity.this.startActivity(loginFinishedIntent);
				finish();*/
			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
		

	}
	
	 @Override
		protected void onNewIntent(Intent intent) {
			super.onNewIntent(intent);
			/*
			 * If we are dealing with an intent generated by the GCMIntentService
			 * class, then display the provided message.
			 */
			if (intent.getBooleanExtra("gcmIntentServiceMessage", false)) {
				showDialog(intent.getStringExtra("message"));
		
		}
	}
	 
	 private void showDialog(String message) {
			new AlertDialog.Builder(this)
			.setMessage(message)
			.setPositiveButton(android.R.string.ok,
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
				}
			}).show();
		}
	 
	 
	 public class GetUserTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			
			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
			
			builder = CloudEndpointUtils.updateBuilder(builder);
			Poliuserendpoint endpoint = builder.setApplicationName("polimisocial").build();
			
			try {
				PoliUser user = endpoint.checkForDuplicateEmail(params[0]).execute();
				sessionManager.setId(Long.toString(user.getUserId()));
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			new RegisterUser().execute();
		}
		 
	 }
	 
	 public class RegisterUser extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			GCMIntentService.register(getApplicationContext());
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			Intent i = new Intent(LoginActivity.this,TabActivity.class);
			startActivity(i);
			showProgress(false);
			finish();
		}
		
		 
	 }

}
