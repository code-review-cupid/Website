package com.jannetta;

import com.jannetta.controller.Controller;
import com.jannetta.controller.IndexController;
import com.jannetta.controller.UploadFileController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static spark.Spark.*;


public class SparkProgram {
    private static String STORAGE;
    private static final int PORT = getHerokuAssignedPort();

    public static void main(String[] args) {
        Properties properties = Controller.loadProperties();
        STORAGE = properties.getProperty("STORAGE");
        File storageDir = new File(STORAGE);
        if (!storageDir.isDirectory())
            storageDir.mkdir();
        staticFiles.header("Access-Control-Allow-Origin", "*");
        staticFiles.location("/public");
        staticFiles.externalLocation("externalpublic");
        staticFiles.expireTime(600L);
        port(PORT);

        // Serve index page using index.vm
        get("/", IndexController.serveIndexPage);
        // Serve text directly
        get("/hello", (req, res) -> "Hello World");
        // Upload a file
        post("/upload", (req, res) -> UploadFileController.uploadFile(req, STORAGE));
        // Get all filenames in the upload directory
        post("/getFileNames", UploadFileController::getUploadedFilenames);
        get("/download/:file", (req, res) -> downloadFile(req.params(":file")));
        post("/deleteFileName", UploadFileController::deleteUploadedFilename);

    }

    /**
     * https://sparktutorials.github.io/2015/08/24/spark-heroku.html
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    private static String downloadFile(String fileName) {
        Path filePath = Paths.get(STORAGE).resolve(fileName);
        File file = filePath.toFile();
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (file.exists()) {
            try {
                // Read from file and join all the lines into a string
                return String.join("", Files.readAllLines(filePath));
            } catch (IOException e) {
                return "Exception occurred while reading file" + e.getMessage();
            }
        }
        return "File doesn't exist. Cannot download";
    }
}


