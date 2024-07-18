package com.rockseat.planner.trip;

import com.rockseat.planner.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trips")

public class TripController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private TripRepository repository;

    @PostMapping
    public ResponseEntity<TripCreateResponse> createTrip(@RequestBody TripRequestPayload payload) {
        Trip newTRip = new Trip(payload);

        this.repository.save(newTRip);

        this.participantService.registerParticipantsToEvent(payload.emails_to_invite());

        return ResponseEntity.ok(body: new TripCreateResponse(newTRip.getId());
    }
    @GetMapping("*/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id){
       Optional<Trip> trip = this.repository.findById(id);

       return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
