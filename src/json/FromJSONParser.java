package json;

import com.google.gson.JsonElement;

public class FromJSONParser {


    private String type;
    private JsonElement key;
    private JsonElement value;


    public FromJSONParser(String type, JsonElement key, JsonElement value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public FromJSONParser(String type, JsonElement key) {
        this.type = type;
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public JsonElement getKey() {
        return key;
    }

    public JsonElement getValue() {
        return value;
    }


    @Override
    public String toString() {
        if (value == null ) {
            return type + " " + key;

        } else {
            return type + " " + key + " " + value;
        }
    }
}
