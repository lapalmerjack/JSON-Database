package server;

public class JsonForClient {



    private String response;
    private String value = "";
    private String reason = "";




    public JsonForClient(String response, String value, String reason) {
        this.response = response;
        this.value = value;
        this.reason = reason;
    }

    public JsonForClient(String response, String reason) {
        this.response = response;
        this.reason = reason;
    }


    public JsonForClient(String response) {
        this.response = response;
    }

    public JsonForClient() {
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}



