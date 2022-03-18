/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * Tests the IssueWriter class
 * 
 * @author michaelabrams
 *
 */
class IssueWriterTest {
	

	/**
	 * Tests the construction of the IssueWriter class
	 */
	@Test
	void testIssueWriter() {
		assertNotNull(new IssueWriter());
	}
	
	/**
	 * Tests the testWriteIssuesToFile method.
	 */
	@Test
	void testWriteIssuesToFile() {
		
		
		List<Issue> issues = IssueReader.readIssuesFromFile("test-files/issue1.txt");
		
		assertEquals(5, issues.size());
		
		IssueWriter.writeIssuesToFile("test-files/output.txt", issues);
			
		checkFiles("test-files/writer_test.txt", "test-files/output.txt");
			
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
