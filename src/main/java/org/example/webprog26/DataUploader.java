package org.example.webprog26;

public interface DataUploader {

    void uploadData(String urlString, String data, final OnDataUploadedListener listener);
}
