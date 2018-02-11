package com.topchain.node.model.viewModel;

public class NotifyBlockViewModel {
    private String message;

    public NotifyBlockViewModel() {
    }

    public NotifyBlockViewModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String index) {
        this.message = index;
    }
}
