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
        "id",
        "suggestion_status",
        "title",
        "isbn",
        "pages",
        "author",
        "first_publish_year",
        "suggested_by"
})
@Generated("jsonschema2pojo")
public class SuggestionsResponse {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("suggestion_status")
    private String suggestionStatus;
    @JsonProperty("title")
    private String title;
    @JsonProperty("isbn")
    private String isbn;
    @JsonProperty("pages")
    private Integer pages;
    @JsonProperty("author")
    private String author;
    @JsonProperty("first_publish_year")
    private Integer firstPublishYear;
    @JsonProperty("suggested_by")
    private String suggestedBy;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public SuggestionsResponse() {
    }

    public SuggestionsResponse(Long id, String suggestionStatus, String title, String isbn, Integer pages, String author, Integer firstPublishYear, String suggestedBy) {
        super();
        this.id = id;
        this.suggestionStatus = suggestionStatus;
        this.title = title;
        this.isbn = isbn;
        this.pages = pages;
        this.author = author;
        this.firstPublishYear = firstPublishYear;
        this.suggestedBy = suggestedBy;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("suggestion_status")
    public String getSuggestionStatus() {
        return suggestionStatus;
    }

    @JsonProperty("suggestion_status")
    public void setSuggestionStatus(String suggestionStatus) {
        this.suggestionStatus = suggestionStatus;
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
    public Integer getPages() {
        return pages;
    }

    @JsonProperty("pages")
    public void setPages(Integer pages) {
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

    @JsonProperty("first_publish_year")
    public Integer getFirstPublishYear() {
        return firstPublishYear;
    }

    @JsonProperty("first_publish_year")
    public void setFirstPublishYear(Integer firstPublishYear) {
        this.firstPublishYear = firstPublishYear;
    }

    @JsonProperty("suggested_by")
    public String getSuggestedBy() {
        return suggestedBy;
    }

    @JsonProperty("suggested_by")
    public void setSuggestedBy(String suggestedBy) {
        this.suggestedBy = suggestedBy;
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
        sb.append(SuggestionsResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("suggestionStatus");
        sb.append('=');
        sb.append(((this.suggestionStatus == null)?"<null>":this.suggestionStatus));
        sb.append(',');
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
        sb.append("suggestedBy");
        sb.append('=');
        sb.append(((this.suggestedBy == null)?"<null>":this.suggestedBy));
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
        result = ((result* 31)+((this.pages == null)? 0 :this.pages.hashCode()));
        result = ((result* 31)+((this.author == null)? 0 :this.author.hashCode()));
        result = ((result* 31)+((this.isbn == null)? 0 :this.isbn.hashCode()));
        result = ((result* 31)+((this.firstPublishYear == null)? 0 :this.firstPublishYear.hashCode()));
        result = ((result* 31)+((this.suggestionStatus == null)? 0 :this.suggestionStatus.hashCode()));
        result = ((result* 31)+((this.suggestedBy == null)? 0 :this.suggestedBy.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.additionalProperties == null)? 0 :this.additionalProperties.hashCode()));
        result = ((result* 31)+((this.title == null)? 0 :this.title.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof SuggestionsResponse) == false) {
            return false;
        }
        SuggestionsResponse rhs = ((SuggestionsResponse) other);
        return ((((((((((this.pages == rhs.pages)||((this.pages!= null)&&this.pages.equals(rhs.pages)))&&((this.author == rhs.author)||((this.author!= null)&&this.author.equals(rhs.author))))&&((this.isbn == rhs.isbn)||((this.isbn!= null)&&this.isbn.equals(rhs.isbn))))&&((this.firstPublishYear == rhs.firstPublishYear)||((this.firstPublishYear!= null)&&this.firstPublishYear.equals(rhs.firstPublishYear))))&&((this.suggestionStatus == rhs.suggestionStatus)||((this.suggestionStatus!= null)&&this.suggestionStatus.equals(rhs.suggestionStatus))))&&((this.suggestedBy == rhs.suggestedBy)||((this.suggestedBy!= null)&&this.suggestedBy.equals(rhs.suggestedBy))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.title == rhs.title)||((this.title!= null)&&this.title.equals(rhs.title))));
    }

}