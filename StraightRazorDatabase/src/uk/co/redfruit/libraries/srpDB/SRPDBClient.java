package uk.co.redfruit.libraries.srpDB;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.redfruit.libraries.srpDB.data.Manufacturer;
import uk.co.redfruit.libraries.srpDB.exceptions.SRBClientException;
import uk.co.redfruit.libraries.srpDB.utils.ContentCleaner;

import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.android.AndroidHttpClient;

/**
 * User: paul Date: 21/06/13 Time: 21:41
 */
public class SRPDBClient {

	private final static String MANUFACTURER_URL = "http://straightrazorplace.com/srpwiki/api.php?action=query&list=categorymembers&cmtitle=Category:Manufacturers&cmlimit=500&format=json";
	private final static String CONTENT_URL = "http://straightrazorplace.com/srpwiki/index.php?curid=****&action=render";
	//the user agent is required as a workaround since the server returns a 403 to whatever the default is.
	private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.2 Safari/537.36";

	public List<Manufacturer> getManufacturers() throws SRBClientException {
		return getManufacturersFromJSON(MANUFACTURER_URL);
	}


	public List<Manufacturer> getManufacturers(String title) throws SRBClientException {
		String sanitzedTitle = sanitize(title);
		String URL = null;
		if (sanitzedTitle != null) {
			URL = MANUFACTURER_URL.replace("Category:Manufacturers", sanitzedTitle);
		} else {
			return null;
		}
		return getManufacturersFromJSON(URL);
	}

	private String sanitize(String title) {
		String sanitized = title.replace(" ", "_");
		try {
			sanitized = URLEncoder.encode(sanitized, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// cannot proceed
			return null;
		}
		return "Category:" + sanitized;
	}

	private List<Manufacturer> getManufacturersFromJSON(String targetURL) throws SRBClientException {
		if (targetURL == null) {
			return null;
		}
		AndroidHttpClient httpClient = new AndroidHttpClient();
		httpClient.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = httpClient.get(targetURL, null);
		String content = response.getBodyAsString();
		List<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
		try {
			//String jsonContent = stringBuilder.toString();
			JSONObject json = new JSONObject(content);
			JSONObject query = json.getJSONObject("query");
			JSONArray jsonManufacturers = query.getJSONArray("categorymembers");
			for (int counter = 0; counter < jsonManufacturers.length(); counter++) {
				JSONObject currentJSONObject = (JSONObject) jsonManufacturers.get(counter);
				Manufacturer currentManufacturer = new Manufacturer();
				currentManufacturer.setPageID(currentJSONObject.getInt("pageid"));
				currentManufacturer.setTitle(currentJSONObject.getString("title"));
				manufacturers.add(currentManufacturer);
			}
		} catch (JSONException e) {
			throw new SRBClientException("Failed to get or parse JSON", e);
		}

		return manufacturers;
	}

	public String getContent(int pageID) throws SRBClientException {
		String contentURL = CONTENT_URL.replace("****", "" + pageID);
		AndroidHttpClient httpClient = new AndroidHttpClient();
		httpClient.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = httpClient.get(contentURL, null);
		return ContentCleaner.cleanContent(response.getBodyAsString());
	}
}
