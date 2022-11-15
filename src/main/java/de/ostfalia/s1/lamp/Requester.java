package de.ostfalia.s1.lamp;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Requester {
    protected static final String base = "http://172.28.19.10:1880/lamp";

    private HttpURLConnection setupConnection(URL url, String method) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;
    }

    private void setState(String json, URL url) {
        HttpURLConnection connection = null;
        try {
            connection = setupConnection(url, "PUT");
            sendJsonCommand(json, connection);
            JsonReader jsonReader = Json.createReader(connection.getInputStream());
            jsonReader.close();
        }catch(IOException ex){
            ex.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    private void handleBridgeException(JsonObject object) throws Exception {
        if (object.getJsonObject("success") == null) throw new Exception("Bridge returned an error: " + object);
    }

    private void handleStatusException(int status) throws Exception {
        if (status != HttpURLConnection.HTTP_OK) throw new Exception("Bridge returned status " + status);
    }

    private void sendJsonCommand(String json, HttpURLConnection connection) throws IOException {
        OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
        os.write(json);
        os.close();
    }

    public JsonObject getState(URL url) throws Exception {
        HttpURLConnection connection = null;
        JsonObject jsonObject = null;
        try {
            connection = setupConnection(url, "GET");
            handleStatusException(connection.getResponseCode());
            JsonReader jsonReader = Json.createReader(connection.getInputStream());
            jsonObject = jsonReader.readObject();
            jsonReader.close();
            return jsonObject;
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    public void setLampState(int lamp, String json) {
        try {
            URL url = new URL(base);
            setState(json, url);
        }catch(MalformedURLException ex){
            ex.printStackTrace();
        }
    }
}
