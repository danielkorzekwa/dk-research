package dk.betexarchive.domain;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Represents a timestamped record for last matched price on market selection.
 * 
 * @author daniel
 * 
 */

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class LastMatchedPriceJDO {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private Long marketId;
	@Persistent
	private Long selectionId;

	@Persistent
	private Double lastMatchedPrice;
	
	@Persistent
	private Double avgPrice15MinSlope;

	@Persistent
	private Date lastMatchedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMarketId() {
		return marketId;
	}

	public void setMarketId(Long marketId) {
		this.marketId = marketId;
	}

	public Long getSelectionId() {
		return selectionId;
	}

	public void setSelectionId(Long selectionId) {
		this.selectionId = selectionId;
	}

	public Double getLastMatchedPrice() {
		return lastMatchedPrice;
	}

	public void setLastMatchedPrice(Double lastMatchedPrice) {
		this.lastMatchedPrice = lastMatchedPrice;
	}

	public Date getLastMatchedDate() {
		return lastMatchedDate;
	}

	public void setLastMatchedDate(Date lastMatchedDate) {
		this.lastMatchedDate = lastMatchedDate;
	}

	public Double getAvgPrice15MinSlope() {
		return avgPrice15MinSlope;
	}

	public void setAvgPrice15MinSlope(Double avgPrice15MinSlope) {
		this.avgPrice15MinSlope = avgPrice15MinSlope;
	}	
}
