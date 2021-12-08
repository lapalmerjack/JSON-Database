package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.security.UnrecoverableKeyException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {


    private JsonObject database;
    private final ResponseToClient response = new ResponseToClient();

    static ReadWriteLock lock = new ReentrantReadWriteLock();
    private final static String directory = "/Users/Programming/JSON_Databases/src/server/data/db.json";

    public Database () {

        importFile();
    }


    public void set(JsonElement key, JsonElement value) {
        this.response.setValue("");
        this.response.setReason("");
        if(database == null) {
            database = new JsonObject();
            database.add(key.getAsString(), value);
        } else {
            if(key.isJsonPrimitive()) {
                database.add(key.getAsString(), value);

            } else if (key.isJsonArray()) {
                JsonArray keys = key.getAsJsonArray();
                String toAdd = keys.remove(keys.size()-1).getAsString();
                findElement(keys, true).getAsJsonObject().add(toAdd, value);
            } }

        exportFile();
        this.response.setResponse("OK");


    }

    public void get(JsonElement key) {
        lock.readLock().lock();

        try {

            this.response.setValue("");
            this.response.setReason("");

            if (key.isJsonPrimitive()  && database.has(key.getAsString())) {
                this.response.setResponse("OK");
                this.response.setValue(String.valueOf(database.get(key.getAsString())));
                System.out.println(database.get(key.getAsString()));

            }

            else if (key.isJsonArray()) {

                    JsonElement value = findElement(key.getAsJsonArray(), false);
                System.out.println(value);
                    String answer = value.toString();
                    System.out.println(answer);

                    this.response.setValue(answer);
                    this.response.setResponse("OK");
                }

            else {
                this.response.setResponse("ERROR");
                this.response.setReason("No such key");
            }
        } catch(Exception e){
           e.printStackTrace();

        } finally {
            lock.readLock().unlock();

        }
    }

    public void delete (JsonElement key) {
        lock.writeLock().lock();
        try {

        this.response.setValue("");
        this.response.setReason("");
        if (key.isJsonPrimitive() && database.has(key.getAsString()) ) {
            this.database.remove(key.getAsString());
            this.exportFile();
            this.response.setResponse("OK");

        } else if (key.isJsonArray()){
            System.out.println("Yes");
            JsonArray keys = key.getAsJsonArray();
            String delete = keys.remove(keys.size()-1).getAsString();
            findElement(keys, true).getAsJsonObject().remove(delete);
            this.exportFile();
            this.response.setResponse("OK");


        } else {
            this.response.setResponse("ERROR");
            this.response.setReason("No such key");

        }
        } finally {
            lock.writeLock().unlock();
        }
    }


    public void exitProgram(){
        this.response.setValue("");
        this.response.setReason("");
        this.response.setResponse("exit");
    }


    private void importFile() {
        StringBuilder row = new StringBuilder();
        lock.readLock().lock();
        try {
            Scanner fileReader = new Scanner(Paths.get(directory));
            while (fileReader.hasNextLine()) {
                row.append(fileReader.nextLine());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
            database = new Gson().fromJson(row.toString(), JsonObject.class);
        }


    }

    private void exportFile() {
        lock.writeLock().lock();
        try {

            PrintWriter printWriter = new PrintWriter(directory);
            printWriter.println(database.toString());
            printWriter.close();
            System.out.println("File has been closed");
        } catch (FileNotFoundException e) {
            System.out.println("No file found");

        } finally {
            lock.writeLock().unlock();
        }

    }
    private JsonElement findElement (JsonArray keys, boolean createIfAbsent){
        JsonElement tmp = database;
        if (createIfAbsent) {

            for(JsonElement key: keys){

                if(!tmp.getAsJsonObject().has(key.getAsString())){
                    tmp.getAsJsonObject().add(key.getAsString(), new JsonObject());
                }

                tmp = tmp.getAsJsonObject().get(key.getAsString());
            }
        }
        else {
            for(JsonElement key: keys){

                if(!key.isJsonPrimitive() || !tmp.getAsJsonObject().has(key.getAsString())) {
                    System.out.println("No such key");
                }

                tmp = tmp.getAsJsonObject().get(key.getAsString());

            }
        }
        return tmp;
    }


    public ResponseToClient getResponseForClient() {
        return response;
    }

}
