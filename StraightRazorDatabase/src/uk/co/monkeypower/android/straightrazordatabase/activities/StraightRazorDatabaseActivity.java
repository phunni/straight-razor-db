package uk.co.monkeypower.android.straightrazordatabase.activities;

import uk.co.monkeypower.android.straightrazordatabase.frgaments.DisplayManufacturersFragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

public class StraightRazorDatabaseActivity extends ActionBarActivity {
	
	//private final static String TAG="StraightRazorDatabaseActivity";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentManager manager = getSupportFragmentManager();
		if (manager.findFragmentById(android.R.id.content) == null ) {
			Fragment manufufacturersFragment = new DisplayManufacturersFragment();
			FragmentTransaction fragmentManager = manager.beginTransaction();
			fragmentManager.add(android.R.id.content, manufufacturersFragment).commit();
		}
	}
	
	
}
