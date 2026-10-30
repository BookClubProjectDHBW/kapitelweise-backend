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

    public String saveAcceptMeeting(Long clubId, Long meetingId, String username, String responseStatus) {
        String sql = """
                INSERT INTO meeting_response 
                (meeting_id, user_id, bookclub_id, response_status, responded_at)
                Select ?, user_id, bookclub_id, ?, now()
                from club_member
                where bookclub_id = ? and username = ?
                on conflict (meeting_id, user_id)
                do update set 
                response_status = EXCLUDED.response_status,
                responded_at = now()
                Returning meeting_id,
                    (SELECT username FROM club_member WHERE user_id = meeting_response.user_id AND bookclub_id = meeting_response.bookclub_id) AS username,
                    response_status, responded_at
        """;
        return "Answer saved";

    }

    @Override
    public boolean meetingExists(Long clubId, Long meetingId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM club_meeting WHERE bookclub_id = ? AND id = ?",
                Integer.class, clubId, meetingId);
        return count != null && count > 0;
    }

    @Override
    public boolean meetingHasStarted(Long clubId, Long meetingId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM club_meeting WHERE bookclub_id = ? AND id = ? AND starts_at <= CURRENT_TIMESTAMP",
                Integer.class, clubId, meetingId);
        return count != null && count > 0;
    }

    public List<UserMeetingResponse> getUserMeetingResponse(Long clubId, Long meetingId) {
        String sql = """
                select mr.meeting_id, cm.username, mr.response_status, mr.responded_at
                from meeting_response mr
                join club_member cm on cm.user_id = mr.user_id and cm.bookclub_id = mr.bookclub_id
                where mr.bookclub_id = ? and mr.meeting_id = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new UserMeetingResponse(
                rs.getLong("meeting_id"),
                rs.getString("username"),
                rs.getString("response_status"),
                rs.getTimestamp("responded_at").toString()
        ), clubId, meetingId);
    }
}
