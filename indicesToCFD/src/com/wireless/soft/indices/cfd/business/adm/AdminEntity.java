package com.wireless.soft.indices.cfd.business.adm;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.wireless.soft.indices.cfd.business.entities.Company;
import com.wireless.soft.indices.cfd.business.entities.QuoteHistoryCompany;
import com.wireless.soft.indices.cfd.deserializable.json.object.ReturnIndexYahooFinanceObject;
import com.wireless.soft.indices.cfd.deserializable.json.object.ReturnIndexYahooFinanceObject.List.Resources;
import com.wireless.soft.indices.cfd.deserializable.json.object.ReturnIndexYahooFinanceObject.List.Resources.Resource;
import com.wireless.soft.indices.cfd.deserializable.json.object.ReturnIndexYahooFinanceObject.List.Resources.Resource.Fields;
import com.wireless.soft.indices.cfd.exception.BusinessException;
import com.wireless.soft.indices.cfd.util.UtilSession;

/**
 * @author Francisco Corredor
 * @version 1.0
 * @since 2015Dec01
 * Clase encargada de gestionar la consulta a la BD
 *
 */
public class AdminEntity {
	
    // ////////////////////////////////////////////////////////////////////////
    // Logger de la clase
    // ////////////////////////////////////////////////////////////////////////
    private static Logger _logger = Logger.getLogger(AdminEntity.class);

    // ////////////////////////////////////////////////////////////////////////
    // Atributos de la clase
    // ////////////////////////////////////////////////////////////////////////
    /** */
    private EntityManagerFactory emf;
    /** */
    private EntityManager em;
    /** */
    private EntityTransaction tx;

    // ////////////////////////////////////////////////////////////////////////
    // Constructor de la clase
    // ////////////////////////////////////////////////////////////////////////
    /**
     * Constructor de la clase
     */
    public AdminEntity() {
    	super();

    this.emf = Persistence.createEntityManagerFactory("entityManager");
	this.em = this.emf.createEntityManager();
	this.tx = this.em.getTransaction();
    }

    // ////////////////////////////////////////////////////////////////////////
    // Metodos de negocio
    // ////////////////////////////////////////////////////////////////////////

	/**
	 * @return
	 * @throws BusinessException
	 */
	public List<Company> getCompanies() throws BusinessException {
		List<Company> lstCompanies = new ArrayList<Company>();

		try {

			List<Object> list = UtilSession.getObjectsByNamedQuery(this.em, Company.FIND_COMPANIES, null);

			for (Object object : list) {
				Company vnt = (Company) object;
				lstCompanies.add(vnt);
			}

		} catch (Exception ex) {
			String s = "Error al consultar las compañias";
			_logger.error(s, ex);
			throw new BusinessException(s, ex);
		}

		

		return lstCompanies;

	}
	
	/**
	 * @param ri
	 * @param cmp
	 */
	public void persistirCompaniesQuotes(ReturnIndexYahooFinanceObject ri, Company cmp){
		
		this.tx.begin();
		
		if (null != ri && null != ri.getList()
			&& null != ri.getList().getResources()){
			ReturnIndexYahooFinanceObject.List.Resources[] rs = ri.getList().getResources();
			
			for (Resources resources : rs) {
				Resource r = resources.getResource();
				if (null != r && null != r.getFields()){
					try{
					
					Fields f =  r.getFields();
					QuoteHistoryCompany qhc = new QuoteHistoryCompany();
					qhc.setCompany(cmp.getId());
					qhc.setFechaCreacion(Calendar.getInstance());
					qhc.setName( f.getName() );
					qhc.setSymbol(f.getSymbol());
					qhc.setTs(f.getTs());
					qhc.setType(f.getType());
					qhc.setUtctime(f.getUtctime());
					qhc.setVolume(f.getVolume());
					qhc.setSyntaxis_change(f.getChange());
					qhc.setChg_percent(f.getChg_percent());
					qhc.setDay_high(f.getDay_high());
					qhc.setDay_low(f.getDay_low());
					qhc.setIssuer_name(f.getIssuer_name());
					qhc.setIssuer_name_lang(f.getIssuer_name_lang());
					qhc.setYear_high(f.getYear_high());
					qhc.setYear_low(f.getYear_low());
					qhc.setPrice(f.getPrice());
					em.persist(qhc);
					this.em.flush();
					//_logger.info("PErsistio.." + qhc.toString());
					}
					catch(Exception e){
						e.printStackTrace();
					}
					
				}
			}
			
			
		}
		
		
		
	    this.tx.commit();	
	}


	/**
	 * @param cmp
	 * @return
	 * @throws Exception 
	 */
	public List<Object> getCompIdxQuote(Company cmp) throws Exception{
		Hashtable<String, Object> param = new Hashtable<String, Object>();
		param.put("company", cmp.getId());
		List<Object> listIdxCompany = UtilSession
			.getObjectsByNamedQuery(
				em,
				QuoteHistoryCompany.FIND_QUOTEHISTORY_BYCOMPANY,
				param);
		
		return listIdxCompany;
		
	}
	
	//TODO
	//para saber si una compañia subio su maximo high del dia realizar la consulta del dia y si encontro unpunto donde 
	//incremento el tope con repecto al minimo, realiar un break;
	
	
	/**
	 * @param cmp
	 * @return
	 * @throws Exception 
	 * Obtiene el primer record de una compañia
	 */
	public QuoteHistoryCompany getFirstRecordDay(Company cmp) throws Exception {

		QuoteHistoryCompany qhcReturn = null;

		Hashtable<String, Object> param = new Hashtable<String, Object>();
		param.put("company", cmp.getId());
		List<Object> listIdxCompany = UtilSession.getObjectsByNamedQuery(em,
				QuoteHistoryCompany.FIND_FIRSTITERACION_BYCOMPANY, param);
		if (null != listIdxCompany && listIdxCompany.size() > 0) {
			for (Object object : listIdxCompany) {
				qhcReturn = (QuoteHistoryCompany) object;
				break;
			}
		}

		return qhcReturn;

	}

}
