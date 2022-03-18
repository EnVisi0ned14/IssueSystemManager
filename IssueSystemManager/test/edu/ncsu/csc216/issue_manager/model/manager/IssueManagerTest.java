/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * Tests the IssueManager class
 * 
 * @author michaelabrams
 *
 */
class IssueManagerTest {
	
	
	/**
	 * Creates a new issue list before each test case.
	 */
	@BeforeEach
	public void setup() {
		IssueManager.getInstance().createNewIssueList();
	}

	/**
	 * Tests the getInstance method by asserting getInstance as not null.
	 */
	@Test
	void testGetInstance() {
		
		assertNotNull(IssueManager.getInstance());
	}

	/**
	 * Tests the saveIssuesToFile method by adding an issue, and comparing it against
	 * expected output.
	 */
	@Test
	void testSaveIssuesToFile() {
		
		IssueManager manager = IssueManager.getInstance();
		
		manager.addIssueToList(IssueType.BUG, "Found a problem", "Problem");
		
		assertNotNull(manager.getIssueById(1));
		
		manager.saveIssuesToFile("test-files/issue1_act.txt");
		
		checkFiles("test-files/issue1_exp.txt", "test-files/issue1_act.txt");
		
		
	}

	/**
	 * Tests the loadIssuesFromFile method by loading a file, and seeing if the
	 * issues were added onto the manager.
	 */
	@Test
	void testLoadIssuesFromFile() {
		
		IssueManager manager = IssueManager.getInstance();
		
		manager.loadIssuesFromFile("test-files/issue1.txt");
		
		assertNotNull(manager.getIssueById(1));
		assertNotNull(manager.getIssueById(3));
		assertNotNull(manager.getIssueById(7));
		assertNotNull(manager.getIssueById(14));
		assertNotNull(manager.getIssueById(15));
		
		
		
		
	}

	/**
	 * Tests the createNewIssueList by adding issues to the list, and then removing all issues.
	 */
	@Test
	void testCreateNewIssueList() {
		
		IssueManager manager = IssueManager.getInstance();
		manager.loadIssuesFromFile("test-files/issue1.txt");
		
		assertEquals(5, manager.getIssueListAsArray().length);
		
		manager.createNewIssueList();
		
		assertEquals(0, manager.getIssueListAsArray().length);
		
	}

	/**
	 * Tests the getIssueListAsArrayByIssueType method by loading issues from a file, and ensuring
	 * the correct amount of enhancements and bugs were added. Also tests for invalid issue types.
	 */
	@Test
	void testGetIssueListAsArrayByIssueType() {
		
		IssueManager manager = IssueManager.getInstance();
		
		manager.loadIssuesFromFile("test-files/issue1.txt");
		
		assertEquals(2, manager.getIssueListAsArrayByIssueType("Bug").length);
		
		assertEquals(3, manager.getIssueListAsArrayByIssueType("Enhancement").length);
		
		assertEquals(0, manager.getIssueListAsArrayByIssueType("trash").length);
		
		manager.loadIssuesFromFile("test-files/exp_issue_closed.txt");
		
		assertEquals(1, manager.getIssueListAsArrayByIssueType("Bug").length);
		

		
	}

	/**
	 * Tests the getIssueListAsArray method by loading issues from a file, and checking if the 
	 * getIssueListAsArray returns an issue's fields in the correct format by checking the placement
	 * of id, state, type, and summary.
	 */
	@Test
	void testGetIssueListAsArray() {
		
		IssueManager manager = IssueManager.getInstance();
		
		manager.loadIssuesFromFile("test-files/issue1.txt");
		
		assertEquals(5, manager.getIssueListAsArray().length);
		
		Object[] issue = manager.getIssueListAsArray()[0];
		
		
		assertEquals(1, (int)issue[0]);
		assertEquals("New", (String)issue[1]);
		assertEquals("Enhancement", (String)issue[2]);
		assertEquals("Issue description", (String) issue[3]);
		
	}

	/**
	 * Tests the getIssueById method by retrieving valid issue id's and invalid issue id's
	 * and asserting null.
	 */
	@Test
	void testGetIssueById() {
		IssueManager manager = IssueManager.getInstance();
		
		manager.loadIssuesFromFile("test-files/issue1.txt");
		
		Issue issue = manager.getIssueById(1);
		
		assertEquals(1, issue.getIssueId());
		assertEquals("New", issue.getStateName());
		assertEquals("Enhancement", issue.getIssueType());
		assertEquals("Issue description", issue.getSummary());
		
		assertNull(manager.getIssueById(99));
		
	}

	
	/**
	 * Tests the executeCommand method by changing an issue from the new to working state
	 * and comparing field values.
	 */
	@Test
	void testExecuteCommand() {
		
		
		
		IssueManager manager = IssueManager.getInstance();
		
		manager.loadIssuesFromFile("test-files/issue1.txt");
		
		Command command = new Command(Command.CommandValue.ASSIGN, "owner", null, "Assigend an owner");
		
		manager.executeCommand(1, command);
		
		Issue rsltIssue = manager.getIssueById(1);
		
		assertEquals(1, rsltIssue.getIssueId());
		assertEquals("owner", rsltIssue.getOwner());
		assertEquals(Issue.WORKING_NAME, rsltIssue.getStateName());
		assertNull(rsltIssue.getResolution());
	}

	/**
	 * Tests the deleteIssueById method by attempting to delete existent and nonexistent issues.
	 */
	@Test
	void testDeleteIssueById() {
		
		IssueManager manager = IssueManager.getInstance();
		
		manager.loadIssuesFromFile("test-files/issue1.txt");
		
		assertEquals(5, manager.getIssueListAsArray().length);
		
		manager.deleteIssueById(1);
		
		assertEquals(4, manager.getIssueListAsArray().length);
		
		manager.deleteIssueById(90);
		
		assertEquals(4, manager.getIssueListAsArray().length);
		
	}

	/**
	 * Tests the addIssueToList method by constructing an issue and adding it to the list.
	 */
	@Test
	void testAddIssueToList() {
		
		IssueManager manager = IssueManager.getInstance();
		
		manager.addIssueToList(IssueType.BUG, "Found a problem", "Problem");
		
		assertNotNull(manager.getIssueById(1));
		
		
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
			 Scanner actScanner = new Scanner(new File(actFile));) {
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}
