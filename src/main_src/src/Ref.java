package src;

import java.util.Comparator;
import java.util.Date;
import java.text.SimpleDateFormat;  

public class Ref //implementsComparable<Ref>
{
	private String title;
	private String[] authors;
	private int yearOfPub;
	private String pName;
	private String DOI;
	private Date date;

	//Constructor
	@SuppressWarnings("deprecation")
	public Ref(String t, String[] a, int yp, String pn, String doi, int d, int m, int y)
	{
		title = t;
		authors = new String[a.length];
		for(int i = 0; i < a.length; i++) authors[i] = a[i];
		yearOfPub = yp;
		pName = pn;
		DOI = doi;
		date = new Date(y, m, d);
	}
	//Getting the title
	public String getTitle()
	{
		return title;
	}
	
	//Getting the list of Authors
	public String[] getAuthors()
	{
		return authors;
	}
	
	//Getting the year of publication
	public int getYearOfPub()
	{
		return yearOfPub;
	}
	
	//Getting the name of the publisher
	public String getPublisherName()
	{
		return pName;
	}
	
	//Getting the digital object identifier
	public String getDOI()
	{
		return DOI;
	}
	//Getting the volume of the journal
	public int getJournalVolume()
	{
		return -1;
	}
	
	//Getting the issue of the journal 
	public int getJournalIssue()
	{
		return -1;
	}
	
	public String getConferenceLocation()
	{
		return "";
	}
	
	public String getBookEditor()
	{
		return "";
	}
	
	//Getting the day the ref was added to the collection
	public String getDate()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String dateToString = formatter.format(this.date);
		return dateToString;
	}
	//Creating a class to be overwritten by the subclass RefJournal
	public String getJournalName()
	{
		return "";
	}
	
	//Creating a class to be overwritten by the subclass RefConference
	public String getConferenceName()
	{
		return "";
	}
	
	//Creating a class to be overwritten by the subclass RefBookChaptes
		public String getBookTitle()
		{
			return "";
		}
	//Getting a citation to be printed
	public String getCitation()
	{
		//Assigning the values from the authors list to a variable for citation.
		String result = "";
		String[] a2 = getAuthors();
		for(int i = 0; i < a2.length; i++)
		{
			if(i != a2.length - 1) result = result + a2[i] + ",";
			else result = result + a2[i];
		}
		
		result = result + " " + getTitle() + ", " + getYearOfPub() + ", "
				+ getPublisherName() + ", " + getDOI();

		return result;
	}
	//Using a comparator to sort the References by the first name on the list of Authors.
    public static Comparator<Ref> AuthorsNameComparator = new Comparator<Ref>()
    {
    	public int compare(Ref ref1, Ref ref2) {

    		String refName1= ref1.getAuthors()[0].toUpperCase();
    		String refName2 = ref2.getAuthors()[0].toUpperCase();

    		return refName1.compareTo(refName2);
    	}
    };
    
}