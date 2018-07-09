package com.sergeydeveloper7.data.validation;

/**
 * Created by serg on 21.03.18.
 */

public class RegisterValidation {

    private boolean isValid = true;
    private Exception exception;

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
