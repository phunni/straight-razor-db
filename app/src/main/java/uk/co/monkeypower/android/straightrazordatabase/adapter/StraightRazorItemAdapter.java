package uk.co.monkeypower.android.straightrazordatabase.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import uk.co.monkeypower.android.straightrazordatabase.R;
import uk.co.redfruit.libraries.srpDB.data.Manufacturer;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class StraightRazorItemAdapter extends ArrayAdapter<Manufacturer> implements SectionIndexer{
	
	private List<Manufacturer> items;
	private Context context;
	private HashMap<String, Integer> alphaIndexer;
	private String[] sections;
	
	public StraightRazorItemAdapter(Context context, int textViewresourceId, List<Manufacturer> items) {
		super(context, textViewresourceId, items);
		this.items = items;
		this.context = context;
		
		alphaIndexer = new HashMap<String, Integer>();
		int size = items.size();
		for (int i = size - 1; i >= 0; i--) {
			String element = items.get(i).getTitle();
			alphaIndexer.put(element.substring(0, 1), i);
		}
		Set<String> keys = alphaIndexer.keySet();
		Iterator<String> it = keys.iterator();
		List<String> keyList = new ArrayList<String>();

		while (it.hasNext()) {
			String key = it.next();
			keyList.add(key);
		}
		Collections.sort(keyList);
		 
        sections = new String[keyList.size()];
        keyList.toArray(sections);
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

	@Override
	public int getPositionForSection(int section) {
		String letter = sections[section];
		 
        return alphaIndexer.get(letter);
	}

	@Override
	public int getSectionForPosition(int arg0) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		return sections;
	}
	
	

}
