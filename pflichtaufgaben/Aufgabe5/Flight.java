package de.hska.iwii.db1.jpa;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
public class Flight {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "flight_number")
	private String flightNumber;
	
    @NotNull
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    
    @NotNull
    @Column(name = "departure_airport")
    private String departureAirport;
    
    public Flight()
    {
    	
    }
    
    public Flight(String flightNumber, LocalDateTime departureTime, String departureAirport)
    {
    	this.flightNumber = flightNumber;
    	this.departureTime = departureTime;
    	this.departureAirport = departureAirport;
    }
    
    public String getFlightNumber()
    {
    	return this.flightNumber;
    }
}
