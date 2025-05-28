package com.catalogar.storage;

import java.util.Objects;

public class Storage {
    private String fileName;
    private String uploadUrl;
    private String accessUrl;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getAccessUrl() {
        return accessUrl;
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    public Storage(String fileName, String uploadUrl, String accessUrl) {
        this.fileName = fileName;
        this.uploadUrl = uploadUrl;
        this.accessUrl = accessUrl;
    }

    public Storage() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Storage storage = (Storage) o;
        return Objects.equals(fileName, storage.fileName) && Objects.equals(uploadUrl, storage.uploadUrl) && Objects.equals(accessUrl, storage.accessUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, uploadUrl, accessUrl);
    }

    @Override
    public String toString() {
        return "Storage{" +
                "fileName='" + fileName + '\'' +
                ", uploadUrl='" + uploadUrl + '\'' +
                ", accessUrl='" + accessUrl + '\'' +
                '}';
    }
}
