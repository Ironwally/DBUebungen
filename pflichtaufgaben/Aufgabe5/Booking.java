package de.hska.iwii.db1.jpa;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name ="customer_id")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "flight_id")
	private Flight flight;
	
	@NotNull
	@Column(name = "booking_date")
	private LocalDateTime bookingDate;
	
	@Min(value=1)
	@Column(name ="number_of_seats")
	private int numberOfSeats;
	
    
	
    public Booking()
    {
    	
    }
    
    public Booking(Customer customer, Flight flight, LocalDateTime bookingDate, int numberOfSeats)
    {
    	this.customer = customer;
    	this.flight = flight;
    	this.bookingDate = bookingDate;
    	this.numberOfSeats = numberOfSeats;
    }

	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
    
	public Customer getCustomer()
	{
		return this.customer;
	}
	
	public Flight getFlight()
	{
		return this.flight;
	}
	
	public int getNumberOfSeats()
	{
		return this.numberOfSeats;
	}
	
	public LocalDateTime getBookingDate()
	{
		return this.bookingDate;
	}
}
