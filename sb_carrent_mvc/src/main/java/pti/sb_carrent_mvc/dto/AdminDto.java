package pti.sb_carrent_mvc.dto;

import java.util.List;

public class AdminDto {
	
	private List<BookingDto> allBookings;
	private List<CarDto> allCars;
	
	public AdminDto(List<BookingDto> allBookings, List<CarDto> allCars) {
		super();
		this.allBookings = allBookings;
		this.allCars = allCars;
	}
	public List<BookingDto> getAllBookings() {
		return allBookings;
	}
	public void setAllBookings(List<BookingDto> allBookings) {
		this.allBookings = allBookings;
	}
	public List<CarDto> getAllCars() {
		return allCars;
	}
	public void setAllCars(List<CarDto> allCars) {
		this.allCars = allCars;
	}
	
	

}
