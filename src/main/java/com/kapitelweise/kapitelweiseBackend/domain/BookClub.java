package com.kapitelweise.kapitelweiseBackend.domain;

import java.util.Objects;

public class BookClub {

    private String bookClubName;
    private String description;


    public BookClub(String bookClubName, String description){
        this.bookClubName = bookClubName;
        this.description = description;
    }

    public String getBookClubName() {
        return bookClubName;
    }

    public String getDescription() {
        return description;
    }

    public void setBookClubName(String bookClubName) {
        this.bookClubName = bookClubName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookClub bookclub = (BookClub) o;
        return Objects.equals(bookClubName, bookclub.bookClubName) && Objects.equals(description, bookclub.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookClubName, description);
    }

    public BookClub(String bookClubName, String description, String foundingDate){
        this.bookClubName = bookClubName;
        this.description = description;
    }
}
