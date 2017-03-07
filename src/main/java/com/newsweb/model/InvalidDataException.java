package com.newsweb.model;

public class InvalidDataException extends Exception {

	public InvalidDataException(String message) {
		super(message);
	}

	public InvalidDataException(Exception e) {
		super(e);
	}
}
