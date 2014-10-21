package it.polimi.dima.polisocial.adapter;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public abstract class EndlessListAdapter<T> extends ArrayAdapter<T> {

	protected LayoutInflater mInflater;
	Context context;
	protected int loading_row=1;
	
	public EndlessListAdapter(Context context, int resource) {
		super(context, resource);
		this.context = context;
		this.mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(List<T> data) {
		clear();
		if (data != null) {
			for (T appEntry : data) {
				add(appEntry);
			}
		}
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
	
	@Override
	public abstract int getItemViewType(int position);

	@Override
	public abstract int getViewTypeCount();

	@Override
	public int getCount() {
		return super.getCount() + loading_row;
	}

	@Override
	public T getItem(int position) {
		if (position < (getCount() - loading_row))
			return super.getItem(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		if (position < (getCount() - loading_row))
			return super.getItemId(position);
		else
			return -1;
	}

	public int getLoading_row() {
		return loading_row;
	}

	public void setLoading_row(int loading_row) {
		this.loading_row = loading_row;
	}


}
