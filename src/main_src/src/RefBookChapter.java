package src;

public class RefBookChapter extends Ref{
	
	private String book;
	private String editor;
	
	//Constructor
	public RefBookChapter(String t, String[] a, int yp, String pn, String doi, int d, int m, int y, String bt, String be)
	{
		//Using the parent
		super(t, a, yp, pn, doi, d, m, y);
		
		book = bt;
		editor = be;
	}
	
	//Getting the name of the journal
	public String getBookTitle()
	{
		return book;
	}
	
	//Getting the name of the editor
	public String getBookEditor()
	{
		return editor;
	}
	
	//Getting the citation
	public String getCitation()
	{
		String result = "";
		String[] a2 = getAuthors();
		for(int i = 0; i < a2.length; i++)
		{
			if(i != a2.length - 1) result = result + a2[i] + ",";
			else result = result + a2[i];
		}
		
		result = result + " (" + getYearOfPub() + "), " + getTitle() + ", in: " + getBookTitle() + ", ed: "
				+ getBookEditor() + ", " + getPublisherName() + ", doi: " + getDOI();

		return result;
	}

}
