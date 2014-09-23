package it.polimi.dima.polisocial.customOnClickListeners;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class StringStringParametersOnClickListener implements OnClickListener {
	protected String fieldType;
	protected TextView comment;

	public StringStringParametersOnClickListener(String fieldType, TextView comment)
	{
	    this.fieldType = fieldType;
	    this.comment=comment;
	}
	@Override
	public void onClick(View view) {


	}
}
