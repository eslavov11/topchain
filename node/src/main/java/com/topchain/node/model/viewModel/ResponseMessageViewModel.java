package com.topchain.node.model.viewModel;

public class ResponseMessageViewModel {
    private String message;

    public ResponseMessageViewModel() {
    }

    public ResponseMessageViewModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String index) {
        this.message = index;
    }
}
