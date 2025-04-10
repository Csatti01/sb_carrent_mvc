package pti.sb_carrent_mvc.dto;

import java.time.LocalDate;

public class BookingDto {
	
	private int carId;
	private LocalDate startDate;
	private LocalDate endDate;
	private long fullPrice;
	
	public BookingDto(int carId, LocalDate startDate, LocalDate endDate, long fullPrice) {
		super();
		this.carId = carId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.fullPrice = fullPrice;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public long getFullPrice() {
		return fullPrice;
	}

	public void setFullPrice(long fullPrice) {
		this.fullPrice = fullPrice;
	}
	
}
