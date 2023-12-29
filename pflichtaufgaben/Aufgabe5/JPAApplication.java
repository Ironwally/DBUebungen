package de.hska.iwii.db1.jpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.query.Query;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JPAApplication {
	private EntityManagerFactory entityManagerFactory;

	public JPAApplication() {
		Logger.getLogger("org.hibernate").setLevel(Level.ALL);
		entityManagerFactory = Persistence.createEntityManagerFactory("DB1");
	}
	
	public void testFlights() 
	{
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = null;
		
		transaction = entityManager.getTransaction();
		transaction.begin();
		
		Customer customer1 = new Customer("Patrick", "Korb", "patrick@web.de");
		Customer customer2 = new Customer("Colin", "Bankert", "colin@gmail.com");
        entityManager.persist(customer1);
        entityManager.persist(customer2);
		
		Flight flight1 = new Flight("FL1", LocalDateTime.now(), "FRA");
		Flight flight2 = new Flight("FL2", LocalDateTime.now(), "BAW");
		Flight flight3 = new Flight("FL3", LocalDateTime.now(), "NED");
		entityManager.persist(flight1);
        entityManager.persist(flight2);
        entityManager.persist(flight3);
        
        
        Booking booking1 = new Booking(customer1, flight1, LocalDateTime.now(), 2);
        Booking booking2 = new Booking(customer2, flight2, LocalDateTime.now(), 2);
		entityManager.persist(booking1);
        entityManager.persist(booking2);
        
        transaction.commit();
        getCustomerBookings(entityManager, transaction, "Korb");

	}
	
	private void getCustomerBookings(EntityManager entityManager, EntityTransaction transaction, String lastName)
	{
		transaction.begin();
		String jpql = "SELECT b FROM Booking b JOIN b.customer c WHERE c.lastName = :lastName";
		Query query = (Query) entityManager.createQuery(jpql); 
		query.setParameter("lastName", lastName);
		List<Booking> bookings = query.getResultList();
        System.out.println("Buchungen des Kunden: " + lastName);
        for (Booking booking : bookings) {
            System.out.println("Booking ID: " + booking.getId() +
                    ", Flugnummer: " + booking.getFlight().getFlightNumber() +
                    ", Anzahl der Plätze: " + booking.getNumberOfSeats() +
                    ", Buchungsdatum: " + booking.getBookingDate());
        }
        transaction.commit();
        entityManager.close();
	}
	
	
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}
	
	public static void main(String[] args) {
		JPAApplication app = new JPAApplication();
		app.testFlights();
	}
}
