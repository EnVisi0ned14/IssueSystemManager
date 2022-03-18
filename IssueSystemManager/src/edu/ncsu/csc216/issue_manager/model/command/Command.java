package edu.ncsu.csc216.issue_manager.model.command;

/**
 * The command class represents the commands which can be given to issues to update their state/fields. Command includes 
 * fields such as note, ownerId, command values, and resolutions. Only getters are available for all
 * fields.
 * 
 * @author michaelabrams
 *
 */
public class Command {

	/** String representation of the fixed resolution for issues */
	public static final String R_FIXED = "Fixed";
	
	/** String representation of the duplicate resolution for issues */
	public static final String R_DUPLICATE = "Duplicate";
	
	/** String representation of the wont fix resolution for issues */
	public static final String R_WONTFIX = "WontFix";
	
	/** String representation of the works for me resolution for issues */
	public static final String R_WORKSFORME = "WorksForMe";
	
	/** Note is the note of the command */
	private String note;
	
	/** ownerId is the owner id of the command */
	private String ownerId;
	
	/** c is the the command value for the Command object */
	private CommandValue c;
	
	/** resolution is the command resolution for the Command object */
	private Resolution resolution;
	
	/**
	 * CommandValue contains the possible commands a user can make for the Issue Manager FSM
	 * 
	 * @author michaelabrams
	 *
	 */
	public enum CommandValue { ASSIGN, CONFIRM, RESOLVE, VERIFY, REOPEN };
	
	/**
	 * Resolution contains the possible ways a user can resolve an issue 
	 * 
	 * @author michaelabrams
	 *
	 */
	public enum Resolution { FIXED, DUPLICATE, WONTFIX, WORKSFORME };
	
	/**
	 * Constructs a command object with a given command, owner id, resolution, and note
	 * 
	 * @param command is the command value
	 * @param ownerId is the owner id of the command
	 * @param resolution is the resolution of the command
	 * @param note is the note for the command
	 * 
	 * @throws IllegalArgumentException if the command parameter is null, the command parameter is ASSIGN with an
	 * null/empty owner id, command value of RESOLVE with a null resolution, or with a command with a null or empty
	 * note parameter.
	 */
	public Command(CommandValue command, String ownerId, Resolution resolution, String note) {
		
		//Checks to see if the command is null
		if(command == null) {
			throw new IllegalArgumentException("Invalid information.");
		}
		
		//If the command is an ASSIGN command, command needs an owner id, if doesn't have one throw an exception
		if(command == CommandValue.ASSIGN && (ownerId == null || "".equals(ownerId))) {
				throw new IllegalArgumentException("Invalid information.");
		}
		
		//If the command is a RESOLVE command and the resolution is null
		if(command == CommandValue.RESOLVE && resolution == null) {
			throw new IllegalArgumentException("Invalid information.");
		}
		
		//Checks to see if the note is null or empty
		if(note == null || "".equals(note)) {
			throw new IllegalArgumentException("Invalid information.");
		}
		
		//All checks were passed and constructs the Command object
		
		this.note = note;
		
		this.ownerId = ownerId;
		
		this.c = command;
		
		this.resolution = resolution;
		
	}
	
	/**
	 * Gets the command value
	 * 
	 * @return the command
	 */
	public CommandValue getCommand() {
		return this.c;
	}
	
	/**
	 * Gets the owner id
	 * 
	 * @return the owner id
	 */
	public String getOwnerId() {
		return this.ownerId;
	}
	
	/**
	 * Gets the resolution
	 * 
	 * @return the resolution
	 */
	public Resolution getResolution() {
		return this.resolution;
	}
	
	/**
	 * Gets the note
	 *  
	 * @return the note
	 */
	public String getNote() {
		return this.note;
	}
	
}
