package org.fetch.exercise.exception;

public class ReceiptNotFoundException extends Exception {

    public ReceiptNotFoundException( final String id) {
        super(String.format("Receipt does not exist with id: %s", id));
    }
}
