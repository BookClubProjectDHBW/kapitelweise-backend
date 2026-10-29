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
        "userId",
        "responseStatus",
        "respondetAt"
})
@Generated("jsonschema2pojo")
public class UserMeetingResponse {

    @JsonProperty("meetingId")
    private Integer meetingId;
    @JsonProperty("userId")
    private Integer userId;
    @JsonProperty("responseStatus")
    private String responseStatus;
    @JsonProperty("respondetAt")
    private String respondetAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public UserMeetingResponse() {
    }

    public UserMeetingResponse(Integer meetingId, Integer userId, String responseStatus, String respondetAt) {
        super();
        this.meetingId = meetingId;
        this.userId = userId;
        this.responseStatus = responseStatus;
        this.respondetAt = respondetAt;
    }

    @JsonProperty("meetingId")
    public Integer getMeetingId() {
        return meetingId;
    }

    @JsonProperty("meetingId")
    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }

    @JsonProperty("userId")
    public Integer getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @JsonProperty("responseStatus")
    public String getResponseStatus() {
        return responseStatus;
    }

    @JsonProperty("responseStatus")
    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    @JsonProperty("respondetAt")
    public String getRespondetAt() {
        return respondetAt;
    }

    @JsonProperty("respondetAt")
    public void setRespondetAt(String respondetAt) {
        this.respondetAt = respondetAt;
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
        sb.append("userId");
        sb.append('=');
        sb.append(((this.userId == null)?"<null>":this.userId));
        sb.append(',');
        sb.append("responseStatus");
        sb.append('=');
        sb.append(((this.responseStatus == null)?"<null>":this.responseStatus));
        sb.append(',');
        sb.append("respondetAt");
        sb.append('=');
        sb.append(((this.respondetAt == null)?"<null>":this.respondetAt));
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
