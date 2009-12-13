package dk.geomap.reports.bfbdcompare;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.velocity.app.VelocityEngine;

import dk.geomap.dao.BettingExchangeEnum;
import dk.geomap.dao.TotalMatchedDAO;
import dk.geomap.dao.model.TotalMatched;
import dk.geomap.visualization.timelinechart.TimeChartData;
import dk.geomap.visualization.timelinechart.TimeChartDataValue;
import dk.geomap.visualization.timelinechart.TimeLineChart;

public class BfBdCompareImpl implements BfBdCompare {

	private static long MILLIS_IN_MINUTE = 1000l * 60l;
	private final TotalMatchedDAO totalMatchedDao;

	public BfBdCompareImpl(TotalMatchedDAO totalMatchedDao) {
		this.totalMatchedDao = totalMatchedDao;
	}

	public String generate(Date day) {

		String[] series = new String[] { "BetFair", "BetDaq" };
		
		/** Key - timestamp of value */
		SortedMap<Long, TimeChartDataValue> timeChartValuesMap = new TreeMap<Long, TimeChartDataValue>();
		
		List<TotalMatched> betFairValues = totalMatchedDao.getTotalMatched(BettingExchangeEnum.BETFAIR, day);
		updateValuesInMap(betFairValues, timeChartValuesMap, 0);
		List<TotalMatched> betDaqValues = totalMatchedDao.getTotalMatched(BettingExchangeEnum.BETDAQ, day);
		updateValuesInMap(betDaqValues, timeChartValuesMap, 1);
		
		TimeChartDataValue[] values = timeChartValuesMap.values().toArray(new TimeChartDataValue[timeChartValuesMap.values().size()]);
		TimeChartData chartData = new TimeChartData(series, values);
		String report = new TimeLineChart(new VelocityEngine()).generate(chartData);

		return report;
	}

	private void updateValuesInMap(List<TotalMatched> totalMatchedList,SortedMap<Long, TimeChartDataValue> timeChartValuesMap, int valueIndex) {
		Collections.sort(totalMatchedList);
		
		for (TotalMatched totalMatched : totalMatchedList) {
			long time = (totalMatched.getTimestamp().getTime() / MILLIS_IN_MINUTE) * MILLIS_IN_MINUTE;

			TimeChartDataValue timeChartValue = timeChartValuesMap.get(time);
			if (timeChartValue == null) {
				timeChartValue = new TimeChartDataValue(new Date(time), new Double[2]);
			}
			
			if(valueIndex==0) {
			timeChartValue.getValues()[valueIndex] = totalMatched.getTotalMatched();
			}
			else {
				timeChartValue.getValues()[valueIndex] = totalMatched.getTotalMatched()/2d;
			}
			
			
			timeChartValuesMap.put(time, timeChartValue);
		}
	}

}
