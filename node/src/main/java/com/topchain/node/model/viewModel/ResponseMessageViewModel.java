package com.topchain.node.model.viewModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ResponseMessageViewModel {
    private String message;

    @JsonIgnore
    private boolean exists;

    public ResponseMessageViewModel() {
        this.setExists(false);
    }

    public ResponseMessageViewModel(String message) {
        this();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String index) {
        this.message = index;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}
