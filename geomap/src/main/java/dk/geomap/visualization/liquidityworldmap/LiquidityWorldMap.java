package dk.geomap.visualization.liquidityworldmap;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.math.util.MathUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.google.visualization.datasource.DataSourceHelper;
import com.google.visualization.datasource.DataSourceRequest;
import com.google.visualization.datasource.base.DataSourceException;
import com.google.visualization.datasource.base.DataSourceParameters;
import com.google.visualization.datasource.base.TypeMismatchException;
import com.google.visualization.datasource.datatable.ColumnDescription;
import com.google.visualization.datasource.datatable.DataTable;
import com.google.visualization.datasource.datatable.value.ValueType;
import com.google.visualization.datasource.query.Query;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.ULocale;

import dk.geomap.visualization.VisualizationException;

/**Presents betting exchange liquidity on the world map.
 * 
 * @author korzekwad
 *
 */
public class LiquidityWorldMap {

	private final VelocityEngine velocityEngine;

	public LiquidityWorldMap(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	/**Generated html report
	 * @throws DataSourceException */
	public String generate(Collection<CountryLiquidity> liquidity,Date from, Date to) throws VisualizationException {
		try {
		String dataTableJson = generateDataTableJson(liquidity);
		String geoMapHtmlPage = generateHtmlGeoPage(dataTableJson,from, to);
		return geoMapHtmlPage;
		}
		catch(Exception e) {
			throw new VisualizationException(e);
		}
	}
	
	private String generateDataTableJson(Collection<CountryLiquidity> liquidity) throws DataSourceException {
		DataTable data = new DataTable();
		ArrayList<ColumnDescription> cd = new ArrayList<ColumnDescription>();
		cd.add(new ColumnDescription("country", ValueType.TEXT, "Country"));
		cd.add(new ColumnDescription("totalMatched", ValueType.NUMBER, "TotalMatched"));
		data.addColumns(cd);
		try {
			Map<String, Locale> countries = getCountries();
			for (CountryLiquidity countryLiquidity : liquidity) {
				Locale country = countries.get(countryLiquidity.getCountryCode());
				if (country != null) {
					if (country.getDisplayCountry().length() > 0) {
						double totalMatched = countryLiquidity.getTotalMatched();
						totalMatched = MathUtils.round(totalMatched, 2);
						System.out.println("Country: " + country.getDisplayCountry() + ", TotalMatched: " + totalMatched);
						data.addRowFromValues(country.getDisplayCountry(), totalMatched);
					} else {
						System.out
								.println("Country name is empty for countryCode:" + countryLiquidity.getCountryCode());
					}
				} else {
					System.out.println("Country doesnt exist for countryCode:" + countryLiquidity.getCountryCode());
				}
			}

		} catch (TypeMismatchException e) {
			System.out.println("Invalid type!");
		}
		Query query = new Query();
		DataSourceParameters parameters = new DataSourceParameters("");
		DataSourceRequest request = new DataSourceRequest(query, parameters, ULocale.UK);
		String generateResponse = DataSourceHelper.generateResponse(data, request);

		return generateResponse;
	}
	

	private String generateHtmlGeoPage(String dataTableJson,Date from, Date to) throws ParseErrorException, MethodInvocationException,
			ResourceNotFoundException, IOException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		FileReader templateReader;
		templateReader = new FileReader(new File("src/main/resources/templates/geomap_template.html"));
		VelocityContext ctx = new VelocityContext();
		ctx.put("rawdata", dataTableJson);
		ctx.put("fromDate", dateFormat.format(from));
		ctx.put("toDate", dateFormat.format(to));
		Writer writer = new StringWriter();
		velocityEngine.evaluate(ctx, writer, "logTag", templateReader);
		return writer.toString();
	}
	
	/**
	 * 
	 * @return /**key - ISO3 country code, e.g. GBR, USA
	 */
	public Map<String, Locale> getCountries() {
		/** key - ISO3 country code, e.g. GBR, USA */
		Map<String, Locale> countries = new HashMap<String, Locale>();
		for (String country : Locale.getISOCountries()) {
			Locale countryLocale = new Locale("", country);
			countries.put(countryLocale.getISO3Country(), countryLocale);
		}
		return countries;
	}
}
