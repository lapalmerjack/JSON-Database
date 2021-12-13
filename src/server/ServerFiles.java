package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerFiles {

    static ReadWriteLock lock = new ReentrantReadWriteLock();
    private final static String directory = "/Users/Programming/JSON_Databases/src/server/data/db.json";
    private Database database;

    public ServerFiles(Database database) {
        this.database = database;
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
          //  database = new Gson().fromJson(row.toString(), JsonObject.class);
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



}