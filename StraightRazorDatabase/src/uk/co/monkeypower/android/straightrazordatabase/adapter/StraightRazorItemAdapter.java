package uk.co.monkeypower.android.straightrazordatabase.adapter;

import java.util.List;

import uk.co.monkeypower.android.straightrazordatabase.R;
import uk.co.redfruit.libraries.srpDB.data.Manufacturer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StraightRazorItemAdapter extends ArrayAdapter<Manufacturer> {
	
	private List<Manufacturer> items;
	private Context context;
	
	public StraightRazorItemAdapter(Context context, int textViewresourceId, List<Manufacturer> items) {
		super(context, textViewresourceId, items);
		this.items = items;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_list, null);
		}
		Manufacturer currentManufacturer = items.get(position);
		TextView title = (TextView) convertView.findViewById(R.id.itemTitle);
		title.setText(currentManufacturer.getTitle());
		return convertView;
	}
	
	

}
