package com.kapitelweise.kapitelweiseBackend.service;

import com.kapitelweise.kapitelweiseBackend.modules.alexa.*;
import com.kapitelweise.kapitelweiseBackend.response.BookClubResponse;
import com.kapitelweise.kapitelweiseBackend.response.BookResponse;
import com.kapitelweise.kapitelweiseBackend.response.SuggestionsResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AlexaService {
    private final BookClubService bookClubService;
    private final JwtService jwtService;

    public AlexaService(BookClubService bookClubService, JwtService jwtService) {
        this.bookClubService = bookClubService;
        this.jwtService = jwtService;
    }

    public AlexaRO handle(AlexaRO request) {
        String type = request == null || request.getRequest() == null ? null : request.getRequest().getType();
        if ("LaunchRequest".equals(type)) {
            return response("Willkommen bei Kapitelweise. Frage mich nach dem aktuellen Buch oder den Buchvorschlägen deines Buchclubs.", false);
        }
        if (type == null || !"IntentRequest".equals(type)) {
            return response("Diesen Alexa-Aufruf kenne ich nicht.", true);
        }

        IntentRO intent = request.getRequest().getIntent();
        String name = intent == null ? null : intent.getName();
        if ("AMAZON.StopIntent".equals(name) || "AMAZON.CancelIntent".equals(name)) {
            return response("Bis zum nächsten Mal bei Kapitelweise.", true);
        }
        if ("AMAZON.HelpIntent".equals(name)) {
            return response("Du kannst nach dem aktuellen Buch oder den Vorschlägen fragen. Nenne dabei die Buchclub-ID.", false);
        }

        try {
            String username = linkedUsername(request);
            Long clubId = clubId(intent);



            //Ausgabe aller Buchclubs mit ID
            if ("ClubsIntent".equals(name)) {
                List<BookClubResponse> bookclubs = bookClubService.getClubs(username);
                String clubs = bookclubs.stream()
                        .map(club -> club.getClubname()  + ", mit der ID " + club.getClubId())
                        .reduce((a,b) -> a + ". " + b)
                        .orElse("Du bist aktuell in keinem Buchclub Mitglied");
                return response("Deine Buchclubs sind: " + clubs, true);
            }

            if (clubId == null) return response("Bitte nenne auch die ID deines Buchclubs.", false);
            //Für das bekommen des aktuellen Buchs
            if ("CurrentBookIntent".equals(name) || "GetCurrentBookIntent".equals(name)) {
                BookResponse book = bookClubService.getCurrentBook(clubId, username);
                return response("Das aktuelle Buch ist " + book.getTitle() + authorPhrase(book.getAuthor()) + ".", true);
            }


           // Für das Bekommen seiner Vorschläge
            if ("SuggestionsIntent".equals(name) || "GetSuggestionsIntent".equals(name)) {
                List<SuggestionsResponse> suggestions = bookClubService.returnAllSuggestions(clubId, username);
                if (suggestions.isEmpty()) return response("Für diesen Buchclub gibt es gerade keine Vorschläge.", true);
                String titles = suggestions.stream()
                        .map(SuggestionsResponse::getTitle)
                        .filter(title -> title != null && !title.isBlank())
                        .limit(5).reduce((a, b) -> a + ", " + b)
                        .orElse("keine benannten Vorschläge");
                return response("Die Buchvorschläge sind: " + titles + ".", true);
            }
            return response("Diesen Alexa-Aufruf kenne ich nicht. Frage nach dem aktuellen Buch oder den Vorschlägen.", true);


        } catch (JwtException | IllegalArgumentException e) {
            return response("Bitte verknüpfe zuerst dein Kapitelweise-Konto mit dieser Alexa-Skill.", true);
        } catch (ResponseStatusException e) {
            if (e.getStatusCode().value() == 409) return response("In diesem Buchclub gibt es gerade kein aktuelles Buch.", true);
            if (e.getStatusCode().value() == 403) return response("Dein verknüpftes Konto ist kein Mitglied dieses Buchclubs.", true);
            return response("Ich konnte die Buchclub-Daten gerade nicht laden.", true);
        }
    }

    //prüft die Anmeldung bzw. Token des Users
    private String linkedUsername(AlexaRO request) {
        SessionRO session = request.getSession();
        String token = session == null || session.getUser() == null ? null : session.getUser().getAccessToken();
        if (token == null || token.isBlank())
            throw new IllegalArgumentException("Alexa account linking token missing");
        Claims claims = jwtService.verifyTokenAndExtract(token);
        String subject = claims.getSubject();
        if (subject == null || subject.isBlank())
            throw new IllegalArgumentException("Token has no subject");
        return subject;
    }

    private Long clubId(IntentRO intent) {
        if (intent == null || intent.getSlots() == null) return null;
        for (String key : List.of("clubId", "club", "id")) {
            SlotsRO.SlotRO slot = intent.getSlots().getSlots().get(key);
            if (slot != null && slot.getValue() != null) {
                try { return Long.valueOf(slot.getValue()); }
                catch (NumberFormatException ignored) { return null; }
            }
        }
        return null;
    }

    private String authorPhrase(String author) {
        return author == null || author.isBlank() ? "" : " von " + author;
    }

    private AlexaRO response(String text, boolean endSession) {
        AlexaRO result = new AlexaRO("1.0");
        result.setResponse(new ResponseRO(new OutputSpeechRO("PlainText", text), endSession));
        return result;
    }
}
