
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


@GetMapping("/clubs/{clubId}/meeting")
public List<MeetingResponse> getUpcomingMeetings(
        @PathVariable("clubId") Long clubId,
        Principal principal) {

    return meetingService.getUpcomingMeetings(
            clubId,
            principal.getName()
    );
}


    @PutMapping("/clubs/{clubId}/meetings/{meetingId}/attendance/{responseStatus}")
    public String acceptMeetingInvitation(@PathVariable Long clubId, @PathVariable Long meetingId, @AuthenticationPrincipal String username, @PathVariable String responseStatus){
         return meetingService.acceptMeeting(clubId, meetingId, username, responseStatus);

    }

    @GetMapping("/clubs/{clubId}/meetings/{meetingId}/attendance")
    public List<UserMeetingResponse> getResponses(@PathVariable Long clubId,@PathVariable Long meetingId, @AuthenticationPrincipal String username){
        return meetingService.getUserResponse(clubId, meetingId, username);
    }

}
