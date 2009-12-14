package dk.betexarchive;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import dk.betexarchive.dao.LastMatchedPriceDAO;
import dk.betexarchive.dao.LastMatchedPriceDAOImpl;

public class BetexArchiveModule extends AbstractModule{

	@Override
	protected void configure() {
	    bind(LastMatchedPriceDAO.class).to(LastMatchedPriceDAOImpl.class).in(Singleton.class);
	}

}
