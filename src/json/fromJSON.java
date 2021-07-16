package json;

import com.google.gson.JsonElement;

public class fromJSON {


    private String type;
    private JsonElement key;
    private JsonElement value;


    public fromJSON(String type, JsonElement key, JsonElement value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    public fromJSON(String type, JsonElement key) {
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
