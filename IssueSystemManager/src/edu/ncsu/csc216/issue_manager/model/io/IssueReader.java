package edu.ncsu.csc216.issue_manager.model.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * IssueReader is used for reading issues from a file. IssueReader contains
 * only static methods, which are used by the IssueManager class.
 * 
 * @author michaelabrams
 *
 */
public class IssueReader {
	
	/**
	 * Constructs an IssueReader object with no parameters
	 */
	public IssueReader() {
		//Empty constructor
	}
	
	/**
	 * Reads from a file and returns an array list of issues
	 * 
	 * @param file is the file which is read for issues
	 * 
	 * @return an array list of issues read from a file
	 * 
	 * @throws IllegalArgumentException if the file can not be read from/found, if an issue has too many items, 
	 * to little items, or unexpected format of an issue object.
	 */
	public static ArrayList<Issue> readIssuesFromFile(String file) {
		
		ArrayList<Issue> issues = new ArrayList<Issue>();
		
		String fileText = "";
		
		try {
			Scanner fileReader = new Scanner(new FileInputStream(file));
			
			while(fileReader.hasNextLine()) {
				fileText += fileReader.nextLine();
				fileText += "\n";
			}
			
			Scanner fileTextParser = new Scanner(fileText);
		    fileTextParser.useDelimiter("\\r?\\n?[*]");
		    
		    while(fileTextParser.hasNext()) {
		    	
		    	Issue issue = processIssue(fileTextParser.next());
		    	
		    	issues.add(issue);
		    	
		    }
		    
		    fileTextParser.close();
		    return issues;
			
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
	}
	
	/**
	 * Helper method for readIssuesFromFile. Takes an issue from a line and parses the
	 * information into an issue object which is returned.
	 * 
	 * @param issue is the string representation of an issue which needs to be parsed
	 * 
	 * @return an issue object created from the issue string representation.
	 * 
	 * @throws IllegalArgumentException if an issue has too many items, to little items, or unexpected
	 * format of an issue object.
	 */
	private static Issue processIssue(String issue) {
		
		ArrayList<String> notes = new ArrayList<String>();
		
		Scanner issueNoteReader = new Scanner(issue);
		
		issueNoteReader.useDelimiter("\\r?\\n?[*]");
		
		Scanner issueReader = new Scanner(issueNoteReader.nextLine());
		
		issueNoteReader.useDelimiter("\r?\n?[-]");
	
		
		issueReader.useDelimiter(",");
		
		try {
			int id = issueReader.nextInt();
			String state = issueReader.next();
			String type = issueReader.next();
			String summary = issueReader.next();
			String owner = issueReader.next();
			boolean confirmed = issueReader.nextBoolean();
			
			if(issueReader.hasNext()) {
				String resolution = issueReader.next();
				
				
				
				//Checks for extra items
				if(issueReader.hasNext()) {
					issueNoteReader.close();
					issueReader.close();
					throw new IllegalArgumentException("Unable to load file.");
				}
				
			
				while(issueNoteReader.hasNext()) {
					String note = issueNoteReader.next().stripTrailing();
					notes.add(note);
				}
				
				issueNoteReader.close();
				issueReader.close();
				
				Issue issue1 = new Issue(id, state, type, summary, owner, confirmed, resolution, notes);
				return issue1;
				
			}
			else {
				String resolution = "";
				
				//Checks for extra items
				if(issueReader.hasNext()) {
					issueNoteReader.close();
					issueReader.close();
					throw new IllegalArgumentException("Unable to load file.");
				}
				
				
				while(issueNoteReader.hasNext()) {
					String note = issueNoteReader.next().stripTrailing();
					notes.add(note);
				}
				
				issueNoteReader.close();
				issueReader.close();
				return new Issue(id, state, type, summary, owner, confirmed, resolution, notes);

			}
			
		}
		catch (Exception e) {
			issueReader.close();
			throw new IllegalArgumentException("Unable to load file.");
		}
		
	}
}
