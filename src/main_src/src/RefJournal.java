package src;

public class RefJournal extends Ref{

	private String journal;
	private int volume;
	private int issue;
	
	//Constructor
	public RefJournal(String t, String[] a, int yp, String pn, String doi, int d, int m, int y,String jn, int v, int is)
	{
		//Using the parent
		super(t, a, yp, pn, doi, d, m, y);
		
		journal = jn;
		volume = v;
		issue = is;
	}
	
	//Getting the name of the journal
	public String getJournalName()
	{
		return journal;
	}
	
	//Getting the volume of the journal
	public int getJournalVolume()
	{
		return volume;
	}
	
	//Getting the issue of the journal 
	public int getJournalIssue()
	{
		return issue;
	}
	
	//Getting the reference citation-ready
	public String getCitation()
	{
		String result = "";
		String[] a2 = getAuthors();
		for(int i = 0; i < a2.length; i++)
		{
			if(i != a2.length - 1) result = result + a2[i] + ",";
			else result = result + a2[i];
		}
		
		result = result + " (" + getYearOfPub() + "), " + getTitle() + ", from: " + getJournalName() + ", vol: "
				+ getJournalVolume() + ", iss: " + getJournalIssue() + ", " + getPublisherName() + ", doi: " + getDOI();

		return result;
	}
}

