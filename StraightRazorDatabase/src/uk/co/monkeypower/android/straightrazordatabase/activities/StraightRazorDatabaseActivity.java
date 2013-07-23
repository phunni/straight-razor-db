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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class StraightRazorDatabaseActivity extends Activity implements OnItemClickListener {
	
	private List<Manufacturer> manufacturers;
	private ProgressDialog progressDialog;
	private Handler gotManufacturersHandler = new Handler(Looper.getMainLooper());
	private final static String TAG="StraightRazorDatabaseActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//if (manufacturers == null) {
			String progressMessage = getResources().getString(R.string.waitForManufacturers);
			progressDialog = ProgressDialog.show(this, "", progressMessage,true);
			Thread downloadManufacturersThread = new Thread(new DownloadManufacturers());
			downloadManufacturersThread.start();
		//} else {
			
		//}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		Manufacturer manufacturer = manufacturers.get(position);
		Intent intent = new Intent(this, DisplayItemsActivity.class);
		intent.putExtra("manufacturer", manufacturer);
		startActivity(intent);
	}
	
	private class DownloadManufacturers implements Runnable {

		@Override
		public void run() {
			try {
				manufacturers = new SRPDBClient().getManufacturers();
			} catch (SRBClientException e) {
				Toast toast = Toast.makeText(
						StraightRazorDatabaseActivity.this,
						R.string.noManufacturersFound, Toast.LENGTH_LONG);
				toast.show();
				Log.e(TAG, "Failed to get manufacturer data.", e);
				e.printStackTrace();
			}
			gotManufacturersHandler.post(new UICallbackHandler());
		}
		
	}
	
	private class UICallbackHandler implements Runnable {

		@Override
		public void run() {
			setContentView(R.layout.activity_straight_razor_database);
			StraightRazorItemAdapter itemAdapter = new StraightRazorItemAdapter(
					getApplicationContext(), R.layout.item_list, manufacturers);
			ListView manufacturersView = (ListView) findViewById(R.id.allManufacturers);
			manufacturersView.setAdapter(itemAdapter);
			manufacturersView.setOnItemClickListener(StraightRazorDatabaseActivity.this);
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
		
	}


}
