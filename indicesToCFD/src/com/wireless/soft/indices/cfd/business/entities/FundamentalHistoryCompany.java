package com.wireless.soft.indices.cfd.business.entities;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author 	Francisco Corredor
 * @since  	23Dec2015
 * @version	1.0  
 * entity de la tabla indexyahoocfd.iyc_fundamental_company_history
 * http://www.investopedia.com/terms/e/eps.asp
 * http://www.investopedia.com/terms/p/pegratio.asp
 */

@NamedQueries(value = {				
		@NamedQuery(name = "findAllQuoteHistory", query = "SELECT s FROM QuoteHistoryCompany s ORDER BY s.id"),
		@NamedQuery(name = "findQuoteHistoryByCompany", query = "SELECT s FROM QuoteHistoryCompany s WHERE s.company = :company ORDER BY s.id desc ")
					})
@NamedNativeQueries({
	//TODO --> encontrar la primera iteracion para saber si a superado el high del dia y del año, para dar un ponderado
	@NamedNativeQuery(name = "findFirstIteracionHistoryByCompany", query = "SELECT	0 as id, qch_codigo, SCN_CODIGO as company, SCN_CODIGO, QHC_FECHA_CREACION as fechaCreacion, QHC_FECHA_CREACION, name, symbol, ts, type, utctime, volume, syntaxis_change, chg_percent, day_high, day_low, issuer_name, issuer_name_lang, year_high, year_low, price "+
																			"	FROM		indexyahoocfd.iyc_quote_company_history quotehisto0_ "+
																			"	WHERE	SCN_CODIGO = :company "+
																			"	AND		QHC_FECHA_CREACION between  DATE_SUB(NOW(), INTERVAL 1 DAY)  AND NOW() "+  
																			"	ORDER	by QHC_FECHA_CREACION desc "+
																			"	LIMIT 1 ", resultClass = QuoteHistoryCompany.class)
 })
@Entity
@Table(name = "indexyahoocfd.iyc_fundamental_company_history")
public class FundamentalHistoryCompany {

	Terminar de realizar el entity.
}
