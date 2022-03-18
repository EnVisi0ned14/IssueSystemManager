/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.issue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * Tests the Issue class
 * 
 * @author michaelabrams
 *
 */
class IssueTest {

	/** validIssue is a valid issue which is used for testing getters in the Issue class */
	Issue validIssue;
	
	/** validIssue2 is a valid issue where the owner is not null */
	Issue validIssue2;
	
	/** validIssue3 is a valid issue which does not have a null resolution */
	Issue validIssue3;
	
	/** validIssue4 is a valid issue constructed in the new state */
	Issue validIssue4;
	
	
	/**
	 * Constructs an issue object used for testing
	 */
	@BeforeEach
	public void setUp() {
		
		ArrayList<String> notes = new ArrayList<String>();
		
		notes.add("First note");
		notes.add("Second note");
		notes.add("This is a very very very very long note");

		validIssue = new Issue(5, "New", "Bug", "Found a problem", "", false, null, notes);
		
		validIssue2 = new Issue(6, "Working", "Bug", "Found a problem", "owner", true, null, notes);
		
		validIssue3 = new Issue(7, "Closed", "Enhancement", "Found a problem", "owner", false, "WontFix", notes);
		
		validIssue4 = new Issue(8, IssueType.ENHANCEMENT, "Needs improvement", "Improve stuff");
	}
	
	/**
	 * Tests the shorter Issue's constructor for valid and null/empty parameters.
	 */
	@Test
	void testIssueShorterConstruction() {
		
		//Creates an issue with an id less than 1
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
			new Issue(0, IssueType.BUG, "Major bug", "First note");
		});
		
		assertEquals("Issue cannot be created.", e1.getMessage());
		
		//Creates an issue with a null IssueType
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> {
			new Issue(1, null, "Major bug", "First note");
		});
		
		assertEquals("Issue cannot be created.", e2.getMessage());
		
		//Creates an issue with a null Summary
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> {
			new Issue(1, IssueType.ENHANCEMENT, null, "First note");
		});
		
		assertEquals("Issue cannot be created.", e3.getMessage());
		
		//Creates an issue with a null note
		Exception e4 = assertThrows(IllegalArgumentException.class, () -> {
			new Issue(1, IssueType.ENHANCEMENT, "Needs improvement", null);
		});
		
		assertEquals("Issue cannot be created.", e4.getMessage());
		
		Issue i = new Issue(1, IssueType.ENHANCEMENT, "Needs improvement", "Improve stuff");
		
		assertNotNull(i);
		
	
		
	}
	
	/**
	 * Tests the longer Issue's constructor for valid and invalid parameters.
	 */
	@Test
	void testIssueLongerConstruction() {
		
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("First note");
		
		//Creates an issue with an id less than 1
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
			new Issue(0, "Confirmed", "Bug", "We have a problem", null, true, null, notes);
		});
		
		assertEquals("Issue cannot be created.", e1.getMessage());
		
		//Creates an issue with not a valid state
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> {
			new Issue(1, "NotValid", "Bug", "We have a problem", null, true, null, notes);
		});
		
		assertEquals("Issue cannot be created.", e2.getMessage());
		
		//Creates an issue with not a valid IssueType
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> {
			new Issue(1, "New", "NotValid", "We have a problem", null, true, null, notes);
		});
		
		assertEquals("Issue cannot be created.", e3.getMessage());
		
		//Creates an issue with an empty summary
		Exception e4 = assertThrows(IllegalArgumentException.class, () -> {
			new Issue(1, "Verifying", "Bug", "", null, true, null, notes);
		});
		
		assertEquals("Issue cannot be created.", e4.getMessage());
		
		//Creates an issue with an invalid resolution
		Exception e5 = assertThrows(IllegalArgumentException.class, () -> {
			new Issue(1, "Closed", "Bug", "We have a problem", null, true, "Invalid Resolution", notes);
		});
		
		assertEquals("Issue cannot be created.", e5.getMessage());
		
		//Creates an issue with notes being null
		Exception e6 = assertThrows(IllegalArgumentException.class, () -> {
			new Issue(1, "Confirmed", "Bug", "", null, true, null, null);
		});
		
		assertEquals("Issue cannot be created.", e6.getMessage());
		
		//Creates an issue with an empty summary
		Issue i = new Issue(1, "Closed", "Bug", "We got a problem", "Owner", true, "Fixed", notes);

		assertNotNull(i);
		
		
	}
	
	/**
	 * Tests the getIssueId method when constructing an issue with a valid id.
	 */
	@Test
	void testGetIssueId() {
		
		assertEquals(5, validIssue.getIssueId());
		
	}

	/**
	 * Tests the getSummary method.
	 */
	@Test
	void testGetSummary() {
		
		assertEquals("Found a problem", validIssue.getSummary());
		
	}

	/**
	 * Tests the getOwner method when the owner is null and populated
	 */
	@Test
	void testGetOwner() {
		
		assertNull(validIssue.getOwner());
		
		assertEquals("owner", validIssue2.getOwner());
		
	}

	/**
	 * Tests the isConfirmed method.
	 */
	@Test
	void testIsConfirmed() {
		
		assertFalse(validIssue.isConfirmed());
		
	}

	/**
	 * Tests the getResolution method for null and set resolutions.
	 */
	@Test
	void testGetResolution() {
		
		assertNull(validIssue.getResolution());
		
		assertEquals("WontFix", validIssue3.getResolution());
		
	}

	/**
	 * Tests the getNotes method.
	 */
	@Test
	void testGetNotes() {
		
		ArrayList<String> notes = validIssue.getNotes();
		
		assertEquals("First note", notes.get(0));
		assertEquals("Second note", notes.get(1));
		assertEquals("This is a very very very very long note", notes.get(2));
		
	}

	/**
	 * Tests the getNotesString method after both issue constructions.
	 */
	@Test
	void testGetNotesString() {
		
		String expectedString = "";
		expectedString += "-First note\n";
		expectedString += "-Second note\n";
		expectedString += "-This is a very very very very long note\n";
		
		String expectedString2 = "";
		expectedString2 += "-[New] Improve stuff\n";
		
		assertEquals(expectedString, validIssue.getNotesString());
		
		assertEquals(expectedString2, validIssue4.getNotesString());
		
	}

	/**
	 * Tests the getStateName method for closed, working, and new state.
	 */
	@Test
	void testGetStateName() {
		
		assertEquals("Closed", validIssue3.getStateName());
		
		assertEquals("Working", validIssue2.getStateName());
		
		assertEquals("New", validIssue.getStateName());
	}

	/**
	 * Tests the getIssueType method for bugs and enhancements
	 */
	@Test
	void testGetIssueType() {
		
		assertEquals("Bug", validIssue.getIssueType());
		
		assertEquals("Enhancement", validIssue3.getIssueType());
		
	}

	/**
	 * Tests the update method on an issue by trying to update an enhancement from the new state
	 * to the working state, update a bug from the confirmed state to the working state, and
	 * incorrectly attempts to reopen a bug from the confirmed state.
	 */
	@Test
	void testUpdate() {
		
		ArrayList<String> initalNotes = new ArrayList<String>();
		
		initalNotes.add("[New] First note");
		initalNotes.add("[Confirmed] Bug Confirmed");
		
		//Updates an enhancement from the new state to the working state
		validIssue4.update(new Command(CommandValue.ASSIGN, "owner", Resolution.DUPLICATE, "Update Please"));
		
		String expectedNotes = "";
		expectedNotes += "-[New] Improve stuff\n";
		expectedNotes += "-[Working] Update Please\n";
		
		assertEquals(Issue.WORKING_NAME, validIssue4.getStateName());
		assertEquals("owner", validIssue4.getOwner());
		assertNull(validIssue4.getResolution());
		assertEquals(expectedNotes, validIssue4.getNotesString());
		
		//Updates a bug from the confirmed state to the working state
		Issue i1 = new Issue(8, "Confirmed", "Bug", "Fix Now", "", true, "", initalNotes);
		
		i1.update(new Command(CommandValue.ASSIGN, "owner", Resolution.DUPLICATE, "Update Please"));
		
		String expectedNotes1 = "";
		expectedNotes1 += "-[New] First note\n";
		expectedNotes1 += "-[Confirmed] Bug Confirmed\n";
		expectedNotes1 += "-[Working] Update Please\n";
		
		assertEquals(Issue.WORKING_NAME, i1.getStateName());
		assertEquals("owner", i1.getOwner());
		assertNull(i1.getResolution());
		assertEquals(expectedNotes1, i1.getNotesString());
		
		
		//Incorrectly try to reopen a bug from the confirmed state
		
		Issue i2 = new Issue(8, "Confirmed", "Bug", "Fix Now", "", true, "", initalNotes);
		
		Exception e = assertThrows(UnsupportedOperationException.class, () -> {
			i2.update(new Command(CommandValue.REOPEN, "owner", Resolution.DUPLICATE, "Update Please"));
		});
		
		assertEquals("Invalid information.", e.getMessage());
		
		
		Issue i3 = new Issue(10, "Working", "Bug", "Fix Now", "owner", true, "", initalNotes);
		
		Command command2 = new Command(Command.CommandValue.RESOLVE, "owner", Resolution.FIXED, "Fixed");
		
		i3.update(command2);
		
		assertEquals(Issue.VERIFYING_NAME, i3.getStateName());
		
		Issue i4 = new Issue(13, "Closed", "Bug", "Fix Now", "owner", true, "Fixed", initalNotes);
		
		Command command3 = new Command(Command.CommandValue.REOPEN, "", null, "Fix");
		
		i4.update(command3);
		
		assertEquals(Issue.WORKING_NAME, i4.getStateName());
		
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
			new Issue(14, "Closed", "Bug", "Fix Now", "owner", true, "", initalNotes);
		});
		
		assertEquals("Issue cannot be created", e1.getMessage());
		
		Exception e3 = assertThrows(IllegalArgumentException.class, () -> {
			new Issue(14, "Closed", "Enhancement", "Fix Now", "owner", true, "", initalNotes);
		});
		
		assertEquals("Issue cannot be created", e3.getMessage());
		
		Exception e4 = assertThrows(IllegalArgumentException.class, () -> {
			new Issue(14, "Working", "Bug", "Fix Now", "owner", false, "", initalNotes);
		});
		
		assertEquals("Issue cannot be created", e4.getMessage());
		
		Exception e5 = assertThrows(IllegalArgumentException.class, () -> {
			new Issue(14, "Verifying", "Bug", "Fix Now", "owner", false, "", initalNotes);
		});
		
		assertEquals("Issue cannot be created", e5.getMessage());
		
		Exception e6 = assertThrows(IllegalArgumentException.class, () -> {
			new Issue(14, "Working", "Bug", "Fix Now", "", false, "", initalNotes);
		});
		
		assertEquals("Issue cannot be created", e6.getMessage());
		
		Issue i6 = new Issue(14, "New", "Bug", "Fix Now", "", false, "", initalNotes);
		
		Command command = new Command(Command.CommandValue.CONFIRM, "owner", null, "Fix");
		
		i6.update(command);
		
		assertEquals(Issue.CONFIRMED_NAME, i6.getStateName());
		
		Issue i7 = new Issue(14, "Working", "Bug", "Fix Now", "owner", true, "", initalNotes);
		
		Command command4 = new Command(Command.CommandValue.RESOLVE, "", Command.Resolution.WONTFIX, "Fix");
		
		i7.update(command4);
		
		assertEquals(Issue.CLOSED_NAME, i7.getStateName());
		
		Issue i8 = new Issue(15, "Verifying", "Enhancement", "Improve", "owner", false, "Fixed", initalNotes);
		
		Command command5 = new Command(Command.CommandValue.VERIFY, "", null, "Fix");
		
		i8.update(command5);
		
		assertEquals(Issue.CLOSED_NAME, i8.getStateName());
		
		Issue i9 = new Issue(15, "Verifying", "Enhancement", "Improve", "owner", false, "Fixed", initalNotes);
		
		Command command6 = new Command(Command.CommandValue.REOPEN, "", null, "Fix");
		
		i9.update(command6);
		
		assertEquals(Issue.WORKING_NAME, i9.getStateName());
		
		Issue i10 = new Issue(15, "Closed", "Bug", "Improve", "owner", true, "Fixed", initalNotes);
		
		Command command7 = new Command(Command.CommandValue.REOPEN, "", null, "Fix");
		
		i10.update(command7);
		
		assertEquals(Issue.WORKING_NAME, i10.getStateName());
		
		Issue i11 = new Issue(15, "Closed", "Enhancement", "Improve", "owner", false, "Fixed", initalNotes);
		
		Command command8 = new Command(Command.CommandValue.REOPEN, "", null, "Fix");
		
		i11.update(command8);
		
		assertEquals(Issue.WORKING_NAME, i10.getStateName());
		
		Issue i12 = new Issue(18, "Closed", "Bug", "Improve", null, false, "Fixed", initalNotes);
		
		Command command9 = new Command(Command.CommandValue.REOPEN, "", null, "Fix");
		
		i12.update(command9);
		
		assertEquals(Issue.NEW_NAME, i12.getStateName());
		
		Issue i13 = new Issue(20, "New", "Enhancement", "Improve", null, false, null, initalNotes);
		
		assertNotNull(i13);
		
		Command command10 = new Command(Command.CommandValue.RESOLVE, "", Command.Resolution.DUPLICATE, "note");
		
		i13.update(command10);
		
		assertEquals(Issue.CLOSED_NAME, i13.getStateName());
		
		Command command11 = new Command(Command.CommandValue.REOPEN, "", null, "note2");
		
		i13.update(command11);

		assertEquals(Issue.NEW_NAME, i13.getStateName());
		
		Issue i14 = new Issue(21, "Closed", "Bug", "Fix", null, true, "Fixed", initalNotes);
		
		i14.update(command11);
		
		assertEquals(Issue.CONFIRMED_NAME, i14.getStateName());
		
		Issue i15 = new Issue(22, "Confirmed", "Bug", "Fix", null, true, null, initalNotes);
		
		Command command12 = new Command(Command.CommandValue.RESOLVE, "", Command.Resolution.WONTFIX, "note2");
		
		i15.update(command12);
		
		assertEquals(Issue.CLOSED_NAME, i15.getStateName());
		
		Issue i16 = new Issue(23, "Working", "Enhancement", "Duplicate", "owner", false, "", initalNotes);
		
		Command command13 = new Command(Command.CommandValue.RESOLVE, "", Command.Resolution.DUPLICATE, "note2");
		
		i16.update(command13);
		
		assertEquals(Issue.CLOSED_NAME, i16.getStateName());
		
		
	}

	/**
	 * Tests the toString method.
	 */
	@Test
	void testToString() {

		String expectedString = "";
		expectedString += "*";
		expectedString += "8,New,Enhancement,Needs improvement,null,false,\n";
		expectedString += "-[New] Improve stuff\n";
		
		assertEquals(expectedString, validIssue4.toString());
		
		
	}

}
