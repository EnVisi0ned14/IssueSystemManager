/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.io;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * Tests the IssueReader class
 * 
 * @author michaelabrams
 *
 */
class IssueReaderTest {
	
	
	/**
	 * Tests the constructor for the issue reader class
	 */
	@Test
	void testIssueReader() {
		assertNotNull(new IssueReader());
	}

	/**
	 * Tests the readIssueFromFile method.
	 */
	@Test
	void testReadIssuesFromFile() {

		ArrayList<Issue> issues = IssueReader.readIssuesFromFile("test-files/issue1.txt");
		
		assertEquals(5, issues.size());
		
		Issue i1 = issues.get(0);
		
		String expectedNote = "-[New] Note 1\n";
		
		assertEquals(1, i1.getIssueId());
		assertEquals("New", i1.getStateName());
		assertEquals("Enhancement", i1.getIssueType());
		assertEquals("Issue description", i1.getSummary());
		assertNull(i1.getOwner());
		assertFalse(i1.isConfirmed());
		assertEquals(expectedNote, i1.getNotesString());
		
		Issue i2 = issues.get(1);
		
		String expectedNote2 = "-[New] Note 1\n";
		expectedNote2 += "-[Confirmed] Note 2\n";
		expectedNote2 += "that goes on a new line\n";
		
		assertEquals(3, i2.getIssueId());
		assertEquals("Confirmed", i2.getStateName());
		assertEquals("Bug", i2.getIssueType());
		assertEquals("Issue description", i2.getSummary());
		assertNull(i2.getOwner());
		assertTrue(i2.isConfirmed());
		assertEquals(expectedNote2, i2.getNotesString());
		

		
	}

}
