package uk.co.monkeypower.android.straightrazordatabase.activities;

import uk.co.monkeypower.android.straightrazordatabase.frgaments.DisplayItemsFragment;
import uk.co.redfruit.libraries.srpDB.data.Manufacturer;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

public class DisplayItemsActivity extends ActionBarActivity {

	//private final static String TAG = "DisplayItemsActivity";
	private String manufacturerTitle;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		if (savedInstanceState != null) {
			manufacturerTitle = savedInstanceState.getString("manufacturerTitle");
		} else {
			manufacturerTitle = ((Manufacturer) getIntent().getExtras().getParcelable("manufacturer")).getTitle();
		}
		getActionBar().setTitle(manufacturerTitle);
		FragmentManager manager = getSupportFragmentManager();
		if (manager.findFragmentById(android.R.id.content) == null ) {
			Fragment manufufacturersFragment = new DisplayItemsFragment();
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.add(android.R.id.content, manufufacturersFragment).commit();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("manufacturerTitle", manufacturerTitle);
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
