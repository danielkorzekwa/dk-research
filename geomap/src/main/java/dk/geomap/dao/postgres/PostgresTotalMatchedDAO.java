package dk.geomap.dao.postgres;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import dk.geomap.dao.BettingExchangeEnum;
import dk.geomap.dao.TotalMatchedDAO;
import dk.geomap.dao.model.TotalMatched;

public class PostgresTotalMatchedDAO extends SimpleJdbcDaoSupport implements TotalMatchedDAO{

	public void add(BettingExchangeEnum exchange, Date timestamp, double totalMatched) {
		String SQL = "insert into total_matched(exchange_id,timestamp,total_matched) values(?,?,?)";
		
		getSimpleJdbcTemplate().update(SQL, exchange.getBettingExchangeId(),timestamp,totalMatched);
	}
	
	public List<TotalMatched> getTotalMatched(BettingExchangeEnum exchange, Date day) {
		DateMidnight dayStart = new DateMidnight(day);
		DateTime dayEnd = dayStart.toDateTime().plusDays(1).minus(1);
		String SQL = "select * from total_matched where exchange_id=? and timestamp>=? and timestamp <=?";
		
		ParameterizedRowMapper<TotalMatched> mapper = new ParameterizedRowMapper<TotalMatched>(){
		
			public TotalMatched mapRow(ResultSet rs, int rowNum) throws SQLException {
				int exchangeId = rs.getInt("exchange_id");
				Date timestamp = rs.getTimestamp("timestamp");
				double totalMatched = rs.getDouble("total_matched");
				
				return new TotalMatched(BettingExchangeEnum.parse(exchangeId),timestamp,totalMatched);
			}
		};;
		List<TotalMatched> totalMatched = getSimpleJdbcTemplate().query(SQL,mapper ,exchange.getBettingExchangeId(),dayStart.toDate(),dayEnd.toDate());
		return totalMatched;
	}

}
