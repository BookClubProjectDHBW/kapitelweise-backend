package com.kapitelweise.kapitelweiseBackend.response;

import java.time.LocalDateTime;
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
        "id",
        "invitation_status",
        "invited_user",
        "created_at"
})
@Generated("jsonschema2pojo")
public class InvitationResponse {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("invitation_status")
    private String invitationStatus;
    @JsonProperty("invited_user")
    private String invitedUser;
    @JsonProperty("created_at")
    private LocalDateTime created_at;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public InvitationResponse() {
    }

    public InvitationResponse(Long id, String invitationStatus, String invitedUser, LocalDateTime created_at) {
        super();
        this.id = id;
        this.invitationStatus = invitationStatus;
        this.invitedUser = invitedUser;
        this.created_at = created_at;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("invitation_status")
    public String getInvitationStatus() {
        return invitationStatus;
    }

    @JsonProperty("invitation_status")
    public void setInvitationStatus(String invitationStatus) {
        this.invitationStatus = invitationStatus;
    }

    @JsonProperty("invited_user")
    public String getInvitedUser() {
        return invitedUser;
    }

    @JsonProperty("invited_user")
    public void setInvitedUser(String invitedUser) {
        this.invitedUser = invitedUser;
    }

    @JsonProperty("created_at")
    public LocalDateTime getCreated_at() {
        return created_at;
    }

    @JsonProperty("created_at")
    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
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
        sb.append(InvitationResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("invitationStatus");
        sb.append('=');
        sb.append(((this.invitationStatus == null)?"<null>":this.invitationStatus));
        sb.append(',');
        sb.append("invitedUser");
        sb.append('=');
        sb.append(((this.invitedUser == null)?"<null>":this.invitedUser));
        sb.append(',');
        sb.append("created_at");
        sb.append('=');
        sb.append(((this.created_at == null)?"<null>":this.created_at));
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
        result = ((result* 31)+((this.created_at == null)? 0 :this.created_at.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.invitationStatus == null)? 0 :this.invitationStatus.hashCode()));
        result = ((result* 31)+((this.invitedUser == null)? 0 :this.invitedUser.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InvitationResponse) == false) {
            return false;
        }
        InvitationResponse rhs = ((InvitationResponse) other);
        return ((((((this.created_at == rhs.created_at)||((this.created_at!= null)&&this.created_at.equals(rhs.created_at)))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.invitationStatus == rhs.invitationStatus)||((this.invitationStatus!= null)&&this.invitationStatus.equals(rhs.invitationStatus))))&&((this.invitedUser == rhs.invitedUser)||((this.invitedUser!= null)&&this.invitedUser.equals(rhs.invitedUser))));
    }

}