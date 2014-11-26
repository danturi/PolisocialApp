package it.polimi.dima.polisocial.customListeners;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class IdButtonTextViewIntegerParameterOnClickListener implements OnClickListener{

	protected long id;
	protected Button button;
	protected TextView number;
	protected Integer numOf;

	public IdButtonTextViewIntegerParameterOnClickListener(long id,Button button, TextView numOfText,Integer numOf)
	{
	    this.id = id;
	    this.button=button;
	    this.number=numOfText;
	    this.numOf=numOf;
	}

	@Override
	public void onClick(View v) {
		
	}

	

}
