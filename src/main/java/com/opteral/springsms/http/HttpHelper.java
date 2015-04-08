package com.opteral.springsms.http;

import com.opteral.springsms.json.Parser;

import java.io.*;
import java.net.*;

public class HttpHelper {

    private String urlString;
    private String userName;
    private String password;
    private Object content;

    public HttpHelper(String urlString, String userName, String password, Object content) {
        this.urlString = urlString;
        this.userName = userName;
        this.password = password;
        this.content = content;
    }

    public HttpHelper(String urlString, Object content) {
        this.urlString = urlString;
        this.content = content;
    }

    public String postRequest() throws IOException {

        InputStream inputStream = null;
        try
        {
            inputStream = getData();

            return inputStreamToString(inputStream);
        }
        catch (Exception e) {
            return e.getMessage();
        }
        finally
        {
            if (inputStream != null) {
                inputStream.close();
            }
        }


    }

    private InputStream getData() throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        configureConnection(connection);
        setCredentials(userName, password);
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(convertRequestJSONtoBytes(content));
        connection.connect();
        return connection.getInputStream();
    }

    private void configureConnection(HttpURLConnection conn) throws ProtocolException {
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoInput(true);
        conn.setDoOutput(true);
    }

    private void setCredentials(final String userName, final String password)
    {
        if (userName == null)
            return;

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password.toCharArray());
            }
        });
    }

    private static byte[] convertRequestJSONtoBytes(Object requestJSON) throws IOException {
        return Parser.getJSON(requestJSON).getBytes("UTF-8");

    }

    private String inputStreamToString(InputStream is) throws IOException {

        String line = "";
        StringBuilder total = new StringBuilder();

        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        while ((line = rd.readLine()) != null) {
            total.append(line);
        }


        return total.toString();
    }
}
