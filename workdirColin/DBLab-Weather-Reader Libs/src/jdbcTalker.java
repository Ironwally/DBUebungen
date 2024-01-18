import dbClasses.Weather_db;
import de.hska.iwii.db1.weather.model.Weather;
import de.hska.iwii.db1.weather.model.WeatherForecast;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class jdbcTalker {
	private EntityManagerFactory entityManagerFactory;

	public jdbcTalker() throws ClassNotFoundException {
		Logger.getLogger("org.hibernate").setLevel(Level.ALL);

		entityManagerFactory = Persistence.createEntityManagerFactory("irondb");
	}

	public int getStations(int year,int month, int day, int minT, int maxT) {
		Date date = new Date(year,month,day);
		if (date == null) {
			System.out.println("Error: Bad Date.");
			return 0;
		}

		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		String jpql = "SELECT w.station_id FROM Weather_db w where w.date = :date and w.maxTemp <= :maxT and w.minTemp >= :minT";
		Query query = (Query) entityManager.createQuery(jpql);
		query.setParameter("date",date);
		query.setParameter("maxT", maxT);
		query.setParameter("minT", minT);
		List<Integer> wf = query.getResultList();
		for (Integer station : wf) {
			System.out.println("station_id: " + station + "\n");
		}
		transaction.commit();
		entityManager.close();

		return 1;
	}
	public void getWeather(int id) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();
		String jpql = "SELECT w FROM Weather_db w where w.station_id = :station_id";
		Query query = (Query) entityManager.createQuery(jpql);
		query.setParameter("station_id", id);
		List<Weather_db> wf = query.getResultList();
        System.out.println("Station_id: " + id);
        for (Weather_db weather : wf) {
            System.out.println("date: " + weather.getDate() +
					"\n minTemp: " + weather.getMinTemp() +
					"\n maxTemp: " + weather.getMaxTemp() +
					"\n sunshine: " + weather.getSunshine() +
					"\n precepitation: " + weather.getPrecipitation())
					;
        }
		transaction.commit();
		entityManager.close();

	}
	public void addWeather(WeatherForecast wfparam, Date queryDate, int station_id)
	{
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = null;

		//create the table objects
		ArrayList<Weather_db> wtlist = null;
		for (Weather wt : wfparam.getWeather()) {
            wtlist.add(new Weather_db(1L, queryDate, wt.getMinTemp(), wt.getMaxTemp(), wt.getPrecipitation(), wt.getSunshine(), station_id));
		}

		transaction = entityManager.getTransaction();
		transaction.begin();

		//with hibernate
//		for (Weather_db wdb : wtlist) {
//			entityManager.persist(wdb);
//		}
//		entityManager.persist(q);

		//with batch processing
		Long queryId;
		ArrayList<Long> weatherIds = new ArrayList<>();

		String jpql = "CREATE TABLE IF NOT EXISTS Query_db " +
				"(id BIGINT PRIMARY KEY, " +
				"queryDate DATE NOT NULL, forecast_id BIGINT NOT NULL)";
		Query query = (Query) entityManager.createNativeQuery(jpql);
		query.executeUpdate();

		jpql = "CREATE TABLE IF NOT EXISTS Weather_db (id BIGINT PRIMARY KEY, " +
				"date DATE NOT NULL, minTemp FLOAT, maxTemp FLOAT, precipitation INT, sunshine INT, " +
				"station_id INT)";
		query = (Query) entityManager.createNativeQuery(jpql);
		query.executeUpdate();

		jpql = "CREATE TABLE IF NOT EXISTS Query_Weather (query_id BIGINT, weather_id BIGINT, PRIMARY KEY (query_id, weather_id))";
		query  = (Query) entityManager.createNativeQuery(jpql);
		query.executeUpdate();

		jpql = "INSERT INTO Query_db VALUES (default, :queryDate) RETURNING id";
		query = (Query) entityManager.createNativeQuery(jpql);
		query.setParameter("queryDate", queryDate);
		queryId = (Long) query.getSingleResult(); // get the generated query id
		query.executeUpdate();

		jpql = "INSERT INTO Weather_db VALUES (default, :date, :minTemp, :maxTemp, :pre, :suns, :station) RETURNING id";
		query = (Query) entityManager.createQuery(jpql);
		for (Weather wt : wfparam.getWeather()) {
			query.setParameter("date", wt.getDate());
			query.setParameter("minTemp",wt.getMinTemp());
			query.setParameter("maxTemp", wt.getMaxTemp());
			query.setParameter("pre", wt.getPrecipitation());
			query.setParameter("suns", wt.getSunshine());
			query.setParameter("station", station_id);
			weatherIds.add((Long) query.getSingleResult()); // get the generated weather id
			query.executeUpdate();
		}
		// insert into the Query_Weather table
		jpql = "INSERT INTO Query_Weather VALUES (:query_id, :weather_id)";
		query = (Query) entityManager.createNativeQuery(jpql);
		for (Long weatherId : weatherIds) {
			query.setParameter("query_id", queryId);
			query.setParameter("weather_id", weatherId);
			query.executeUpdate();
		}
		transaction.commit();
		entityManager.close();
	}
	public void testFlights() 
	{
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction transaction = null;
		
		transaction = entityManager.getTransaction();
		transaction.begin();
		
//		Customer customer1 = new Customer("Patrick", "Korb", "patrick@web.de");
//		Customer customer2 = new Customer("Colin", "Bankert", "colin@gmail.com");
//        entityManager.persist(customer1);
//        entityManager.persist(customer2);
//
//		Flight flight1 = new Flight("FL1", LocalDateTime.now(), "FRA");
//		Flight flight2 = new Flight("FL2", LocalDateTime.now(), "BAW");
//		Flight flight3 = new Flight("FL3", LocalDateTime.now(), "NED");
//		entityManager.persist(flight1);
//        entityManager.persist(flight2);
//        entityManager.persist(flight3);
//
//
//        Booking booking1 = new Booking(customer1, flight1, LocalDateTime.now(), 2);
//        Booking booking2 = new Booking(customer2, flight2, LocalDateTime.now(), 2);
//		entityManager.persist(booking1);
//        entityManager.persist(booking2);
//
        transaction.commit();
        getCustomerBookings(entityManager, transaction, "Korb");

	}
	
	private void getCustomerBookings(EntityManager entityManager, EntityTransaction transaction, String lastName)
	{
		transaction.begin();
//		String jpql = "SELECT b FROM Booking b JOIN b.customer c WHERE c.lastName = :lastName";
//		Query query = (Query) entityManager.createQuery(jpql);
//		query.setParameter("lastName", lastName);
//		List<Booking> bookings = query.getResultList();
//        System.out.println("Buchungen des Kunden: " + lastName);
//        for (Booking booking : bookings) {
//            System.out.println("Booking ID: " + booking.getId() +
//                    ", Flugnummer: " + booking.getFlight().getFlightNumber() +
//                    ", Anzahl der Plätze: " + booking.getNumberOfSeats() +
//                    ", Buchungsdatum: " + booking.getBookingDate());
//        }
        transaction.commit();
        entityManager.close();
	}
	
	
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}
}
