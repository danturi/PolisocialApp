package it.polimi.dima.polisocial.utilClasses;

import it.polimi.dima.polisocial.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;

	// Editor for Shared preferences
	Editor editor;

	// Context
	Context _context;

	// Shared pref mode
	int PRIVATE_MODE = 0;

	// Sharedpref file name
	private static final String PREF_NAME = "PolisocialPref";

	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";

	// User name (make variable public to access from outside)
	public static final String KEY_NAME = "name";

	// Email address (make variable public to access from outside)
	public static final String KEY_EMAIL = "email";

	// Id (make variable public to access from outside)
	public static final String KEY_USERID = "key";

	public static final String KEY_FACULTY = "faculty";

	public static final String KEY_LIKE = "like";

	public static final String KEY_DISLIKE = "dislike";

	// Constructor
	public SessionManager(Context context) {
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}

	/**
	 * Create login session
	 * */
	public void createLoginSession(String name, String email) {
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);

		// Storing name in pref
		editor.putString(KEY_NAME, name);

		// Storing email in pref
		editor.putString(KEY_EMAIL, email);

		editor.putStringSet(KEY_LIKE, null);

		editor.putStringSet(KEY_DISLIKE, null);

		// commit changes
		editor.commit();
	}

	public void setId(String id) {
		editor.putString(KEY_USERID, id);
		editor.commit();
	}

	public void setFaculty(String faculty) {
		editor.putString(KEY_FACULTY, faculty);
		editor.commit();
	}

	public void setLikeSpotted(Long postId) {

		if (loadArrayLikeSpotted() == null || loadArrayLikeSpotted().isEmpty()) {
			ArrayList<Long> arrayLike = new ArrayList<Long>();
			arrayLike.add(postId);
			saveArrayLikeSpotted(arrayLike);
			return;
		}

		ArrayList<Long> arrayLike = loadArrayLikeSpotted();
		arrayLike.add(postId);
		saveArrayLikeSpotted(arrayLike);
	}

	public void setLikeEvent(Long postId) {

		if (loadArrayLikeEvent() == null || loadArrayLikeEvent().isEmpty()) {
			ArrayList<Long> arrayLike = new ArrayList<Long>();
			arrayLike.add(postId);
			saveArrayLikeEvent(arrayLike);
			return;
		}

		ArrayList<Long> arrayLike = loadArrayLikeEvent();
		arrayLike.add(postId);
		saveArrayLikeEvent(arrayLike);
	}

	public void setDisLike(Long postId) {

		if (loadArrayDisLike() == null || loadArrayDisLike().isEmpty()) {
			ArrayList<Long> arrayDisLike = new ArrayList<Long>();
			arrayDisLike.add(postId);
			saveArrayDisLike(arrayDisLike);
			return;
		}

		ArrayList<Long> dislikePosts = loadArrayDisLike();
		dislikePosts.add(postId);
		saveArrayDisLike(dislikePosts);
	}
	
	public void setGoing(Long postId) {

		if (loadArrayGoing() == null || loadArrayGoing().isEmpty()) {
			ArrayList<Long> arrayGoing = new ArrayList<Long>();
			arrayGoing.add(postId);
			saveArrayGoing(arrayGoing);
			return;
		}

		ArrayList<Long> arrayGoing = loadArrayGoing();
		arrayGoing.add(postId);
		saveArrayGoing(arrayGoing);
	}
	
	

	public boolean saveArrayLikeSpotted(ArrayList<Long> array) {

		editor.putInt("Like_size", array.size());

		for (int i = 0; i < array.size(); i++) {
			editor.remove("Like_" + i);
			editor.putLong("Like_" + i, array.get(i));
		}

		return editor.commit();
	}
	
	
	public boolean saveArrayLikeEvent(ArrayList<Long> array) {

		editor.putInt("LikeEvent_size", array.size());

		for (int i = 0; i < array.size(); i++) {
			editor.remove("LikeEvent_" + i);
			editor.putLong("LikeEvent_" + i, array.get(i));
		}

		return editor.commit();
	}

	public boolean saveArrayDisLike(ArrayList<Long> array) {

		editor.putInt("DisLike_size", array.size());

		for (int i = 0; i < array.size(); i++) {
			editor.remove("DisLike_" + i);
			editor.putLong("DisLike_" + i, array.get(i));
		}

		return editor.commit();
	}
	
	public boolean saveArrayGoing(ArrayList<Long> array) {

		editor.putInt("Going_size", array.size());

		for (int i = 0; i < array.size(); i++) {
			editor.remove("Going_" + i);
			editor.putLong("Going_" + i, array.get(i));
		}

		return editor.commit();
	}

	public ArrayList<Long> loadArrayLikeSpotted() {
		ArrayList<Long> post = new ArrayList<Long>();
		int size = pref.getInt("Like_size", 0);

		for (int i = 0; i < size; i++) {
			post.add(pref.getLong("Like_" + i, 0));
		}
		return post;
	}
	
	public ArrayList<Long> loadArrayLikeEvent() {
		ArrayList<Long> post = new ArrayList<Long>();
		int size = pref.getInt("LikeEvent_size", 0);

		for (int i = 0; i < size; i++) {
			post.add(pref.getLong("LikeEvent_" + i, 0));
		}
		return post;
	}

	public ArrayList<Long> loadArrayDisLike() {
		ArrayList<Long> post = new ArrayList<Long>();
		int size = pref.getInt("DisLike_size", 0);

		for (int i = 0; i < size; i++) {
			post.add(pref.getLong("DisLike_" + i, 0));
		}
		return post;
	}
	
	public ArrayList<Long> loadArrayGoing() {
		ArrayList<Long> post = new ArrayList<Long>();
		int size = pref.getInt("Going_size", 0);

		for (int i = 0; i < size; i++) {
			post.add(pref.getLong("Going_" + i, 0));
		}
		return post;
	}

	/**
	 * Check login method wil check user login status If false it will redirect
	 * user to login page Else won't do anything
	 * */
	public void checkLogin() {
		// Check login status
		if (!this.isLoggedIn()) {
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, LoginActivity.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			// Staring Login Activity
			_context.startActivity(i);
		}

	}

	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		user.put(KEY_NAME, pref.getString(KEY_NAME, null));

		// user email
		user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

		// user id
		user.put(KEY_USERID, pref.getString(KEY_USERID, null));

		// user faculty
		user.put(KEY_FACULTY, pref.getString(KEY_FACULTY, null));

		// return user
		return user;
	}

	/**
	 * Clear session details
	 * */
	public void logoutUser() {
		// Clearing all data from Shared Preferences
		editor.remove(KEY_NAME).remove(KEY_EMAIL).remove(KEY_USERID);
		editor.putBoolean(IS_LOGIN, false);
		editor.commit();

		// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, LoginActivity.class);

		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// Staring Login Activity
		_context.startActivity(i);
	
	}

	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn() {
		return pref.getBoolean(IS_LOGIN, false);
	}
}