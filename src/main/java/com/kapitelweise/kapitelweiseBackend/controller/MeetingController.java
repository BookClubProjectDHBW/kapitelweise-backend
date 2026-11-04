
package com.kapitelweise.kapitelweiseBackend.controller;

import com.kapitelweise.kapitelweiseBackend.request.MeetingRequest;
import com.kapitelweise.kapitelweiseBackend.response.MeetingResponse;
import com.kapitelweise.kapitelweiseBackend.response.UserMeetingResponse;
import com.kapitelweise.kapitelweiseBackend.service.MeetingService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping("/clubs/{clubId}/meetings")
    @ResponseStatus(HttpStatus.CREATED)
    public MeetingResponse createMeeting(@PathVariable("clubId") Long clubId, @RequestBody MeetingRequest request, Principal principal) {

        return meetingService.createMeeting(clubId, request, principal.getName()
        );
    }


@GetMapping("/clubs/{clubId}/meetings")
public List<MeetingResponse> getUpcomingMeetings(
        @PathVariable("clubId") Long clubId,
        Principal principal) {

    return meetingService.getUpcomingMeetings(
            clubId,
            principal.getName()
    );
}

@PutMapping("/clubs/{clubId}/meetings/{meetingId}/attendance")
public Map<String, String> acceptMeetingInvitation(
        @PathVariable Long clubId,
        @PathVariable Long meetingId,
        Principal principal,
        @RequestBody Map<String, String> request) {

    String status = request.get("status");

    meetingService.acceptMeeting(
            clubId, meetingId, principal.getName(), status
    );

    return Map.of("status", status);
}


    @GetMapping("/clubs/{clubId}/meetings/{meetingId}/attendance")
    public List<UserMeetingResponse> getResponses(@PathVariable Long clubId,@PathVariable Long meetingId, @AuthenticationPrincipal String username){
        return meetingService.getUserResponse(clubId, meetingId, username);
    }

}
