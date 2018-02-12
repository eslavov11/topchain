package com.topchain.node.model.viewModel;

/**
 * Created by eslavov on 12-Feb-18.
 */
public class MinedBlockStatusViewModel {
    private String status;
    private String message;

    public MinedBlockStatusViewModel() {
    }

    public MinedBlockStatusViewModel(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
