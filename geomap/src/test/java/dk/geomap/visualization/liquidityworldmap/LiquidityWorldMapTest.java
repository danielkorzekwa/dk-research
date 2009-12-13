package dk.geomap.visualization.liquidityworldmap;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.velocity.app.VelocityEngine;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.junit.Test;

public class LiquidityWorldMapTest {

	private LiquidityWorldMap liquidityWorldMap = new LiquidityWorldMap(new VelocityEngine());
	
	@Test
	public void testGenerate() {
		Collection<CountryLiquidity> liquidity = new ArrayList<CountryLiquidity>();
		liquidity.add(new CountryLiquidity("GBR",100));
		liquidity.add(new CountryLiquidity("POL",120));
		
		String htmlReport = liquidityWorldMap.generate(liquidity, new DateMidnight().toDate(), new DateTime().toDate());
		System.out.println(htmlReport);
	}

}
