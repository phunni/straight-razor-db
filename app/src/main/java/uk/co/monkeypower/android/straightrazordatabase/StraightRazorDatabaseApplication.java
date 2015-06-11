package uk.co.monkeypower.android.straightrazordatabase;

import android.app.Application;

public class StraightRazorDatabaseApplication extends Application {
	
	private static StraightRazorDatabaseApplication app;
	
	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
	}
	
	public static StraightRazorDatabaseApplication getApplication() {
		return app;
	}

}
