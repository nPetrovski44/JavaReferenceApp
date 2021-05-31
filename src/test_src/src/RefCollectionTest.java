package src;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

/**
 *	@author Nikolay Petrovski
 */
public class RefCollectionTest {
    
    public RefCollectionTest() {
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
     * Test of addCite method, of class RefCollection.
     */
    @Test
    public void testAddCite() {
        System.out.println("addCite");
        Ref ref = null;
        RefCollection instance = new RefCollection();
        instance.addRef(ref);
    }

    
    /**
     * Test of lookUpByJournal method, of class RefCollection.
     */
    @Test
    public void testLookUpByJournal() {
        System.out.println("lookUpByJournal");
        String journal = "Journal";
        String[] test = {"George", "Peter"};
        RefJournal instance = new RefJournal("Title", test, 2000, "Publisher", "DOI", 10, 10, 2000, "Journal", 5, 2);
        RefCollection instance2 = new RefCollection();
        instance2.addRef(instance);
        String expResult = "George,Peter (2000), Title, from: Journal, vol: 5, iss: 2, Publisher, doi: DOI\n";
        String result = instance2.lookUpByJournalName(journal);
        assertEquals(expResult, result);
    }

    /**
     * Test of loopUpByVenue method, of class RefCollection.
     */
    @Test
    public void testLoopUpByVenue() {
    	String[] test = {"Angel"};
    	System.out.println("loopUpByVenue");
        RefConference instance = new RefConference("Title", test, 2000, "Publisher", "DOI", 10, 10, 2000, "E3", "Paris");
        String venue = "E3";
        RefCollection instance2 = new RefCollection();
        instance2.addRef(instance);
        String expResult = "Angel (2000), Title, at: E3, in: Paris, Publisher, doi: DOI\n";
        String result = instance2.lookUpByConferenceVenue(venue);
        assertEquals(expResult, result);
    }

    /**
     * Test of lookUpByPublisher method, of class RefCollection.
     */
    @Test
    public void testLookUpByPublisher() {
        System.out.println("lookUpByPublisher");
        String[] test = {"Angel"};
        RefConference instance = new RefConference("Title", test, 2000, "Publisher", "DOI", 10, 10, 2000, "E3", "Paris");
        String publisher = "Publisher";
        RefCollection instance2 = new RefCollection();
        instance2.addRef(instance);
        String expResult = "Angel (2000), Title, at: E3, in: Paris, Publisher, doi: DOI\n";
        String result = instance2.lookUpByPublisher(publisher);
        assertEquals(expResult, result);
    }

    /**
     * Test of getNumberOfRefs method, of class RefCollection.
     */
    @Test
    public void testGetNumberOfRefs() {
        System.out.println("getNumberOfRefs");
        RefCollection instance2 = new RefCollection();
        int expResult = 0;
        int result = instance2.getNumberOfRefs();
        assertEquals(expResult, result);
    }
    
    
    /**
     * Test of exportAll method, of class RefCollection.
     * @throws FileNotFoundException 
     */
    @Test
    public void testExportAll() throws FileNotFoundException {
        System.out.println("exportAll");
        RefCollection instance = new RefCollection();
        String expResult = "Exported successfully.";
        String result = instance.exportAll("TXT",null);
        assertEquals(expResult, result);
    }

    
    /**
     * Test of importMany method, of class RefCollection.
     */
    @Test
    public void testImportMany() {
        System.out.println("importMany");
        File filePath = null;
        RefCollection instance = new RefCollection();
        String result = "Imported succesfully";
        String expResult =  instance.importMany(filePath);
        assertEquals(expResult, result);
    }
}
