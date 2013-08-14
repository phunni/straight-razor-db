package uk.co.monkeypower.android.straightrazordatabase.activities;

import java.util.List;

import uk.co.monkeypower.android.straightrazordatabase.R;
import uk.co.monkeypower.android.straightrazordatabase.adapter.StraightRazorItemAdapter;
import uk.co.redfruit.libraries.srpDB.SRPDBClient;
import uk.co.redfruit.libraries.srpDB.data.Manufacturer;
import uk.co.redfruit.libraries.srpDB.exceptions.SRBClientException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class DisplayItemsActivity extends Activity implements
		OnItemClickListener {

	private List<Manufacturer> items;
	private ProgressDialog progressDialog;
	private Handler gotItemsHandler = new Handler(Looper.getMainLooper());
	private final static String TAG = "DisplayItemsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		items = (List<Manufacturer>) getLastNonConfigurationInstance();
		if (items == null) {
			String manufacturerTitle = ((Manufacturer) getIntent().getExtras()
					.getParcelable("manufacturer")).getTitle();
			getActionBar().setTitle(manufacturerTitle);
			String progressMessage = getResources().getString(
					R.string.waitForItems);
			progressDialog = ProgressDialog.show(this, "", progressMessage,
					true);
			Thread downloadItems = new Thread(new DownloadItems());
			downloadItems.start();
		} else {
			gotItemsHandler.post(new UICallbackHandler());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_items, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long id) {
		Manufacturer selectedItem = items.get(position);
		Intent intent = new Intent(this, DisplayContentActivity.class);
		intent.putExtra("item", selectedItem);
		startActivity(intent);
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return items;
	}

	private class DownloadItems implements Runnable {

		@Override
		public void run() {
			Manufacturer manufacturer = getIntent().getExtras().getParcelable(
					"manufacturer");
			try {
				items = new SRPDBClient().getManufacturers(manufacturer
						.getTitle());
			} catch (SRBClientException e) {
				Toast toast = Toast.makeText(DisplayItemsActivity.this,
						R.string.noManufacturersFound, Toast.LENGTH_LONG);
				toast.show();
				Log.e(TAG, "Failed to get manufacturer data.", e);
			}
			gotItemsHandler.post(new UICallbackHandler());
		}

	}

	private class UICallbackHandler implements Runnable {

		@Override
		public void run() {
			setContentView(R.layout.activity_display_items);
			StraightRazorItemAdapter itemAdapter = new StraightRazorItemAdapter(
					getApplicationContext(), R.layout.item_list, items);
			ListView itemsView = (ListView) findViewById(R.id.allItems);
			itemsView.setAdapter(itemAdapter);
			itemsView.setOnItemClickListener(DisplayItemsActivity.this);
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}

	}

}
