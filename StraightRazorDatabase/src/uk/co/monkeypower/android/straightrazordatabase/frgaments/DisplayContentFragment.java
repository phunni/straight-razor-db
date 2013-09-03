package uk.co.monkeypower.android.straightrazordatabase.frgaments;

import uk.co.monkeypower.android.straightrazordatabase.R;
import uk.co.redfruit.libraries.srpDB.SRPDBClient;
import uk.co.redfruit.libraries.srpDB.data.Manufacturer;
import uk.co.redfruit.libraries.srpDB.exceptions.SRBClientException;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class DisplayContentFragment extends Fragment {
	
	private FragmentActivity parentActivity;
	private String content;

	private ProgressDialog progressDialog;
	private Handler gotItemsHandler = new Handler(Looper.getMainLooper());

	private static final String TAG = "DisplayContentFragment";
	private static final String BASE_URL = "http://straightrazorplace.com/";
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentActivity = getActivity();
		if (savedInstanceState != null) {
			content = savedInstanceState.getString("content");
		}
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
	
	

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("content", content);
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
			contentView.loadDataWithBaseURL(BASE_URL,
					content, "text/html", "UTF-8", null);
			Button showInBrowserButton = (Button) parentActivity.findViewById(R.id.viewInBrowser);
			showInBrowserButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String title = ((Manufacturer)parentActivity.getIntent().getParcelableExtra("item")).getTitle();
					String sanitizedTitle = sanitize(title);
					Uri uri = Uri.parse(BASE_URL + "srpwiki/index.php/" + sanitizedTitle);
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
					parentActivity.startActivity(browserIntent);
				}
			});
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
		
		private String sanitize(String title) {
			String sanitized = title.replace(" ", "_");
			return sanitized;
		}

	}

}
