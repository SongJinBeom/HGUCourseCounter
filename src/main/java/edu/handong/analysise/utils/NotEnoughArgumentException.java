package edu.handong.analysise.utils;

public class NotEnoughArgumentException extends Exception {

	public NotEnoughArgumentException() {
		super("No CLI argument Exception! Please put a file path.");
	}

	NotEnoughArgumentException(String message) {
		System.out.println(message);
		System.exit(0);
	}
}
