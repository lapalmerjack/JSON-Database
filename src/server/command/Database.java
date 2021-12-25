package server.command;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.JsonForClient;
import server.ServerFiles;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {

    private JsonObject database;
    static ReadWriteLock lock = new ReentrantReadWriteLock();
    private final ServerFiles serverFiles = new ServerFiles();
    private JsonForClient response;

    public Database(JsonForClient response) {
        this.response = response;
        database = serverFiles.importFile();
    }

    public void set(JsonElement key, JsonElement value) {
        if(database == null) {
            database = new JsonObject();
        }
            if(key.isJsonPrimitive()) {
                database.add(key.getAsString(), value);
            } else if (key.isJsonArray()) {
                JsonArray keys = key.getAsJsonArray();
                String toAdd = keys.remove(keys.size()-1).getAsString();
              addOrRemoveElementFromFile(keys).getAsJsonObject().add(toAdd, value);
            }

        serverFiles.exportFile(database);

       this.response.setResponse("OK");
    }


    public void get(JsonElement key) {
        lock.readLock().lock();

        String answer = "";
        try {

            if (key.isJsonPrimitive() && database.has(key.getAsString())) {
                answer = String.valueOf(database.get(key.getAsString()));
                System.out.println(database.get(key.getAsString()));
                response.setResponse("OK");
                response.setValue(answer);
            } else if (key.isJsonArray()) {
                JsonElement value = findElement(key.getAsJsonArray());
                answer = value.toString();
                response.setResponse("OK");
                response.setValue(answer);

            } else {

                response.setResponse("ERROR");
                response.setValue("No Such Key");
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            lock.readLock().unlock();
        }


    }

    public void delete(JsonElement key) {
        lock.writeLock().lock();

        try {

            if (key.isJsonPrimitive() && database.has(key.getAsString()) ) {
                this.database.remove(key.getAsString());
                serverFiles.exportFile(database);


                response.setResponse("OK");

            } else if (key.isJsonArray()){
                JsonArray keys = key.getAsJsonArray();
                String delete = keys.remove(keys.size()-1).getAsString();
                addOrRemoveElementFromFile(keys).getAsJsonObject().remove(delete);
                serverFiles.exportFile(database);
                response.setResponse("OK");


            } else {
                response.setResponse("ERROR");
                response.setReason("No such key");

            }
        } finally {
            lock.writeLock().unlock();
        }


    }

    private JsonElement addOrRemoveElementFromFile(JsonArray keys) {
        JsonElement tmp = database;
            for(JsonElement key: keys){
                if(!tmp.getAsJsonObject().has(key.getAsString())){
                    tmp.getAsJsonObject().add(key.getAsString(), new JsonObject());
                }

                tmp = tmp.getAsJsonObject().get(key.getAsString());
            }

        return tmp;
    }

    private JsonElement findElement(JsonArray keys) {
        JsonElement tmp = database;
        for(JsonElement key: keys){
            if(!key.isJsonPrimitive() || !tmp.getAsJsonObject().has(key.getAsString())) {
                System.out.println("No such key");
            }
            tmp = tmp.getAsJsonObject().get(key.getAsString());
        }

        return tmp;
    }

}
