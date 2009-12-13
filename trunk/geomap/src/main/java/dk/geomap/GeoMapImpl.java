package dk.geomap;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GeoMapImpl implements GeoMap {

	public GeoMapImpl(String dbUser, String dbPassword, String dbUrl) {
		System.setProperty("jdbc.user", dbUser);
		System.setProperty("jdbc.password", dbPassword);
		System.setProperty("jdbc.url", dbUrl);
		new ClassPathXmlApplicationContext(new String[] { "spring-geomap.xml", "spring-geomap-tasks.xml",
				"spring-geomap-dao.xml" });
	}

	public static void main(String[] args) {
		System.out.println("GeoMap is starting...");
		GeoMapImpl geoMapImpl = new GeoMapImpl("postgres", "postgres", "jdbc:postgresql://localhost/geomap");
		System.out.println("GeoMap is started.");
		for (;;) {
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
