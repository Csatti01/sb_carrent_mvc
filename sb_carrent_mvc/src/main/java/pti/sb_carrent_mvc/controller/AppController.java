package pti.sb_carrent_mvc.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import pti.sb_carrent_mvc.dto.AdminDto;
import pti.sb_carrent_mvc.dto.BookingDto;
import pti.sb_carrent_mvc.dto.CarDto;
import pti.sb_carrent_mvc.dto.CarListDto;
import pti.sb_carrent_mvc.dto.EditCarDto;
import pti.sb_carrent_mvc.service.AppService;

@Controller
public class AppController {
	
	private AppService service;

	@Autowired
	public AppController(AppService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/")
	public String showMainPage () {

		return "index.html";
	}
	
	@GetMapping("/cars")
	public String getAvailableCars (
				Model model,
				@RequestParam("startdate") LocalDate startDate,
				@RequestParam("enddate") LocalDate endDate	
			) {
		
		CarListDto cld = service.getAllCarsByDatePicker(startDate, endDate);
		
		model.addAttribute("cld", cld);

		return "cars.html";
	}
	
	@PostMapping("/cars/startbooking")
	public String getBookingPage (
				Model model,
				@RequestParam("startdate") LocalDate startDate,
				@RequestParam("enddate") LocalDate endDate,
				@RequestParam("carid") int carId
			
			) {
		
		BookingDto bookingDto = service.getCarBookingPage(startDate, endDate, carId);
		
		model.addAttribute("bookingDto", bookingDto);
	
		return "booking.html";
	}
	
	@PostMapping("/cars/endbooking")
	public String confirmBooking (
				Model model,
				@RequestParam("carid") int carId,
				@RequestParam("startdate") LocalDate startDate,
				@RequestParam("enddate") LocalDate endDate,
				@RequestParam("name") String name,
				@RequestParam("email") String email,
				@RequestParam("address") String address,
				@RequestParam("phone") String phone
			
			) {
		
		CarDto carDto = service.confirmAutoRental(carId, startDate, endDate, name, email, address, phone);
		
		model.addAttribute("carDto", carDto);
		
		return "thanks.html";
	}
	
	@GetMapping("/admin")
	public String showAdminPage(Model model) {
		
		AdminDto adminDto = service.getAllBookings();
		
		model.addAttribute("adminDto", adminDto);
		
		return "admin.html";
	}
	
	@PostMapping("/admin/newcar")
	public String addNewCar(
				Model model,
				@RequestParam("type") String type,
				@RequestParam("price") int price,
				@RequestParam("active") boolean active
				
			) {
		
		EditCarDto editCarDto = service.addNewCar(type, price, active);
		
		model.addAttribute("editCarDto", editCarDto);
		
		return "editcar.html";
		
	}
	
	@GetMapping("/admin/editcar")
	public String editCar (
				Model model,
				@RequestParam(name="carid", required = false, defaultValue = "-1") int carId

			) {
		if(carId != -1) {
			EditCarDto editCarDto = service.editCar(carId);
		
			model.addAttribute("editCarDto", editCarDto);
		}
		return "editcar.html";
	}
	
	@PostMapping("/admin/editcar/update")
    public String updateCar(
	    		Model model,
	            @RequestParam("carid") int carId,
	            @RequestParam("type") String type,
	            @RequestParam("price") int price,
	            @RequestParam("active") boolean active
            ) {
        
		AdminDto adminDto = service.updateCar(carId, type, price, active);
        
        model.addAttribute("adminDto", adminDto);
        
        return "admin.html";
	}
}


