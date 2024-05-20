package org.example.webprog26;

public class DataUploadResult {

    private final int responseCode;
    private final String responseMessage;


    public DataUploadResult(int responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
