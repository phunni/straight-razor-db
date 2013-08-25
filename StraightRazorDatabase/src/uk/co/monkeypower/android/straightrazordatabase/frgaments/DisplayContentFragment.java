package uk.co.monkeypower.android.straightrazordatabase.frgaments;

import uk.co.monkeypower.android.straightrazordatabase.R;
import uk.co.redfruit.libraries.srpDB.SRPDBClient;
import uk.co.redfruit.libraries.srpDB.data.Manufacturer;
import uk.co.redfruit.libraries.srpDB.exceptions.SRBClientException;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class DisplayContentFragment extends Fragment {
	
	private Activity parentActivity;
	private String content;

	private ProgressDialog progressDialog;
	private Handler gotItemsHandler = new Handler(Looper.getMainLooper());

	private String TAG = "DisplayContentFragment";
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		parentActivity = getActivity();
		if (content == null) {
			String progressMessage = getResources().getString(
					R.string.waitForContent);
			progressDialog = ProgressDialog.show(parentActivity, "", progressMessage,
					true);
			Thread downloadContent = new Thread(new DownloadContent());
			downloadContent.start();
		} else {
			gotItemsHandler.post(new UICallbackHandler());
		}
	}

	private class DownloadContent implements Runnable {

		@Override
		public void run() {
			int pageID = ((Manufacturer) parentActivity.getIntent().getParcelableExtra("item"))
					.getPageID();
			try {
				SRPDBClient contentClient = new SRPDBClient();
				content = contentClient.getContent(pageID);
			} catch (SRBClientException e) {
				Toast toast = Toast.makeText(parentActivity,
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
			parentActivity.setContentView(R.layout.activity_display_content);
			final WebView contentView = (WebView) parentActivity.findViewById(R.id.contentView);
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
