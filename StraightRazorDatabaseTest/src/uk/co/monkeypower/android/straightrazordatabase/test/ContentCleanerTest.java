package uk.co.monkeypower.android.straightrazordatabase.test;

import uk.co.redfruit.libraries.srpDB.utils.ContentCleaner;
import android.test.AndroidTestCase;
import android.util.Log;

public class ContentCleanerTest extends AndroidTestCase {
	
	private static final String TAG = "ContentCleanerTest";
	
	public static final String TEST_STRING = "<html></head/><body><a href='google.com'>test link</a> <img width='549' " +
			"height='786' src='test.jpg'></img><table style='solid;float:right' /></body></html>";
	
	public void testCleanContent() {
		String cleaned = ContentCleaner.cleanContent(TEST_STRING);
		assertNotNull(cleaned);
		Log.d(TAG, "Cleaned: " + cleaned);
		assertTrue(cleaned.indexOf("width=") == -1);
		assertTrue(cleaned.indexOf("height=") == -1);
		assertTrue(cleaned.indexOf("</a>") == -1);
		assertTrue(cleaned.indexOf("solid;float:right") == -1);
	}

}
