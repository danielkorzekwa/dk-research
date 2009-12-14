package dk.betexarchive;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class BetexArchiveGuiceServletContextListener extends GuiceServletContextListener{

	@Override protected Injector getInjector() {
	    return Guice.createInjector(
	        new BetexArchiveServletModule(), new BetexArchiveModule());
	  }
}
