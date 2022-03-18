package edu.ncsu.csc216.issue_manager.model.manager;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * IssueList maintains a list of all issues. Responsible for keeping track of issue id's to make
 * sure they do not conflict, adding issues to list, removing issues from list, adding a collection
 * of issues to the list, searching for issues on the list, updating issues, and returning an entire
 * list of issues or sublists of itself. Issue list sorts issues in sorted order with ascending id's.
 * 
 * @author michaelabrams
 *
 */
public class IssueList {

	/** counter represents the id for the next issue */
	private int counter;
	
	/** issues is a list of the issues stored in the issue list */
	private List<Issue> issues;
	
	/**
	 * Constructs an IssueList object with no parameters
	 */
	public IssueList() {
		issues = new ArrayList<Issue>();
		counter = 1;
	}
	
	/**
	 * Adds an issue onto the list provided the issue's type, summary, and note.
	 * 
	 * @param issueType is the type of issue being added
	 * @param summary is the summary of the issue being added
	 * @param note is the note of the issue being added
	 * 
	 * @return the id of the last issue added
	 */
	public int addIssue(IssueType issueType, String summary, String note) {
		
		Issue newIssue = new Issue(counter, issueType, summary, note);
		
		this.issues.add(newIssue);
		
		counter += 1;
		
		return counter - 1;
	}
	
	/**
	 * Adds a list of issues onto the issue list in sorted order ignoring duplicate ids.
	 * 
	 * @param issues is the list of issues to be added onto the list
	 */
	public void addIssues(ArrayList<Issue> issues) {
		
		this.issues.clear();
		
		for(Issue issue: issues) {
			addIssue(issue);
		}
		
		//Remove Duplicates
		removeDuplicateIds();
		
		//Sort Issues
		sortIssues();
		

		
		//Updates Counter
		counter = issues.get(issues.size() - 1).getIssueId() + 1;
		
		
	}
	
	/**
	 * Sorts the issue list, from smallest to largest id's
	 */
	private void sortIssues() {
		
		Issue tempIssue;
		
		for(int i = 0; i < issues.size(); i++) {
			for (int j = 0; j < issues.size(); j++) {
				if(issues.get(i).getIssueId() < issues.get(j).getIssueId()) {
					tempIssue = issues.get(i);
					issues.set(i, issues.get(j));
					issues.set(j, tempIssue);
				}
			}
		}
		
	}
	
	/**
	 * Removes duplicate issues which have the same id from the issue list.
	 */
	private void removeDuplicateIds() {
		
		int currentId;
		int compareId;
		
		
		for(int i = 0; i < issues.size(); i++) {
			
			currentId = issues.get(i).getIssueId();
			
			for(int j = 0; j < issues.size(); j++) {
				//If reading the same issue, continue
				if(i == j) {
					continue;
				}
				
				compareId = issues.get(j).getIssueId();
				
				//If a duplicate id is detected, remove duplication
				if(currentId == compareId) {
					issues.remove(j);
					j--;
				}
				
			}
		}
		
	}
	
	/**
	 * Helper method used to add a single issue onto the issue list.
	 * 
	 * @param issue is the issue to be added onto the issue list
	 */
	private void addIssue(Issue issue) {
		this.issues.add(issue);
		
	}
	
	/**
	 * Gets the issue list
	 * 
	 * @return the current issue list
	 */
	public List<Issue> getIssues() {
		return this.issues;
	}
	
	/**
	 * Retrieves a filtered list of issues by the specified type.
	 * 
	 * @param issueType is the type of issues which are returned
	 * 
	 * @return a list of issues filtered by the issue type
	 * 
	 * @throws IllegalArgumentException if the issueType is null.
	 */
	public List<Issue> getIssuesByType(String issueType) {
		
		if(issueType == null) {
			throw new IllegalArgumentException("Issue type cannot be null");
		}
		
		List<Issue> rtnList = new ArrayList<Issue>();
		
		for(Issue issue : issues) {
			if(issue.getIssueType().equals(issueType)) {
				rtnList.add(issue);
			}
		}
		
		return rtnList;
		
	}
	
	/**
	 * Retrieves an issue from the issue list given a target id, and returns null if issue
	 * can not be found.
	 * 
	 * @param id is the id of the issue aiming to be returned
	 * 
	 * @return the issue with the specified id, or null if no issue is found.
	 */
	public Issue getIssueById(int id) {
		
		for (Issue issue: issues) {
			
			if(issue.getIssueId() == id) {
				return issue;
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * Executes a command on the given issue's target id
	 * 
	 * @param id is the id of the issue you want to run the command on
	 * 
	 * @param command is the command which is ran on an issue
	 * 
	 * @throws UnsupportedOperationException if the command is not supported for the working state.
	 */
	public void executeCommand(int id, Command command) {
		
		for (Issue issue : issues) {
			if(issue.getIssueId() == id) {
				issue.update(command);
			}
		}
		
		
	}
	
	/**
	 * Deletes an issue from the issue list provided a target id
	 * 
	 * @param id is the id of the issue you wish to delete
	 */
	public void deleteIssueById(int id) {
		
		for(int i = 0; i < issues.size(); i++) {
			
			Issue currentIssue = issues.get(i);
			
			if(currentIssue.getIssueId() == id) {
				issues.remove(i);
				counter = issues.get(issues.size() - 1).getIssueId() + 1;
				break;
			}
			
		}
		
	}
	
}
