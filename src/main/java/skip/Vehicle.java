package skip;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Vehicle {
	private long id;
        
	private int truckload;
	
	@Size(min=3, max=64)
	@Pattern(regexp="^[[\\p{L}]+[ ]?[\\p{L}\\d]+]*$")
	@NotNull
	private String brand;
        
        @Size(min=3, max=64)
	@Pattern(regexp="^[\\p{L}-]*$")
	@NotNull
        private String colour;
	
	@Pattern(regexp="^[A-Z0-9-]{4,12}$")
        @NotNull
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
	
	public String getRegistrationNumber() {
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
	
	public void setRegistrationNumber(String regNumber){
		this.registrationNumber = regNumber;
	}
}
