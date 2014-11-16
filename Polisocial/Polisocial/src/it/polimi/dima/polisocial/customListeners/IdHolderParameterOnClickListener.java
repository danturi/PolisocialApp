package it.polimi.dima.polisocial.customListeners;

import android.view.View;
import android.view.View.OnClickListener;


public class IdHolderParameterOnClickListener implements OnClickListener{

	protected long id;
	protected Object object;

	public IdHolderParameterOnClickListener(long id,Object obj)
	{
	    this.id = id;
	    this.object=obj;
	}

	@Override
	public void onClick(View v) {
		
	}

	

}
