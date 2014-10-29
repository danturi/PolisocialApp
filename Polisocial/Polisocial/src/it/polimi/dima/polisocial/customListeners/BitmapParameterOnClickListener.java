package it.polimi.dima.polisocial.customListeners;

import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;

public class BitmapParameterOnClickListener implements OnClickListener{
	protected Bitmap bitmap;

	public BitmapParameterOnClickListener(Bitmap bitmap)
	{
	    this.bitmap = bitmap;
	}

	@Override
	public void onClick(View view) {


	}
}
