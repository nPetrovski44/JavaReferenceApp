package src;

import java.util.*;
import java.util.Scanner;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;  

public class RefCollection {
	
	private String title, publisher, doi, journaln, v, l, bName, bEditor, authors = "", d;
	private int yearPub, vol, iss;
	private List<Ref> collection;
	private PrintWriter writer;
	
	private static DocumentBuilder builder = null;
	private static Schema schema = null;
	private static Document document = null;
	
	//We create a constructor
	public RefCollection()
	{
		collection = new ArrayList<Ref>();
	}
	
	//Adding a reference to the collection.
	public void addRef(Ref r)
	{
		collection.add(r);
	}
	
	//We create a method to check whether a reference has already been added.
	//Since the DOI value is unique for each refence we are going to use that
	//as a check. In case the value is not entered we will use the other 3 elements.
	public boolean existAllready(String title, String DOI, String publisher, int pubYear)
	{
		ListIterator<Ref> iter = collection.listIterator();
		while(iter.hasNext())
		{
			Ref current = iter.next();
			if(DOI.equals(""))
				{
					if(current.getTitle().equals(title) && current.getPublisherName().equals(publisher) && current.getYearOfPub() == pubYear)
					return true;
				}
			else if(current.getDOI().equals(DOI)) return true;
		}
		return false;
	}
	
	//Getting all the references from a specific journal.
	public String lookUpByJournalName(String jName)
	{
		Collections.sort(collection, Ref.AuthorsNameComparator);
		ListIterator<Ref> iter = collection.listIterator();  
		String result = "";
		while(iter.hasNext())
		{
			Ref current  = iter.next();
			if(current.getJournalName().equals(jName)) result += current.getCitation() + "\n";
		}
		return result;
	}
	
	//Getting all the references from a specific venue.
	public String lookUpByConferenceVenue(String cVenue)
	{
		Collections.sort(collection, Ref.AuthorsNameComparator);
		ListIterator<Ref> iter = collection.listIterator();  
		String result = "";
		while(iter.hasNext())
		{
			Ref current  = iter.next();
			if(current.getConferenceName().equals(cVenue)) result += current.getCitation() + "\n";
		}
		return result;
	}
	
	//Getting all the references from a specific publisher.
	public String lookUpByPublisher(String pName)
	{
		Collections.sort(collection, Ref.AuthorsNameComparator);
		ListIterator<Ref> iter = collection.listIterator();  
		String result = "";
		while(iter.hasNext())
		{
			Ref current  = iter.next();
			if(current.getPublisherName().equals(pName)) result += current.getCitation() + "\n";
		}
		return result;
	}

	//Getting the total number of References.
	public int getNumberOfRefs()
	{
		ListIterator<Ref> iter = collection.listIterator();
		int count = 0;
		while(iter.hasNext())
		{
			count++;
			iter.next();
		}
		return count;
	}
	
	/*
	 * This method receives a file, it checks if the type of the import is in csv 
	 * and if so, it goes through the file. We assume there could only be 4-types of import files.
	 * -all_data
	 * -only_journals
	 * -only_conferences
	 * -only_books
	 * The method checks which type we are working with based on its headers and it produces the
	 * relevant Reference.
	 */
	@SuppressWarnings({ "deprecation", "resource" })
	public String importMany(File inputFilePath)
	{
		blankAll();
		try {
			File inputFile = inputFilePath;
			Scanner lineScanner = new Scanner(inputFile);
			String wordsOnLine = "", wordsOnLineHeaders = "";
			lineScanner.useDelimiter("\n");
			//We skip the row with the headers and use it to decide the type of csv file.
			wordsOnLineHeaders = lineScanner.next();
			String[] headersOnLine = wordsOnLineHeaders.split(",");
			//Going through the csv file line by line.
			while(lineScanner.hasNext())
			{
				int rowCounter = 0;
				//Assigning the contents of that line to a String to parse to an array of Strings.
				wordsOnLine = lineScanner.next();
				String[] wordArray = wordsOnLine.split(",");
				for(int i = 0; i < wordArray.length; i++)
				{
					//For all data
						if(headersOnLine.length > 9)
						{
							if(rowCounter == 2)
							{
								//We use the way data is presented in the csv file to build the list of authors.
								try
								{		
									Integer.parseInt(wordArray[i]);
								}catch(Exception e)
								{
									rowCounter--;
								}
							}
							switch(rowCounter)
							{
								case 0:
									title = wordArray[i];
									break;
								case 1:
									authors += wordArray[i] + ", ";
									break;
								case 2:
									if(wordArray[i].equals(""))yearPub = 0;
									else yearPub = Integer.parseInt(wordArray[i]);
									break;
								case 3:
									publisher = wordArray[i];
									break;
								case 4:
									doi = wordArray[i];
									break;
								case 5:
									d = wordArray[i];
									break;
								case 6:
									journaln = wordArray[i];
									break;
								case 7:
									if(wordArray[i].equals(""))vol = 0;
									else vol = Integer.parseInt(wordArray[i]);
									break;
								case 8:
									if(wordArray[i].equals(""))iss = 0;
									else iss = Integer.parseInt(wordArray[i]);
									break;
								case 9:
									v = wordArray[i];
									break;
								case 10:
									l = wordArray[i];
									break;
								case 11:
									bName = wordArray[i];
									break;
								case 12:
									wordArray[i] = wordArray[i].substring(0,wordArray[i].length()-1);
									bEditor = wordArray[i];
									break;
							}
							rowCounter++;
						}
						
						//For journals only
						else if(headersOnLine[6].equals("journal"))
						{
							if(rowCounter == 2)
							{
								try
								{		
									Integer.parseInt(wordArray[i]);
								}catch(Exception e)
								{
									rowCounter--;
								}
							}
							switch(rowCounter)
							{
								case 0:
									title = wordArray[i];
									break;
								case 1:
									authors += wordArray[i] + ", ";
									break;
								case 2:
									if(wordArray[i].equals(""))yearPub = 0;
									else yearPub = Integer.parseInt(wordArray[i]);
									break;
								case 3:
									publisher = wordArray[i];
									break;
								case 4:
									doi = wordArray[i];
									break;
								case 5:
									d = wordArray[i];
									break;
								case 6:
									journaln = wordArray[i];
									break;
								case 7:
									if(wordArray[i].equals(""))vol = 0;
									else vol = Integer.parseInt(wordArray[i]);
									break;
								case 8:
									if(wordArray[i].equals(""))iss = 0;
									else 
										{
											wordArray[i] = wordArray[i].substring(0,wordArray[i].length()-1);
											iss = Integer.parseInt(wordArray[i]);
										}
									break;
							}
							rowCounter++;
							
						}
						//For conferences only
						else if(headersOnLine[6].equals("venue"))
						{
							if(rowCounter == 2)
							{
								try
								{		
									Integer.parseInt(wordArray[i]);
								}catch(Exception e)
								{
									rowCounter--;
								}
							}
							switch(rowCounter)
							{
								case 0:
									title = wordArray[i];
									break;
								case 1:
									authors += wordArray[i] + ", ";
									break;
								case 2:
									if(wordArray[i].equals(""))yearPub = 0;
									else yearPub = Integer.parseInt(wordArray[i]);
									break;
								case 3:
									publisher = wordArray[i];
									break;
								case 4:
									doi = wordArray[i];
									break;
								case 5:
									d = wordArray[i];
									break;
								case 6:
									v = wordArray[i];
									break;
								case 7:
									wordArray[i] = wordArray[i].substring(0,wordArray[i].length()-1);
									l = wordArray[i];
									break;
							}
							rowCounter++;
						}
						
						//For books only
						else if(headersOnLine[6].equals("bookTitle"))
						{
							if(rowCounter == 2)
							{
								try
								{		
									Integer.parseInt(wordArray[i]);
								}catch(Exception e)
								{
									rowCounter--;
								}
							}
							switch(rowCounter)
							{
								case 0:
									title = wordArray[i];
									break;
								case 1:
									authors += wordArray[i] + ", ";
									break;
								case 2:
									if(wordArray[i].equals(""))yearPub = 0;
									else yearPub = Integer.parseInt(wordArray[i]);
									break;
								case 3:
									publisher = wordArray[i];
									break;
								case 4:
									doi = wordArray[i];
									break;
								case 5:
									d = wordArray[i];
									break;
								case 6:
									bName = wordArray[i];
									break;
								case 7:
									wordArray[i] = wordArray[i].substring(0,wordArray[i].length()-1);
									bEditor = wordArray[i];
									break;
							}
							rowCounter++;
						}
				}
				try
				{
					Date date1; 
					if(d.equals(""))date1 = new Date();
					else date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d);  
					authors = authors.trim();
					//Since the delimitor for the csv file overlap with the custom delimitor we need to trim the additional quotes.
					if(authors.substring(0,1).equals("\""))authors = authors.substring(1);
					if(authors.substring(authors.length()-2, authors.length()-1).equals("\""))authors = authors.substring(0, authors.length()-2) + ",";
					String[] authorsArray = authors.split(", ");
					//We check for the type of reference
					if(v.equals("") && (bName.equals("")))
					{
						if(existAllready(title, doi, publisher, yearPub) == false)
						{
							RefJournal rj = new RefJournal(title, authorsArray, yearPub, publisher, doi, date1.getDate(), date1.getMonth(), date1.getYear(), journaln, vol, iss);
							addRef(rj);
						}
					}
					if(journaln.equals("")&& (bName.equals("")))
					{
						if(existAllready(title, doi, publisher, yearPub) == false)
						{
							RefConference rc = new RefConference(title, authorsArray, yearPub, publisher, doi, date1.getDate(), date1.getMonth(), date1.getYear(), v, l);
							addRef(rc);
						}
					}
					if(journaln.equals("") && (v.equals("")))
					{
						if(existAllready(title, doi, publisher, yearPub) == false)
						{
							RefBookChapter rb = new RefBookChapter(title, authorsArray, yearPub, publisher, doi, date1.getDate(), date1.getMonth(), date1.getYear(), bName, bEditor);
							addRef(rb);
						}
					}
				}catch(Exception e) {
					return "Choose a correct file.";
				}
				blankAll();
			}
			lineScanner.close();
		} catch (FileNotFoundException e)
		{
			return "Choose a correct file.";
		} catch(Exception e)
		{
			return "Choose a correct file.";
		}
		return "Imported succesfully";
	}
	
	//Exports all the References to a specified format.
	public String exportAll(String type, File schemeName) throws FileNotFoundException
	{
		
		ListIterator<Ref> iter = collection.listIterator();
		if(type.equals("TXT"))
			{
				writer = new PrintWriter(new File("bibliographyTXT.txt"));
				while(iter.hasNext())
		    	{
		    		Ref current = iter.next();
		    		writer.println(current.getCitation());
		    	}
		    	writer.close();
		    	return "The file was exported succesfully.";
			}
		else
			{
				writer = new PrintWriter(new File("bibliographyXML.xml"));
				String fillingXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<bibliography>\n";
				String journals = "", books = "", conferences = "";
				while(iter.hasNext())
		    	{
					Ref current = iter.next();
					String authors = "";
					String[] a2 = current.getAuthors();
					for(int i = 0; i < a2.length; i++)
					{
						if(i != a2.length - 1) authors = authors + a2[i] + ",";
						else authors = authors + a2[i];
					}
					if(current.getBookTitle().equals("") && current.getConferenceName().equals(""))
						journals = journals + "\t<referenceGeneral>\n" +"\t\t<referenceJournal category=\"Journals\">\n" + "\t\t\t<title>" + current.getTitle() + "</title>\n" + "\t\t\t<authors>" +
						authors + "</authors>\n" + "\t\t\t<yearPub>" + current.getYearOfPub() + "\t\t\t</yearPub>\n" +
						"\t\t\t<publisher>" + current.getPublisherName() + "</publisher>\n" + "\t\t\t<doi>" +current.getDOI() + "</doi>\n" +
						"\t\t\t<date>" + current.getDate().toString() + "</date>\n" + "\t\t\t<journalName>" + current.getJournalName() +
						"</journalName>\n" + "\t\t\t<volume>" + current.getJournalVolume() + "</volume>\n" + "\t\t\t<issue>" +
						current.getJournalIssue() + "</issue>\n" + "\t\t</referenceJournal>\n" + "\t</referenceGeneral>\n";
					
					if(current.getJournalName().equals("") && current.getBookTitle().equals(""))
						conferences = conferences + "\t<referenceGeneral>\n" + "\t\t<referenceConference category=\"Conferences\">\n" + "\t\t\t<title>" + current.getTitle() + "</title>\n" + "\t\t\t<authors>" +
						authors + "</authors>\n" + "\t\t\t<yearPub>" + current.getYearOfPub() + "</yearPub>\n" +
						"\t\t\t<publisher>" + current.getPublisherName() + "</publisher>\n" + "\t\t\t<doi>" +current.getDOI() + "</doi>\n" +
						"\t\t\t<date>" + current.getDate().toString() + "</date>\n" + "\t\t\t<venue>" + current.getConferenceName() +
						"</venue>\n" + "\t\t\t<location>" + current.getConferenceLocation() + "</location>\n" + "\t\t</referenceConference>\n" + "\t</referenceGeneral>\n";
					
					if(current.getJournalName().equals("") && current.getConferenceName().equals(""))
						books = books + "\t<referenceGeneral>\n" + "\t\t<referenceBook category=\"Books\">\n" +"\t\t\t<title>" + current.getTitle() + "</title>\n" + "\t\t\t<authors>" +
						authors + "</authors>\n" + "\t\t\t<yearPub>" + current.getYearOfPub() + "</yearPub>\n" +
						"\t\t\t<publisher>" + current.getPublisherName() + "</publisher>\n" + "\t\t\t<doi>" +current.getDOI() + "</doi>\n" +
						"\t\t\t<date>" + current.getDate().toString() + "</date>\n" + "\t\t\t<bookTitle>" + current.getBookTitle() +
						"</bookTitle>\n" + "\t\t\t<editor>" + current.getBookEditor() + "</editor>\n" + "\t\t</referenceBook>\n" + "\t</referenceGeneral>\n";
					
		    	}
				
				//The resulting XML file has been tested using DOMMenu parser from the last Practical!
				fillingXML = fillingXML + journals + conferences + books + "</bibliography>\n";
				writer.println(fillingXML);
				writer.close();
				
				loadDocument("bibliographyXML.xml");
				if(validateDocument(schemeName) == true) return"The xml file parsed the selected scheme successfully.";
				else return "The xml file did not parse the selected scheme.";
			}
	}
	//Exports the search results to a specified format.
	//All of the exported XML results have been tested with a DOMMenu parser.
	public String exportSearchResult(String lastLookUpText, String type, String lastLookUp, File schemeName) throws FileNotFoundException
	{
		if(lastLookUpText.equals("") || lastLookUp.equals(""))return "Search before you export.";
		if(type.equals("TXT"))
		{
			writer = new PrintWriter(new File("bibliographyTXT.txt"));
			
			if(lastLookUp.equals("JournalName"))writer.println(lookUpByJournalName(lastLookUpText));
			else if(lastLookUp.equals("ConferenceVenue"))writer.println(lookUpByConferenceVenue(lastLookUpText));
			else writer.println(lookUpByPublisher(lastLookUpText));
			writer.close();
			return "The file was exported succesfully.";
		}
	else
		{
			//If the search was for journal names
			if(lastLookUp.equals("JournalName"))
			{
				
				ListIterator<Ref> iter = collection.listIterator();  
				writer = new PrintWriter(new File("bibliographyXML.xml"));
				String fillingXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<bibliography>\n";
				String journals = "";
				while(iter.hasNext())
		    	{
					Ref current = iter.next();
					if(current.getJournalName().equals(lastLookUpText))
					{
						String authors = "";
						String[] a2 = current.getAuthors();
						for(int i = 0; i < a2.length; i++)
						{
							if(i != a2.length - 1) authors = authors + a2[i] + ",";
							else authors = authors + a2[i];
						}
						if(current.getBookTitle().equals("") && current.getConferenceName().equals(""))
							journals = journals + "\t<referenceGeneral>\n" +"\t\t<referenceJournal category=\"Journals\">\n" + "\t\t\t<title>" + current.getTitle() + "</title>\n" + "\t\t\t<authors>" +
							authors + "</authors>\n" + "\t\t\t<yearPub>" + current.getYearOfPub() + "\t\t\t</yearPub>\n" +
							"\t\t\t<publisher>" + current.getPublisherName() + "</publisher>\n" + "\t\t\t<doi>" +current.getDOI() + "</doi>\n" +
							"\t\t\t<date>" + current.getDate().toString() + "</date>\n" + "\t\t\t<journalName>" + current.getJournalName() +
							"</journalName>\n" + "\t\t\t<volume>" + current.getJournalVolume() + "</volume>\n" + "\t\t\t<issue>" +
							current.getJournalIssue() + "</issue>\n" + "\t\t</referenceJournal>\n" + "\t</referenceGeneral>\n";
					}
		    	}
				fillingXML = fillingXML + journals + "</bibliography>\n";
				writer.println(fillingXML);
				writer.close();
				
				loadDocument("bibliographyXML.xml");
				if(validateDocument(schemeName) == true) return"The xml file parsed the selected scheme successfully.";
				else return "The xml file did not parse the selected scheme.";
			}
			//If the search was for conference venues
			else if(lastLookUp.equals("ConferenceVenue"))
			{
				ListIterator<Ref> iter = collection.listIterator();  
				writer = new PrintWriter(new File("bibliographyXML.xml"));
				String fillingXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<bibliography>\n";
				String conferences = "";
				while(iter.hasNext())
		    	{
					Ref current = iter.next();
					if(current.getJournalName().equals(lastLookUpText))
					{
						String authors = "";
						String[] a2 = current.getAuthors();
						for(int i = 0; i < a2.length; i++)
						{
							if(i != a2.length - 1) authors = authors + a2[i] + ",";
							else authors = authors + a2[i];
						}
						if(current.getJournalName().equals("") && current.getBookTitle().equals(""))
							conferences = conferences + "\t<referenceGeneral>\n" + "\t\t<referenceConference category=\"Conferences\">\n" + "\t\t\t<title>" + current.getTitle() + "</title>\n" + "\t\t\t<authors>" +
							authors + "</authors>\n" + "\t\t\t<yearPub>" + current.getYearOfPub() + "</yearPub>\n" +
							"\t\t\t<publisher>" + current.getPublisherName() + "</publisher>\n" + "\t\t\t<doi>" +current.getDOI() + "</doi>\n" +
							"\t\t\t<date>" + current.getDate().toString() + "</date>\n" + "\t\t\t<venue>" + current.getConferenceName() +
							"</venue>\n" + "\t\t\t<location>" + current.getConferenceLocation() + "</location>\n" + "\t\t</referenceConference>\n" + "\t</referenceGeneral>\n";
					}
		    	}
				fillingXML = fillingXML + conferences + "</bibliography>\n";
				writer.println(fillingXML);
				writer.close();
				
				loadDocument("bibliographyXML.xml");
				if(validateDocument(schemeName) == true) return"The xml file parsed the selected scheme successfully.";
				else return "The xml file did not parse the selected scheme.";
			}
			//If the search was for publishers
			else
			{
				ListIterator<Ref> iter = collection.listIterator();  
				writer = new PrintWriter(new File("bibliographyXML.xml"));
				String fillingXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<bibliography>\n";
				String journals = "", books = "", conferences = "";
				while(iter.hasNext())
		    	{
					Ref current = iter.next();
					String authors = "";
					if(current.getPublisherName().equals(lastLookUpText))
					{
						String[] a2 = current.getAuthors();
						for(int i = 0; i < a2.length; i++)
						{
							if(i != a2.length - 1) authors = authors + a2[i] + ",";
							else authors = authors + a2[i];
						}
						if(current.getBookTitle().equals("") && current.getConferenceName().equals(""))
							journals = journals + "\t<referenceGeneral>\n" +"\t\t<referenceJournal category=\"Journals\">\n" + "\t\t\t<title>" + current.getTitle() + "</title>\n" + "\t\t\t<authors>" +
							authors + "</authors>\n" + "\t\t\t<yearPub>" + current.getYearOfPub() + "\t\t\t</yearPub>\n" +
							"\t\t\t<publisher>" + current.getPublisherName() + "</publisher>\n" + "\t\t\t<doi>" +current.getDOI() + "</doi>\n" +
							"\t\t\t<date>" + current.getDate().toString() + "</date>\n" + "\t\t\t<journalName>" + current.getJournalName() +
							"</journalName>\n" + "\t\t\t<volume>" + current.getJournalVolume() + "</volume>\n" + "\t\t\t<issue>" +
							current.getJournalIssue() + "</issue>\n" + "\t\t</referenceJournal>\n" + "\t</referenceGeneral>\n";
						
						if(current.getJournalName().equals("") && current.getBookTitle().equals(""))
							conferences = conferences + "\t<referenceGeneral>\n" + "\t\t<referenceConference category=\"Conferences\">\n" + "\t\t\t<title>" + current.getTitle() + "</title>\n" + "\t\t\t<authors>" +
							authors + "</authors>\n" + "\t\t\t<yearPub>" + current.getYearOfPub() + "</yearPub>\n" +
							"\t\t\t<publisher>" + current.getPublisherName() + "</publisher>\n" + "\t\t\t<doi>" +current.getDOI() + "</doi>\n" +
							"\t\t\t<date>" + current.getDate().toString() + "</date>\n" + "\t\t\t<venue>" + current.getConferenceName() +
							"</venue>\n" + "\t\t\t<location>" + current.getConferenceLocation() + "</location>\n" + "\t\t</referenceConference>\n" + "\t</referenceGeneral>\n";
						
						if(current.getJournalName().equals("") && current.getConferenceName().equals(""))
							books = books + "\t<referenceGeneral>\n" + "\t\t<referenceBook category=\"Books\">\n" +"\t\t\t<title>" + current.getTitle() + "</title>\n" + "\t\t\t<authors>" +
							authors + "</authors>\n" + "\t\t\t<yearPub>" + current.getYearOfPub() + "</yearPub>\n" +
							"\t\t\t<publisher>" + current.getPublisherName() + "</publisher>\n" + "\t\t\t<doi>" +current.getDOI() + "</doi>\n" +
							"\t\t\t<date>" + current.getDate().toString() + "</date>\n" + "\t\t\t<bookTitle>" + current.getBookTitle() +
							"</bookTitle>\n" + "\t\t\t<editor>" + current.getBookEditor() + "</editor>\n" + "\t\t</referenceBook>\n" + "\t</referenceGeneral>\n";
					}
		    	}

				fillingXML = fillingXML + journals + conferences + books + "</bibliography>\n";
				writer.println(fillingXML);
				writer.close();
				
				loadDocument("bibliographyXML.xml");
				if(validateDocument(schemeName) == true) return"The xml file parsed the selected scheme successfully.";
				else return "The xml file did not parse the selected scheme.";
			}
		}
	}
	
	private static void loadDocument(String filename) {
	    try {
	      // create a document builder
	      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	      builder = builderFactory.newDocumentBuilder();

	      // parse the document for later searching
	      document = builder.parse(new File(filename));
	    }
	    catch (Exception exception) {
	      System.err.println("could not load document " + exception);
	    }
	  }
	
	  private static Boolean validateDocument(File filename) 
	  {
	    try {
	      String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
	      SchemaFactory factory = SchemaFactory.newInstance(language);
	      schema = factory.newSchema(filename);
	      Validator validator = schema.newValidator();
	      validator.validate(new DOMSource(document));
	      return true;
	    }
	    catch (SAXParseException e){
	      System.out.println("Could not validate because of " + e.getMessage());
	      return false;
	    }
	    catch(Exception e)
	    {
	        System.out.println("Could not validate because of " + e.getMessage());
	        return false;
	    }
	  }
	
	//A method to reset all the values to avoid miss entries.
	private void blankAll()
	{
		title = "";
		publisher = "";
		doi = "";
		journaln = "";
		v = "";
		l = "";
		bName = "";
		bEditor = "";
		authors = "";
		yearPub = -1;
		vol = -1;
		iss = -1;
		d = "";
	}
	

}
