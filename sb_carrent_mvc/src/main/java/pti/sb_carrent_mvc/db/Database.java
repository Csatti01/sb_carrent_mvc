package pti.sb_carrent_mvc.db;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.SelectionQuery;
import org.springframework.stereotype.Repository;

import pti.sb_carrent_mvc.model.Booking;
import pti.sb_carrent_mvc.model.Car;

@Repository
public class Database {
	
	private SessionFactory sessionFactory;
	
	public Database () {
		
		Configuration cfg = new Configuration();
		cfg.configure();
		
		this.sessionFactory = cfg.buildSessionFactory();
		
	}

	public List<Car> getAllCars() {
		
		List <Car> cars = null;
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		SelectionQuery <Car> query = session.createSelectionQuery(
					"SELECT c FROM Car c", Car.class);
		
		cars = query.getResultList();
		
		tx.commit();
		session.close();

		return cars;
	}

	public List<Booking> getAllBookingsByDate(LocalDate startDate, LocalDate endDate) {
		
		List<Booking> bookingsList = null;

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		SelectionQuery<Booking> query = session.createSelectionQuery(
						"FROM Booking b WHERE " +									// azt vizsgáljuk, hogy
					    "(b.startDate BETWEEN :startDate AND :endDate) " + 			// egy foglalás kezdete beleesik a megadott időintervallumba
					    "OR (b.endDate BETWEEN :startDate AND :endDate) " +			// egy foglalás vége esik-e bele a megadott időintervallumba.
					    "OR (b.startDate <= :startDate AND b.endDate >= :endDate)", // foglalás teljes egészében lefedi a keresett időintervallumot
					    Booking.class);

		query.setParameter("startDate", startDate);
		query.setParameter("endDate", endDate);

		bookingsList = query.getResultList();

		tx.commit();
		session.close();

		return bookingsList;
	}

	public Car getCarById(int carId) {
		
		Car car = null;
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		car = session.get(Car.class, carId);
		
		tx.commit();
		session.close();
		
		return car;

	}

	public void saveNewAutoRent(Booking bookedCar) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.persist(bookedCar);
		
		tx.commit();
		session.close();
		
	}
	
	public List<Booking> getAllBookings() {
		
		List<Booking> bookings = null;
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		SelectionQuery <Booking> query = session.createSelectionQuery(
					"SELECT b FROM Booking b", Booking.class);
		
		bookings = query.getResultList();
		
		tx.commit();
		session.close();
		
		return bookings;
	}

	public void persistNewCar(Car newCar) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.persist(newCar);
		
		tx.commit();
		session.close();
	}
	
	public void updateCar(Car car) {
		
	    Session session = sessionFactory.openSession();
	    Transaction tx = session.beginTransaction();
	    
	    session.merge(car);
	    
	    tx.commit();
	    session.close();
	}


}
