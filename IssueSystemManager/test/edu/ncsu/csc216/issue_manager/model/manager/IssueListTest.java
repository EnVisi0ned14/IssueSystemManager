/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.io.IssueReader;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * Tests the IssueList class
 * 
 * @author michaelabrams
 *
 */
class IssueListTest {

	/**
	 * Tests the addIssue method by adding a valid issue and comparing the issue list size.
	 */
	@Test
	void testAddIssue() {
		
		IssueList issueList = new IssueList();
		
		issueList.addIssue(Issue.IssueType.ENHANCEMENT, "Improve", "Want better");
		
		assertEquals(1, issueList.getIssues().size());
		
	}

	/**
	 * Tests the addIssues method for adding duplicate id's, adding in sorting order,
	 * and ensuring the counter is updated properly.
	 */
	@Test
	void testAddIssues() {
		
		IssueList issueList = new IssueList();
		
		ArrayList<Issue> issues = new ArrayList<Issue>();
		
		
		issues = IssueReader.readIssuesFromFile("test-files/duplicate_issue1.txt");
		
		issueList.addIssues(issues);
		
		assertEquals(5, issueList.getIssues().size());
		
		assertEquals(1, issueList.getIssues().get(0).getIssueId());
		
		assertEquals(3, issueList.getIssues().get(1).getIssueId());
		
		assertEquals(7, issueList.getIssues().get(2).getIssueId());
		
		assertEquals(14, issueList.getIssues().get(3).getIssueId());
		
		assertEquals(15, issueList.getIssues().get(4).getIssueId());

		
		
		
		
		
	}

	/**
	 * Tests the getIssues method for when issues are added and comparing the expected size
	 * through the getIssue method.
	 */
	@Test
	void testGetIssues() {
		
		IssueList issueList = new IssueList();
		
		ArrayList<Issue> issues = IssueReader.readIssuesFromFile("test-files/issue1.txt");
		
		issueList.addIssues(issues);
		
		List<Issue> rtnIssueList = issueList.getIssues();
		
		assertEquals(5, rtnIssueList.size());
		
	}

	/**
	 * Tests the getIssueByType method by loading the issue list, and retrieving all issues
	 * of type bug and comparing fields.
	 */
	@Test
	void testGetIssuesByType() {
		
		IssueList issueList = new IssueList();
		
		ArrayList<Issue> issues = IssueReader.readIssuesFromFile("test-files/issue1.txt");
		
		issueList.addIssues(issues);
		
		List<Issue> bugIssues = issueList.getIssuesByType(Issue.I_BUG);
		
		assertEquals(2, bugIssues.size());
		
		Issue bugIssue = bugIssues.get(1);
		
		assertEquals("Bug", bugIssue.getIssueType());
		
		assertEquals(7, bugIssue.getIssueId());
		
		assertEquals("owner", bugIssue.getOwner());
		
		
	}

	/**
	 * Tests the getIssueById method by loading the issueList, and searching for existent
	 * and nonexistent issues.
	 */
	@Test
	void testGetIssueById() {
		
		IssueList issueList = new IssueList();
		
		ArrayList<Issue> issues = IssueReader.readIssuesFromFile("test-files/issue1.txt");
		
		issueList.addIssues(issues);
		
		Issue searchIssue = issueList.getIssueById(14);

		assertEquals(Issue.I_ENHANCEMENT, searchIssue.getIssueType());
		assertEquals(14, searchIssue.getIssueId());
		assertEquals(Issue.VERIFYING_NAME, searchIssue.getStateName());
		
		Issue searchIssue2 = issueList.getIssueById(25);
		
		assertNull(searchIssue2);
		
	}

	/**
	 * Tests the executeCommand method by adding a new enhancment, and assigning it to
	 * an owner.
	 */
	@Test
	void testExecuteCommand() {
		
		IssueList issueList = new IssueList();
		
		issueList.addIssue(Issue.IssueType.ENHANCEMENT, "Improve", "Want better");
		
		assertEquals(1, issueList.getIssues().size());
		
		Command command = new Command(Command.CommandValue.ASSIGN, "owner", null, "Assigend an owner");
		
		issueList.executeCommand(1, command);
		
		Issue rsltIssue = issueList.getIssueById(1);
		
		assertEquals(1, rsltIssue.getIssueId());
		assertEquals("owner", rsltIssue.getOwner());
		assertEquals(Issue.WORKING_NAME, rsltIssue.getStateName());
		assertNull(rsltIssue.getResolution());
		
	}

	/**
	 * Tests the deleteIssueById method when deleting existent and nonexistent issues.
	 */
	@Test
	void testDeleteIssueById() {
		IssueList issueList = new IssueList();
		
		ArrayList<Issue> issues = IssueReader.readIssuesFromFile("test-files/issue1.txt");
		
		issueList.addIssues(issues);
		
		issueList.deleteIssueById(14);
		
		assertEquals(4, issueList.getIssues().size());
		
		issueList.deleteIssueById(99);
		
		assertEquals(4, issueList.getIssues().size());
		
	}

}
