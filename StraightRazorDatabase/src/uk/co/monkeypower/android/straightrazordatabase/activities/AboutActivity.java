package uk.co.monkeypower.android.straightrazordatabase.activities;

import uk.co.monkeypower.android.straightrazordatabase.R;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class AboutActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.about);
	}
	
	

}
