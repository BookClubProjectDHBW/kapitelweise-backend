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
        "respondedAt"
})
@Generated("jsonschema2pojo")
public class UserMeetingResponse {

    @JsonProperty("meetingId")
    private Long meetingId;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("responseStatus")
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

    public UserMeetingResponse(Long meetingId, Long userId, String responseStatus, String respondedAt) {
        super();
        this.meetingId = meetingId;
        this.userId = userId;
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

    @JsonProperty("userId")
    public Long getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(Long userId) {
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
        sb.append("userId");
        sb.append('=');
        sb.append(((this.userId == null)?"<null>":this.userId));
        sb.append(',');
        sb.append("responseStatus");
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
