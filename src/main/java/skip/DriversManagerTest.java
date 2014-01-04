package skip;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DriversManagerTest {
	DriversManager mgr = new DriversManager();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddDriver() {
		Driver d = new Driver("test","driver");
		mgr.addDriver(d);
		Driver out = mgr.getDriverById(d.getId());
		assertEquals(d.getId(),out.getId());
	}

}
