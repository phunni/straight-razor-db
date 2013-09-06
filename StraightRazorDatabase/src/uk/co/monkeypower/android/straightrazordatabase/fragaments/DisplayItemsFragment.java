package uk.co.monkeypower.android.straightrazordatabase.fragaments;

import java.util.ArrayList;
import java.util.List;

import uk.co.monkeypower.android.straightrazordatabase.R;
import uk.co.monkeypower.android.straightrazordatabase.activities.DisplayContentActivity;
import uk.co.monkeypower.android.straightrazordatabase.adapter.StraightRazorItemAdapter;
import uk.co.redfruit.libraries.srpDB.SRPDBClient;
import uk.co.redfruit.libraries.srpDB.data.Manufacturer;
import uk.co.redfruit.libraries.srpDB.exceptions.SRBClientException;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayItemsFragment extends Fragment implements OnItemClickListener{
	
	private List<Manufacturer> items;
	private ProgressDialog progressDialog;
	private Handler gotItemsHandler = new Handler(Looper.getMainLooper());
	private FragmentActivity parentActivity;
	
	private static final String TAG = "DisplayItemsFragment";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentActivity = getActivity();
		if (savedInstanceState != null) {
			items = savedInstanceState.getParcelableArrayList("items");
		}
		if (items == null) {
			String progressMessage = getResources().getString(
					R.string.waitForItems);
			progressDialog = ProgressDialog.show(parentActivity, "", progressMessage,
					true);
			Thread downloadItems = new Thread(new DownloadItems());
			downloadItems.start();
		} else {
			gotItemsHandler.post(new UICallbackHandler());
		}
	}
	
	
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList("items", (ArrayList<Manufacturer>)items);
	}



	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long id) {
		Manufacturer selectedItem = items.get(position);
		Intent intent = new Intent(parentActivity, DisplayContentActivity.class);
		intent.putExtra("item", selectedItem);
		startActivity(intent);
	}
	
	private class DownloadItems implements Runnable {

		@Override
		public void run() {
			Manufacturer manufacturer = parentActivity.getIntent().getExtras().getParcelable(
					"manufacturer");
			try {
				items = new SRPDBClient().getManufacturers(manufacturer
						.getTitle());
			} catch (SRBClientException e) {
				Log.e(TAG, "Failed to get manufacturer data.", e);
				gotItemsHandler.post(new ExceptionUICallbackHandler());
				return;
			}
			gotItemsHandler.post(new UICallbackHandler());
		}

	}

	private class UICallbackHandler implements Runnable {

		@Override
		public void run() {
			if (items.size() == 0) {
				parentActivity.setContentView(R.layout.display_items_empty);
				TextView noItemsText = (TextView) parentActivity.findViewById(R.id.noItemsText);
				Linkify.addLinks(noItemsText, Linkify.WEB_URLS);
			} else {
				parentActivity.setContentView(R.layout.activity_display_items);
				StraightRazorItemAdapter itemAdapter = new StraightRazorItemAdapter(parentActivity, R.layout.item_list, 
						items);
				ListView itemsView = (ListView) parentActivity.findViewById(R.id.allItems);
				itemsView.setAdapter(itemAdapter);
				itemsView.setOnItemClickListener(DisplayItemsFragment.this);
			}
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}

	}

	private class ExceptionUICallbackHandler implements Runnable {

		@Override
		public void run() {
			Toast toast = Toast.makeText(parentActivity,R.string.noManufacturersFound, Toast.LENGTH_LONG);
			toast.show();
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
		
	}

}
