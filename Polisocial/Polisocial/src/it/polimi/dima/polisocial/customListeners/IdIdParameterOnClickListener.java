package it.polimi.dima.polisocial.customListeners;

import android.view.View;
import android.view.View.OnClickListener;

public class IdIdParameterOnClickListener implements OnClickListener{
	protected long id;
	protected long postAuthor;

	public IdIdParameterOnClickListener(long id, long postAuthor)
	{
	    this.id = id;
	    this.postAuthor = postAuthor;
	}

	@Override
	public void onClick(View view) {


	}
}
