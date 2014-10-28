package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.SingleChoiceDialogFragm.FacultyDialogListener;
import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser;
import it.polimi.dima.polisocial.tabactivityAndFragments.TabActivity;
import it.polimi.dima.polisocial.utilClasses.AeSimpleSHA1;
import it.polimi.dima.polisocial.utilClasses.SessionManager;
import it.polimi.dima.polisocial.utilClasses.ShowProgress;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

public class RegistrationActivity extends Activity implements
		FacultyDialogListener {

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
	private EditText mBirthDateView;
	private EditText mPasswordView;
	private EditText mConfirmPasswordView;
	private View mProgressView;
	private View mRegistrationFormView;

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, LoginActivity.class);
		startActivity(i);
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		sessionManager = new SessionManager(getApplicationContext());
		getActionBar().setIcon(R.drawable.logo_login);
		// Set up the registration form.
		mEmailView = (EditText) findViewById(R.id.email);
		mUsernameView = (EditText) findViewById(R.id.username);
		mFacultyView =  (EditText) findViewById(R.id.faculty);
		mPasswordView = (EditText) findViewById(R.id.password);
		mBirthDateView = (EditText) findViewById(R.id.dateBirth);
		mConfirmPasswordView = (EditText) findViewById(R.id.confirm_password);
		
		mFacultyView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showNoticeDialog();
				
			}
		});

		mBirthDateView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//To show current date in the datepicker
	            Calendar mcurrentDate=Calendar.getInstance();
	            int mYear = mcurrentDate.get(Calendar.YEAR);
	            int mMonth = mcurrentDate.get(Calendar.MONTH);
	            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

	        
	            DatePickerDialog mDatePicker=new DatePickerDialog(RegistrationActivity.this,new OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						mBirthDateView.setText(dayOfMonth+"/"+(++monthOfYear)+"/"+year);
						
					}
				}, mYear, mMonth, mDay);
	            mDatePicker.setTitle("Select your birthdate");                
	            mDatePicker.show();  }
				
			
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
	 * Attempts register the account specified by the register form. If there
	 * are form errors (invalid email, missing fields, etc.), the errors are
	 * presented and no actual login attempt is made.
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	public void attemptRegistration() throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		if (mRegTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);
		mFacultyView.setError(null);
		mBirthDateView.setError(null);

		// Store values at the time of the registration attempt.
		String email = mEmailView.getText().toString();
		String username = mUsernameView.getText().toString();
		String faculty = mFacultyView.getText().toString();
		String date = mBirthDateView.getText().toString();
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

		// Check if password fields are equal
		if (!password.equals(confirmPassword)) {
			mConfirmPasswordView
					.setError(getString(R.string.error_passwords_dont_match));
			focusView = mConfirmPasswordView;
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

		// nessun username
		if (TextUtils.isEmpty(username)) {
			mUsernameView.setError(getString(R.string.error_field_required));
			focusView = mUsernameView;
			cancel = true;
		}

		// nessuna facoltà
		if (TextUtils.isEmpty(faculty)) {
			mFacultyView.setError(getString(R.string.error_field_required));
			focusView = mFacultyView;
			cancel = true;
		}
		
		//nessuna data
		if (TextUtils.isEmpty(date)) {
			mBirthDateView.setError(getString(R.string.error_field_required));
			focusView = mBirthDateView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			ShowProgress.showProgress(true, mProgressView,
					mRegistrationFormView, getApplicationContext());
			Date datebirth = null;
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
				datebirth = dateFormat.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			mRegTask = new UserRegisterTask(email, username, password, faculty, datebirth);
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
	 * Represents an asynchronous registration task used to register the user.
	 */
	public class UserRegisterTask extends AsyncTask<Void, Void, Integer> {

		private final String mEmail;
		private final String mPassword;
		private final String mUsername;
		private final String mFaculty;
		private final Date mDate;
		private PoliUser newPoliUser = new PoliUser();
		PoliUser poliUser;

		UserRegisterTask(String email, String username, String password,
				String faculty,Date datebirth) throws NoSuchAlgorithmException,
				UnsupportedEncodingException {
			mEmail = email;
			mUsername = username;
			mFaculty=faculty;
			mDate = datebirth;
			mPassword = AeSimpleSHA1.SHA1(password);

			newPoliUser.setNickname(mUsername.toUpperCase());
			newPoliUser.setEmail(mEmail);
			newPoliUser.setPassword(mPassword);
			newPoliUser.setFaculty(mFaculty);
			newPoliUser.setDatebirth(new DateTime(mDate));
			newPoliUser.setNotifyAnnouncement(true);
			newPoliUser.setNotifyEvent(true);
			newPoliUser.setNotifySpotted(true);
			newPoliUser.setNotifiedAnnouncement(false);
			newPoliUser.setNotifiedEvent(false);
			newPoliUser.setNotifiedSpotted(false);
			newPoliUser.setNotifiedHitOn(false);
		}

		@Override
		protected Integer doInBackground(Void... params) {

			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);

			Poliuserendpoint endpoint = builder.setApplicationName(
					"polimisocial").build();
			Boolean emailAlreadyExists = true;
			Boolean userNameAlreadyExists = true;

			// check if email is available
			try {
				poliUser = endpoint.checkForDuplicateEmail(mEmail).execute();

			} catch (IOException e2) {
				if (new StringTokenizer(e2.getMessage().toString()).nextToken()
						.equals("404"))
					emailAlreadyExists = false;
			}

			// check if username is available
			try {
				poliUser = endpoint.checkForDuplicateUsername(mUsername)
						.execute();
			} catch (IOException e1) {
				if (new StringTokenizer(e1.getMessage().toString()).nextToken()
						.equals("404"))
					userNameAlreadyExists = false;
			}

			if (userNameAlreadyExists || emailAlreadyExists) {
				if (emailAlreadyExists)
					return 1;
				else
					return 2;
			} else {
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

			if (result == 0) {
				new UserLoginTask(mEmail, mPassword).execute();
				// mAuthTask = new UserLoginTask(mEmail,mPassword);
				// mAuthTask.execute();
			} else if (result == 1) {
				ShowProgress.showProgress(false, mProgressView,
						mRegistrationFormView, getApplicationContext());
				mEmailView.setError(getString(R.string.error_duplicate_email));
				mPasswordView.requestFocus();
			} else {
				ShowProgress.showProgress(false, mProgressView,
						mRegistrationFormView, getApplicationContext());
				mUsernameView
						.setError(getString(R.string.error_duplicate_username));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mRegTask = null;
			ShowProgress.showProgress(false, mProgressView,
					mRegistrationFormView, getApplicationContext());
		}
	}

	/**
	 * Represents an asynchronous login task used to authenticate the user after
	 * registration.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;
		private final String mPassword;
		private String nickname = "";
		PoliUser poliuser;

		UserLoginTask(String email, String password) {
			mEmail = email;
			mPassword = password;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			Poliuserendpoint.Builder builder = new Poliuserendpoint.Builder(
					AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
					null);

			builder = CloudEndpointUtils.updateBuilder(builder);
			Poliuserendpoint endpoint = builder.setApplicationName(
					"polimisocial").build();

			try {
				poliuser = endpoint.checkCredentials(mEmail, mPassword)
						.execute();
				nickname = poliuser.getNickname();
				return true;
			} catch (IOException e) {

			}
			return false;

		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			ShowProgress.showProgress(false, mProgressView,
					mRegistrationFormView, getApplicationContext());

			if (success) {
				Intent loginFinishedIntent = new Intent(
						RegistrationActivity.this, TabActivity.class);
				sessionManager.createLoginSession(nickname, mEmail);
				sessionManager.setId(Long.toString(poliuser.getUserId()));
				sessionManager.setFaculty(poliuser.getFaculty());
				GCMIntentService.register(getApplicationContext());
				RegistrationActivity.this.startActivity(loginFinishedIntent);
				finish();
			} else {
				Toast.makeText(RegistrationActivity.this, "Login error",
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			ShowProgress.showProgress(false, mProgressView,
					mRegistrationFormView, getApplicationContext());
		}
	}

	@Override
	public void onFacultyDialogPositiveClick(String faculty) {
		// TODO Auto-generated method stub
		mFacultyView.setText(faculty);

	}

	public void showNoticeDialog() {
		// Create an instance of the dialog fragment and show it
		DialogFragment dialog = new SingleChoiceDialogFragm();
		dialog.show(getFragmentManager(), "SingleChoiceDialogFragm");
	}
	
	 private  int calculateAge(Date birthDate)
	   {
	      int years = 0;
	      int months = 0;
	      int days = 0;
	      //create calendar object for birth day
	      Calendar birthDay = Calendar.getInstance();
	      birthDay.setTimeInMillis(birthDate.getTime());
	      //create calendar object for current day
	      long currentTime = System.currentTimeMillis();
	      Calendar now = Calendar.getInstance();
	      now.setTimeInMillis(currentTime);
	      //Get difference between years
	      years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
	      int currMonth = now.get(Calendar.MONTH) + 1;
	      int birthMonth = birthDay.get(Calendar.MONTH) + 1;
	      //Get difference between months
	      months = currMonth - birthMonth;
	      //if month difference is in negative then reduce years by one and calculate the number of months.
	      if (months < 0)
	      {
	         years--;
	         months = 12 - birthMonth + currMonth;
	         if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
	            months--;
	      } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
	      {
	         years--;
	         months = 11;
	      }
	      //Calculate the days
	      if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
	         days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
	      else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
	      {
	         int today = now.get(Calendar.DAY_OF_MONTH);
	         now.add(Calendar.MONTH, -1);
	         days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
	      } else
	      {
	         days = 0;
	         if (months == 12)
	         {
	            years++;
	            months = 0;
	         }
	      }
	      
	      return years;
	   }

}