package org.example.webprog26;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.*;

public class UserUploader implements DataUploader {

    @Override
    public void uploadData(String urlString, String data, OnDataUploadedListener listener) {

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<DataUploadResult> future = service.submit(new Callable<DataUploadResult>() {
            @Override
            public DataUploadResult call() throws Exception {
                System.out.println("We are trying to upload data on thread: " + Thread.currentThread().getName());
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("https://jsonplaceholder.typicode.com/posts");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true); //Set the DoOutput flag to true if you intend to use the URL connection for output, false if not. The default is false.
                    connection.setDoInput(true); // means you aren't interested in a response body,
                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    OutputStream stream = connection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(stream, "UTF-8"));
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    stream.close();

                    connection.disconnect();

                    return new DataUploadResult(connection.getResponseCode(), connection.getResponseMessage());
                } catch (IOException e) {
                    if (listener != null) {
                        listener.onFailure(e.getMessage());
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                return null;
            }
        });

       try {
           final DataUploadResult dataUploadResult = future.get();
           service.shutdown();

           if (listener != null) {
               if (dataUploadResult != null) {
                   if (dataUploadResult.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
                       listener.onSuccess();
                   } else {
                       listener.onFailure(dataUploadResult.getResponseMessage());
                   }
               }
           }

       } catch (InterruptedException | ExecutionException e) {
            if (listener != null) {
                listener.onFailure(e.getMessage());
            }
       }
    }
}
