package uk.co.monkeypower.android.straightrazordatabase.test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import android.test.AndroidTestCase;

import uk.co.redfruit.libraries.srpDB.SRPDBClient;
import uk.co.redfruit.libraries.srpDB.data.Manufacturer;
import uk.co.redfruit.libraries.srpDB.exceptions.SRBClientException;

/**
 * User: paul
 * Date: 21/06/13
 * Time: 21:41
 */

public class SRPDBClientTest extends AndroidTestCase {

    
    public void testGetManufacturers() {
        SRPDBClient client = new SRPDBClient();
        List<Manufacturer> manufacturers = null;
		try {
			manufacturers = client.getManufacturers();
		} catch (SRBClientException e) {
			System.out.println("Cocked up: " + e);
			e.printStackTrace();
		}
        assertNotNull(manufacturers);
        assertTrue(manufacturers.size() > 0);
        System.out.println("Manfacturers returned: " + manufacturers.size());
        //assertTrue(manufacturers.get(0).getPageID() == 3572);
        System.out.println("First Title: " + manufacturers.get(0).getTitle());
    }
    
    public void testGetSpecificManufacturers() {
    	SRPDBClient client = new SRPDBClient();
    	List<Manufacturer> manufacturers = null;
		try {
			manufacturers = client.getManufacturers("Fox Cutlery Co.");
		} catch (SRBClientException e) {
			System.out.println("Cocked up: " + e);
			e.printStackTrace();
		}
    	assertNotNull(manufacturers);
        assertTrue(manufacturers.size() > 0);
        for (Manufacturer currentManufacturer : manufacturers) {
        	System.out.println("Result: " + currentManufacturer.getTitle());
        }
    }
    
    public void testGetContent() {
    	SRPDBClient client = new SRPDBClient();
    	String content = null;
		try {
			content = client.getContent(1583);
		} catch (SRBClientException e) {
			System.out.println("Cocked up: " + e);
			e.printStackTrace();
		}
    	assertNotNull(content);
    	assertTrue(!"".equals(content));
    	assertTrue(content.contains("Eskilstuna, Sweden"));
    }
}
