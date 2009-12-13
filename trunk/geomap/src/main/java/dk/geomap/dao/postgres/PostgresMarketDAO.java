package dk.geomap.dao.postgres;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import dk.geomap.dao.BettingExchangeEnum;
import dk.geomap.dao.MarketDAO;
import dk.geomap.dao.model.Market;

public class PostgresMarketDAO extends SimpleJdbcDaoSupport implements MarketDAO {

	public void addMarket(Market market) {
		String SQL = "insert into market(exchange_id,market_id, market_time, total_matched) values(?,?,?,?)";

		getSimpleJdbcTemplate().update(SQL, market.getBettingExchange().getBettingExchangeId(), market.getMarketId(),
				market.getMarketTime(), market.getTotalMatched());

	}

	public void updateTotalMatched(BettingExchangeEnum bettingExchangeId, long marketId, double totalMatched) {
		String SQL = "update market set total_matched=? where exchange_id=? and market_id=?";

		getSimpleJdbcTemplate().update(SQL, totalMatched, bettingExchangeId.getBettingExchangeId(), marketId);

	}

	public Market getMarket(BettingExchangeEnum bettingExchangeId, long marketId) {
		String SQL = "select * from market where exchange_id=? and market_id=?";

		ParameterizedRowMapper<Market> mapper = new ParameterizedRowMapper<Market>() {

			public Market mapRow(ResultSet rs, int rowNum) throws SQLException {
				int exchangeId = rs.getInt("exchange_id");
				long marketId = rs.getLong("market_id");
				Date marketTime = rs.getTimestamp("market_time");
				double totalMatched = rs.getDouble("total_matched");

				return new Market(BettingExchangeEnum.parse(exchangeId), marketId, marketTime, totalMatched);
			}
		};
		try {
			Market market = getSimpleJdbcTemplate().queryForObject(SQL, mapper,
					bettingExchangeId.getBettingExchangeId(), marketId);
			return market;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	public double getTotalMatched(BettingExchangeEnum exchange, Date marketTimeFrom, Date marketTimeTo) {
		String SQL = "select sum(total_matched) from market where exchange_id=? and market_time>=? and market_time<?";

		Double totalMatched = getSimpleJdbcTemplate().queryForObject(SQL, Double.class,
				exchange.getBettingExchangeId(), marketTimeFrom, marketTimeTo);

		if (totalMatched == null) {
			totalMatched = new Double(0);
		}
		return totalMatched;
	}

}
