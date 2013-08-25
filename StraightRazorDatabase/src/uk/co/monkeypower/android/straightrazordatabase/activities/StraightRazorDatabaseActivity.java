package uk.co.monkeypower.android.straightrazordatabase.activities;

import uk.co.monkeypower.android.straightrazordatabase.frgaments.DisplayManufacturersFragment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;

public class StraightRazorDatabaseActivity extends Activity {
	
	//private final static String TAG="StraightRazorDatabaseActivity";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentManager manager = getFragmentManager();
		if (manager.findFragmentById(android.R.id.content) == null ) {
			DisplayManufacturersFragment manufufacturersFragment = new DisplayManufacturersFragment();
			manager.beginTransaction().add(android.R.id.content, manufufacturersFragment).commit();
		}
	}
	
	
}
