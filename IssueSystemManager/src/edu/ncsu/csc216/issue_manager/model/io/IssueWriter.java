package edu.ncsu.csc216.issue_manager.model.io;


import java.io.File;
import java.io.PrintStream;
import java.util.List;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;


/**
 * IssueWriter is the class used to write issues to an output file. All the methods
 * are static and is used by the IssueManager class.
 * 
 * @author michaelabrams
 *
 */
public class IssueWriter {

	/**
	 * Constructs an IssueWriter object with no parameters
	 *
	 */
	public IssueWriter() {
		//Empty constructor
	}
	
	/**
	 * Parses issue objects into an output file.
	 * 
	 * @param file is the file which issues are written to
	 * @param issues are the issues which are written in the output file
	 * 
	 * @throws IllegalArgumentException if the file is not able to be saved.
	 */
	public static void writeIssuesToFile(String file, List<Issue> issues) {
		
		try {
			PrintStream fileWriter = new PrintStream(new File(file));
			
			for (Issue i : issues) {
				fileWriter.print(i.toString());
			}
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
 
		
	}
	
}
