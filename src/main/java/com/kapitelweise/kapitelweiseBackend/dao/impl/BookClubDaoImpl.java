package com.kapitelweise.kapitelweiseBackend.dao.impl;

import com.kapitelweise.kapitelweiseBackend.dao.BookclubDao;
import com.kapitelweise.kapitelweiseBackend.domain.SuggestionEvent;
import com.kapitelweise.kapitelweiseBackend.request.BookRequest;
import com.kapitelweise.kapitelweiseBackend.response.BookResponse;
import com.kapitelweise.kapitelweiseBackend.response.SuggestionsResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Repository
public class BookClubDaoImpl implements BookclubDao {

    private final JdbcTemplate jdbcTemplate;

    public BookClubDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void deleteSuggestion(String username, Long clubId){
        jdbcTemplate.update("""
        Delete from club_suggestions where bookclub_id = ? and suggested_by = ?""",
                clubId, username);
    }

    @Override
    public void markSuggestionReady(Long suggestionId, String title, String author, Integer pages, Integer firstPublishYear){
        jdbcTemplate.update("""
        Update club_suggestions
        set suggestion_status = 'READY',
            title = ?,
            author = ?,
            pages = ?,
            first_publish_year = ?
        where id = ? and suggestion_status = 'PENDING'""",
                title, author, pages, firstPublishYear, suggestionId);
    }

    @Override
    public void markSuggestionRejected(Long suggestionId){
        jdbcTemplate.update("""
        Update club_suggestions
        set suggestion_status = 'REJECTED'
        where id = ? and suggestion_status = 'PENDING'""",
                suggestionId);
    }

    @Override
    public boolean isUserAdmin(String username, Long bookClubId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM club_member WHERE username = ? AND bookclub_id = ? AND role = 'ADMIN'",
                Integer.class, username, bookClubId);
        return count != null && count > 0;
    }

    @Override
    public boolean isMember(String username, Long clubId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM club_member WHERE username = ? and bookclub_id = ?",
                Integer.class, username, clubId);
        return count != null && count > 0;
    }


    // RETURNING liefert die neue Zeile inkl. generierter id zurück (wird für die Queue und das Frontend gebraucht)
    public SuggestionsResponse saveBooksSuggestion(Long clubId, BookRequest request, String username) {
        String sql = """
                INSERT INTO club_suggestions
                    (bookclub_id, title, isbn, pages, author, first_publish_year, suggested_by)
                VALUES (?, ?, ?, ?, ?, ?,?)
                RETURNING id, suggestion_status, title, isbn, pages, author, first_publish_year, suggested_by
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                        new SuggestionsResponse(
                                rs.getLong("id"),
                                rs.getString("suggestion_status"),
                                rs.getString("title"),
                                rs.getString("isbn"),
                                rs.getObject("pages", Integer.class),
                                rs.getString("author"),
                                rs.getObject("first_publish_year", Integer.class),
                                rs.getString("suggested_by")
                        ),
                clubId,
                request.getTitle(),
                request.getIsbn(),
                request.getPages(),
                request.getAuthor(),
                request.getFirstPublishYear(),
                username);
    }

    public boolean checkExistence(Long clubId, Long proposalId){
        Integer isThisSuggestionValid = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM club_suggestions WHERE id = ? and bookclub_id = ?",
                Integer.class, proposalId, clubId);
        return isThisSuggestionValid != null && isThisSuggestionValid > 0;
    }

    public boolean checkStatus(Long clubId, Long proposalId){
        Integer isThisSuggestionValid = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM club_suggestions WHERE id = ? and bookclub_id = ? and suggestion_status = 'READY'",
                Integer.class, proposalId, clubId);
        return isThisSuggestionValid != null && isThisSuggestionValid > 0;
    }

    public boolean activeBook(Long clubId){
        Integer activeBooks = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM club_book WHERE bookclub_id = ? and book_status = 'CURRENT' ",
                Integer.class, clubId);
        return activeBooks != null && activeBooks > 0;
    }

    // kopiert den Vorschlag nach club_book, book_status und started_at kommen aus den DB-Defaults
    public BookResponse saveCurrentBook(Long clubId, Long proposalId) {
        String sql = """
                INSERT INTO club_book (bookclub_id, title, isbn, pages, author, first_publish_year)
                SELECT bookclub_id, title, isbn, pages, author, first_publish_year
                FROM club_suggestions
                WHERE id = ? AND bookclub_id = ?
                RETURNING id, book_status, title, isbn, pages, author, first_publish_year, started_at, finished_at
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                        new BookResponse(
                                rs.getLong("id"),
                                rs.getString("book_status"),
                                rs.getString("title"),
                                rs.getString("isbn"),
                                rs.getObject("pages", Integer.class),
                                rs.getString("author"),
                                rs.getObject("first_publish_year", Integer.class),
                                rs.getObject("started_at", LocalDateTime.class),
                                rs.getObject("finished_at", LocalDateTime.class)
                        ),
                proposalId, clubId);
    }

    @Override
    public void deleteSuggestions(Long clubId){
        jdbcTemplate.update("""
            DELETE From club_suggestions where bookclub_id = ?""", clubId);
    }

    @Override
    public List<SuggestionEvent> getPendingSuggestions(){
        String sql = """
                SELECT id, bookclub_id, isbn
                FROM club_suggestions
                WHERE suggestion_status = 'PENDING'
                and created_at < now() - interval '2 minutes'
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new SuggestionEvent(
                        "SEND_SUGGESTION",
                        rs.getLong("id"),
                        rs.getLong("bookclub_id"),
                        rs.getString("isbn")
                ));
    }

    @Override
    public BookResponse getCurrentBook(Long clubId){
        String sql = """
                Select id, book_status, title, isbn, pages, author, first_publish_year, started_at, finished_at from club_book where bookclub_id = ? and book_status = 'CURRENT'
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new BookResponse(
                        rs.getLong("id"),
                        rs.getString("book_status"),
                        rs.getString("title"),
                        rs.getString("isbn"),
                        rs.getObject("pages", Integer.class),
                        rs.getString("author"),
                        rs.getObject("first_publish_year", Integer.class),
                        rs.getObject("started_at", LocalDateTime.class),
                        rs.getObject("finished_at", LocalDateTime.class)
                ),clubId );
    }

    public List<SuggestionsResponse> getBookSuggestions(Long clubId, String username){
        String sql = """
            SELECT id, suggestion_status, title, isbn, pages, author, first_publish_year, suggested_by
            FROM club_suggestions
            WHERE bookclub_id = ?
            and (suggestion_status = 'READY'
                or suggested_by = ?)
        """;
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                        new SuggestionsResponse(
                                rs.getLong("id"),
                                rs.getString("suggestion_status"),
                                rs.getString("title"),
                                rs.getString("isbn"),
                                rs.getObject("pages", Integer.class),
                                rs.getString("author"),
                                rs.getObject("first_publish_year", Integer.class),
                                rs.getString("suggested_by")
                        ),
                clubId, username
        );

    }

}
