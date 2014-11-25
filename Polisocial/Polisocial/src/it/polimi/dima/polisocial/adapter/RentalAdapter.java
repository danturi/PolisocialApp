package it.polimi.dima.polisocial.adapter;

import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.entity.rentalendpoint.model.Rental;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RentalAdapter extends ArrayAdapter<Rental> {

	protected LayoutInflater mInflater;

	public RentalAdapter(Context context, int resource) {
		super(context, resource);
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(List<Rental> data) {
		clear();
		if (data != null) {
			for (Rental appEntry : data) {
				add(appEntry);
			}
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final RentalViewHolder holder;

		if (view == null) {
			holder = new RentalViewHolder();

			view = mInflater.inflate(R.layout.rental_item, parent, false);

			holder.title = (TextView) view.findViewById(R.id.title);
			holder.address = (TextView) view.findViewById(R.id.address);
			holder.price = (TextView) view.findViewById(R.id.price);
			holder.typeAndSquareMeters = (TextView) view.findViewById(R.id.type_and_square_meters);
			view.setTag(holder);
		} else {
			holder = (RentalViewHolder) view.getTag();
		}
		
		Rental item = getItem(position);
		
		holder.title.setText(item.getTitle());
		holder.price.setText(item.getPrice().shortValue() + " €");
		holder.address.setText(item.getAddress());
		holder.typeAndSquareMeters.setText(item.getType() + "     " + item.getSquaredMeter() + " mq");
		
		return view;
	}

	static class RentalViewHolder {
		TextView title;
		TextView address;
		TextView price;
		TextView typeAndSquareMeters;
	}

}
