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
		validDriver.setLatestCoordinates("N12.1234567 W12.1234567");
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
	public void testCoordiantesWODot(){
		validDriver.setLatestCoordinates("N001234567 W001234567");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "latestCoordinates");
		assertEquals(1, constraintViolations.size());

	}


	@Test
	public void testCoordiantesWOSpace(){
		validDriver.setLatestCoordinates("N00.1234567W00.1234567");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "latestCoordinates");
		assertEquals(1, constraintViolations.size());

	}
	
	@Test
	public void testNSCoordiantesOnly6FractionDigits(){
		validDriver.setLatestCoordinates("N00.123456 W00.1234567");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "latestCoordinates");
		assertEquals(1, constraintViolations.size());

	}
	
	@Test
	public void testNSCoordiantesBiggerThan90Degrees(){
		validDriver.setLatestCoordinates("N90.1234567 W00.1234567");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "latestCoordinates");
		assertEquals(1, constraintViolations.size());

	}	
	
	@Test
	public void testCoordiantesNSBiggerThan90Degrees2(){
		validDriver.setLatestCoordinates("N91.0000000 W00.1234567");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "latestCoordinates");
		assertEquals(1, constraintViolations.size());

	}

	@Test
	public void testValidNSCoordiantes90Degrees(){
		validDriver.setLatestCoordinates("N90.0000000 W00.1234567");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "latestCoordinates");
		assertEquals(0, constraintViolations.size());

	}
	
	@Test
	public void testValidWECoordiantes180Degrees(){
		validDriver.setLatestCoordinates("N01.0000000 W180.0000000");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "latestCoordinates");
		assertEquals(0, constraintViolations.size());

	}

	@Test
	public void testWECoordiantesBiggerThan180Degrees(){
		validDriver.setLatestCoordinates("N01.0000000 W181.1234567");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "latestCoordinates");
		assertEquals(1, constraintViolations.size());

	}
	
	@Test
	public void testWECoordiantesBiggerThan180Degrees2(){
		validDriver.setLatestCoordinates("N01.0000000 W180.1234567");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "latestCoordinates");
		assertEquals(1, constraintViolations.size());

	}
	
	@Test
	public void testCoordinatesWithCommas(){
		validDriver.setLatestCoordinates("N01,1234567 W01,1234567");
		Set<ConstraintViolation<Driver>> 
				constraintViolations = validator.validateProperty(
						validDriver, "latestCoordinates");
		assertEquals(1, constraintViolations.size());

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
}
