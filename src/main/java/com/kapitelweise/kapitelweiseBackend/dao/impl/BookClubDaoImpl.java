package com.kapitelweise.kapitelweiseBackend.dao.impl;

import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.domain.BookClub;
import com.kapitelweise.kapitelweiseBackend.response.BookClubDetailResponse;
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
    public boolean bookclubExists(Long clubId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM bookclub WHERE id = ?",
                Integer.class, clubId);
        return count != null && count > 0;
    }

    @Override
    public Optional<String> getBookClubName(Long clubId) {
        return jdbcTemplate.queryForList(
                        "SELECT name FROM bookclub WHERE id = ?",
                        String.class, clubId)
                .stream()
                .findFirst();
    }

    @Override
    public List<BookClubResponse> getBookClubForMember(String username) {
        String sql = """
        select bookclub.id, bookclub.name,  bookclub.description
        from bookclub
        join clubmember on clubmember.bookclub_id = bookclub.id
        join users on users.id = clubmember.userid
        where users.username = ?;
        """;
        return jdbcTemplate.query(sql,(rs, rowNum) ->
                new BookClubResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ),

                        username
                );
        }
        @Override
        public BookClubDetailResponse getClubDetails(Long clubId, String username){
        String sql= """
                select bookclub.id, bookclub.name,  bookclub.description, clubmember.role
                from bookclub
                join clubmember on clubmember.bookclub_id = bookclub.id
                join users on users.id = clubmember.userid
                where bookclub.id = ? and users.username = ? ;
                """;
            return jdbcTemplate.queryForObject(sql,(rs, rowNum) ->
                            new BookClubDetailResponse(
                                    rs.getLong("id"),
                                    rs.getString("name"),
                                    rs.getString("description"),
                                    rs.getString("role")
                            ),

                     clubId, username
            );
        }


    @Override
    public Long saveBooksSuggestion(Long suggestionId) {
        List<Long> bookIds = jdbcTemplate.query(
                """
                WITH source AS (
                    SELECT title, author, isbn
                    FROM found_book
                    WHERE found_book_id = ?
                ),
                inserted AS (
                    INSERT INTO book (title, author, isbn)
                    SELECT title, author, isbn
                    FROM source
                    ON CONFLICT (isbn) DO NOTHING
                    RETURNING book_id
                )
                SELECT book_id FROM inserted
                UNION ALL
                SELECT book.book_id
                FROM book 
                JOIN source  ON source.isbn = book.isbn
                LIMIT 1
                """,
                (rs, rowNum) -> rs.getLong("book_id"),
                suggestionId
        );
        if (bookIds.isEmpty()) {
            throw new org.springframework.dao.EmptyResultDataAccessException(
                    "Kein Treffer mit found_book_id=" + suggestionId, 1);
        }
        return bookIds.get(0);
    }
}
