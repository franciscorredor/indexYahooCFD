package com.wireless.soft.indices.cfd.deserializable.json.object;

import java.io.Serializable;

/**
 * @author Francisco
 * 
 * Obtiene en unobjeto el resultado del WebService Yahoo
 * http://query.yahooapis.com/v1/public/yql?q=select%20PERatio%20from%20yahoo.finance.quotes%20where%20symbol%20IN%20(%22LLOY.L%22)&format=json&env=http://datatables.org/alltables.env
 * http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20IN%20(%22LLOY.L%22)&format=json&env=http://datatables.org/alltables.env
 */
public class ReturnYahooFinanceQuoteObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5151925176934907761L;
	
	// ////////////////////////////////////////////////////////////////////////
	// Atributos de la clase
	// ////////////////////////////////////////////////////////////////////////
	
	private Query query;
	
	/**
	 * @return the query
	 */
	public Query getQuery() {
		return query;
	}


	/**
	 * @param query the query to set
	 */
	public void setQuery(Query query) {
		this.query = query;
	}

	
	public class Query{
		private String count;
		
		private String created;
		
		private String lang;
		
		private Results results;
		
		/**
		 * @return the count
		 */
		public String getCount() {
			return count;
		}


		/**
		 * @param count the count to set
		 */
		public void setCount(String count) {
			this.count = count;
		}
		
		
		/**
		 * @return the created
		 */
		public String getCreated() {
			return created;
		}


		/**
		 * @param created the created to set
		 */
		public void setCreated(String created) {
			this.created = created;
		}


		/**
		 * @return the lang
		 */
		public String getLang() {
			return lang;
		}


		/**
		 * @param lang the lang to set
		 */
		public void setLang(String lang) {
			this.lang = lang;
		}


		/**
		 * @return the results
		 */
		public Results getResults() {
			return results;
		}


		/**
		 * @param results the results to set
		 */
		public void setResults(Results results) {
			this.results = results;
		}


		public class Results{
			
			/**
			 * @return the quote
			 */
			public Quote getQuote() {
				return quote;
			}

			/**
			 * @param quote the quote to set
			 */
			public void setQuote(Quote quote) {
				this.quote = quote;
			}

			private Quote quote;
			
			public class Quote{
				private String PERatio;
				private String Bid;
				private String EBITDA;
				private String PriceSales;
				private String PriceEPSEstimateCurrentYear;
				private String PriceEPSEstimateNextYear;
				private String Ask;
				private String PEGRatio;
				private String MarketCapitalization;
				private String MarketCapRealtime;

				/**
				 * @return the pERatio
				 */
				public String getPERatio() {
					return PERatio;
				}

				/**
				 * @param pERatio the pERatio to set
				 */
				public void setPERatio(String pERatio) {
					PERatio = pERatio;
				}
				
				public String getBid() {
					return Bid;
				}

				public void setBid(String bid) {
					Bid = bid;
				}

				public String getEBITDA() {
					return EBITDA;
				}

				public void setEBITDA(String eBITDA) {
					EBITDA = eBITDA;
				}

				public String getPriceSales() {
					return PriceSales;
				}

				public void setPriceSales(String priceSales) {
					PriceSales = priceSales;
				}

				public String getPriceEPSEstimateCurrentYear() {
					return PriceEPSEstimateCurrentYear;
				}

				public void setPriceEPSEstimateCurrentYear(String priceEPSEstimateCurrentYear) {
					PriceEPSEstimateCurrentYear = priceEPSEstimateCurrentYear;
				}

				public String getPriceEPSEstimateNextYear() {
					return PriceEPSEstimateNextYear;
				}

				public void setPriceEPSEstimateNextYear(String priceEPSEstimateNextYear) {
					PriceEPSEstimateNextYear = priceEPSEstimateNextYear;
				}

				public String getAsk() {
					return Ask;
				}

				public void setAsk(String ask) {
					Ask = ask;
				}

				/**
				 * @return the pEGRatio
				 */
				public String getPEGRatio() {
					return PEGRatio;
				}

				/**
				 * @param pEGRatio the pEGRatio to set
				 */
				public void setPEGRatio(String pEGRatio) {
					PEGRatio = pEGRatio;
				}

				/**
				 * @return the marketCapitalization
				 */
				public String getMarketCapitalization() {
					return MarketCapitalization;
				}

				/**
				 * @param marketCapitalization the marketCapitalization to set
				 */
				public void setMarketCapitalization(String marketCapitalization) {
					MarketCapitalization = marketCapitalization;
				}

				/**
				 * @return the marketCapRealtime
				 */
				public String getMarketCapRealtime() {
					return MarketCapRealtime;
				}

				/**
				 * @param marketCapRealtime the marketCapRealtime to set
				 */
				public void setMarketCapRealtime(String marketCapRealtime) {
					MarketCapRealtime = marketCapRealtime;
				}

				@Override
			    public String toString() {
				StringBuffer s = new StringBuffer(this.getClass().getCanonicalName());
				if (this.PERatio !=null){
					s.append("\n pERatio [" + this.PERatio.toString() + "]");
				}

				return s.toString();
				}

			}
			
			@Override
		    public String toString() {
			StringBuffer s = new StringBuffer(this.getClass().getCanonicalName());
			if (this.quote !=null){
				s.append("\n quote [" + this.quote.toString() + "]");
			}

			return s.toString();
			}
		}
		
		@Override
	    public String toString() {
		StringBuffer s = new StringBuffer(this.getClass().getCanonicalName());
		s.append("\n count [" + this.count + "]");
		s.append(" created [" + this.created + "]");
		s.append(" lang [" + this.lang + "]");
		if (this.results !=null){
			s.append("\n results [" + this.results.toString() + "]");
		}

		return s.toString();
		}


		
	}

	@Override
    public String toString() {
	StringBuffer s = new StringBuffer(this.getClass().getCanonicalName());
	if (this.query !=null){
		s.append("\n query [" + this.query.toString() + "]");
	}

	return s.toString();
	}

	

}
