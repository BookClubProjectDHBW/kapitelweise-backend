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
        "bookId",
        "title",
        "staus",
        "closed_at"
})
@Generated("jsonschema2pojo")
public class CloseBooksResponse {

    @JsonProperty("bookId")
    private String bookId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("staus")
    private String staus;
    @JsonProperty("closed_at")
    private String closedAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public CloseBooksResponse() {
    }

    public CloseBooksResponse(String bookId, String title, String staus, String closedAt) {
        super();
        this.bookId = bookId;
        this.title = title;
        this.staus = staus;
        this.closedAt = closedAt;
    }

    @JsonProperty("bookId")
    public String getBookId() {
        return bookId;
    }

    @JsonProperty("bookId")
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("staus")
    public String getStaus() {
        return staus;
    }

    @JsonProperty("staus")
    public void setStaus(String staus) {
        this.staus = staus;
    }

    @JsonProperty("closed_at")
    public String getClosedAt() {
        return closedAt;
    }

    @JsonProperty("closed_at")
    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
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
        sb.append(CloseBooksResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("bookId");
        sb.append('=');
        sb.append(((this.bookId == null)?"<null>":this.bookId));
        sb.append(',');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("staus");
        sb.append('=');
        sb.append(((this.staus == null)?"<null>":this.staus));
        sb.append(',');
        sb.append("closedAt");
        sb.append('=');
        sb.append(((this.closedAt == null)?"<null>":this.closedAt));
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