package com.example.bdero.bulibraryreserves;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class RLResponse {
    @SerializedName("reading_list")
    private ReadingList[] readingLists;

    public ReadingList[] getReadingLists() {
        return readingLists;
    }

    public void setReadingLists(ReadingList[] readingLists) {
        this.readingLists = readingLists;
    }

    @Override
    public String toString() {
        return "RLResponse{" +
                "reading Lists=" + Arrays.toString(readingLists) +
                '}';
    }

    public class ReadingList {
        private long id;
        private Status status;
        private Visibility visibility;
        private PublishingStatus publishingStatus;
        private String link;

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

        public Visibility getVisibility() {
            return visibility;
        }

        public void setVisibility(Visibility visibility) {
            this.visibility = visibility;
        }

        public PublishingStatus getPublishingStatus() {
            return publishingStatus;
        }

        public void setPublishingStatus(PublishingStatus publishingStatus) {
            this.publishingStatus = publishingStatus;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        private class Status {
            private String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        private class Visibility {
            private String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        private class PublishingStatus{
            private String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        @Override
        public String toString() {
            return "ReadingList{" +
                    "id=" + id +
                    ", status=" + status +
                    ", visibility=" + visibility +
                    ", publishingStatus=" + publishingStatus +
                    ", link='" + link + '\'' +
                    '}';
        }
    }
}
