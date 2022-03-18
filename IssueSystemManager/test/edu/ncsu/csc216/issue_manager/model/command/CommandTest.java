package edu.ncsu.csc216.issue_manager.model.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;

/**
 * Tests the Command Class
 * 
 * @author michaelabrams
 *
 */
class CommandTest {

	/**
	 * Test the Command's instructor for a valid command, and all cases where Command should throw an IllegalArgumentException.
	 */
	@Test
	void testCommand() {
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> {
			new Command(null, "owner", Resolution.FIXED, "First note");
		});

		assertEquals("Invalid information.", e1.getMessage());

		Exception e2 = assertThrows(IllegalArgumentException.class, () -> {
			new Command(CommandValue.ASSIGN, "", Resolution.FIXED, "First note");
		});

		assertEquals("Invalid information.", e2.getMessage());

		Exception e3 = assertThrows(IllegalArgumentException.class, () -> {
			new Command(CommandValue.ASSIGN, null, Resolution.FIXED, "First note");
		});

		assertEquals("Invalid information.", e3.getMessage());

		Exception e4 = assertThrows(IllegalArgumentException.class, () -> {
			new Command(CommandValue.RESOLVE, "owner", null, "First note");
		});

		assertEquals("Invalid information.", e4.getMessage());

		Exception e5 = assertThrows(IllegalArgumentException.class, () -> {
			new Command(CommandValue.ASSIGN, "owner", Resolution.WORKSFORME, null);
		});

		assertEquals("Invalid information.", e5.getMessage());
		
		Command c = new Command(CommandValue.ASSIGN, "owner", Resolution.WORKSFORME, "First note");
		
		assertEquals(CommandValue.ASSIGN, c.getCommand());
		assertEquals("owner", c.getOwnerId());
		assertEquals(Resolution.WORKSFORME, c.getResolution());
		assertEquals("First note", c.getNote());
		
	}

	/**
	 * Test method for getCommand.
	 */
	@Test
	void testGetCommand() {
		Command c = new Command(CommandValue.ASSIGN, "owner", Resolution.WORKSFORME, "First note");
		
		assertEquals(CommandValue.ASSIGN, c.getCommand());
	}

	/**
	 * Test method for getOwnerId.
	 */
	@Test
	void testGetOwnerId() {
		Command c = new Command(CommandValue.ASSIGN, "owner", Resolution.WORKSFORME, "First note");
		
		assertEquals("owner", c.getOwnerId());
	}

	/**
	 * Test method for getResolution.
	 */
	@Test
	void testGetResolution() {
		Command c = new Command(CommandValue.ASSIGN, "owner", Resolution.WORKSFORME, "First note");
		
		assertEquals(Resolution.WORKSFORME, c.getResolution());
	}

	/**
	 * Test method for getNote.
	 */
	@Test
	void testGetNote() {
		Command c = new Command(CommandValue.ASSIGN, "owner", Resolution.WORKSFORME, "First note");
		
		assertEquals("First note", c.getNote());
	}

}




