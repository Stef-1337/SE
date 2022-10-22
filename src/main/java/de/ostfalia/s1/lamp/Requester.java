package de.ostfalia.s1.lamp;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Set;

public class Requester {
    protected static final String base = "http://172.28.19.10:1880/lamp";

    //  protected static final URL state = new URL("http://192.168.0.235/api/z9S2a53sZ8ZGA0LT0S0E-6gtBe9bWXKuubaqpDqN/lights/3/state");
    public Requester() {
    }

    public static void main(String[] args) throws IOException {
        Requester bridge = new Requester();
        Lamp lamp = new Lamp(false, 154, 7778, 254, new double[]{0.5330,0.4273}, "Stehlampe rechts");
        Jsonb jsonb = JsonbBuilder.create();
        String result = jsonb.toJson(lamp);
        System.out.println(result);
        bridge.setLampState(4, result);
        JsonObject state = bridge.getState(new URL(base));
        JsonObject s = state.getJsonObject("state"); //"entpacken"
        int bri = s.getInt("bri"); // einzelne Parameter aus der Json auslesen (Anstatt die String/Substring Variante)
        System.out.println(bri);
        String feedback = state.toString();
        System.out.println(feedback);
        feedback = feedback.substring(9, feedback.length());
        String[] feedbackArray = feedback.split(",");
        feedback = feedbackArray[0] + ", " + feedbackArray[1] + ", " + feedbackArray[2] + ", " + feedbackArray[3] +
                ", " + feedbackArray[5].substring(0,12) + ", " + feedbackArray[6] + ", " + feedbackArray[15] + "}";
        System.out.println(feedback);

//        JsonString feedback = state.getJsonString("sat");
//        System.out.println(feedback);

        lamp =  jsonb.fromJson(feedback, Lamp.class); // Damit können wir später den Status aus derLampe auslesen. Müssen wir aber noch anpassen
        System.out.println(lamp.name);
    }

    private HttpURLConnection setupConnection(URL url, String method) throws IOException{
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

    private JsonObject getState(URL url) throws IOException, Exception {
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

    public void setLampState(int lamp, String json) throws IOException {
        URL url = new URL(base);
//        URL url = new URL(base + "/lights/" + Integer.toString(lamp) + "/state");
        setState(json, url);
    }

    public JsonObject getAllScenes() throws Exception {
        URL url = new URL(base + "/scenes/");
        return getState(url);
    }
}
