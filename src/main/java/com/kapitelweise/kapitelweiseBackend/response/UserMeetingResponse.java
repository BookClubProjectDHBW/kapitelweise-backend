package com.kapitelweise.kapitelweiseBackend.response;


import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "meetingId",
        "username",
        "status",
        "respondedAt"
})
@Generated("jsonschema2pojo")
public class UserMeetingResponse {

    @JsonProperty("meetingId")
    private Long meetingId;
    @JsonProperty("username")
    private String username;
    @JsonProperty("status")
    private String responseStatus;
    @JsonProperty("respondedAt")
    private String respondedAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public UserMeetingResponse() {
    }

    public UserMeetingResponse(Long meetingId, String username, String responseStatus, String respondedAt) {
        super();
        this.meetingId = meetingId;
        this.username = username;
        this.responseStatus = responseStatus;
        this.respondedAt = respondedAt;
    }

    @JsonProperty("meetingId")
    public Long getMeetingId() {
        return meetingId;
    }

    @JsonProperty("meetingId")
    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("status")
    public String getResponseStatus() {
        return responseStatus;
    }

    @JsonProperty("status")
    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    @JsonProperty("respondedAt")
    public String getRespondedAt() {
        return respondedAt;
    }

    @JsonProperty("respondedAt")
    public void setRespondedAt(String respondedAt) {
        this.respondedAt = respondedAt;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(UserMeetingResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("meetingId");
        sb.append('=');
        sb.append(((this.meetingId == null)?"<null>":this.meetingId));
        sb.append(',');
        sb.append("username");
        sb.append('=');
        sb.append(((this.username == null)?"<null>":this.username));
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(((this.responseStatus == null)?"<null>":this.responseStatus));
        sb.append(',');
        sb.append("respondedAt");
        sb.append('=');
        sb.append(((this.respondedAt == null)?"<null>":this.respondedAt));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
