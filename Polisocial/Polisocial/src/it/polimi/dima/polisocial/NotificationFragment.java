package it.polimi.dima.polisocial;

import it.polimi.dima.polisocial.adapter.NotificationAdapter;
import it.polimi.dima.polisocial.entity.notificationendpoint.model.Notification;
import it.polimi.dima.polisocial.loader.NotificationListLoader;
import it.polimi.dima.polisocial.utilClasses.NotificationCategory;
import it.polimi.dima.polisocial.utilClasses.SessionManager;

import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NotificationFragment extends ListFragment implements
		LoaderManager.LoaderCallbacks<List<Notification>> {

	private boolean refreshRequest = false;
	private NotificationAdapter mAdapter;
	private SwipeRefreshLayout mSwipeRefreshLayout;
	private View mProgressView;
	private SessionManager session;
	private TextView mTextView;
	private TabActivity activity = null;

	public NotificationFragment() {
	}

	public NotificationFragment(TabActivity activity) {
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_notification, null);
		session = new SessionManager(getActivity().getApplicationContext());
		mTextView = (TextView) v.findViewById(R.id.no_notification);
		mTextView.setVisibility(View.GONE);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setHasOptionsMenu(true);

		// Create an empty adapter we will use to display the loaded data.
		mAdapter = new NotificationAdapter(getActivity());
		setListAdapter(mAdapter);

		mProgressView = getView().findViewById(R.id.progress_bar);

		mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(
				R.id.swipe_container);
		mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		mSwipeRefreshLayout
				.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						refreshRequest = true;
						initiateRefresh();
					}
				});

		// Start out with a progress indicator.
		showProgress(true);

		Bundle bundle = new Bundle();
		bundle.putLong(
				"userId",
				Long.valueOf(session.getUserDetails().get(
						SessionManager.KEY_USERID)));
		getLoaderManager().initLoader(0, bundle, this);

	}

	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.findItem(R.id.action_create_event).setVisible(false);
		menu.findItem(R.id.action_add_restaurant).setVisible(false);
		menu.findItem(R.id.menu_filter_events).setVisible(false);
		menu.findItem(R.id.action_write_spotted_post).setVisible(false);
	}

	/**
	 * Shows the progress UI and hides notification list.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mSwipeRefreshLayout.setVisibility(show ? View.GONE : View.VISIBLE);
			mSwipeRefreshLayout.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mSwipeRefreshLayout.setVisibility(show ? View.GONE
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
			mSwipeRefreshLayout.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	private void initiateRefresh() {
		Bundle bundle = new Bundle();
		bundle.putLong(
				"userId",
				Long.valueOf(session.getUserDetails().get(
						SessionManager.KEY_USERID)));
		getLoaderManager().restartLoader(0, bundle, this);
	}

	private void onRefreshComplete() {
		refreshRequest = false;
		mSwipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Notification notificationClicked = (Notification) getListView()
				.getItemAtPosition(position);
		long postId = notificationClicked.getPostId();
		String notificationCategory = notificationClicked.getTypePost();
		Intent showRelativeCommentsIntent = new Intent(getActivity(),
				ShowRelatedCommentsActivity.class);
		showRelativeCommentsIntent.putExtra("postId", postId);
		showRelativeCommentsIntent.putExtra("notificationCategory",
				notificationCategory);
		showRelativeCommentsIntent.putExtra("notificationId",
				notificationClicked.getId());

		ImageView icon = (ImageView) v
				.findViewById(R.id.notification_type_icon);

		// depending on notification type and read flag, pick the appropriate
		// icon
		if (notificationCategory.equals(NotificationCategory.SIMPLE_SPOTTED
				.toString())) {
			icon.setImageResource(R.drawable.spotted_icon_normal);
		} else if (notificationCategory.equals(NotificationCategory.EVENT
				.toString())) {
			icon.setImageResource(R.drawable.events_icon_normal);
		} else if (notificationCategory.equals(NotificationCategory.HIT_ON
				.toString())) {
			icon.setImageResource(R.drawable.cupido_pressed);
		} else if (notificationCategory
				.equals(NotificationCategory.SECOND_HAND_BOOK.toString())) {
			icon.setImageResource(R.drawable.spotted_icon_normal);
		} else if (notificationCategory.equals(NotificationCategory.RENTAL
				.toString())) {
			icon.setImageResource(R.drawable.spotted_icon_normal);
		} else if (notificationCategory
				.equals(NotificationCategory.PRIVATE_LESSON.toString())) {

		}

		startActivity(showRelativeCommentsIntent);
		// Insert desired behaviour here.
	}

	@Override
	public Loader<List<Notification>> onCreateLoader(int arg0, Bundle bundle) {
		long userId = (Long) bundle.get("userId");
		return new NotificationListLoader(getActivity(), userId);

	}

	@Override
	public void onLoadFinished(Loader<List<Notification>> arg0,
			List<Notification> data) {
		if (data != null)
			mAdapter.setData(data);
		else {
			mTextView.setVisibility(View.VISIBLE);
			mTextView.setText("There is no notification");
		}
		// The list should now be shown.
		if (refreshRequest) {
			onRefreshComplete();
		}
		showProgress(false);
		// if (isResumed()) {
		// setListShown(true);
		// } else {
		// setListShownNoAnimation(true);
		// }
	}

	@Override
	public void onLoaderReset(Loader<List<Notification>> arg0) {
		mAdapter.setData(null);
	}

	@Override
	public void onResume() {
		if (activity != null) {

			if (activity.getNotificationIntent().getBooleanExtra(
					"gcmNotification", false)) {
				initiateRefresh();
				activity.setNotificationIntent(new Intent());
			}
		}
		super.onResume();
	}
}
