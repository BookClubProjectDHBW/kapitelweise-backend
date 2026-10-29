package com.kapitelweise.kapitelweiseBackend.dao.impl;

import com.kapitelweise.kapitelweiseBackend.dao.MeetingDao;
import com.kapitelweise.kapitelweiseBackend.request.MeetingRequest;
import com.kapitelweise.kapitelweiseBackend.response.MeetingResponse;
import com.kapitelweise.kapitelweiseBackend.response.UserMeetingResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MeetingDaoImpl implements MeetingDao {

    private final JdbcTemplate jdbcTemplate;

    public MeetingDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MeetingResponse saveMeeting(Long clubId, MeetingRequest request) {
        String sql = """
                INSERT INTO club_meeting
                    (bookclub_id, meeting_date, title, description,
                     starts_at, ends_at, location, book_id)
                VALUES (?, ?, ?, ?, ?, ?, ?,
                    (SELECT id FROM club_book
                     WHERE bookclub_id = ?
                     AND book_status = 'CURRENT'
                     LIMIT 1))
                RETURNING id, book_id, title, description,
                          starts_at, ends_at, location
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new MeetingResponse(
                        rs.getLong("id"),
                        rs.getObject("book_id", Long.class),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getObject("starts_at", LocalDateTime.class),
                        rs.getObject("ends_at", LocalDateTime.class),
                        rs.getString("location")
                ),
                clubId,
                request.getStartsAt(),
                request.getTitle(),
                request.getDescription(),
                request.getStartsAt(),
                request.getEndsAt(),
                request.getLocation(),
                clubId
        );
    }

    @Override
    public MeetingResponse getMeetingForMember(Long clubId) {
        String sql = """
                SELECT id, book_id, title, description, starts_at, ends_at, location
                FROM club_meeting
                WHERE bookclub_id = ?; 
        """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new MeetingResponse(
                        rs.getLong("id"),
                        rs.getObject("book_id", Long.class),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getObject("starts_at", LocalDateTime.class),
                        rs.getObject("ends_at", LocalDateTime.class),
                        rs.getString("location")
        ),
                clubId
        );
    }

    public UserMeetingResponse saveAcceptMeeting(Long clubId, Long meetingId, String username, String responseStatus) {
        String sql = """
                INSERT INTO meeting_response 
                (meeting_id, user_id, bookclub_id, response_status, responded_at)
                Select ?, user_id, bookclub_id, ?
                from club_member
                where bookclub_id = ? and username = ?
                on conflict (meeting_id, user_id)
                do update set 
                response_status = EXCLUDED.response_status,
                responded_at = now()
                Returning meeting_id, user_id, response_status, responded_at                
        """;
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new UserMeetingResponse(
                rs.getLong("meeting_id"),
                rs.getLong("user_id"),
                rs.getString("response_status"),
                rs.getString("responded_at").toString()
                ),
                meetingId, clubId, username);
    }

    public List<UserMeetingResponse> getUserMeetingResponse(Long clubId){
        return null;
    }
}