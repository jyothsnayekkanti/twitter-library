package com.josh.twitter.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class HttpClient {


    public static String execute(String endPointUrl, String requestMethod, Map<String, String> requestProperites) throws IOException {

        HttpsURLConnection connection = null;

        try {
            URL url = new URL(endPointUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(requestMethod);
            connection.setRequestProperty("Host", "api.twitter.com");
            connection.setRequestProperty("User-Agent", "Your Program Name");

            for (Map.Entry<String, String> entry: requestProperites.entrySet()
                 ) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            connection.setUseCaches(false);

            if(requestMethod.equals("POST"))
            writeRequest(connection, "grant_type=client_credentials");

            return readResponse(connection);


        }
        catch (MalformedURLException e) {
            throw new IOException("Invalid endpoint URL specified.", e);
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    // Writes a request to a connection
    public static boolean writeRequest(HttpsURLConnection connection, String textBody) {
        try {
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            wr.write(textBody);
            wr.flush();
            wr.close();

            return true;
        }
        catch (IOException e) { return false; }
    }

    // Reads a response for a given connection and returns it as a string.
    public static String readResponse(HttpsURLConnection connection) {
        try {
            StringBuilder str = new StringBuilder();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            while((line = br.readLine()) != null) {
                str.append(line + System.getProperty("line.separator"));
            }
            return str.toString();
        }
        catch (IOException e) { return new String(); }
    }

}
