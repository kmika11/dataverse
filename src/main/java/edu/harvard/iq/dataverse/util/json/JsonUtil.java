package edu.harvard.iq.dataverse.util.json;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;
import jakarta.json.JsonWriterFactory;
import jakarta.json.stream.JsonGenerator;

public class JsonUtil {

    private static final Logger logger = Logger.getLogger(JsonUtil.class.getCanonicalName());

    /**
     * Make an attempt at pretty printing a String but will return the original
     * string if it isn't JSON or if there is any exception.
     */
    public static String prettyPrint(String jsonString) {
        try {
            JsonObject jsonObject = getJsonObject(jsonString);
            return prettyPrint(jsonObject);
        } catch (Exception ex) {
            logger.info("Returning original string due to exception: " + ex);
            return jsonString;
        }
    }

    public static String prettyPrint(JsonArray jsonArray) {
        Map<String, Boolean> config = new HashMap<>();
        config.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory jsonWriterFactory = Json.createWriterFactory(config);
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = jsonWriterFactory.createWriter(stringWriter)) {
            jsonWriter.writeArray(jsonArray);
        }
        return stringWriter.toString();
    }

    public static String prettyPrint(JsonObject jsonObject) {
        Map<String, Boolean> config = new HashMap<>();
        config.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory jsonWriterFactory = Json.createWriterFactory(config);
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = jsonWriterFactory.createWriter(stringWriter)) {
            jsonWriter.writeObject(jsonObject);
        }
        return stringWriter.toString();
    }
    
    public static JsonObject getJsonObject(String serializedJson) {
        try (StringReader rdr = new StringReader(serializedJson)) {
            try (JsonReader jsonReader = Json.createReader(rdr)) {
                return jsonReader.readObject();
            }
        }
    }
    
    public static JsonArray getJsonArray(String serializedJson) {
        try (StringReader rdr = new StringReader(serializedJson)) {
            try (JsonReader jsonReader = Json.createReader(rdr)) {
                return jsonReader.readArray();
            }
        }
    }
}
