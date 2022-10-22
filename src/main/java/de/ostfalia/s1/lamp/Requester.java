package de.ostfalia.s1.lamp;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.json.*;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class Requester {

    protected static final String base = "http://127.0.0.1:1880/lamp";
//    protected static final String base = "http://172.28.19.10:1880/lamp";

    //  protected static final URL state = new URL("http://192.168.0.235/api/z9S2a53sZ8ZGA0LT0S0E-6gtBe9bWXKuubaqpDqN/lights/3/state");
    public Requester() {
    }

    public static void main(String[] args) {
        Requester requester = new Requester();
        JsonObject state = requester.getState(new URL("http://127.0.0.1:1880/lamp"));
        JsonObject s = state.getJsonObject("state");
        System.out.println(s.toString());
    }


    private HttpURLConnection setupConnection(URL url, String method) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;
    }

    private void setState(String json, URL url) throws Exception {
        HttpURLConnection connection = null;
        try {
            connection = setupConnection(url, "PUT");
            sendJsonCommand(json, connection);
            handleStatusException(connection.getResponseCode());
            JsonReader jsonReader = Json.createReader(connection.getInputStream());
//            JsonArray jsonArray = jsonReader.readArray();
            jsonReader.close();
//            JsonObject jsonObject = jsonArray.getJsonObject(0);
//            handleBridgeException(jsonObject);
        } finally {
            if (connection != null)
                connection.disconnect();
        }
    }


    private void handleStatusException(int status) throws Exception {
        if (status != HttpURLConnection.HTTP_OK)
            throw new Exception("Bridge returned status " + status);
    }

    private void sendJsonCommand(String json, HttpURLConnection connection) throws IOException {
        OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());
        os.write(json);
        os.close();
    }

    JsonObject getState(URL url) throws Exception {
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
            if (connection != null)
                connection.disconnect();
        }
    }

    public void setLampState(int lamp, String json) throws Exception {
        URL url = new URL(base);
//        URL url = new URL(base + "/lights/" + Integer.toString(lamp) + "/state");
        setState(json, url);
    }
}
