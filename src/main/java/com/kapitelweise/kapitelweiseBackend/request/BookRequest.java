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
        "title",
        "isbn",
        "pages",
        "author",
        "firstPublishYear"
})
@Generated("jsonschema2pojo")
public class BookRequest {

    @JsonProperty("title")
    private String title;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("pages")
    private String pages;
    @JsonProperty("author")
    private String author;
    @JsonProperty("firstPublishYear")
    private String firstPublishYear;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public BookRequest() {
    }

    public BookRequest(String title, String isbn, String pages, String author, String firstPublishYear) {
        super();
        this.title = title;
        this.isbn = isbn;
        this.pages = pages;
        this.author = author;
        this.firstPublishYear = firstPublishYear;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("isbn")
    public String getIsbn() {
        return isbn;
    }

    @JsonProperty("isbn")
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @JsonProperty("pages")
    public String getPages() {
        return pages;
    }

    @JsonProperty("pages")
    public void setPages(String pages) {
        this.pages = pages;
    }

    @JsonProperty("author")
    public String getAuthor() {
        return author;
    }

    @JsonProperty("author")
    public void setAuthor(String author) {
        this.author = author;
    }

    @JsonProperty("firstPublishYear")
    public String getFirstPublishYear() {
        return firstPublishYear;
    }

    @JsonProperty("firstPublishYear")
    public void setFirstPublishYear(String firstPublishYear) {
        this.firstPublishYear = firstPublishYear;
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
        sb.append(BookRequest.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("title");
        sb.append('=');
        sb.append(((this.title == null)?"<null>":this.title));
        sb.append(',');
        sb.append("isbn");
        sb.append('=');
        sb.append(((this.isbn == null)?"<null>":this.isbn));
        sb.append(',');
        sb.append("pages");
        sb.append('=');
        sb.append(((this.pages == null)?"<null>":this.pages));
        sb.append(',');
        sb.append("author");
        sb.append('=');
        sb.append(((this.author == null)?"<null>":this.author));
        sb.append(',');
        sb.append("firstPublishYear");
        sb.append('=');
        sb.append(((this.firstPublishYear == null)?"<null>":this.firstPublishYear));
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