package it.raffo.my_dashboard.response;

import org.springframework.http.HttpStatus;

public class Payload<T> {

    private T payload;
    private String errorMessage;
    private HttpStatus status;

    // ----------------------------
    // ------ CONSTRUCTORS --------
    // ----------------------------

    public Payload() {
    }

    public Payload(T payload, String errorMessage, HttpStatus status) {
        this.payload = payload;
        this.errorMessage = errorMessage;
        this.status = status;
    }

    // ----------------------------
    // ----- GETTERS & SETTERS ----
    // ----------------------------

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

}
