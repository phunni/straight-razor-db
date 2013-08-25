package uk.co.monkeypower.android.straightrazordatabase.activities;

import uk.co.monkeypower.android.straightrazordatabase.frgaments.DisplayContentFragment;
import uk.co.redfruit.libraries.srpDB.data.Manufacturer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class DisplayContentActivity extends Activity {

	//private String TAG = "DisplayContentActivity";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		String razorTitle = ((Manufacturer) getIntent().getParcelableExtra("item")).getTitle();
		getActionBar().setTitle(razorTitle);
		FragmentManager manager = getFragmentManager();
		if (manager.findFragmentById(android.R.id.content) == null ) {
			Fragment contentFragment = new DisplayContentFragment();
			manager.beginTransaction().add(android.R.id.content, contentFragment).commit();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {		
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;	
		}
		return super.onOptionsItemSelected(item);
	}

}
