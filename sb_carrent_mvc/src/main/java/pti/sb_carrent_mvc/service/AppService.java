package pti.sb_carrent_mvc.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pti.sb_carrent_mvc.db.Database;
import pti.sb_carrent_mvc.dto.AdminDto;
import pti.sb_carrent_mvc.dto.BookingDto;
import pti.sb_carrent_mvc.dto.CarDto;
import pti.sb_carrent_mvc.dto.CarListDto;
import pti.sb_carrent_mvc.dto.EditCarDto;
import pti.sb_carrent_mvc.model.Booking;
import pti.sb_carrent_mvc.model.Car;

@Service
public class AppService {
	
	private Database db;

	@Autowired
	public AppService(Database db) {
		super();
		this.db = db;
	}

	public CarListDto getAllCarsByDatePicker(LocalDate startDate, LocalDate endDate) {
		
		CarListDto cld = null;
		
		List<CarDto> carDtoList = new ArrayList<>();
		
		List<Car> carsList = db.getAllCars();
		
		List<Booking> bookings = db.getAllBookingsByDate(startDate, endDate);
		
		if(carsList != null) {
	
			for(int carsIndex = 0; carsIndex < carsList.size(); carsIndex ++) {
				
				Car currentCar = carsList.get(carsIndex);
				
				if(currentCar.isActive()) {
					
					boolean isRented = false;
					
					if(bookings != null) {
					
						for (int bookingIndex = 0; bookingIndex < bookings.size(); bookingIndex ++) {
							
							Booking currentBooking = bookings.get(bookingIndex);
							
							if(currentBooking.getCarId() == currentCar.getId()) {
								
								isRented = true;
							}
						}
						
						if(isRented != true) {
							
							CarDto carDto = new CarDto (
										currentCar.getId(),
										currentCar.getType(),
										currentCar.getPrice()
									);
							
							carDtoList.add(carDto);
						}
					}
				}
			}
			
			cld = new CarListDto (
					carDtoList,
					startDate,
					endDate
					);
		}
		
		
		return cld;
	}

	public BookingDto getCarBookingPage(LocalDate startDate, LocalDate endDate, int carId) {

		BookingDto bookingDto = null;
		
		Car selectedCar = db.getCarById(carId);
		
		if(selectedCar != null) {
			
			long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

			long fullPrice = selectedCar.getPrice() * days; 
			
			bookingDto = new BookingDto (
						selectedCar.getId(),
						startDate,
						endDate,
						fullPrice
					);

		}
		
		return bookingDto;
	}

	public CarDto confirmAutoRental(int carId, LocalDate startDate, LocalDate endDate, String name, String email,
			String address, String phone) {
		
		CarDto carDto = null;
		
		Car selectedCar = db.getCarById(carId);
		
		long fullPrice = 0;
		
		if(selectedCar != null) {
			
			long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;

			fullPrice = selectedCar.getPrice() * days; 
			
			Booking bookedCar = new Booking ();
			
			bookedCar.setName(name);		
			bookedCar.setEmail(email);
			bookedCar.setAddress(address);
			bookedCar.setPhoneNumber(phone);
			bookedCar.setStartDate(startDate);
			bookedCar.setEndDate(endDate);
			bookedCar.setCarId(selectedCar.getId());
			
			db.saveNewAutoRent(bookedCar);
		}
		
		carDto = new CarDto(
				selectedCar.getId(),
				selectedCar.getType(),
				fullPrice
				);

		return carDto;
	}

	public AdminDto getAllBookings() {
		
	    AdminDto adminDto = null;
	    BookingDto bookingDto = null;
	    List<BookingDto> bookingDtoList = new ArrayList<>();
	    List<CarDto> carDtoList = new ArrayList<>();

	    List<Booking> allBookings = db.getAllBookings();
	    List<Car> allCars = db.getAllCars();

	    if (allBookings != null) {
	    	
	        for (int bookingIndex = 0; bookingIndex < allBookings.size(); bookingIndex++) {
	            Booking currentBooking = allBookings.get(bookingIndex);

	            Car bookedCar = db.getCarById(currentBooking.getCarId());

	            long numberOfDays = ChronoUnit.DAYS.between(		// 2 dátum közötti különbség kiszámolása
	                currentBooking.getStartDate(),
	                currentBooking.getEndDate()
	            ) + 1;										// +1, hogy a kezdőnapot is beleszámoljuk a foglalásba

	            long totalPrice;

	            if (bookedCar != null) {
	                totalPrice = bookedCar.getPrice() * numberOfDays;
	            } else {
	                totalPrice = 0;
	            }

	            bookingDto = new BookingDto(
	                currentBooking.getCarId(),
	                currentBooking.getStartDate(),
	                currentBooking.getEndDate(),
	                totalPrice
	            );

	            bookingDtoList.add(bookingDto);
	        }
	    }

	    if (allCars != null) {
	    	
	        for (int carIndex = 0; carIndex < allCars.size(); carIndex++) {
	        	
	            Car currentCar = allCars.get(carIndex);

	            CarDto carDto = new CarDto(
	            		
	                currentCar.getId(),
	                currentCar.getType(),
	                currentCar.getPrice()
	            );

	            carDtoList.add(carDto);
	        }
	    }

	    adminDto = new AdminDto(bookingDtoList, carDtoList);
	    return adminDto;
	}

	public EditCarDto addNewCar(String type, int price, boolean active) {
		
		EditCarDto editCarDto = null;
		
		Car newCar = new Car(
				type, 
				price, 
				active);
		
		db.persistNewCar(newCar);
		
		editCarDto = new EditCarDto(
				newCar.getId(),
				newCar.getType(),
				newCar.getPrice(),
				newCar.isActive()
			);

		return editCarDto;
	}

	public EditCarDto editCar(int carId) {
		
		EditCarDto editCarDto = null;
		
		Car pickedCar = db.getCarById(carId);
		
		if(pickedCar != null) {
			
			editCarDto = new EditCarDto(
					pickedCar.getId(),
					pickedCar.getType(),
					pickedCar.getPrice(),
					pickedCar.isActive()
				);
		}
	
		return editCarDto;
	}


	public EditCarDto updateCar(int carId, String type, int price, boolean active) {
		
		EditCarDto editCarDto = null;
	    
		Car car = db.getCarById(carId);
	    
	    if (car != null) {
	        
	        car.setType(type);
	        car.setPrice(price);
	        car.setActive(active);

	        db.updateCar(car);
	        
	        editCarDto = new EditCarDto(
					car.getId(),
					car.getType(),
					car.getPrice(),
					car.isActive()
				);
	    }
	    
	    return editCarDto;
	}


	

}
