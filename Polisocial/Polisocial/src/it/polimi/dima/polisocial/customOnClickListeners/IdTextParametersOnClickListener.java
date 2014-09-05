package it.polimi.dima.polisocial.customOnClickListeners;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class IdTextParametersOnClickListener implements OnClickListener{
		protected long id;
		protected TextView comment;

		public IdTextParametersOnClickListener(long id, TextView comment)
		{
		    this.id = id;
		    this.comment=comment;
		}

		@Override
		public void onClick(View view) {


		}
}
