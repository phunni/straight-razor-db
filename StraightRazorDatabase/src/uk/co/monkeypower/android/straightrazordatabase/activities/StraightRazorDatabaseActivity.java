package uk.co.monkeypower.android.straightrazordatabase.activities;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class StraightRazorDatabaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_straight_razor_database);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.straight_razor_database, menu);
		return true;
	}

}
