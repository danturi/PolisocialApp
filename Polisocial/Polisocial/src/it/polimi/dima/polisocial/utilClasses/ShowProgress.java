package it.polimi.dima.polisocial.utilClasses;


import it.polimi.dima.polisocial.customListeners.AnimatorListenerAdapterCustomized;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;

public class ShowProgress {

	 /**
	 * Shows the progress UI and hides post list.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static void showProgress(boolean show, View mProgressView, View mLayout, Context context) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = context.getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLayout.setVisibility(show ? View.GONE : View.VISIBLE);
			mLayout.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapterCustomized(mLayout,show));

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapterCustomized(mProgressView,!show));
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLayout.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
	
}
