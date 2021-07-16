package json;

import com.google.gson.Gson;

import java.util.LinkedHashMap;
import java.util.Map;

public class forClient {


    Map<String, String> sendOVer;
    private Gson gson;

    public forClient() {
        sendOVer = new LinkedHashMap<>();
        gson = new Gson();
    }



    public String stringToJSON(String commanders) {
        String [] parts = commanders.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        if(parts[0].equals("delete")) {
            stringBuilder.append(parts[1]);
            for(int i = 2; i < parts.length; i++) {
                stringBuilder.append(" ").append(parts[i]);
            }
            String key = stringBuilder.toString();
            this.sendOVer.put("type", parts[0]);
            this.sendOVer.put("key", key);

        }
        else  if(parts.length > 2) {
            stringBuilder.append(parts[2]);
            for(int i = 3; i < parts.length; i++) {
                stringBuilder.append(" ").append(parts[i]);
            }
            String message = stringBuilder.toString();
            this.sendOVer.put("type",parts[0]);
            this.sendOVer.put("key", parts[1]);
            this.sendOVer.put("value", message);

        } else if (parts.length == 1){
            this.sendOVer.put("type", parts[0]);
        }  else{
            this.sendOVer.put("type",parts[0]);
            this.sendOVer.put("key", parts[1]);
        }




        return gson.toJson(sendOVer);

    }
}
