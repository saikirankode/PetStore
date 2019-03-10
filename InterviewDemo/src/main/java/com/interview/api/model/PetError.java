package com.interview.api.model;

public class PetError {
	  private String message;
	  public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	private String details;

	  public PetError(String message, String details) {
	    super();
	    this.message = message;
	    this.details = details;
	  }
}