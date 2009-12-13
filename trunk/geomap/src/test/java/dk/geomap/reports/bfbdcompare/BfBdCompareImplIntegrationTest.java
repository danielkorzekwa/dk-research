package dk.geomap.reports.bfbdcompare;

import java.util.Date;

import javax.annotation.Resource;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-geomap.xml","/spring-geomap-dao.xml"})
public class BfBdCompareImplIntegrationTest {

	private static long MILLIS_IN_MINUTE=1000l*60l;
	
	@Resource
	BfBdCompare bfbdCompare;
	
	@BeforeClass
	public static void setUp() {
		System.setProperty("jdbc.user", "postgres");
		System.setProperty("jdbc.password", "postgres");
		System.setProperty("jdbc.url", "jdbc:postgresql://localhost/geomap");
	}
	
	@Test
	public void testGenerate() {
		long time = System.currentTimeMillis();
		
		long timeInSeconds = (time/MILLIS_IN_MINUTE)*MILLIS_IN_MINUTE;
		System.out.println(new Date(timeInSeconds));

		String report = bfbdCompare.generate(new DateMidnight().minusDays(2).toDate());
		
		System.out.println(report);
	}

}
