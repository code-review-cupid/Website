package com.jannetta.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileName {

    @SerializedName("FileName")
    @Expose
    String fileName;

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @param fileName
     */
    public FileName(String fileName) {
        this.fileName = fileName;
    }
    
}
