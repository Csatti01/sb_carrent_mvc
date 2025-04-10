package pti.sb_carrent_mvc.dto;

public class EditCarDto {
	
	private int id;
	private String type;
	private int price;
	private boolean active;
	private byte[] image;
	
	
	public EditCarDto(int id, String type, int price, boolean active) {
		super();
		this.id = id;
		this.type = type;
		this.price = price;
		this.active = active;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
