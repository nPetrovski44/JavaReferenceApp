package src;

public class RefConference extends Ref{

	private String venue;
	private String location;
	
	//Constructor
	public RefConference(String t, String[] a, int yp, String pn, String doi, int d, int m, int y, String cn, String cl)
	{
		//Using the parent
		super(t, a, yp, pn, doi, d, m, y);
		
		venue = cn;
		location = cl;
	}
	
	//Getting the name of the journal
	public String getConferenceName()
	{
		return venue;
	}
	
	public String getConferenceLocation()
	{
		return location;
	}
	
	public String getCitation()
	{
		String result = "";
		String[] a2 = getAuthors();
		for(int i = 0; i < a2.length; i++)
		{
			if(i != a2.length - 1) result = result + a2[i] + ",";
			else result = result + a2[i];
		}
		
		result = result + " (" + getYearOfPub() + "), " + getTitle() + ", at: " + getConferenceName() + ", in: "
				+ getConferenceLocation() + ", " + getPublisherName() + ", doi: " + getDOI();

		return result;
	}
}
