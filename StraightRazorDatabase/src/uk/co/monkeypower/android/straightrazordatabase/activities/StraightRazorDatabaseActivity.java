package uk.co.monkeypower.android.straightrazordatabase.activities;

import java.util.List;

import uk.co.monkeypower.android.straightrazordatabase.R;
import uk.co.redfruit.libraries.srpDB.SRPDBClient;
import uk.co.redfruit.libraries.srpDB.data.Manufacturer;
import uk.co.redfruit.libraries.srpDB.exceptions.SRBClientException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class StraightRazorDatabaseActivity extends Activity {
	
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
	public boolean onCreateOptionsMenu(Menu menu) {
		//Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.straight_razor_database, menu);
		return true;
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
			TextView text = (TextView) findViewById(R.id.label);
			text.setText(manufacturers.get(0).getTitle());
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
		
	}

}
