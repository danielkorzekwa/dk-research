package dk.geomap.dao;

public enum BettingExchangeEnum {

	BETFAIR(1),
	BETDAQ(2);

	private final int bettingExchangeId;

	private BettingExchangeEnum(int bettingExchangeId) {
		this.bettingExchangeId = bettingExchangeId;
	}

	public int getBettingExchangeId() {
		return bettingExchangeId;
	}
	
	public static BettingExchangeEnum parse(int bettingExchangeId) {
		for(BettingExchangeEnum bettingExchange:  BettingExchangeEnum.values()) {
			if(bettingExchange.getBettingExchangeId()== bettingExchangeId) {
				return bettingExchange;
			}
		}
		throw new IllegalArgumentException("Can't find BettingExchangeEnum for bettingExchangeId: " + bettingExchangeId);
	}
}
