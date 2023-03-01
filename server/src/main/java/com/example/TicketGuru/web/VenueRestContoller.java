package com.example.TicketGuru.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.TicketGuru.domain.Event;
import com.example.TicketGuru.domain.Venue;
import com.example.TicketGuru.domain.VenueRepository;

@RestController
public class VenueRestContoller {

	@Autowired
	VenueRepository venueRepository;
	
	// palauttaa listan tapahtumapaikoista
	@GetMapping("/venues")
	public Iterable<Venue> getVenues(){
		return venueRepository.findAll();
	}
	
	// Palauttaa tapahtumapaikan id:llä
	@GetMapping("/venues/{venueId}")
	public Optional<Venue> getVenue(@PathVariable("venueId") Long venueId) {
		return venueRepository.findById(venueId);
	}
	
	// lisää uuden tapahtumapaikan
	@PostMapping("/venues")
	Venue newVenue(@RequestBody Venue newVenue) {
		return venueRepository.save(newVenue);
	}
	
	// muokkaa tapahtumapaikkaa, jolla valittu venueId
	// jos tapahtumapaikkaa ei löydy ID:llä, metodi luo uuden tapahtuman seuraavalla vapaalla id:llä
	@PutMapping("/venues/{venueId}")
	Venue replaceVenue(@RequestBody Venue editedVenue, @PathVariable("venueId") Long venueId) {
		editedVenue.setVenueId(venueId);
		return venueRepository.save(editedVenue);
	}
	//Poistaa tapahtumapaikan id:n perusteella
	@DeleteMapping(value = "/delete_venue/{venueId}")
	public Iterable<Venue> deleteVenue(@PathVariable("venueId") Long venueId) {
		venueRepository.deleteById(venueId);
		return venueRepository.findAll();
		}
		
}
