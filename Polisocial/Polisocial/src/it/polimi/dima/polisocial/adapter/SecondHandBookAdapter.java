package it.polimi.dima.polisocial.adapter;

import it.polimi.dima.polisocial.R;
import it.polimi.dima.polisocial.entity.secondhandbookendpoint.model.SecondHandBook;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SecondHandBookAdapter extends EndlessListAdapter<SecondHandBook> {

	private final int VIEW_BOOK = 0;
	private final int VIEW_LOADING = 1;

	public SecondHandBookAdapter(Context context) {
		super(context, R.layout.book_row_item);
	}

	@Override
	public int getItemViewType(int position) {
		// Define a way to determine which layout to use.
		if (position >= (getCount() - loading_row)) {
			return VIEW_LOADING;
		} else {
			return VIEW_BOOK;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	/**
	 * Populate new items in the list.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		SpottedViewHolder holder;
		int type = getItemViewType(position);

		if (view == null) {
			holder = new SpottedViewHolder();
			if (type == VIEW_LOADING) {
				view = mInflater.inflate(R.layout.progress_white, parent, false);
			} else {
				view = mInflater.inflate(R.layout.book_row_item, parent, false);
				holder.title = (TextView) view.findViewById(R.id.title);
				holder.authorNames = (TextView) view
						.findViewById(R.id.author_names);
				holder.price = (TextView) view.findViewById(R.id.price);
				holder.condition = (TextView) view.findViewById(R.id.condition);

			}
			view.setTag(holder);
		} else {
			holder = (SpottedViewHolder) view.getTag();
		}

		if (getItemViewType(position) != VIEW_LOADING) {
			SecondHandBook item = getItem(position);
			holder.title.setText(item.getTitle());
			holder.authorNames.setText(item.getAuthorsBook().toString());
			if (item.getText()==null) {
				holder.condition.setText("Description not available");
			} else {
				holder.condition.setText("\"" + item.getText() + "\"");
			}
			holder.price.setText(item.getPrice().toString() + " €");
		}
		return view;
	}

	static class SpottedViewHolder {

		TextView title;
		TextView authorNames;
		TextView price;
		TextView condition;

	}

}
