package uk.co.monkeypower.android.straightrazordatabase.activities;

import uk.co.monkeypower.android.straightrazordatabase.R;
import uk.co.redfruit.libraries.srpDB.SRPDBClient;
import uk.co.redfruit.libraries.srpDB.data.Manufacturer;
import uk.co.redfruit.libraries.srpDB.exceptions.SRBClientException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class DisplayContentActivity extends Activity {

	private String content;

	private ProgressDialog progressDialog;
	private Handler gotItemsHandler = new Handler(Looper.getMainLooper());

	private String TAG = "DisplayContentActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		content = (String) getLastNonConfigurationInstance();
		if (content == null) {
			String razorTitle = ((Manufacturer) getIntent().getParcelableExtra(
					"item")).getTitle();
			this.getActionBar().setTitle(razorTitle);
			String progressMessage = getResources().getString(
					R.string.waitForContent);
			progressDialog = ProgressDialog.show(this, "", progressMessage,
					true);
			Thread downloadContent = new Thread(new DownloadContent());
			downloadContent.start();
		} else {
			gotItemsHandler.post(new UICallbackHandler());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_content, menu);
		return true;
	}

	@Override
	public Object onRetainNonConfigurationInstance() {
		return content;
	}

	private class DownloadContent implements Runnable {

		@Override
		public void run() {
			int pageID = ((Manufacturer) getIntent().getParcelableExtra("item"))
					.getPageID();
			try {
				int displayWidth = getWindowManager().getDefaultDisplay()
						.getWidth();
				SRPDBClient contentClient = new SRPDBClient();
				contentClient.setDisplayWidth(displayWidth);
				content = contentClient.getContent(pageID);
			} catch (SRBClientException e) {
				Toast toast = Toast.makeText(DisplayContentActivity.this,
						R.string.noManufacturersFound, Toast.LENGTH_LONG);
				toast.show();
				Log.e(TAG, "Failed to get content.", e);
			}
			gotItemsHandler.post(new UICallbackHandler());
		}

	}

	private class UICallbackHandler implements Runnable {

		@Override
		public void run() {
			setContentView(R.layout.activity_display_content);
			final WebView contentView = (WebView) findViewById(R.id.contentView);
			WebSettings settings = contentView.getSettings();
			settings.setSupportZoom(true);
			settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
			contentView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					return true;
				}

			});
			contentView.loadDataWithBaseURL("http://straightrazorplace.com/",
					content, "text/html", "UTF-8", null);
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}

	}

}
