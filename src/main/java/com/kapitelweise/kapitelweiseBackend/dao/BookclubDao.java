package com.kapitelweise.kapitelweiseBackend.dao;

import com.kapitelweise.kapitelweiseBackend.domain.BookClub;

import java.util.Optional;

public interface BookclubDao {
     Long createBookclub(BookClub bookClub);

     void addMember(Long clubId, String username);

    Optional<BookClub> getBookClub(Long clubId);
}
