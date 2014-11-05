package it.polimi.dima.polisocial.adapter;
import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.creationActivities.NewRentalActivity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class GridViewPictureAdapter extends ArrayAdapter<Bitmap> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<Bitmap> data = new ArrayList<Bitmap>();
	private TextView textPictureView;
	private ArrayList<byte[]> byteArray;

	public GridViewPictureAdapter(Context context, int layoutResourceId,
			ArrayList<Bitmap> data, ArrayList<byte[]> byteArray, TextView textPictureView) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
		this.textPictureView = textPictureView;
		this.byteArray = byteArray;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) row.findViewById(R.id.image);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}

		Bitmap item = (Bitmap) data.get(position);
		holder.image.setImageBitmap(item);
		holder.image.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				showDeleteDialog(position,context);
				return false;
			}
		});
		
		holder.image.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {

	            switch (event.getAction()) {
	                case MotionEvent.ACTION_DOWN: {
	                    ImageView view = (ImageView) v;
	                    //overlay is black with transparency of 0x77 (119)
	                    view.getDrawable().setColorFilter(0x77000000,PorterDuff.Mode.SRC_ATOP);
	                    view.invalidate();
	                    break;
	                }
	                case MotionEvent.ACTION_UP:
	                case MotionEvent.ACTION_CANCEL: {
	                    ImageView view = (ImageView) v;
	                    //clear the overlay
	                    view.getDrawable().clearColorFilter();
	                    view.invalidate();
	                    break;
	                }
	            }
				return false;
			}
		});
		return row;
	}
	
	static class ViewHolder {
		
		ImageView image;
	}
	
	public void showDeleteDialog(final int position,Context c){
		
    	final TextView output = new TextView(c);
    	AlertDialog.Builder dialog = new AlertDialog.Builder(c);
    	dialog.setTitle("Do you want to delete this picture? ");
    	dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            		data.remove(position);
            		byteArray.remove(position);
            		notifyDataSetChanged();
            		if(data.isEmpty()){
            			textPictureView.setVisibility(View.GONE);
            		}
               	 	dialog.dismiss();
            		}
            	
            });
    	dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
               	 	dialog.dismiss();
            		}
            	
            });
    	dialog.show();
    	
        }
}