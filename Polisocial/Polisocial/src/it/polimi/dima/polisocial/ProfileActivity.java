package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.customListeners.StringStringParametersOnClickListener;
import it.polimi.dima.polisocial.entity.poliuserendpoint.Poliuserendpoint;
import it.polimi.dima.polisocial.entity.poliuserendpoint.model.PoliUser;
import it.polimi.dima.polisocial.utilClasses.PictureEditing;
import it.polimi.dima.polisocial.utilClasses.ProfileFieldType;
import it.polimi.dima.polisocial.utilClasses.SessionManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.jackson2.JacksonFactory;

public class ProfileActivity extends SwipeBackActivity {

	private static final int RESULT_LOAD_PICTURE = 1;

	private SwipeBackLayout mSwipeBackLayout;
	private View mProgressView;
	private View mProfileScreen;
	private long mUserToRetrieveId;
	private boolean mShowEditable = false;
	private ActionBar actionBar;
	private SessionManager session;

	ImageView profilePic1;
	TextView generalInfoText;
	TextView selfSummaryLabel;
	TextView selfSummaryText;
	TextView whatImDoingLabel;
	TextView whatImDoingText;
	TextView imReallyGoodAtLabel;
	TextView imReallyGoodAtText;
	TextView favoriteLabel;
	TextView favoriteText;
	TextView sixThingsWithoutLabel;
	TextView sixThingsWithoutText;

	ImageButton changePictureButton;
	ImageButton editGeneralInfoButton;
	ImageButton editSelfSummaryButton;
	ImageButton editWhatImDoingButton;
	ImageButton editImReallyGoodAtButton;
	ImageButton editFavouriteButton;
	ImageButton editSixThingsWithoutButton;

	private byte[] pictureInBytes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		getActionBar().setIcon(R.drawable.logo_login);
		session = new SessionManager(getApplicationContext());
		Long thisUserId = Long.valueOf(session.getUserDetails().get(SessionManager.KEY_USERID));
		

		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
		mProgressView = findViewById(R.id.progress);
		mProfileScreen = findViewById(R.id.show_profile_form);
		actionBar = getActionBar();

		profilePic1 = (ImageView) findViewById(R.id.profile_picture);
		generalInfoText = (TextView) findViewById(R.id.general_info_text);
		selfSummaryLabel = (TextView) findViewById(R.id.self_summary_label);
		selfSummaryText = (TextView) findViewById(R.id.self_summary_text);
		whatImDoingLabel = (TextView) findViewById(R.id.what_im_doing_label);
		whatImDoingText = (TextView) findViewById(R.id.what_im_doing_text);
		imReallyGoodAtLabel = (TextView) findViewById(R.id.im_really_good_at_label);
		imReallyGoodAtText = (TextView) findViewById(R.id.im_really_good_at_text);
		favoriteLabel = (TextView) findViewById(R.id.favorite_label);
		favoriteText = (TextView) findViewById(R.id.favorite_text);
		sixThingsWithoutLabel = (TextView) findViewById(R.id.six_things_without_label);
		sixThingsWithoutText = (TextView) findViewById(R.id.six_things_without_text);
		showProgress(true);
		// get id parameter passed from previous activity, if existing
		mUserToRetrieveId = getIntent().getLongExtra("userToRetrieveId", 0);
		// if the value of the userId is equal to zero, then show the user
		// profile of the user logged in
		// and enable all the button to edit fields
		if (mUserToRetrieveId == 0) {
			mShowEditable = true;
			// mUserToRetrieveId=SessionManager.getIdDellutenteLoggato
			try {
				new RetrieveProfileTask(thisUserId).execute();
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			changePictureButton = (ImageButton) findViewById(R.id.change_picture_button);
			editGeneralInfoButton = (ImageButton) findViewById(R.id.edit_general_info_button);
			editSelfSummaryButton = (ImageButton) findViewById(R.id.edit_self_summary_button);
			editWhatImDoingButton = (ImageButton) findViewById(R.id.edit_what_im_doing_button);
			editImReallyGoodAtButton = (ImageButton) findViewById(R.id.edit_im_really_good_at_button);
			editFavouriteButton = (ImageButton) findViewById(R.id.edit_favorite_button);
			editSixThingsWithoutButton = (ImageButton) findViewById(R.id.edit_six_things_without_button);

			// show the profile of a user identified by the id passed as
			// parameter
		} else {
			mShowEditable = false;
			try {
				new RetrieveProfileTask(mUserToRetrieveId).execute();
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_search) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
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
			// TODO:update pic on server
			showPicture();
		}
	}

	private void showPicture() {
		Bitmap bitmap = BitmapFactory.decodeByteArray(pictureInBytes, 0,
				pictureInBytes.length);
		profilePic1.setImageBitmap(bitmap);
	}

	/**
	 * Shows the progress UI and hides the profile form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mProfileScreen.setVisibility(show ? View.GONE : View.VISIBLE);
			mProfileScreen.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mProfileScreen.setVisibility(show ? View.GONE
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
			mProfileScreen.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous task used to retrieve profile data.
	 */
	public class RetrieveProfileTask extends AsyncTask<Void, Void, Boolean> {

		private PoliUser poliuser;
		private long userId;

		RetrieveProfileTask(long userId) throws NoSuchAlgorithmException,
				UnsupportedEncodingException {
			this.userId = userId;
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
				poliuser=endpoint.getPoliUser(userId).execute();
				
			}
			 catch (IOException e) {
				 if( new StringTokenizer(e.getMessage().toString()).nextToken().equals("404")) 
					 return false; 
			} 
			
			 return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			showProgress(false);

			if (success) {
				if (mShowEditable) {
					changePictureButton.setVisibility(View.VISIBLE);
					editGeneralInfoButton.setVisibility(View.VISIBLE);
					editSelfSummaryButton.setVisibility(View.VISIBLE);
					editWhatImDoingButton.setVisibility(View.VISIBLE);
					editImReallyGoodAtButton.setVisibility(View.VISIBLE);
					editFavouriteButton.setVisibility(View.VISIBLE);
					editSixThingsWithoutButton.setVisibility(View.VISIBLE);

					int age = poliuser.getAge();
					int height = poliuser.getHeight();
					generalInfoText.setText(age + "years, " + height + "cm ");

					if (poliuser.getSelfSummary() != null) {
						selfSummaryText.setText(poliuser.getSelfSummary());
					}
					if (poliuser.getWhatImDoingWithMyLife() != null) {
						whatImDoingText.setText(poliuser
								.getWhatImDoingWithMyLife());
					}
					if (poliuser.getImReallyGoodAt() != null) {
						imReallyGoodAtText
								.setText(poliuser.getImReallyGoodAt());
					}
					if (poliuser.getFavoriteBooksMoviesShowsMusic() != null) {
						favoriteText.setText(poliuser
								.getFavoriteBooksMoviesShowsMusic().toString());
					}
					if (poliuser.getSixThingsWithout() != null) {
						sixThingsWithoutText.setText(poliuser
								.getSixThingsWithout());
					}

					changePictureButton
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									Intent i = new Intent(
											Intent.ACTION_PICK,
											android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

									startActivityForResult(i,
											RESULT_LOAD_PICTURE);

								}
							});

					editSelfSummaryButton
							.setOnClickListener(new StringStringParametersOnClickListener(
									ProfileFieldType.SELF_SUMMARY.toString(),
									selfSummaryText) {

								@Override
								public void onClick(View v) {
									String text = selfSummaryText.getText()
											.toString();
									Intent intent = new Intent(
											getApplicationContext(),
											EditProfileFieldActivity.class);
									intent.putExtra("text", text);
									intent.putExtra("fieldType", fieldType);
									startActivity(intent);
								}
							});

					editWhatImDoingButton
							.setOnClickListener(new StringStringParametersOnClickListener(
									ProfileFieldType.WHAT_IM_DOING.toString(),
									whatImDoingText) {

								@Override
								public void onClick(View v) {
									String text = whatImDoingText.getText()
											.toString();
									Intent intent = new Intent(
											getApplicationContext(),
											EditProfileFieldActivity.class);
									intent.putExtra("text", text);
									intent.putExtra("fieldType", fieldType);
									startActivity(intent);
								}
							});
					editImReallyGoodAtButton
							.setOnClickListener(new StringStringParametersOnClickListener(
									ProfileFieldType.IM_REALLY_GOOD_AT
											.toString(), imReallyGoodAtText) {

								@Override
								public void onClick(View v) {
									String text = imReallyGoodAtText.getText()
											.toString();
									Intent intent = new Intent(
											getApplicationContext(),
											EditProfileFieldActivity.class);
									intent.putExtra("text", text);
									intent.putExtra("fieldType", fieldType);
									startActivity(intent);
								}
							});
					editFavouriteButton
							.setOnClickListener(new StringStringParametersOnClickListener(
									ProfileFieldType.FAVORITE.toString(),
									favoriteText) {

								@Override
								public void onClick(View v) {
									String text = favoriteText.getText()
											.toString();
									Intent intent = new Intent(
											getApplicationContext(),
											EditProfileFieldActivity.class);
									intent.putExtra("text", text);
									intent.putExtra("fieldType", fieldType);
									startActivity(intent);
								}
							});
					editSixThingsWithoutButton
							.setOnClickListener(new StringStringParametersOnClickListener(
									ProfileFieldType.SIX_THINGS_WITHOUT
											.toString(), sixThingsWithoutText) {

								@Override
								public void onClick(View v) {
									String text = sixThingsWithoutText
											.getText().toString();
									Intent intent = new Intent(
											getApplicationContext(),
											EditProfileFieldActivity.class);
									intent.putExtra("text", text);
									intent.putExtra("fieldType", fieldType);
									startActivity(intent);
								}
							});

				} else {
					
					if (poliuser.getSelfSummary() != null) {
						selfSummaryText.setText(poliuser.getSelfSummary());
					} else {
						selfSummaryLabel.setVisibility(View.GONE);
						selfSummaryText.setVisibility(View.GONE);
					}

					if (poliuser.getWhatImDoingWithMyLife() != null) {
						whatImDoingText.setText(poliuser
								.getWhatImDoingWithMyLife());
					} else {
						whatImDoingLabel.setVisibility(View.GONE);
						whatImDoingText.setVisibility(View.GONE);
					}
					if (poliuser.getImReallyGoodAt() != null) {
						imReallyGoodAtText
								.setText(poliuser.getImReallyGoodAt());
					} else {
						imReallyGoodAtLabel.setVisibility(View.GONE);
						imReallyGoodAtText.setVisibility(View.GONE);
					}
					if (poliuser.getFavoriteBooksMoviesShowsMusic() != null) {
						favoriteText.setText(poliuser
								.getFavoriteBooksMoviesShowsMusic().toString());
					} else {
						favoriteLabel.setVisibility(View.GONE);
						favoriteText.setVisibility(View.GONE);
					}
					if (poliuser.getSixThingsWithout() != null) {
						sixThingsWithoutText.setText(poliuser
								.getSixThingsWithout());
					} else {
						sixThingsWithoutLabel.setVisibility(View.GONE);
						sixThingsWithoutText.setVisibility(View.GONE);
					}

				}

				actionBar.setTitle(poliuser.getNickname());
				if (poliuser.getProfilePicture1() != null) {
					byte[] byteArrayImage = Base64.decode(
							poliuser.getProfilePicture1(), Base64.DEFAULT);
					profilePic1.setImageBitmap(BitmapFactory.decodeByteArray(
							byteArrayImage, 0, byteArrayImage.length));
				} else {
					profilePic1.setImageResource(R.drawable.no_picture_pic);
				}

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
