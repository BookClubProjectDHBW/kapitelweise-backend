package com.kapitelweise.kapitelweiseBackend.modules.alexa;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.HashMap;
import java.util.Map;


@JsonTypeName(value = SessionRO.TYPENAME)
public class SessionRO 
{
    protected final static String TYPENAME = "SessionRO";

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();
    private UserRO user;

    public SessionRO()
    {
        super();
    }

    @com.fasterxml.jackson.annotation.JsonProperty("user")
    public UserRO getUser() { return user; }

    @com.fasterxml.jackson.annotation.JsonProperty("user")
    public void setUser(UserRO user) { this.user = user; }

    public static class UserRO {
        private String accessToken;
        @com.fasterxml.jackson.annotation.JsonProperty("accessToken")
        public String getAccessToken() { return accessToken; }
        @com.fasterxml.jackson.annotation.JsonProperty("accessToken")
        public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
    }

}
