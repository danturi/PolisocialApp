package it.polimi.dima.polisocial.customListeners;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;

public class AnimatorListenerAdapterCustomized extends AnimatorListenerAdapter {
	
	private boolean show;
	private View view;
	
	public AnimatorListenerAdapterCustomized(View view, Boolean show) {
		super();
		this.show= show;
		this.view = view;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAnimationEnd(Animator animation) {
		super.onAnimationEnd(animation);
		view.setVisibility(show ? View.GONE
				: View.VISIBLE);
	}


}
