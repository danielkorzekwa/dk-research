package dk.geomap;

import org.junit.Test;

public class GeoMapImplIntegrationTest {

	@Test
	public void testGeoMapImpl() throws InterruptedException {
		GeoMapImpl geoMapImpl = new GeoMapImpl("postgres","postgres","jdbc:postgresql://localhost/geomapTest");
		
		Thread.sleep(2000);
	}

}
