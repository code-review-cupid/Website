package com.jannetta.controller;

import java.io.*;
import java.util.Properties;

public class Controller {
  
    
        /**
     * Load properties from system.properties file
     * 
     * @return
     */
    public static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            File f = new File("system.properties");
            // If the file doesn't exist, create it
            OutputStream out;
            if (!(f.exists())) {
                out = new FileOutputStream(f);
                out.close();
            }
            InputStream is = new FileInputStream(f);
            properties.load(is);
            // If there are no properties yet, set STORAGE to the default value of /upload
            if (properties.size() == 0) {
                properties.setProperty("STORAGE", "upload");
                properties.setProperty("PGPORT", "4567");
                properties.setProperty("PGHOST", "postgres");
            }
            FileOutputStream os = new FileOutputStream("system.properties");
            properties.store(os, "");
            // String STORAGE = properties.getProperty("STORAGE");
            // int PORT = Integer.valueOf(properties.getProperty("PGPORT"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
