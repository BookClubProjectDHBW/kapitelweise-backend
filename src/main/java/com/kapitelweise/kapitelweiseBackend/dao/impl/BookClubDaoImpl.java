package com.kapitelweise.kapitelweiseBackend.dao.impl;

import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.domain.BookClub;
import com.kapitelweise.kapitelweiseBackend.response.BookClubResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public class BookClubDaoImpl implements BookclubDao {

    private final JdbcTemplate jdbcTemplate;

    public BookClubDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BookClub createBookclub(BookClub bookClub){

        return jdbcTemplate.queryForObject("insert into bookclub(name, description) Values(?, ?) Returning id, name, description, created_at",
                (rs, rowNum) -> new BookClub(
                    rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ), bookClub.getName(), bookClub.getDescription());
    }


    @Override
    public void addMember(Long bookclubId, String username, String role){
        Long userId = jdbcTemplate.queryForObject("Select id from users where username = ?", Long.class, username);
        jdbcTemplate.update("Insert into clubmember (bookclub_id, userId, role) Values(?,?,?)", bookclubId, userId, role);
    }


    @Override
    public List<BookClubResponse> getBookClubForMember(String username) {
        String sql = """
        select bookclub.name,  bookclub.description, clubmember.role
        from bookclub
        join clubmember on clubmember.bookclub_id = bookclub.id
        join users on users.id = clubmember.userid
        where username = ?;
        """;
        return jdbcTemplate.query(sql,(rs, rowNum) ->
                new BookClubResponse(
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("role")
                 ),

                username
        );
        }
}
