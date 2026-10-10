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
        "invitationId",
        "invitationStatus",
        "clubId",
        "clubName",
        "respondedAt"
})
@Generated("jsonschema2pojo")
public class InvitationFeedbackResponse {

    @JsonProperty("invitationId")
    private Long invitationId;
    @JsonProperty("invitationStatus")
    private String invitationStatus;
    @JsonProperty("clubId")
    private Long clubId;
    @JsonProperty("clubName")
    private String clubName;
    @JsonProperty("respondedAt")
    private String respondedAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public InvitationFeedbackResponse() {
    }

    public InvitationFeedbackResponse(Long invitationId, String invitationStatus, Long clubId, String clubName, String respondedAt) {
        super();
        this.invitationId = invitationId;
        this.invitationStatus = invitationStatus;
        this.clubId = clubId;
        this.clubName = clubName;
        this.respondedAt = respondedAt;
    }

    @JsonProperty("invitationId")
    public Long getInvitationId() {
        return invitationId;
    }

    @JsonProperty("invitationId")
    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    @JsonProperty("invitationStatus")
    public String getInvitationStatus() {
        return invitationStatus;
    }

    @JsonProperty("invitationStatus")
    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    @JsonProperty("clubId")
    public Long getClubId() {
        return clubId;
    }

    @JsonProperty("clubId")
    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    @JsonProperty("clubName")
    public String getClubName() {
        return clubName;
    }

    @JsonProperty("clubName")
    public void setClubName(String clubName) {
        this.clubName = clubName;
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
        sb.append(InvitationFeedbackResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("invitationId");
        sb.append('=');
        sb.append(((this.invitationId == null)?"<null>":this.invitationId));
        sb.append(',');
        sb.append("invitationStatus");
        sb.append('=');
        sb.append(((this.invitationStatus == null)?"<null>":this.invitationStatus));
        sb.append(',');
        sb.append("clubId");
        sb.append('=');
        sb.append(((this.clubId == null)?"<null>":this.clubId));
        sb.append(',');
        sb.append("clubName");
        sb.append('=');
        sb.append(((this.clubName == null)?"<null>":this.clubName));
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

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.clubName == null)? 0 :this.clubName.hashCode()));
        result = ((result* 31)+((this.clubId == null)? 0 :this.clubId.hashCode()));
        result = ((result* 31)+((this.invitationId == null)? 0 :this.invitationId.hashCode()));
        result = ((result* 31)+((this.respondedAt == null)? 0 :this.respondedAt.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.invitationStatus == null)? 0 :this.invitationStatus.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InvitationFeedbackResponse) == false) {
            return false;
        }
        InvitationFeedbackResponse rhs = ((InvitationFeedbackResponse) other);
        return (((((((this.clubName == rhs.clubName)||((this.clubName!= null)&&this.clubName.equals(rhs.clubName)))&&((this.clubId == rhs.clubId)||((this.clubId!= null)&&this.clubId.equals(rhs.clubId))))&&((this.invitationId == rhs.invitationId)||((this.invitationId!= null)&&this.invitationId.equals(rhs.invitationId))))&&((this.respondedAt == rhs.respondedAt)||((this.respondedAt!= null)&&this.respondedAt.equals(rhs.respondedAt))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.invitationStatus == rhs.invitationStatus)||((this.invitationStatus!= null)&&this.invitationStatus.equals(rhs.invitationStatus))));
    }

}