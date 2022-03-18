package edu.ncsu.csc216.issue_manager.model.issue;

import java.util.ArrayList;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;

/**
 * Issue represents an issue managed by the system. The issue fields include an issue id, summary
 * owner, confirmed status, resolution, notes, state, and issueType. Issue contains a FSM state pattern
 * used for implementing different behaviors for the issue given a state status. Has inner classes and interfaces such 
 * as IssueState, NewState, WorkingState, ConfirmedState, VerifyingState, and ClosedState. Issue controls the life cycle
 * of all these classes and interfaces. Issue also stores static constants for the string representation of an enhancement,
 * bug, and all states.
 * 
 * @author michaelabrams
 *
 */
public class Issue {
	
	/** I_ENHANCEMENT is the string representation for issues of type enhancement. */
	public static final String I_ENHANCEMENT = "Enhancement";
	
	/** I_BUG is the string representation for issues of type bug. */
	public static final String I_BUG = "Bug";
	
	/** NEW_NAME is the string representation for issues in the new state */
	public static final String NEW_NAME = "New";
	
	/** WORKING_NAME is the string representation for issues in the working state */
	public static final String WORKING_NAME = "Working";
	
	/** CONFIRMED_NAME is the string representation for issues in the confirmed state */
	public static final String CONFIRMED_NAME = "Confirmed";
	
	/** VERIFYING_NAME is the string representation for issues in the verifying state */
	public static final String VERIFYING_NAME  = "Verifying";
	
	/** CLOSED_NAME is the string representation for issues in the closed state */
	public static final String CLOSED_NAME = "Closed";
	
	/** issueId is a unique issue id for an issue */
	private int issueId;
	
	/** summary is the issue’s summary information from when the issue was created */
	private String summary;
	
	/** owner is the user id of the issue owner or null if there is not an assigned owner */
	private String owner;
	
	/** confirmed represents if the issue has been confirmed or not. Only can be true if issue is a bug. */
	private boolean confirmed;
	
	/** resolution is the issue's resolution and will be null if there is no resolution */
	private Resolution resolution;
	
	/** notes is an array list of all notes for the issue */
	private	ArrayList<String> notes;
	
	/** state is the current state for the issue */
	private IssueState state;
	
	/** issueType is the type of issue either IssueType.ENHANCEMENT or IssueType.BUG */
	private IssueType issueType;
	
	/** newState is the state of the issue if it were in the new state */
	private final NewState newState;
	
	/** workingState is the state of the issue if it were in the working state */
	private final WorkingState workingState;
	
	/** confirmedState is the state of the issue if it were in the confirmed state */
	private final ConfirmedState confirmedState;
	
	/** verifyingState is the state of the issue if it were in the verifying state */
	private final VerifyingState verifyingState;
	
	/** closedState is the state of the issue if it were in the closed state */
	private final ClosedState closedState;
	
	/**
	 * IssueType contains the two different types an issue can be: either bug or enhancement.
	 * 
	 * @author michaelabrams
	 *
	 */
	public enum IssueType { ENHANCEMENT, BUG }
	
	/**
	 * The constructor for the issue class which takes an id, issueType, summary, and a note.
	 * 
	 * @param id is the id of the issue
	 * @param issueType is the issueType of the issue
	 * @param summary is the summary of the issue
	 * @param note is the note of the issue
	 * 
	 * @throws IllegalArgumentException if any of the parameters are null/empty strings, the id is less
	 * than 1, or if isValid issues returns false. isValid will return false if the issue has incorrect fields
	 * given it's current state/type.
	 */
	public Issue(int id, IssueType issueType, String summary, String note) {
		
		setIssueId(id);
		
		setIssueType(issueType);
		
		setConfirmed(false);
		
		setResolution("");
		
		setOwner(null);
		
		setSummary(summary);
		
		newState = new NewState();
		workingState = new WorkingState();
		confirmedState = new ConfirmedState();
		verifyingState = new VerifyingState();
		closedState = new ClosedState();
		
		setState(NEW_NAME);
		
		setNotes(note);
		
		if(!isValidIssue()) {
			throw new IllegalArgumentException("Issue cannot be created");
		}
		
	}
	
	/**
	 * The constructor for the issue class which takes an id, issueType, summary, and a note.
	 * 
	 * @param id is the id of the issue
	 * @param issueType is the issueType of the issue
	 * @param summary is the summary of the issue
	 * @param note is the note of the issue
	 * @param owner is the owner of the issue
	 * @param state is the current state of the issue
	 * @param confirmed is if the issue is currently confirmed or not
	 * @param resolution is the resolution of the issue
	 * 
	 * @throws IllegalArgumentException if any of the parameters are null/empty strings or the id is less
	 * than 1, or if isValid issues returns false. isValid will return false if the issue has incorrect fields
	 * given it's current state/type.
	 */
	public Issue(int id, String state, String issueType, String summary, String owner, 
			boolean confirmed, String resolution, ArrayList<String> note) {
		
		this.setIssueType(issueType);
		
		this.setConfirmed(confirmed);
		
		this.setResolution(resolution);
		
		this.setOwner(owner);
		
		this.setIssueId(id);
		
		this.setSummary(summary);
		
		this.newState = new NewState();
		this.workingState = new WorkingState();
		this.closedState = new ClosedState();
		this.confirmedState = new ConfirmedState();
		this.verifyingState = new VerifyingState();
		
		this.setState(state);
		
		if(!isValidIssue()) {
			throw new IllegalArgumentException("Issue cannot be created");
		}
		
		this.setNotes(note);
		
	}

	/**
	 * Gets the issue id
	 * 
	 * @return the issueId
	 */
	public int getIssueId() {
		return issueId;
	}

	/**
	 * Sets the issue id
	 * 
	 * @param issueId the issueId to set
	 * 
	 * @throws IllegalArgumentException if the issueId is less than one.
	 */
	private void setIssueId(int issueId) {
		
		//Checks to see if id is less than one
		if(issueId < 1) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		
		this.issueId = issueId;
	}

	/**
	 * Gets the summary 
	 * 
	 * @return the summary
	 */
	public String getSummary() {
		
		return summary;
	}

	/**
	 * Sets the summary
	 * 
	 * @param summary the summary to set
	 * 
	 * @throws IllegalArgumentException if the summary is null or empty
	 */
	private void setSummary(String summary) {
		
		//Checks to see if the summary is null or empty
		if(summary == null || "".equals(summary)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		else {
			this.summary = summary;
		}
	}

	/**
	 * Gets the owner
	 * 
	 * @return the owner or an empty string if owner is null
	 */
	public String getOwner() {
		
		return owner;
	}

	/**
	 * Sets the owner
	 * 
	 * @param owner the owner to set
	 * 
	 */
	private void setOwner(String owner) {
		
		if(owner == null || "".equals(owner)) {
			this.owner = null;
		}
		else {
			this.owner = owner;
		}
	}

	/**
	 * Gets the confirmed status
	 * 
	 * @return the confirmed field
	 */
	public boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * Sets the confirmed status
	 * 
	 * @param confirmed the confirmed to set
	 */
	private void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * Gets the resolution in a string format using the static final constants
	 * in the Command class. If the resolution is null, the resolution will be returned as null.
	 * 
	 * @return the resolution in a string representation, or null if the resolution is null
	 *
	 */
	public String getResolution() {
		
		if(resolution == null) {
			return null;
		}
		else {
			switch(resolution) {
			case DUPLICATE:
				return Command.R_DUPLICATE;
			case FIXED:
				return Command.R_FIXED;
			case WONTFIX:
				return Command.R_WONTFIX;
			case WORKSFORME:
				return Command.R_WORKSFORME;
			default:
				return null;
			}
		}
	}

	/**
	 * Sets the resolution
	 * 
	 * @param resolution is the resolution to set
	 * 
	 * @throws IllegalArgumentException if the resolution is not one of the possible resolutions for an issue.
	 */
	private void setResolution(String resolution) {
		
		if(resolution == null || "".equals(resolution)) {
			this.resolution = null;
		}
		else {
			switch (resolution) {
			case Command.R_DUPLICATE:
				this.resolution = Command.Resolution.DUPLICATE;
				break;
			case Command.R_FIXED:
				this.resolution = Command.Resolution.FIXED;
				break;
			case Command.R_WONTFIX:
				this.resolution = Command.Resolution.WONTFIX;
				break;
			case Command.R_WORKSFORME:
				this.resolution = Command.Resolution.WORKSFORME;
				break;
			default:
				throw new IllegalArgumentException("Issue cannot be created.");
			}
		}
	}
	
	/**
	 * Sets the resolution of the issue
	 * 
	 * @param resolution is the resolution to set for the issue
	 */
	private void setResolution(Resolution resolution) {
		this.resolution = resolution;
	}

	/**
	 * Gets the notes
	 * 
	 * @return the notes field
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}
	
	/**
	 * Gets the issues notes and returns all of them in a single string
	 * 
	 * @return the notes of the issue in a string
	 */
	public String getNotesString() {
		
		String s = "";
		
		for(int i = 0; i < notes.size(); i++) {
			
			s += "-";
			s += notes.get(i);
			s += "\n";
		}
		
		
		return s;
	}

	/**
	 * Sets the notes field
	 * 
	 * @param notes the notes to set
	 * 
	 * @throws IllegalArgumentException if the notes parameter is null or the note array list
	 * has a size of 0.
	 */
	private void setNotes(ArrayList<String> notes) {
		
		//Checks to see if the note is null
		if(notes == null || notes.size() == 0) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		
		
		this.notes = notes;
	}
	
	/**
	 * Sets the notes field by assigning the notes field to a new array list and appending the 
	 * passed in note parameter.
	 * 
	 * @param note is the initial note which is set for the issue
	 * 
	 * @throws IllegalArgumentException if the note parameter is null or empty.
	 */
	private void setNotes(String note) {
		
		//Checks to see if note is null or empty
		if(note == null || "".equals(note)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		
		
		//Constructs the notes array list and adds the note
		this.notes = new ArrayList<String>();
		
		this.addNote(note);
		
	}

	/**
	 * Gets the state of the issue
	 * 
	 * @return the state
	 */
	public String getStateName() {

		return state.getStateName();

	}

	/**
	 * Sets the state of the issue
	 * 
	 * @param state the state to set as a string
	 * 
	 * @throws IllegalArgumentException if the state parameter is not a valid state for an issue.
	 */
	private void setState(String state) {
		
		switch (state) {
		case NEW_NAME:
			this.state = newState;
			break;
		case WORKING_NAME:
			this.state = workingState;
			break;
		case VERIFYING_NAME:
			this.state = verifyingState;
			break;
		case CLOSED_NAME:	
			this.state = closedState;
			break;
		case CONFIRMED_NAME:		
			this.state = confirmedState;
			break;
		default:
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		

	}

	/**
	 * Gets the issue type
	 * 
	 * @return the issueType as a string representation
	 */
	public String getIssueType() {
		
		switch(issueType) {
		case ENHANCEMENT:
			return I_ENHANCEMENT;
		default:
			return I_BUG;
		
		}

	}

	/**
	 * Sets the issue type
	 * 
	 * @param issueType the issueType to set
	 * 
	 * @throws IllegalArgumentException if the issue type is null
	 */
	private void setIssueType(IssueType issueType) {
		
		if(issueType == null) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		
		this.issueType = issueType;
	}
	
	/**
	 * Sets the issue type
	 * 
	 * @param issueType is the issue type to set
	 * 
	 * @throws IllegalArgumentException if the issueType is not a valid type for an issue.
	 */
	private void setIssueType(String issueType) {
		
		if(I_BUG.equals(issueType)) {
			this.issueType = IssueType.BUG;
		}
		else if (I_ENHANCEMENT.equals(issueType)){
			this.issueType = IssueType.ENHANCEMENT;
		}
		else {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		
	}
	
	/**
	 * Adds a note onto the notes array list.
	 * 
	 * @param note is the note which is added to the notes
	 */
	private void addNote(String note) {
		
		String newNote = "";
		
		if(note != null && !"".equals(note)) {
			//Prepend the state to the note
			newNote += "[" + this.getStateName() + "] ";
			newNote += note;
			
			notes.add(newNote);
		}
		
	}
	
	/**
	 * Updates the issues state given a command
	 * 
	 * @param command is the command used to update the issue's state
	 */
	public void update(Command command) {
		state.updateState(command);
	}
	
	/**
	 * Checks for valid issue.
	 * 
	 * @return true if the issue object is valid, and false if the issue object is invalid.
	 */
	private boolean isValidIssue() {
		
		//If an enhancement has an invalid resolution
		if(issueType == IssueType.ENHANCEMENT && resolution == Resolution.WORKSFORME) {
			return false;
		}
		
		//If an issue is in the new state with a resolution
		if((getStateName().equals(NEW_NAME) || getStateName().equals(CONFIRMED_NAME)) && resolution != null) {
			return false;
		}
		
		//If an enhancement is confirmed
		if(issueType.equals(IssueType.ENHANCEMENT) && confirmed) {
			return false;
		}
		
		//If issue is in closed state without a resolution
		if(state.getStateName().equals(CLOSED_NAME) && resolution == null) {
			return false;
		}
		
		//If an issue in the verifying state has a resolution other than fixed
		if(state.getStateName().equals(VERIFYING_NAME) && (resolution == null || resolution != Resolution.FIXED)) {
				return false;
		}
		
		//If a bug is in the working state and not confirmed
		if(state.getStateName().equals(WORKING_NAME) && issueType.equals(IssueType.BUG) && !confirmed) {
				return false;
		}
		
		//If enhancement is in the confirmed state
		if(issueType.equals(IssueType.ENHANCEMENT) && state.getStateName().equals(CONFIRMED_NAME)) {
			return false;
		}
		
		//If issue is in the verifying state or closed state without a resolution
		if((state.getStateName().equals(VERIFYING_NAME) || state.getStateName().equals(CLOSED_NAME)) && resolution == null) {
				return false;
		}
		
		//If the issue is in the working or verifying state without an owner
		if((state.getStateName().equals(WORKING_NAME) || state.getStateName().equals(VERIFYING_NAME)) && owner == null) {
				return false;
		}
		
		//If the issue has an owner in the new or confirmed state
		return !((state.getStateName().equals(NEW_NAME) || state.getStateName().equals(CONFIRMED_NAME)) && (owner != null || "".equals(owner)));
	}
	
	/**
	 * Returns the Issue object in a string format
	 * 
	 * @return the string representation of an issue.
	 */
	@Override
	public String toString() {
		
		String s = "";
		
		String resolutionString = "";

		if(this.getResolution() != null) {
			resolutionString = this.getResolution();
		}

		s += "*";
		s += this.getIssueId() + ",";
		s += this.getStateName() + ",";
		s += this.getIssueType() + ",";
		s += this.getSummary() + ",";
		s += this.getOwner() + ",";
		s += this.isConfirmed() + ",";
		s += resolutionString + "\n";
		s += this.getNotesString();

		
		return s;
		
	}

	/**
	 * Interface for states in the Issue State Pattern.  All 
	 * concrete issue states must implement the IssueState interface.
	 * The IssueState interface should be a private interface of the 
	 * Issue class.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu) 
	 */
	private interface IssueState {
		
		/**
		 * Update the Issue based on the given Command.
		 * An UnsupportedOperationException is throw if the Command
		 * is not a valid action for the given state.  
		 * @param command Command describing the action that will update the Issue's
		 * state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 * for the given state.
		 */
		void updateState(Command command);
		
		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		String getStateName();

	}
	
	/**
	 * NewState holds the functionality for the Issue in the NewState. You can
	 * get the state's name or update the state based on a command.
	 * 
	 * @author michaelabrams
	 *
	 */
	private class NewState implements IssueState {
		
		/**
		 * Constructs the NewState class with no parameters.
		 */
		private NewState() {
			
		}
		
		/**
		 * Updates the issue's state based on the given command. 
		 * 
		 * @param command is the command given to the issue in order to update
		 * 
		 * @throws UnsupportedOperationException if the command is not supported for the new state
		 */
		public void updateState(Command command) {
			
			CommandValue commandValue = command.getCommand();
			
			switch (commandValue) {
			case ASSIGN:
				
				if(issueType == IssueType.ENHANCEMENT) {
					setOwner(command.getOwnerId());
					state = workingState;
					setResolution("");
				}
				else {
					throw new UnsupportedOperationException("Invalid information.");
				}
				
				break;
			case RESOLVE:
				
				if(I_ENHANCEMENT.equals(getIssueType()) && command.getResolution() == Command.Resolution.WORKSFORME) {
					throw new UnsupportedOperationException("Invalid information.");
				}
				
				if(command.getResolution() == Command.Resolution.FIXED) {
					throw new UnsupportedOperationException("Invalid information.");
				}
				
				setResolution(command.getResolution());
				state = closedState;
				break;
			case CONFIRM:
				
				if(issueType == IssueType.BUG) {
					setConfirmed(true);
					state = confirmedState;
				}
				else {
					throw new UnsupportedOperationException("Invalid information.");
				}
		
				break;
			default:			
				throw new UnsupportedOperationException("Invalid information.");
			}
			
			//Adds the command's note to the issue
			
			addNote(command.getNote());
			
		}
		
		/**
		 * Returns the name of the state.
		 * 
		 * @return the name of the state in string representation
		 */
		public String getStateName() {
			return NEW_NAME;
		}
		
	}
	
	/**
	 * WorkingState holds the functionality for the Issue in the WorkingState. You can
	 * get the state's name or update the state based on a command.
	 * 
	 * @author michaelabrams
	 *
	 */
	private class WorkingState implements IssueState {
		
		/**
		 * Constructs the WorkingState class with no parameters.
		 */
		private WorkingState() {
			
		}
		
		/**
		 * Updates the issue's state based on the given command. 
		 * 
		 * @param command is the command given to the issue in order to update
		 * 
		 * @throws UnsupportedOperationException if the command is not supported for the working state.
		 */
		public void updateState(Command command) {

			CommandValue commandValue = command.getCommand();
			
			switch (commandValue) {
			case RESOLVE:
				
				if(command.getResolution() == Command.Resolution.FIXED) {
					setResolution(command.getResolution());
					state = verifyingState;
				}
				else if(getIssueType().equals(I_BUG)) {
					setResolution(command.getResolution());
					state = closedState;
				}
				else {
					
					//The Resolution can not be a works for me for the enhancement state
					if(command.getResolution() == Command.Resolution.WORKSFORME) {
						throw new UnsupportedOperationException("Invalid information.");
					}
					
					setResolution(command.getResolution());
					state = closedState;
					
				}
				
				break;
			default:
				throw new UnsupportedOperationException("Invalid information.");
			}
			
			
			//Adds the command's notes to the issue
			
			addNote(command.getNote());
			
		}
		
		/**
		 * Returns the name of the state.
		 * 
		 * @return the name of the state in string representation
		 */
		public String getStateName() {
			return WORKING_NAME;
		}
		
	}
	
	/**
	 * ConfirmedState holds the functionality for the Issue in the ConfirmedState. You can
	 * get the state's name or update the state based on a command.
	 * 
	 * @author michaelabrams
	 *
	 */
	private class ConfirmedState implements IssueState {
		
		/**
		 * Constructs the ConfirmedState class with no parameters.
		 */
		private ConfirmedState() {
			
		}
		
		/**
		 * Updates the issue's state based on the given command. 
		 * 
		 * @param command is the command given to the issue in order to update
		 * 
		 * @throws UnsupportedOperationException if the command is not supported for the working state.
		 */
		public void updateState(Command command) {
			CommandValue commandValue = command.getCommand();
			
			switch (commandValue) {
			case ASSIGN:
				
				setOwner(command.getOwnerId());
				state = workingState;
				setResolution("");
				
				break;
			case RESOLVE:
				
				if(command.getResolution() == Command.Resolution.WONTFIX) {
					setResolution(command.getResolution());
					state = closedState;
				}
				else {
					throw new UnsupportedOperationException("Invalid information.");
				}
				
				break;
			default:
				throw new UnsupportedOperationException("Invalid information.");
				
			}
			
			
			//Adds the command's notes to the issue
			
			addNote(command.getNote());
		}
		
		/**
		 * Returns the name of the state.
		 * 
		 * @return the name of the state in string representation
		 */
		public String getStateName() {
			return CONFIRMED_NAME;
		}
	}
	
	/**
	 * VerifyingState holds the functionality for the Issue in the VerifyingState. You can
	 * get the state's name or update the state based on a command.
	 * 
	 * @author michaelabrams
	 *
	 */
	private class VerifyingState implements IssueState {
		
		/**
		 * Constructs the VerifyingState class with no parameters.
		 */
		private VerifyingState() {
			
		}
		
		/**
		 * Updates the issue's state based on the given command. 
		 * 
		 * @param command is the command given to the issue in order to update
		 * 
		 * @throws UnsupportedOperationException if the command is not supported for the working state.
		 */
		public void updateState(Command command) {
			
			CommandValue commandValue = command.getCommand();
			
			switch (commandValue) {
			case REOPEN:
				
				state = workingState;
				setResolution("");
				
				break;
			case VERIFY:
				
				state = closedState;
				
				break;
			default:
				throw new UnsupportedOperationException("Invalid information.");
			}
			
			//Adds the command's notes to the issue
			addNote(command.getNote());
			
		}
		
		/**
		 * Returns the name of the state.
		 * 
		 * @return the name of the state in string representation
		 */
		public String getStateName() {
			return VERIFYING_NAME;
		}
	}
	
	/**
	 * ClosedState holds the functionality for the Issue in the ClosedState. You can
	 * get the state's name or update the state based on a command.
	 * 
	 * @author michaelabrams
	 *
	 */
	private class ClosedState implements IssueState {
		
		/**
		 * Constructs the ClosedState class with no parameters.
		 */
		private ClosedState() {
			
		}
		
		/**
		 * Updates the issue's state based on the given command. 
		 * 
		 * @param command is the command given to the issue in order to update
		 * 
		 * @throws UnsupportedOperationException if the command is not supported for the working state.
		 */
		public void updateState(Command command) {
			
			if(command.getCommand() == CommandValue.REOPEN) {
				
				//If the issue is an enhancement with an owner
				if(getIssueType().equals(I_ENHANCEMENT) && getOwner() != null && !getOwner().isEmpty()) {
					state = workingState;
					setResolution("");
				}
				
				//If the issue is a bug, confirmed, and has an owner
				else if(getIssueType().equals(I_BUG) && isConfirmed() && getOwner() != null && !getOwner().isEmpty()) {
					state = workingState;
					setResolution("");
				}
				
				//If the issue is a bug, confirmed, and does not have an owner
				else if(getIssueType().equals(I_BUG) && isConfirmed() && (getOwner() == null || getOwner().isEmpty())) {
					state = confirmedState;
					setResolution("");
				}
				
				else if(getOwner() == null || getOwner().isEmpty()) {
					state = newState;
					setResolution("");
				}
				
				else {
					throw new UnsupportedOperationException("Invalid information.");
				}
				
			}
			else {
				throw new UnsupportedOperationException("Invalid information.");
			}
			
			//Adds the command's notes to the issue
			addNote(command.getNote());
			
			
			
		}
		
		/**
		 * Returns the name of the state.
		 * 
		 * @return the name of the state in string representation
		 */
		public String getStateName() {
			return CLOSED_NAME;
		}
	}
}
