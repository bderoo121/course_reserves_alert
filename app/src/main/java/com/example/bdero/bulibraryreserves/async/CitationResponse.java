package com.example.bdero.bulibraryreserves.async;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class CitationResponse {

    @SerializedName("citation")
    private Citation[] ciitations;

    @SerializedName("link")
    private String citationsLink;

    public Citation[] getCiitations() {
        return ciitations;
    }

    public void setCiitations(Citation[] ciitations) {
        this.ciitations = ciitations;
    }

    public String getCitationsLink() {
        return citationsLink;
    }

    public void setCitationsLink(String citationsLink) {
        this.citationsLink = citationsLink;
    }

    @Override
    public String toString() {
        return "CitationResponse{" +
                "ciitations=" + Arrays.toString(ciitations) +
                ", citationsLink='" + citationsLink + '\'' +
                '}';
    }

    private class Citation{
        private long id;
        private Status status;
        private CopyrightStatus copyright_status;
        private MaterialType type;
        private Metadata metadata;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public CopyrightStatus getCopyright_status() {
            return copyright_status;
        }

        public void setCopyright_status(CopyrightStatus copyright_status) {
            this.copyright_status = copyright_status;
        }

        public MaterialType getType() {
            return type;
        }

        public void setType(MaterialType type) {
            this.type = type;
        }

        public Metadata getMetadata() {
            return metadata;
        }

        public void setMetadata(Metadata metadata) {
            this.metadata = metadata;
        }

        @Override
        public String toString() {
            return "{id=" + id +
                    ", status=" + status +
                    ", copyright_status=" + copyright_status +
                    ", type=" + type +
                    ", metadata=" + metadata +
                    '}';
        }
    }

    private class Status{
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private class CopyrightStatus{
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private class MaterialType{
        String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private class Metadata{
        String title;
        String author;
        long mms_id;
        String call_number;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public long getMms_id() {
            return mms_id;
        }

        public void setMms_id(long mms_id) {
            this.mms_id = mms_id;
        }

        public String getCall_number() {
            return call_number;
        }

        public void setCall_number(String call_number) {
            this.call_number = call_number;
        }

        @Override
        public String toString() {
            return "{title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", mms_id=" + mms_id +
                    ", call_number='" + call_number + '\'' +
                    '}';
        }
    }
}
