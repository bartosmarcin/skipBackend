package skip;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DriverTest {
	private static Validator validator;
	private Driver validDriver;
	
    @BeforeClass
    public static void setUpClass() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	
	@Before
	public void setUp(){
		validDriver = new Driver();
		validDriver.setFirstName("first-Name");
		validDriver.setLastName("lastName");
		validDriver.setLatestCoordinates(new Coordinates(20,30));
		validDriver.setEmail("example@mail.com");
		validDriver.setPhoneNumber("048123456789");
		validDriver.setPhoneNumber("048123456789");
	}
	
	@Test
	public void testValid(){
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validate(validDriver);
		assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void testFirstNameLongerThan64(){
		validDriver.setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "firstName");
		assertEquals(1, constraintViolations.size());

	}

	@Test
	public void testFirstNameLength64(){
		validDriver.setFirstName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "firstName");
		assertEquals(0, constraintViolations.size());

	}
	
	@Test
	public void testCoordiantesOutsideRange(){
		validDriver.setLatestCoordinates(new Coordinates(200,200));
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validate(validDriver);
		assertNotEquals(0, constraintViolations.size());

	}
	
	@Test
	public void testEmailWOat(){
		validDriver.setEmail("someemail.com");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "email");
		assertEquals(1, constraintViolations.size());

	}
	
	@Test
	public void testEmailWithPlus(){
		validDriver.setEmail("some+email@email.com");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "email");
		assertEquals(0, constraintViolations.size());

	}
	

	@Test
	public void testEmailLongerThan64(){
		validDriver.setEmail("mailmailmailmailmailmailmailmailmailmailmailmailmailmailmailmailm@email.com");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "email");
		assertNotEquals(0, constraintViolations.size());
	}
	
	@Test
	public void testEmailg64Long(){
		validDriver.setEmail("01234567890123456789012345678901234567890123456789@email.com");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "email");
		assertEquals(0, constraintViolations.size());
	}

	@Test
	public void testPhoneNumber2Null(){
		validDriver.setPhoneNumber2(null);
		Set<ConstraintViolation<Driver>> 
		constraintViolations = validator.validateProperty(
				validDriver, "phoneNumber2");
		assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void testPhoneNumberNull(){
		validDriver.setPhoneNumber(null);
		Set<ConstraintViolation<Driver>> 
		constraintViolations = validator.validateProperty(
				validDriver, "phoneNumber");
		assertEquals(1, constraintViolations.size());
	}
}
