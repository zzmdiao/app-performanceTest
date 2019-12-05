package com.iqianjin.appperformance.exception;

public class ApplicationException extends RuntimeException {
    private ApplicationCode applicationCode;

    public ApplicationCode getApplicationCode() {
        return applicationCode;
    }

    public ApplicationException(ApplicationCode applicationCode) {
        super(applicationCode.getMessage());
        this.applicationCode = applicationCode;
    }


    public ApplicationException(ApplicationCode applicationCode, Throwable cause) {
        super(applicationCode.getMessage(), cause);
        this.applicationCode = applicationCode;
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
