package com.catalogar.storage;

import java.util.Objects;

public class Storage {
    private String sasToken;
    private String name;
    private String url;

    public String getSasToken() {
        return sasToken;
    }

    public void setSasToken(String sasToken) {
        this.sasToken = sasToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Storage(String sasToken, String name, String url) {
        this.sasToken = sasToken;
        this.name = name;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Storage storage = (Storage) o;
        return Objects.equals(sasToken, storage.sasToken) && Objects.equals(name, storage.name) && Objects.equals(url, storage.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sasToken, name, url);
    }

    @Override
    public String toString() {
        return "Storage{" +
                "sasToken='" + sasToken + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
