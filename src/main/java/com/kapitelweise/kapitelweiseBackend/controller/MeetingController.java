
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

    @GetMapping("/clubs/{clubId}/meetings")
    public MeetingResponse getMeeting(@PathVariable("clubId") Long clubId, @AuthenticationPrincipal String username){
        return meetingService.getNextMeeting(clubId, username);
    }

    @PostMapping("/clubs/{clubId}/meeting-response/{meetingId}/attendance/{responseStatus}")
    public String acceptMeetingInvitation(@PathVariable Long clubId, @PathVariable Long meetingId, @AuthenticationPrincipal String username, @PathVariable String responseStatus){
         return meetingService.acceptMeeting(clubId, meetingId, username, responseStatus);

    }

    @GetMapping("/clubs/{clubId}/meeting")
    public List<UserMeetingResponse> getResponses(@PathVariable Long clubId, @AuthenticationPrincipal String username){
        return meetingService.getUserResponse(clubId,username);
    }

}
