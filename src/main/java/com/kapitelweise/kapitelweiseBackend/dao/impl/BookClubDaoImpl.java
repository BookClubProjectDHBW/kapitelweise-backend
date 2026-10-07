package com.kapitelweise.kapitelweiseBackend.dao.impl;

import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.domain.BookClub;
import com.kapitelweise.kapitelweiseBackend.response.BookClubResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;



@Repository
public class BookClubDaoImpl implements BookclubDao {

    private final JdbcTemplate jdbcTemplate;

    public BookClubDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long createBookclub(BookClub bookClub){

        return jdbcTemplate.queryForObject("insert into bookclub(name, description) Values(?, ?) Returning id", Long.class, bookClub.getBookClubName(), bookClub.getDescription());
    }


    @Override
    public void addMember(Long clubId, String username){

    }


    @Override
    public Optional<BookClub> getBookClub(Long clubId) {
        return Optional.of(new BookClub("hallo", "hallo", "19.09.2025"));
    }

    @Override
    public Optional<BookClubResponse> getBookClubForMember(Long clubId, String username){
        String sql = """
        select bookclub.name,  bookclub.description, clubmember.role, clubmember.join_date
        from bookclub
        join clubmember on clubmember.bookclub_id = bookclub.id
        join users on users.id = clubmember.userid
        where bookclub_id = ? and username = ?;
        """;
        return jdbcTemplate.query(sql,(rs, rowNum) ->
                new BookClubResponse(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("role")
                 ),
                clubId, username
        ).stream().findFirst();
    }
}
