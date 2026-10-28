
package com.kapitelweise.kapitelweiseBackend.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class MeetingRequest {

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("starts_at")
    private LocalDateTime startsAt;

    @JsonProperty("ends_at")
    private LocalDateTime endsAt;

    @JsonProperty("location")
    private String location;

    public MeetingRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
