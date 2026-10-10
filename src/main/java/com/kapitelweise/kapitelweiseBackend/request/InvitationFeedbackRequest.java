package com.kapitelweise.kapitelweiseBackend.request;

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
        "invitationStatus"
})
@Generated("jsonschema2pojo")
public class InvitationFeedbackRequest {

    @JsonProperty("invitationStatus")
    private String invitationStatus;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public InvitationFeedbackRequest() {
    }

    public InvitationFeedbackRequest(String invitationStatus) {
        super();
        this.invitationStatus = invitationStatus;
    }

    @JsonProperty("invitationStatus")
    public String getInvitationStatus() {
        return invitationStatus;
    }

    @JsonProperty("invitationStatus")
    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
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
        sb.append(InvitationFeedbackRequest.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("invitationStatus");
        sb.append('=');
        sb.append(((this.invitationStatus == null)?"<null>":this.invitationStatus));
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
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.invitationStatus == null)? 0 :this.invitationStatus.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InvitationFeedbackRequest) == false) {
            return false;
        }
        InvitationFeedbackRequest rhs = ((InvitationFeedbackRequest) other);
        return (((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties)))&&((this.invitationStatus == rhs.invitationStatus)||((this.invitationStatus!= null)&&this.invitationStatus.equals(rhs.invitationStatus))));
    }

}