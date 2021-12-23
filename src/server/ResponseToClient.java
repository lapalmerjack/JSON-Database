package server;

public class ResponseToClient {



    private String response;
    private String value = "";
    private String reason = "";




    public ResponseToClient(String response, String value, String reason) {
        this.response = response;
        this.value = value;
        this.reason = reason;
    }

    public ResponseToClient(String response,  String reason) {
        this.response = response;
        this.reason = reason;
    }


    public ResponseToClient(String response) {
        this.response = response;
    }

    public ResponseToClient() {
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



