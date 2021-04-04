package com.alissonpedrina.dojo.api.error;

public class DojoErrorException extends RuntimeException {

    private static final long serialVersionUID = -8675333130015922870L;

    public DojoErrorException(String message, Throwable err) {
        super(message, err);

    }

}