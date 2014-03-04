package skip;

import javax.validation.constraints.Pattern;

public class Vehicle {
	private long id;
	
	@Pattern(regexp="^[0-9]{1,20}$")
	private int truckload;
	
	@Pattern(regexp="^[a-zA-Z-]{3,64}$")
	private String brand, colour;
	
	@Pattern(regexp="^[A-Z0-9-]{4,12}$")
	private String registrationNumber;
	
	public Vehicle() {}
	 
	public Vehicle(int maxLoad, String brand, String colour, String regNumber){
		this.truckload = maxLoad;
		this.brand = brand;
		this.colour = colour;
		this.registrationNumber = regNumber;
	}
	
	public long getId() {
		return id;
	}
	
	public int getTruckload() {
		return truckload;
	}
	
	public String getBrand(){
		return brand;
	}
	
	public String getColour() {
		return colour;
	}
	
	public String getRegNumber() {
		return registrationNumber;
	}
	
	@SuppressWarnings("unused")
	private void setId(long ID){
		this.id = ID;
	}
	
	public void setTruckload(int maxLoad){
		this.truckload = maxLoad;
	}
	
	public void setBrand(String brand){
		this.brand = brand;
	}

	public void setColour(String colour){
		this.colour = colour;
	}
	
	public void setRegNumber(String regNumber){
		this.registrationNumber = regNumber;
	}
}
