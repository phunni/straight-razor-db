package uk.co.monkeypower.android.straightrazordatabase.activities;

import uk.co.monkeypower.android.straightrazordatabase.frgaments.DisplayItemsFragment;
import uk.co.redfruit.libraries.srpDB.data.Manufacturer;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Activity;

public class DisplayItemsActivity extends Activity {

	//private final static String TAG = "DisplayItemsActivity";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		String manufacturerTitle = ((Manufacturer) getIntent().getExtras().getParcelable("manufacturer")).getTitle();
		getActionBar().setTitle(manufacturerTitle);
		FragmentManager manager = getFragmentManager();
		if (manager.findFragmentById(android.R.id.content) == null ) {
			DisplayItemsFragment manufufacturersFragment = new DisplayItemsFragment();
			manager.beginTransaction().add(android.R.id.content, manufufacturersFragment).commit();
		}
	}

}
