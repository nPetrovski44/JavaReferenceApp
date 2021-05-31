package src;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *	@author Nikolay Petrovski
 */
public class RefConferenceTest {
    
    public RefConferenceTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getVenue method, of class RefConference.
     */
    @Test
    public void testGetConferenceName() {
    	
    	String[] test = {"Someone"};
        System.out.println("getVenue");
        RefConference instance = new RefConference("Title", test, 2000, "Publisher", "DOI", 10, 10, 2000, "E3", "Paris");
        String expResult = "E3";
        String result = instance.getConferenceName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCitation method, of class RefConference.
     */
    @Test
    public void testGetCitation() {
    	String[] test = {"Someone"};
        System.out.println("getCitation");
        RefConference instance = new RefConference("Title", test, 2000, "Publisher", "DOI", 10, 10, 2000, "E3", "Paris");
        String expResult = "Someone (2000), Title, at: E3, in: Paris, Publisher, doi: DOI";
        String result = instance.getCitation();
        assertEquals(expResult, result);
    }
    
}
