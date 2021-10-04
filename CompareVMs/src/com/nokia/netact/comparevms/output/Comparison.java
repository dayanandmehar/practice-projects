/**
 * 
 */
package com.nokia.netact.comparevms.output;

/**
 * @author dmehar
 *
 */
public class Comparison {

	private String attribute;
	private String matchSatus;
	private String comment;

	public Comparison() {}

	public Comparison(String attribute, String matchSatus, String comment) {
		super();
		this.attribute = attribute;
		this.matchSatus = matchSatus;
		this.comment = comment;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getMatchSatus() {
		return matchSatus;
	}

	public void setMatchSatus(String matchSatus) {
		this.matchSatus = matchSatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
