package com.catalogar.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Metadata {
    private final String message;
    private final Pagination pagination;

    public Metadata(String message) {
        this(message, null);
    }

    public Metadata(Pagination pagination) {
        this(null, pagination);
    }

    public Metadata(String message, Pagination pagination) {
        this.message = message;
        this.pagination = pagination;
    }

    public String getMessage() {
        return message;
    }

    public Pagination getPagination() {
        return pagination;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Metadata metadata = (Metadata) o;
        return Objects.equals(message, metadata.message) && Objects.equals(pagination, metadata.pagination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, pagination);
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "message='" + message + '\'' +
                ", pagination=" + pagination +
                '}';
    }
}
