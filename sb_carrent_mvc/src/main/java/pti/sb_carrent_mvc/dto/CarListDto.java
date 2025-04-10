package pti.sb_carrent_mvc.dto;

import java.time.LocalDate;
import java.util.List;

public class CarListDto {
	
	private List <CarDto> carDtoList;
	private LocalDate startDate;
	private LocalDate endDate;
	
	public CarListDto(List<CarDto> carDtoList, LocalDate startDate, LocalDate endDate) {
		super();
		this.carDtoList = carDtoList;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public List<CarDto> getCarDtoList() {
		return carDtoList;
	}

	public void setCarDtoList(List<CarDto> carDtoList) {
		this.carDtoList = carDtoList;
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

	public void setEndData(LocalDate endDate) {
		this.endDate = endDate;
	}

}
