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
        "clubName",
        "clubDescription"
})
@Generated("jsonschema2pojo")

public class InvitationUserResponse {
    @JsonProperty("invitationId")
    private Long invitationId;
    @JsonProperty("clubName")
    private String clubName;
    @JsonProperty("clubDescription")
    private String clubDescription;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public InvitationUserResponse() {
    }

    public InvitationUserResponse(Long invitationId, String clubName, String clubDescription) {
        super();
        this.invitationId = invitationId;
        this.clubName = clubName;
        this.clubDescription = clubDescription;
    }

    @JsonProperty("invitationId")
    public Long getInvitationId() {
        return invitationId;
    }

    @JsonProperty("invitationId")
    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    @JsonProperty("clubName")
    public String getClubName() {
        return clubName;
    }

    @JsonProperty("clubName")
    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    @JsonProperty("clubDescription")
    public String getClubDescription() {
        return clubDescription;
    }

    @JsonProperty("clubDescription")
    public void setClubDescription(String clubDescription) {
        this.clubDescription = clubDescription;
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
        sb.append(InvitationUserResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("invitationId");
        sb.append('=');
        sb.append(((this.invitationId == null)?"<null>":this.invitationId));
        sb.append(',');
        sb.append("clubName");
        sb.append('=');
        sb.append(((this.clubName == null)?"<null>":this.clubName));
        sb.append(',');
        sb.append("clubDescription");
        sb.append('=');
        sb.append(((this.clubDescription == null)?"<null>":this.clubDescription));
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
        result = ((result* 31)+((this.clubDescription == null)? 0 :this.clubDescription.hashCode()));
        result = ((result* 31)+((this.invitationId == null)? 0 :this.invitationId.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof InvitationUserResponse) == false) {
            return false;
        }
        InvitationUserResponse rhs = ((InvitationUserResponse) other);
        return ((((this.clubName == rhs.clubName)||((this.clubName!= null)&&this.clubName.equals(rhs.clubName)))&&((this.clubDescription == rhs.clubDescription)||((this.clubDescription!= null)&&this.clubDescription.equals(rhs.clubDescription))))&&(((this.invitationId == rhs.invitationId)||((this.invitationId!= null)&&this.invitationId.equals(rhs.invitationId)))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties)))));
    }

}

