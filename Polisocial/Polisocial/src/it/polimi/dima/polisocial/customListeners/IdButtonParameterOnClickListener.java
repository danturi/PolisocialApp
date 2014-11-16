package it.polimi.dima.polisocial.customListeners;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class IdButtonParameterOnClickListener implements OnClickListener{

	protected long id;
	protected Button button;

	public IdButtonParameterOnClickListener(long id,Button button)
	{
	    this.id = id;
	    this.button=button;
	}

	@Override
	public void onClick(View v) {
		
	}

	

}
