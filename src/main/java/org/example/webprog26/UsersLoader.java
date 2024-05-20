package org.example.webprog26;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.concurrent.*;

public class UsersLoader implements DataLoader {

    @Override
    public void loadData(String urlString, OnDataLoadedListener listener) {

        final Callable<String> task = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("We are trying to load data on thread: " + Thread.currentThread().getName());
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(urlString);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader bufferedReader  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null)
                        {
                            stringBuilder.append(line + '\n');
                        }
                        bufferedReader.close();

                        return stringBuilder.toString();
                    } else {
                        if (listener != null) {
                            listener.onFailure(connection.getResponseMessage());
                        }
                    }

                } catch (IOException e) {
                    if (listener != null) {
                        listener.onFailure(e.getMessage());
                    }
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                return null;
            }
        };

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> stringFuture = service.submit(task);

        try {
            if (listener != null) {
                listener.onDataLoaded(stringFuture.get());
            }
            service.shutdown();
        } catch (InterruptedException | ExecutionException e) {
            if (listener != null) {
                listener.onFailure(e.getMessage());
            }
        }
    }
}
