package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

/**
 *
 * @author Nikolay Petrovski
 */
public class RefSystemGUI extends JFrame implements ActionListener {
    

	private static final long serialVersionUID = 1L;
	private JPanel common = new JPanel();
	private JPanel commonLine1 = new JPanel();
	private JPanel commonLine2 = new JPanel();
	private JPanel commonLine3 = new JPanel();
	private JPanel commonLine4 = new JPanel();
	private JPanel commonLine5 = new JPanel();
	private JPanel commonLine6 = new JPanel();
	private JPanel commonLine7 = new JPanel();
	private JPanel commonLine8 = new JPanel();

	//Introducing all the required elements of the GUI.
	private JTextField title = new JTextField(15);
    private JTextField authors = new JTextField(20);
    private JTextField yearOfPublication = new JTextField(6);
    private JTextField publisher = new JTextField(15);
    private JTextField digitalObjectIdentifier = new JTextField(13);
    private JTextField day = new JTextField(4);
    private JTextField month = new JTextField(4);
    private JTextField year = new JTextField(6);
    private JTextField lookUpJournal = new JTextField(10);
    private JTextField lookUpVenue = new JTextField(10);
    private JTextField lookUpPublisher = new JTextField(10);
    
    private JLabel labTitle = new JLabel(" Title:");
    private JLabel labAuthors = new JLabel(" Authors:");
    private JLabel labYearP = new JLabel(" Year of publication:");
    private JLabel labPublisher = new JLabel(" Name of publisher:");
    private JLabel labDOI = new JLabel(" DOI:");
    private JLabel labd = new JLabel(" Day:");
    private JLabel labm = new JLabel(" Month:");
    private JLabel laby = new JLabel(" Year:");
	private JLabel labDrop = new JLabel(" Publication type:");
	private JLabel or = new JLabel("or");
	private JLabel exportTo = new JLabel("Export to:");
    
    private JTextArea outputArea = new JTextArea(20, 50);
    private JScrollPane scroll;
    private String[] refOptions = {"Journal Papers", "Conference Papers", "Book Chapters"};
    private String[] exportOptions = {"TXT", "XML"};
    private JComboBox<String> dropDown = new JComboBox<String>(refOptions);
    private JComboBox<String> dropDown2 = new JComboBox<String>(exportOptions);
    
    private JButton addButton = new JButton("Add");
    private JButton journalButton = new JButton("Look Up By Journal");
    private JButton venueButton = new JButton("Look Up By Venue");
    private JButton publisherButton = new JButton("Look Up By Publisher");
    private JButton importButton = new JButton("Import from a File");
    private JButton exportAll = new JButton("Export All");
    private JButton exportSearchResults = new JButton("Export Search Results");
    
    //Adding the situational elements.
    private JTextField journalName = new JTextField(20);
    private JTextField journalVolume = new JTextField(5);
    private JTextField journalIssue = new JTextField(5);
    private JTextField conferenceName = new JTextField(15);
    private JTextField conferenceLocation = new JTextField(10);
    private JTextField bookTitle = new JTextField(15);
    private JTextField bookEditor = new JTextField(20);
    
    private JLabel labJournalName = new JLabel(" Journal name:");
    private JLabel labJournalVolume = new JLabel(" Volume:");
    private JLabel labJournalIssue = new JLabel(" Issue:");
    private JLabel labConferenceName = new JLabel(" Conference Name:");
    private JLabel labConferenceLocation = new JLabel(" Conference Location:");
    private JLabel labBookTitle = new JLabel(" Book Title:");
    private JLabel labBookEditor = new JLabel(" Book Editor:");
    
    private RefCollection bibliography = new RefCollection();
    
    private String lastLookUp = "";
    private String lastLookUpText = "";
    
    private JFileChooser fileChooser = new JFileChooser();
    
    public static void main(String[] args) {
        RefSystemGUI applic = new RefSystemGUI();
        applic.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public RefSystemGUI() {
        super("Bibliography");
        new FlowLayout(10);
		//We set the layout of the GUI to flow for the time-being.
        setLayout(new FlowLayout(FlowLayout.CENTER));
        common.setLayout(new BoxLayout(common, BoxLayout.PAGE_AXIS));
        common.setAlignmentX(RIGHT_ALIGNMENT);
		commonLine1.setLayout(new FlowLayout(FlowLayout.CENTER));
		commonLine2.setLayout(new FlowLayout(FlowLayout.CENTER));
		commonLine3.setLayout(new FlowLayout(FlowLayout.CENTER));
		commonLine4.setLayout(new FlowLayout(FlowLayout.CENTER));
		commonLine5.setLayout(new FlowLayout(FlowLayout.CENTER));
		commonLine6.setLayout(new FlowLayout(FlowLayout.CENTER));
		commonLine7.setLayout(new FlowLayout(FlowLayout.CENTER));
		commonLine8.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        commonLine1.add(labTitle);
        commonLine1.add(title);
        
        commonLine1.add(labAuthors);
        commonLine1.add(authors);
        
        commonLine2.add(labYearP);
        commonLine2.add(yearOfPublication);
        
        commonLine2.add(labPublisher);
        commonLine2.add(publisher);

        commonLine3.add(labDOI);
        commonLine3.add(digitalObjectIdentifier);
        
        commonLine3.add(labd);
        commonLine3.add(day);
        
        commonLine3.add(labm);
        commonLine3.add(month);
        
        commonLine3.add(laby);
        commonLine3.add(year);
        
        title.setEditable(true);
        authors.setEditable(true);
        yearOfPublication.setEditable(true);
        publisher.setEditable(true);
        digitalObjectIdentifier.setEditable(true);
        day.setEditable(true);
        month.setEditable(true);
        year.setEditable(true);
        
        common.add(commonLine1);
        common.add(commonLine2);
        common.add(commonLine3);
        
        commonLine4.add(labDrop);
        commonLine4.add(dropDown);
        dropDown.addActionListener(this);
        
        common.add(commonLine4);
       
        commonLine5.add(labJournalName);
        commonLine5.add(journalName);
        journalName.setEditable(true);
        commonLine5.add(labJournalVolume);
        commonLine5.add(journalVolume);
        journalVolume.setEditable(true);
        commonLine5.add(labJournalIssue);
        commonLine5.add(journalIssue);
        journalIssue.setEditable(true);
        
        commonLine5.add(labConferenceName);
        commonLine5.add(conferenceName);
        conferenceName.setEditable(true);
        labConferenceName.setVisible(false);
        conferenceName.setVisible(false);
        commonLine5.add(labConferenceLocation);
        commonLine5.add(conferenceLocation);
        conferenceLocation.setEditable(true);
        labConferenceLocation.setVisible(false);
        conferenceLocation.setVisible(false);
        
        commonLine5.add(labBookTitle);
        commonLine5.add(bookTitle);
        bookTitle.setEditable(true);
        labBookTitle.setVisible(false);
        bookTitle.setVisible(false);
        commonLine5.add(labBookEditor);
        commonLine5.add(bookEditor);
        bookEditor.setEditable(true);
        labBookEditor.setVisible(false);
        bookEditor.setVisible(false);
        
        commonLine6.add(addButton);
        addButton.addActionListener(this);
        commonLine6.add(or);
        commonLine6.add(importButton);
        importButton.addActionListener(this);
        
        common.add(commonLine5);
        common.add(commonLine6);
        
        scroll = new JScrollPane(outputArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        outputArea.setEditable(false);
        
        commonLine7.add(journalButton);
        commonLine7.add(lookUpJournal);
        lookUpJournal.setEditable(true);
        journalButton.addActionListener(this);
        
        commonLine7.add(venueButton);
        commonLine7.add(lookUpVenue);
        lookUpVenue.setEditable(true);
        venueButton.addActionListener(this);
        
        commonLine7.add(publisherButton);
        commonLine7.add(lookUpPublisher);
        lookUpPublisher.setEditable(true);
        publisherButton.addActionListener(this);
        
        common.add(commonLine7);
        common.add(scroll);
        
        commonLine8.add(exportTo);
        commonLine8.add(dropDown2);
        commonLine8.add(exportAll);
        
        exportAll.addActionListener(this);
        commonLine8.add(exportSearchResults);
        exportSearchResults.addActionListener(this);
        
        common.add(commonLine8);
        add(common);

        setSize(810,650);
        setVisible(true);
        blankDisplay();
    }
    
    public void actionPerformed(ActionEvent event) {
    	
    	String message = "";
    	String resultField = "";
    	if(event.getSource() == dropDown)
        {
    		
    		//We check the dropDown box for which Publication Type is selected and
    		//we set the visibility of the needed elements for that specific type to true.
            if(dropDown.getSelectedItem().toString().equals("Journal Papers"))
            {
            	 labJournalName.setVisible(true);
                 journalName.setVisible(true);
                 
                 labJournalVolume.setVisible(true);
                 journalVolume.setVisible(true);
                 
                 labJournalIssue.setVisible(true);
                 journalIssue.setVisible(true);
                 
                 labConferenceName.setVisible(false);
                 conferenceName.setVisible(false);
                 
                 labConferenceLocation.setVisible(false);
                 conferenceLocation.setVisible(false);
                 
                 labBookTitle.setVisible(false);
                 bookTitle.setVisible(false);
                 
                 labBookEditor.setVisible(false);
                 bookEditor.setVisible(false);
                 
                 
                 SwingUtilities.updateComponentTreeUI(journalName);
                 SwingUtilities.updateComponentTreeUI(journalVolume);
                 SwingUtilities.updateComponentTreeUI(journalIssue);
                 SwingUtilities.updateComponentTreeUI(conferenceName);
                 SwingUtilities.updateComponentTreeUI(conferenceLocation);
                 SwingUtilities.updateComponentTreeUI(bookTitle);
                 SwingUtilities.updateComponentTreeUI(bookEditor);
            }
            if(dropDown.getSelectedItem().toString().equals("Conference Papers"))
            {
            	labJournalName.setVisible(false);
                journalName.setVisible(false);
                
                labJournalVolume.setVisible(false);
                journalVolume.setVisible(false);
                
                labJournalIssue.setVisible(false);
                journalIssue.setVisible(false);
                
                labConferenceName.setVisible(true);
                conferenceName.setVisible(true);
                
                labConferenceLocation.setVisible(true);
                conferenceLocation.setVisible(true);
                
                labBookTitle.setVisible(false);
                bookTitle.setVisible(false);
                
                labBookEditor.setVisible(false);
                bookEditor.setVisible(false);
                
                
                SwingUtilities.updateComponentTreeUI(journalName);
                SwingUtilities.updateComponentTreeUI(journalVolume);
                SwingUtilities.updateComponentTreeUI(journalIssue);
                SwingUtilities.updateComponentTreeUI(conferenceName);
                SwingUtilities.updateComponentTreeUI(conferenceLocation);
                SwingUtilities.updateComponentTreeUI(bookTitle);
                SwingUtilities.updateComponentTreeUI(bookEditor);
            }
            if(dropDown.getSelectedItem().toString().equals("Book Chapters"))
            {	
            	labJournalName.setVisible(false);
                journalName.setVisible(false);
                
                labJournalVolume.setVisible(false);
                journalVolume.setVisible(false);
                
                labJournalIssue.setVisible(false);
                journalIssue.setVisible(false);
                
                labConferenceName.setVisible(false);
                conferenceName.setVisible(false);
                
                labConferenceLocation.setVisible(false);
                conferenceLocation.setVisible(false);
                
                labBookTitle.setVisible(true);
                bookTitle.setVisible(true);
                
                labBookEditor.setVisible(true);
                bookEditor.setVisible(true);
                
                
                SwingUtilities.updateComponentTreeUI(journalName);
                SwingUtilities.updateComponentTreeUI(journalVolume);
                SwingUtilities.updateComponentTreeUI(journalIssue);
                SwingUtilities.updateComponentTreeUI(conferenceName);
                SwingUtilities.updateComponentTreeUI(conferenceLocation);
                SwingUtilities.updateComponentTreeUI(bookTitle);
                SwingUtilities.updateComponentTreeUI(bookEditor);
            }
            
        }
    	
    	if(event.getSource() == addButton)
    	{
    		message = addRef();
    	}
    	
    	if(event.getSource() == journalButton)
    	{
    		resultField = lookUpByJournal();
    	}
    	if(event.getSource() == venueButton)
    	{
    		resultField = lookUpByVenue();
    	}
    	
    	if(event.getSource() == publisherButton)
    	{
    		resultField = lookUpByPublisher();
    	}
    	if(event.getSource() == importButton)
    	{
    		File importedFile = importFile();
    		if(importedFile == null)message = "Choose a file.";
    		else message = bibliography.importMany(importedFile);
    	}
    	if(event.getSource() == exportAll)
    	{
    		try {
    				if(bibliography.getNumberOfRefs() == 0) message = "The bibliography is empty.";
    				else
    				{
	    				if(dropDown2.getSelectedItem().toString().equals("TXT"))message = bibliography.exportAll(dropDown2.getSelectedItem().toString(), null);
	    				else 
	    					{
	    						File schemeFile = importFile();
	    						if(schemeFile == null)message = "Choose a scheme file.";
	    						else message = bibliography.exportAll(dropDown2.getSelectedItem().toString(), schemeFile);
	    					}
    				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
    	}
    	if(event.getSource() == exportSearchResults)
    	{
    		try {
    			if(outputArea.getText().equals("")) message = "Perform a search first.";
    			else 
    				{
	    				if(dropDown2.getSelectedItem().toString().equals("TXT"))message = bibliography.exportSearchResult(lastLookUpText, dropDown2.getSelectedItem().toString(), lastLookUp, null);
	    				else
	    					{
		    					File schemeFile = importFile();
		    	    			if(schemeFile == null)message = "Choose a scheme file.";
		    	    			else message = bibliography.exportSearchResult(lastLookUpText, dropDown2.getSelectedItem().toString(), lastLookUp, schemeFile);
	    					}
    				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
    	}
    	if(resultField.equals(""))JOptionPane.showMessageDialog(common,message);
    	else outputArea.setText(resultField);
        blankDisplay();
    }
    //Adding references to the RefCollection
	@SuppressWarnings("deprecation")
	public String addRef()
    {
    	Date date = new java.util.Date();
    	String message = "Record Added";
    	try
    	{
    		String t = title.getText();
    		//Assuming that author names are separated by a comma plus an interval
    		String a[] = authors.getText().split(", ");
    		int yp = Integer.parseInt(yearOfPublication.getText());
     		String pName = publisher.getText();
     		String DOI = digitalObjectIdentifier.getText();
     		
     		int d, m, y;
     		//The person using the program could emit the d,m,y but if he decides to enter
     		//the data manually the code checks if the input is valid.
     		try
     		{
	     		m = Integer.parseInt(month.getText());
		        if(m < 1 || m > 12) return "Enter a valid month";
		        d = Integer.parseInt(day.getText());
		        y = Integer.parseInt(year.getText());
		        if(d < 0) return "Enter a valid day";
     		}catch(Exception e)
     		{
     			d = date.getDate();
     			m = date.getMonth();
     			y = date.getYear();
     		} 
	        	if((m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) && d > 31) return "Enter a valid day";
	        	if((m == 4 || m == 6 || m == 9 || m == 11) && d > 30 ) return "Enter a valid day";
	        	if(m == 2 && y % 4 != 0 && d > 28) return "Enter a valid day";
	        	if(m == 2 && y % 4 == 0 && d > 29) return "Enter a valid day";
	        	
	        	//Creates a references depending on the type the person has chosen.
	        	if(dropDown.getSelectedItem().toString().equals("Journal Papers"))
	        	{	
	        		String jName = journalName.getText();
	        		int jVolume = Integer.parseInt(journalVolume.getText());
	        		int jIssue = Integer.parseInt(journalIssue.getText());
	        		if(jVolume < 0 || jIssue < 0) return "Enter a valid repetition";
	        		RefJournal refj = new RefJournal(t, a, yp, pName, DOI, d, m, y, jName, jVolume, jIssue);
	        		if(bibliography.existAllready(t, DOI, pName, yp) == false)bibliography.addRef(refj);
	        		else return "This reference has already been added.";
	        	}
	        	
	        	if(dropDown.getSelectedItem().toString().equals("Conference Papers"))
	        	{
	        		String venue = conferenceName.getText();
		        	String location = conferenceLocation.getText();
	        		RefConference refc = new RefConference(t, a, yp, pName, DOI, d, m, y, venue, location);
	        		if(bibliography.existAllready(t, DOI, pName, yp) == false)bibliography.addRef(refc);
	        		else return "This reference has already been added.";
	        	}
	        	
	        	if(dropDown.getSelectedItem().toString().equals("Book Chapters"))
	        	{	
	        		String bTitle = bookTitle.getText();
		        	String bEditor = bookEditor.getText();
	        		RefBookChapter refb = new RefBookChapter(t, a, yp, pName, DOI, d, m, y, bTitle, bEditor);
	        		if(bibliography.existAllready(t, DOI, pName, yp) == false)bibliography.addRef(refb);
	        		else return "This reference has already been added.";
	        	}
     		
    	}catch(Exception e){return "Invalid input";}
    	return message;
    }
	//Creating a method to return the references by a given journal
	public String lookUpByJournal()
	{
		String message = "";
		String journal = lookUpJournal.getText();
		if(journal.equals("")) return "Enter a valid search option.";
		message = bibliography.lookUpByJournalName(journal);
		lastLookUpText = lookUpJournal.getText();
		lastLookUp = "JournalName";
		if(message.equals(""))return  "No references were found from that from that journal.";
		return message;
	}
	
	//Creating a method to return the references by a given venue
		public String lookUpByVenue()
		{
			String message = "";
			String venue = lookUpVenue.getText();
			if(venue.equals("")) return "Enter a valid search option.";
			message = bibliography.lookUpByConferenceVenue(venue);
			lastLookUpText = lookUpVenue.getText();
			lastLookUp = "ConferenceVenue";
			if(message.equals(""))return  "No references were found from that venue.";
			return message;
		}
	//Creating a method to return the references by a given Publisher
	public String lookUpByPublisher()
	{
		String message = "";
		String publisher = lookUpPublisher.getText();
		if(publisher.equals("")) return "Enter a valid search option.";
		message = bibliography.lookUpByPublisher(publisher);
		lastLookUpText = lookUpPublisher.getText();
		lastLookUp = "BookTitle";
		if(message.equals(""))return  "No references were found from that publisher.";
		return message;
	}
	
	public File importFile()
	{
		if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			File returningFile = fileChooser.getSelectedFile();
			return returningFile;
		}
		else return null;
	}
	
	//A method to reset all the textFields.
	public void blankDisplay()
	{
		title.setText("");
		authors.setText("");
		yearOfPublication.setText("");
		publisher.setText("");
		digitalObjectIdentifier.setText("");
		day.setText("");
		month.setText("");
		year.setText("");
		journalName.setText("");
		journalVolume.setText("");
		journalIssue.setText("");
		conferenceName.setText("");
		conferenceLocation.setText("");
		bookTitle.setText("");
		bookEditor.setText("");
		lookUpPublisher.setText("");
		lookUpJournal.setText("");
		lookUpVenue.setText("");
	}
}