package it.polimi.dima.polisocial;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.sip.SipRegistrationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import it.polimi.dima.polisocial.AeSimpleSHA1;
import it.polimi.dima.polisocial.SingleChoiceDialogFragm.NoticeDialogListener;


public class RegistrationActivity extends Activity implements NoticeDialogListener {
	
	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserRegisterTask mRegTask = null;
	private UserLoginTask mAuthTask = null;
	private SessionManager sessionManager;
	// UI references.
	private EditText mEmailView;
	private EditText mUsernameView;
	private EditText mFacultyView;
	private EditText mPasswordView;
	private EditText mConfirmPasswordView;
	private View mProgressView;
	private View mRegistrationFormView;



	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		sessionManager = new SessionManager(getApplicationContext());
		
		// Set up the registration form.
		mEmailView = (EditText) findViewById(R.id.email);
		mUsernameView = (EditText) findViewById(R.id.username);
		mFacultyView =  (EditText) findViewById(R.id.faculty);
		mPasswordView = (EditText) findViewById(R.id.password);
		mConfirmPasswordView = (EditText) findViewById(R.id.confirm_password);
		
		mFacultyView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showNoticeDialog();
				
			}
		});

		Button mRegistrationButton = (Button) findViewById(R.id.registration_button);
		mRegistrationButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					attemptRegistration();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		mRegistrationFormView = findViewById(R.id.registration_form);
		mProgressView = findViewById(R.id.registration_progress);
	}

	/**
	 * Attempts register the account specified by the register form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	public void attemptRegistration() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (mRegTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);
		mFacultyView.setError(null);

		 //Store values at the time of the registration attempt.
		String email = mEmailView.getText().toString();
		String username = mUsernameView.getText().toString();
		String faculty = mFacultyView.getText().toString();
		String password = mPasswordView.getText().toString();
		String confirmPassword = mConfirmPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;
		
		
		
		// Check for a valid password, if the user entered one.
		if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		 //Check if password fields are equal
		if (!password.equals(confirmPassword)){
			mConfirmPasswordView.setError(getString(R.string.error_passwords_dont_match));
			focusView = mConfirmPasswordView;
			cancel=true;
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
		
		//nessun username
		if (TextUtils.isEmpty(username)) {
			mUsernameView.setError(getString(R.string.error_field_required));
			focusView = mUsernameView;
			cancel = true;
		}

		//nessuna facoltà
		if (TextUtils.isEmpty(faculty)){
			mFacultyView.setError(getString(R.string.error_field_required));
			focusView = mFacultyView;
			cancel=true;
		}

		
		
		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			mRegTask = new UserRegisterTask(email,username, password,faculty);
			mRegTask.execute();
		}
	}
	

	private boolean isEmailValid(String email) {
		String emailRegex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; 
		return email.matches(emailRegex);
	}

	private boolean isPasswordValid(String password) {
		return password.length() > 4;
	}

	/**
	 * Shows the progress UI and hides the registration form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mRegistrationFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mRegistrationFormView.setVisibility(show ? View.GONE
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
			mRegistrationFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	
	

	/**
	 * Represents an asynchronous registration task used to register
	 * the user.
	 */
	public class UserRegisterTask extends AsyncTask<Void, Void, Integer> {

		private final String mEmail;
		private final String mPassword;
		private final String mUsername;
		private PoliUser newPoliUser = new PoliUser();
		PoliUser poliUser;

		UserRegisterTask(String email,String username, String password,String faculty) throws NoSuchAlgorithmException, UnsupportedEncodingException {
			mEmail=email;
			mUsername = username;
			mPassword = AeSimpleSHA1.SHA1(password);
			
			newPoliUser.setNickname(mUsername);
			newPoliUser.setEmail(mEmail);
			newPoliUser.setPassword(mPassword);
			newPoliUser.setFaculty(faculty);
			newPoliUser.setNotifyAnnouncement(true);
			newPoliUser.setNotifyEvent(true);
			newPoliUser.setNotifySpotted(true);
		}

		@Override
		protected Integer doInBackground(Void... params) {
			
			

			
			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
			
			builder = CloudEndpointUtils.updateBuilder(builder);
			
			Poliuserendpoint endpoint = builder.setApplicationName("polimisocial").build();
			Boolean emailAlreadyExists=true;
			Boolean userNameAlreadyExists=true;
			
			//check if email is available 
			try {
				poliUser=endpoint.checkForDuplicateEmail(mEmail).execute();
					
			} catch (IOException e2) {
				if( new StringTokenizer(e2.getMessage().toString()).nextToken().equals("404"))
				emailAlreadyExists = false;
			}

			//check if username is available
			try {
				poliUser=endpoint.checkForDuplicateUsername(mUsername).execute();
			} catch (IOException e1) {
				if( new StringTokenizer(e1.getMessage().toString()).nextToken().equals("404"))
					userNameAlreadyExists = false;	
			}
			
			if(userNameAlreadyExists || emailAlreadyExists){
				if(emailAlreadyExists)
					return 1;
				else
					return 2;
			}else{
				try {				
					endpoint.insertPoliUser(newPoliUser).execute();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			// TODO: register the new account here.
				return 0;
			}
		}
		@Override
		protected void onPostExecute(final Integer result) {
			mRegTask = null;
			showProgress(false);

			if (result==0) {
				new UserLoginTask(mEmail,mPassword).execute();
				//mAuthTask = new UserLoginTask(mEmail,mPassword);
				//mAuthTask.execute();
			} else if(result==1){
				mEmailView.setError(getString(R.string.error_duplicate_email));
				mPasswordView.requestFocus();
			}else{
				mUsernameView.setError(getString(R.string.error_duplicate_username));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mRegTask = null;
			showProgress(false);
		}
	}
	
	/**
	 * Represents an asynchronous login task used to authenticate
	 * the user after registration.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;
		private final String mPassword;
		private String nickname="";
		PoliUser poliuser;

		UserLoginTask(String email, String password) {
			mEmail = email;
			mPassword = password;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(), null);
			
			builder = CloudEndpointUtils.updateBuilder(builder);
			Poliuserendpoint endpoint = builder.setApplicationName("polimisocial").build();
		
			try {
				poliuser = endpoint.checkCredentials(mEmail, mPassword).execute();
				nickname=poliuser.getNickname();
				return true;
			} catch (IOException e) {
	
			}
			return false;
			
		}
		
		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				Intent loginFinishedIntent = new Intent(RegistrationActivity.this, TabActivity.class);
				loginFinishedIntent.putExtra("firstLoginFromRegistration", true);
				sessionManager.createLoginSession(nickname, mEmail);
				RegistrationActivity.this.startActivity(loginFinishedIntent);
				finish();
			} else {
				Toast.makeText(RegistrationActivity.this, "Login error",Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}

	@Override
	public void onDialogPositiveClick(String faculty) {
		// TODO Auto-generated method stub
		mFacultyView.setText(faculty);

	}

	
    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new SingleChoiceDialogFragm();
        dialog.show(getFragmentManager(), "SingleChoiceDialogFragm");
    }

}