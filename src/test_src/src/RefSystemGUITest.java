
package src;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *	@author Nikolay Petrovski
 */
public class RefSystemGUITest {
    
    public RefSystemGUITest() {
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
     * Test of main method, of class RefSystemGUI.
     * Just tests if the GUI initiates without explicit fail
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        RefSystemGUI.main(args);
    }
}
