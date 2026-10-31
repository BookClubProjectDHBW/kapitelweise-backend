package com.kapitelweise.kapitelweiseBackend.modules.alexa;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.HashMap;
import java.util.Map;

@JsonTypeName(value = SlotsRO.TYPENAME)
public class SlotsRO
{
    protected final static String TYPENAME = "SlotsRO";

    private Map<String, SlotRO> slots = new HashMap<>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    public SlotsRO()
    {
        super();
    }
  
    @JsonAnyGetter
    public Map<String, SlotRO> getSlots() { return slots; }

    @JsonAnySetter
    public void setSlot(String name, SlotRO value) { this.slots.put(name, value); }

    public Map<String, Object> getAdditionalProperties() { return additionalProperties; }

    public static class SlotRO {
        private String name;
        private String value;
        private String confirmationStatus;
        private Map<String, Object> additionalProperties = new HashMap<>();

        @JsonProperty("name") public String getName() { return name; }
        @JsonProperty("name") public void setName(String name) { this.name = name; }
        @JsonProperty("value") public String getValue() { return value; }
        @JsonProperty("value") public void setValue(String value) { this.value = value; }
        @JsonProperty("confirmationStatus") public String getConfirmationStatus() { return confirmationStatus; }
        @JsonProperty("confirmationStatus") public void setConfirmationStatus(String status) { this.confirmationStatus = status; }
        @JsonAnyGetter public Map<String, Object> getAdditionalProperties() { return additionalProperties; }
        @JsonAnySetter public void setAdditionalProperty(String name, Object value) { additionalProperties.put(name, value); }
    }

}
