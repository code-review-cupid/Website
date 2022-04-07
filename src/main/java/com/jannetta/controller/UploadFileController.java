package com.jannetta.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import com.jannetta.model.FileName;
import com.jannetta.model.FileNames;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class UploadFileController {
    private static Logger logger = LoggerFactory.getLogger(UploadFileController.class);

	/**
	 * Upload a file to the server
	 * @param req
	 * @return
	 */
	public static String uploadFile(Request req, String STORAGE) {
		String uploadedFileName = null;
		// TO allow for multipart file uploads
		req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement(""));
		try {
			// "uploaded_file" is the key of the form data with the file itself being the value
			Part filePart = req.raw().getPart("uploaded_file");
			logger.debug("FILE TO UPLOAD: ");// + filePart.getName());
			// The name of the file that is to be uploadeded
			uploadedFileName = filePart.getSubmittedFileName();
			InputStream stream = filePart.getInputStream();

			// Write stream to file under storage folder
			Files.copy(stream, Paths.get(STORAGE).resolve(uploadedFileName), StandardCopyOption.REPLACE_EXISTING);
			stream.close();
		} catch (IOException | ServletException e) {
			return "Exception occurred while uploading file" + e.getMessage();
		}
		if (uploadedFileName.endsWith(".png")) {
			return "<h1>You uploaded this image:<h1><img src='" + uploadedFileName + "'>";
		} else {
			return "File " + uploadedFileName + " uploaded successfully.";
		}
	}

	public static String getUploadedFilenames(Request request, Response response) {
		String storage = System.getProperty("user.dir") + "/" + Controller.loadProperties().getProperty("STORAGE");
		File file = new File(storage);
		logger.debug("STORAGE: " + storage);
		FileNames filenames = new FileNames();
		String[] listOfFiles = file.list();
		for (int i = 0; i < listOfFiles.length; i++) {
			FileName filename = new FileName(listOfFiles[i]);
			System.out.println("FILE: " + i + " " + filename.getFileName());
			filenames.add(filename);
		}
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		logger.debug("FILENAMES: " + filenames);
        response.type("application/json");
		return gson.toJson(filenames, FileNames.class);
	}

	public static String deleteUploadedFilename(Request request, Response reponse) {
		String storage = System.getProperty("user.dir") + "/" + Controller.loadProperties().getProperty("STORAGE");
		File toDelete = new File(storage + "\\" + request.queryParams("filename"));
		System.out.println("DELETING: " + toDelete);
		if (toDelete.exists()) {
			System.out.println(toDelete.delete());
		}
		return "{}";
	}

}
