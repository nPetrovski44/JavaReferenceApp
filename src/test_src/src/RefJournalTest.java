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
public class RefJournalTest {
    
    public RefJournalTest() {
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
     * Test of getJournal method, of class RefJournal.
     */
    @Test
    public void testGetJournal() {
        System.out.println("getJournal");
        String[] test = {"George", "Peter"};
        RefJournal instance = new RefJournal("Title", test, 2000, "Publisher", "DOI", 10, 10, 2000, "Journal", 5, 2);
        String expResult = "Journal";
        String result = instance.getJournalName();
        assertEquals(expResult, result);
    }
    /*
     * Testing the getAuthors() method of class Ref
     */
    @Test
    public void testGetAuthors()
    {
    	System.out.println("getAuthors");
        String[] test = {"George", "Peter"};
        RefJournal instance = new RefJournal("Title", test, 2000, "Publisher", "DOI", 10, 10, 2000, "Journal", 5, 2);
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
     * Test of getCitation method, of class RefJournal.
     */
    @Test
    public void testGetCitation() {
        System.out.println("getCitation");
        String [] authors = {"Heidi Brynjolfsdottir"};
        RefJournal instance = new RefJournal("Title-F-56",authors, 1999, "ACM","605/1231", 1, 1, 1, "journal-s", 8,9);
        String expResult = "Heidi Brynjolfsdottir (1999), Title-F-56, from: journal-s, vol: 8, iss: 9, ACM, doi: 605/1231";
        String result = instance.getCitation();
        assertEquals(expResult, result);
    }
    
}
