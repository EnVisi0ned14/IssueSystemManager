package edu.ncsu.csc216.issue_manager.model.manager;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.io.IssueReader;
import edu.ncsu.csc216.issue_manager.model.io.IssueWriter;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * IssueManager is responsible for the entire life cycle of issueList. IssueManager can keep track
 * of issues, getIssuesByType, displayIssuesAsAnArray, saveIssuesToFile, loadIssuesFromFile, create
 * a new issue list, getIssueById, execute commands on issues, deleting issues, and adding issues to the
 * issue list. IssueManager follows a singelton pattern so only one instance of IssueManager will ever be created.
 * 
 * @author michaelabrams
 *
 */
public class IssueManager {
	
	/** issueList is the list of issues currently stored in the manager */
	private IssueList issueList;
	
	/** singleton is the shared instance of IssueManager */
	private static IssueManager singleton;
	
	/** COLUMNS is the number of columns when constructing an issue as an array */
	public static final int COLUMNS = 4;
	
	/** ID_POS is the column for the issue's id when constructing an issue as an array */
	public static final int ID_POS = 0;
	
	/** STATE_POS is the column for the issue's state when constructing an issue as an array */
	public static final int STATE_POS = 1;
	
	/** TYPE_POS is the column for the issue's type when constructing an issue as an array */
	public static final int TYPE_POS = 2;
	
	/** SUMMARY_POS is the column for the issue's summary when constructing an issue as an array */
	public static final int SUMMARY_POS = 3;
	
	/**
	 * Constructs an issue manager given no parameters
	 */
	private IssueManager() {
		issueList = new IssueList();
	}
	
	/**
	 * Returns the issue manager
	 * 
	 * @return the issue manager
	 */
	public static IssueManager getInstance() {
		
		if(singleton == null) {
			singleton = new IssueManager();
			return singleton;
		}
		else {
			return singleton;
		}
	}
	
	/**
	 * Writes the issue list to a provided output file.
	 * 
	 * @param file is the file the issues are written to.
	 */
	public void saveIssuesToFile(String file) {

		IssueWriter.writeIssuesToFile(file, issueList.getIssues());
		
	}
	
	/**
	 * Reads issues from file and adds them to the issue list
	 * 
	 * @param file is the file issues are read from
	 */
	public void loadIssuesFromFile(String file) {
		
		createNewIssueList();
		
		ArrayList<Issue> returnedIssues = IssueReader.readIssuesFromFile(file);
		
		this.issueList.addIssues(returnedIssues);
	}
	
	/**
	 * Creates a new issue list
	 */
	public void createNewIssueList() {
		issueList = new IssueList();
	}
	
	/**
	 * Gets the issue list sorted by type. Each list index is an issue, and each issue has stored values
	 * of the issue's id, name, type, and summary. 
	 * 
	 * @param issueType is the type of issue which is filtered for when returning all issues
	 * 
	 * @return a list of the issues in a 2D array, filtered by issue type.
	 */
	public Object[][] getIssueListAsArrayByIssueType(String issueType) {
		
		if(issueType == null) {
			throw new IllegalArgumentException("Issue type cannot be null");
		}
		
		List<Issue> issueTypeList = issueList.getIssuesByType(issueType);
		
		Object[][] returnIssueType = new Object[issueTypeList.size()][COLUMNS];
		
		for(int i = 0; i < issueTypeList.size(); i++) {
			Issue currentIssue = issueTypeList.get(i);
			
			int id = currentIssue.getIssueId();
			String state = currentIssue.getStateName();
			String type = currentIssue.getIssueType();
			String summary = currentIssue.getSummary();
			
			returnIssueType[i][ID_POS] = id;
			returnIssueType[i][STATE_POS] = state;
			returnIssueType[i][TYPE_POS] = type;
			returnIssueType[i][SUMMARY_POS] = summary;
			
		}
		
		
		return returnIssueType;

		
	}
	
	/**
	 * Gets the issue list sorted by type. Each list index is an issue, and each issue has stored values
	 * of the issue's id, name, type, and summary. 
	 * 
	 * @return a list of the issues in a 2D array
	 */
	public Object[][] getIssueListAsArray() {

		
		List<Issue> returnedList = this.issueList.getIssues();
		
		Object[][] returnIssueType = new Object[returnedList.size()][COLUMNS];
		
		for(int i = 0; i < returnedList.size(); i++) {
			Issue currentIssue = returnedList.get(i);
			
			int id = currentIssue.getIssueId();
			String state = currentIssue.getStateName();
			String type = currentIssue.getIssueType();
			String summary = currentIssue.getSummary();
			
			returnIssueType[i][ID_POS] = id;
			returnIssueType[i][STATE_POS] = state;
			returnIssueType[i][TYPE_POS] = type;
			returnIssueType[i][SUMMARY_POS] = summary;
			
		}
		
		
		return returnIssueType;
	}
	
	/**
	 * Gets an issue from the issue list with a targeted id
	 * 
	 * @param id is the id of the issue which is returned
	 * 
	 * @return the issue with the targeted id
	 */
	public Issue getIssueById(int id) {
		return issueList.getIssueById(id);
	}
	
	/**
	 * Executes a command on the given issue's target id
	 * 
	 * @param id is the id of the issue you want to run the command on
	 * 
	 * @param command is the command which is ran on an issue
	 */
	public void executeCommand(int id, Command command) {
		issueList.executeCommand(id, command);
	}
	
	/**
	 * Deletes an issue from the issue list provided a target id
	 * 
	 * @param id is the id of the issue you wish to delete
	 */
	public void deleteIssueById(int id) {
		issueList.deleteIssueById(id);
	}
	
	/**
	 * Adds an issue to the issue list given an issue type, summary, and note.
	 * 
	 * @param issueType is the type of issue to add
	 * @param summary is the summary of the issue to add
	 * @param note is the note of the issue to add
	 */
	public void addIssueToList(IssueType issueType, String summary, String note) {
		issueList.addIssue(issueType, summary, note);
	}
	

	
	
	
}
