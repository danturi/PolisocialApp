/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.deviceinfoendpoint.Deviceinfoendpoint;
import it.polimi.dima.polisocial.deviceinfoendpoint.model.DeviceInfo;
import it.polimi.dima.polisocial.tabactivityAndFragments.TabActivity;
import it.polimi.dima.polisocial.utilClasses.PostType;
import it.polimi.dima.polisocial.utilClasses.SessionManager;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;

@SuppressLint("NewApi")
public class GCMIntentService extends GCMBaseIntentService {
	private final Deviceinfoendpoint endpoint;
	private Long userId;
	final static String GROUP_KEY_SPOTTED = "group_key_spotted";
	final static String GROUP_KEY_ANNOUNCEMENT = "group_key_announcement";
	final static String GROUP_KEY_EVENT = "group_key_event";
	final static String GROUP_KEY_HITON = "group_key_hiton";
	public static int countSpotted = 1;
	public static int countEvent = 1;
	public static int countAnnouncement = 1;
	public static int countHitOn = 1;

	public static final String PROJECT_NUMBER = "617518383250";

	public static void register(Context mContext) {
		GCMRegistrar.checkDevice(mContext);
		GCMRegistrar.checkManifest(mContext);
		GCMRegistrar.register(mContext, PROJECT_NUMBER);
	}

	public static void unregister(Context mContext) {
		GCMRegistrar.unregister(mContext);
	}

	public GCMIntentService() {
		super(PROJECT_NUMBER);

		Deviceinfoendpoint.Builder endpointBuilder = new Deviceinfoendpoint.Builder(
				AndroidHttp.newCompatibleTransport(), new JacksonFactory(),
				new HttpRequestInitializer() {
					public void initialize(HttpRequest httpRequest) {
					}
				});
		endpoint = CloudEndpointUtils.updateBuilder(endpointBuilder).build();
	}

	@Override
	public void onError(Context context, String errorId) {

		sendNotificationIntent(
				context,
				"Registration for notification...FAILED!\n\n"
						+ "A registration error occurred (errorid: "
						+ errorId
						+ "). "
						+ "Do you have your project number ("
						+ ("".equals(PROJECT_NUMBER) ? "<unset>"
								: PROJECT_NUMBER)
						+ ")  set correctly, and do you have Google Cloud Messaging enabled for the "
						+ "project?", true, true);
	}

	@Override
	public void onMessage(Context context, Intent intent) {
		// sendNotificationIntent(context,intent.getStringExtra("message"),
		// false, false);
		generateNotification(context, intent.getStringExtra("message"));
	}

	@Override
	public void onRegistered(Context context, String registration) {
		/*
		 * This is some special exception-handling code that we're using to work
		 * around a problem with the DevAppServer and methods that return null
		 * in App Engine 1.7.5.
		 */
		boolean alreadyRegisteredWithEndpointServer = false;
		try {
			/*
			 * Using cloud endpoints, see if the device has already been
			 * registered with the backend
			 */
			DeviceInfo existingInfo = endpoint.getDeviceInfo(registration)
					.execute();
			// TODO controllo più utenti su stesso device...?
			if (existingInfo != null
					&& registration.equals(existingInfo
							.getDeviceRegistrationID())) {
				alreadyRegisteredWithEndpointServer = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			if (!alreadyRegisteredWithEndpointServer) {
				/*
				 * We are not registered as yet. Send an endpoint message
				 * containing the GCM registration id and some of the device's
				 * product information over to the backend. Then, we'll be
				 * registered.
				 */
				/*
				 * SharedPreferences prefs
				 * =PreferenceManager.getDefaultSharedPreferences
				 * (getApplicationContext()); userId=prefs.getLong("userId", 0);
				 */
				SessionManager session = new SessionManager(
						getApplicationContext());
				userId = Long.valueOf(session.getUserDetails().get(
						SessionManager.KEY_USERID));

				DeviceInfo deviceInfo = new DeviceInfo();
				endpoint.insertDeviceInfo(
						deviceInfo
								.setUserId(userId)
								.setDeviceRegistrationID(registration)
								.setTimestamp(
										new DateTime(System.currentTimeMillis())
												.getValue())
								.setDeviceInformation(
										URLEncoder
												.encode(android.os.Build.MANUFACTURER
														+ " "
														+ android.os.Build.PRODUCT,
														"UTF-8"))).execute();
			}
		} catch (IOException e) {
			Log.e(GCMIntentService.class.getName(),
					"Exception received when attempting to register with server at "
							+ endpoint.getRootUrl(), e);
			sendNotificationIntent(
					context,
					"1) Registration with Google Cloud Messaging...SUCCEEDED!\n\n"
							+ "2) Registration with Endpoints Server...FAILED!\n\n"
							+ "Unable to register your device with your Cloud Endpoints server running at "
							+ endpoint.getRootUrl()
							+ ". Either your Cloud Endpoints server is not deployed to App Engine, or "
							+ "your settings need to be changed to run against a local instance "
							+ "by setting LOCAL_ANDROID_RUN to 'true' in CloudEndpointUtils.java.",
					true, true);
			return;
		}
		/*
		 * sendNotificationIntent( context,
		 * "1) Registration with Google Cloud Messaging...SUCCEEDED!\n\n" +
		 * "2) Registration with Endpoints Server...SUCCEEDED!\n\n" +
		 * "Device registration with Cloud Endpoints Server running at " +
		 * endpoint.getRootUrl() + " succeeded!\n\n" +
		 * "To send a message to this device, " +
		 * "open your browser and navigate to the sample application at " +
		 * getWebSampleUrl(endpoint.getRootUrl()), false, true);
		 */
	}

	/**
	 * Generate a notification intent and dispatch it to the RegisterActivity.
	 * This is how we get information from this service (non-UI) back to the
	 * activity.
	 * 
	 * For this to work, the 'android:launchMode="singleTop"' attribute needs to
	 * be set for the RegisterActivity in AndroidManifest.xml.
	 * 
	 * @param context
	 *            the application context
	 * @param message
	 *            the message to send
	 * @param isError
	 *            true if the message is an error-related message; false
	 *            otherwise
	 * @param isRegistrationMessage
	 *            true if this message is related to registration/unregistration
	 */
	private void sendNotificationIntent(Context context, String message,
			boolean isError, boolean isRegistrationMessage) {
		Intent notificationIntent = new Intent(context, ErrorGcmActivity.class);
		notificationIntent.putExtra("gcmIntentServiceMessage", true);
		notificationIntent.putExtra("registrationMessage",
				isRegistrationMessage);
		notificationIntent.putExtra("error", isError);
		notificationIntent.putExtra("message", message);
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(notificationIntent);
	}

	private String getWebSampleUrl(String endpointUrl) {
		// Not the most elegant solution; we'll improve this in the future
		if (CloudEndpointUtils.LOCAL_ANDROID_RUN) {
			return CloudEndpointUtils.LOCAL_APP_ENGINE_SERVER_URL
					+ "index.html";
		}
		return endpointUrl.replace("/_ah/api/", "/index.html");
	}

	/**
	 * Called back when the Google Cloud Messaging service has unregistered the
	 * device.
	 * 
	 * @param context
	 *            the Context
	 */
	@Override
	protected void onUnregistered(Context context, String registrationId) {
		if (registrationId != null && registrationId.length() > 0) {
			try {
				endpoint.removeDeviceInfo(registrationId).execute();
			} catch (IOException e) {
				Log.e(GCMIntentService.class.getName(),
						"Exception received when attempting to unregister with server at "
								+ endpoint.getRootUrl(), e);
				sendNotificationIntent(
						context,
						"1) De-registration with Google Cloud Messaging....SUCCEEDED!\n\n"
								+ "2) De-registration with Endpoints Server...FAILED!\n\n"
								+ "We were unable to de-register your device from your Cloud "
								+ "Endpoints server running at "
								+ endpoint.getRootUrl() + "."
								+ "See your Android log for more information.",
						true, true);
				return;
			}
		}

		/*
		 * sendNotificationIntent( context,
		 * "1) De-registration with Google Cloud Messaging....SUCCEEDED!\n\n" +
		 * "2) De-registration with Endpoints Server...SUCCEEDED!\n\n" +
		 * "Device de-registration with Cloud Endpoints server running at " +
		 * endpoint.getRootUrl() + " succeeded!", false, true);
		 */
	}

	/**
	 * Create a notification to inform the user that server has sent a message.
	 */
	private static void generateNotification(Context context, String message) {

		int icon = R.drawable.logo_login;
		long when = System.currentTimeMillis();
		String group = null;
		

		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		String title = " messages";
		Intent notificationIntent = new Intent(context, TabActivity.class);
		// set intent so it does not start a new activity
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		Bundle bundle = new Bundle();
		bundle.putBoolean("gcmNotification", true);
		//notificationIntent.putExtra("gcmNotification", true);

		
		// notifica di android non la nostra...
		Notification notification = null;
		
		
		//tipo spotted
		if (message.equals(PostType.SPOTTED.toString())) {
			group=GROUP_KEY_SPOTTED;
			bundle.putString("type", PostType.SPOTTED.toString());
			notificationIntent.putExtras(bundle);
			PendingIntent intent = PendingIntent.getActivity(context, 1,
					notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
			
			if(countSpotted>1){
				notification = new NotificationCompat.Builder(context)
				.setContentIntent(intent).setContentTitle("MyOwl Spotted")
				.setContentText(countSpotted + title).setSmallIcon(icon).setWhen(when).setGroup(group)
				.build();
				countSpotted++;
			}else {
			
			// Create an InboxStyle notification
					notification = new NotificationCompat.Builder(context)
					        .setContentTitle("MyOwl Spotted")
					        .setSmallIcon(R.drawable.logo_login)
					        .setContentIntent(intent)
					        .setStyle(new NotificationCompat.InboxStyle()
					                .addLine("1 message")
					                .setBigContentTitle("MyOwl Spotted"))
					        .setGroup(group)
					        .setGroupSummary(true)
					        .build();
					countSpotted++;

			}	
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.defaults |= Notification.DEFAULT_SOUND;
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notificationManager.notify(1, notification);
		}
		
		
		//tipo announcement
		if (message.equals(PostType.RENTAL.toString())
				|| message.equals(PostType.SECOND_HAND_BOOK.toString())
				|| message.equals(PostType.PRIVATE_LESSON.toString())) {
			group=GROUP_KEY_ANNOUNCEMENT;
			bundle.putString("type", "announcement");
			notificationIntent.putExtras(bundle);
			PendingIntent intent = PendingIntent.getActivity(context, 2,
					notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			if(countAnnouncement>1){
				notification = new NotificationCompat.Builder(context)
				.setContentIntent(intent).setContentTitle("MyOwl Announcement")
				.setContentText(countAnnouncement + title).setSmallIcon(icon).setWhen(when).setGroup(group)
				.build();
				countAnnouncement++;
			}else {
			
			// Create an InboxStyle notification
					notification = new NotificationCompat.Builder(context)
					        .setContentTitle("MyOwl Announcement")
					        .setSmallIcon(R.drawable.logo_login)
					        .setContentIntent(intent)
					        .setStyle(new NotificationCompat.InboxStyle()
					                .addLine("1 message")
					                .setBigContentTitle("MyOwl Announcement"))
					        .setGroup(group)
					        .setGroupSummary(true)
					        .build();
					countAnnouncement++;

			}	
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.defaults |= Notification.DEFAULT_SOUND;
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notificationManager.notify(2, notification);
		}
		
		
		
		if (message.equals(PostType.EVENT.toString())) {
			group=GROUP_KEY_EVENT;
			bundle.putString("type", PostType.EVENT.toString());
			notificationIntent.putExtras(bundle);
			PendingIntent intent = PendingIntent.getActivity(context, 3,
					notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
			if(countEvent>1){
				notification = new NotificationCompat.Builder(context)
				.setContentIntent(intent).setContentTitle("MyOwl Initiative")
				.setContentText(countEvent + title).setSmallIcon(icon).setWhen(when).setGroup(group)
				.build();
				countEvent++;
			}else {
			
			// Create an InboxStyle notification
					notification = new NotificationCompat.Builder(context)
					        .setContentTitle("MyOwl Initiative")
					        .setSmallIcon(R.drawable.logo_login)
					        .setContentIntent(intent)
					        .setStyle(new NotificationCompat.InboxStyle()
					                .addLine("1 message")
					                .setBigContentTitle("MyOwl Initiative"))
					        .setGroup(group)
					        .setGroupSummary(true)
					        .build();
					countEvent++;

			}	
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.defaults |= Notification.DEFAULT_SOUND;
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notificationManager.notify(3, notification);
		}
		
		
		
		
		
		if (message.equals(PostType.HIT_ON.toString())) {
			group=GROUP_KEY_HITON;
			bundle.putString("type", PostType.HIT_ON.toString());
			notificationIntent.putExtras(bundle);
			PendingIntent intent = PendingIntent.getActivity(context, 4,
					notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
			if(countHitOn>1){
				notification = new NotificationCompat.Builder(context)
				.setContentIntent(intent).setContentTitle("MyOwl HitOn")
				.setContentText(countHitOn + title).setSmallIcon(icon).setWhen(when).setGroup(group)
				.build();
				countHitOn++;
			}else {
			
			// Create an InboxStyle notification
					notification = new NotificationCompat.Builder(context)
					        .setContentTitle("MyOwl HitOn")
					        .setSmallIcon(R.drawable.logo_login)
					        .setContentIntent(intent)
					        .setStyle(new NotificationCompat.InboxStyle()
					                .addLine("1 message")
					                .setBigContentTitle("MyOwl HitOn"))
					        .setGroup(group)
					        .setGroupSummary(true)
					        .build();
					countHitOn++;

			}	
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.defaults |= Notification.DEFAULT_SOUND;
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notificationManager.notify(4, notification);
		}

		
		
		
		
		
		
		
		

	}

}