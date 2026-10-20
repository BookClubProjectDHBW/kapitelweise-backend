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
        "book_status",
        "title",
        "isbn",
        "pages",
        "author",
        "first_publish_year",
        "started_at",
        "finished_at"
})
@Generated("jsonschema2pojo")
public class BookResponse {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("book_status")
    private String bookStatus;
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
    @JsonProperty("started_at")
    private LocalDateTime startedAt;
    @JsonProperty("finished_at")
    private LocalDateTime finishedAt;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public BookResponse() {
    }

    public BookResponse(Long id, String bookStatus, String title, String isbn, Integer pages, String author, Integer firstPublishYear, LocalDateTime startedAt, LocalDateTime finishedAt) {
        super();
        this.id = id;
        this.bookStatus = bookStatus;
        this.title = title;
        this.isbn = isbn;
        this.pages = pages;
        this.author = author;
        this.firstPublishYear = firstPublishYear;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("book_status")
    public String getBookStatus() {
        return bookStatus;
    }

    @JsonProperty("book_status")
    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
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

    @JsonProperty("started_at")
    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    @JsonProperty("started_at")
    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    @JsonProperty("finished_at")
    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    @JsonProperty("finished_at")
    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
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
        sb.append(BookResponse.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("bookStatus");
        sb.append('=');
        sb.append(((this.bookStatus == null)?"<null>":this.bookStatus));
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
        sb.append("startedAt");
        sb.append('=');
        sb.append(((this.startedAt == null)?"<null>":this.startedAt));
        sb.append(',');
        sb.append("finishedAt");
        sb.append('=');
        sb.append(((this.finishedAt == null)?"<null>":this.finishedAt));
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
        result = ((result* 31)+((this.bookStatus == null)? 0 :this.bookStatus.hashCode()));
        result = ((result* 31)+((this.startedAt == null)? 0 :this.startedAt.hashCode()));
        result = ((result* 31)+((this.finishedAt == null)? 0 :this.finishedAt.hashCode()));
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
        if ((other instanceof BookResponse) == false) {
            return false;
        }
        BookResponse rhs = ((BookResponse) other);
        return (((((((((((this.pages == rhs.pages)||((this.pages!= null)&&this.pages.equals(rhs.pages)))&&((this.author == rhs.author)||((this.author!= null)&&this.author.equals(rhs.author))))&&((this.isbn == rhs.isbn)||((this.isbn!= null)&&this.isbn.equals(rhs.isbn))))&&((this.firstPublishYear == rhs.firstPublishYear)||((this.firstPublishYear!= null)&&this.firstPublishYear.equals(rhs.firstPublishYear))))&&((this.bookStatus == rhs.bookStatus)||((this.bookStatus!= null)&&this.bookStatus.equals(rhs.bookStatus))))&&((this.startedAt == rhs.startedAt)||((this.startedAt!= null)&&this.startedAt.equals(rhs.startedAt))))&&((this.finishedAt == rhs.finishedAt)||((this.finishedAt!= null)&&this.finishedAt.equals(rhs.finishedAt))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.additionalProperties == rhs.additionalProperties)||((this.additionalProperties!= null)&&this.additionalProperties.equals(rhs.additionalProperties))))&&((this.title == rhs.title)||((this.title!= null)&&this.title.equals(rhs.title))));
    }

}
