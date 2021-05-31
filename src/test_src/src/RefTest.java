package src;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Nikolay Petrovski
 */
public class RefTest {
    
    public RefTest() {
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
     * Test of getTitle method, of class Ref.
     */
    @Test
    public void testGetTitle() {
        System.out.println("getTitle");
        String [] test= {"Angel"};
        Ref instance = new Ref("Title", test, 2000, "Publisher", "DOI", 1,1,1);
        String expResult = "Title";
        String result = instance.getTitle();
        assertEquals(expResult, result);
    }

    /**
     * Test of getAuthors method, of class Ref.
     */
    @Test
    public void testGetAuthors() {
    	System.out.println("getAuthors");
        String[] test = {"George", "Peter"};
        Ref instance = new Ref("Title", test, 2000, "Publisher", "DOI", 1,1,1);
        String[] test2 = instance.getAuthors();
        String result = "";
        String expResult = "George Peter";
        for(int i = 0; i < test2.length; i++)
        {
        	result = result + test2[i] + " ";
        }
        result = result.trim();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPubYear method, of class Ref.
     */
    @Test
    public void testGetPubYear() {
        System.out.println("getPubYear");
        String[] test = {"George", "Peter"};
        Ref instance = new Ref("Title", test, 2000, "Publisher", "DOI", 1,1,1);
        int expResult = 2000;
        int result = instance.getYearOfPub();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPublisher method, of class Ref.
     */
    @Test
    public void testGetPublisher() {
        System.out.println("getPublisher");
        String[] test = {"George", "Peter"};
        Ref instance = new Ref("Title", test, 2000, "Publisher", "DOI", 1,1,1);
        String expResult = "Publisher";
        String result = instance.getPublisherName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDoi method, of class Ref.
     */
    @Test
    public void testGetDoi() {
        System.out.println("getDoi");
        String[] test = {"George", "Peter"};
        Ref instance = new Ref("Title", test, 2000, "Publisher", "DOI", 1,1,1);
        String expResult = "DOI";
        String result = instance.getDOI();
        assertEquals(expResult, result);
    }

    /**
     * Test of getDateAdded method, of class Ref.
     */
    @Test
    public void testGetDateAdded() {
        System.out.println("getDateAdded");
        String[] test = {"George", "Peter"};
        Ref instance = new Ref("Title", test, 2000, "Publisher", "DOI", 1,1,1);
        String expResult = "01/02/1901";
        String result = instance.getDate().toString();
        assertEquals(expResult, result);
    }
}
