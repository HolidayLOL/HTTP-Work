package org.example.webprog26;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class UsersLoader implements DataLoader {

    @Override
    public void loadData(String urlString, OnDataLoadedListener listener) {
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

                System.out.println(stringBuilder.length() > 0 ? stringBuilder.toString() : "null");

                //Using callback
                if (listener != null) {
                    listener.onDataLoaded(stringBuilder.toString());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
