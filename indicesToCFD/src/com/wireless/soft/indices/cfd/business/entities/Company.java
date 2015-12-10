package com.wireless.soft.indices.cfd.business.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;



/**
 * @author 	Francisco Corredor
 * @since  	01Dec2015
 * @version	1.0  
 */
@Entity
@NamedNativeQueries({ 
	@NamedNativeQuery(name = "findCompanies", query = "SELECT	com.SCN_CODIGO as id, com.SCN_NAME as name, sci.SCI_URL_INDEX as urlIndex FROM		indexyahoocfd.iyc_stack_company_index sci  INNER JOIN  indexyahoocfd.iyc_stock_companies com   on com.SCN_CODIGO = sci.SCN_CODIGO ORDER by com.SCN_CODIGO", resultClass = Company.class),
	@NamedNativeQuery(name = "findCompanyById", query = "SELECT	com.SCN_CODIGO as id, com.SCN_NAME as name, sci.SCI_URL_INDEX as urlIndex FROM		indexyahoocfd.iyc_stack_company_index sci  INNER JOIN  indexyahoocfd.iyc_stock_companies com   on com.SCN_CODIGO = sci.SCN_CODIGO WHERE  com.SCN_CODIGO = :companyId  ", resultClass = Company.class)	
})
public class Company  implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -91323491401835022L;
	
	// ////////////////////////////////////////////////////////////////////////
    // Nombre de los queries nombrados
    // ////////////////////////////////////////////////////////////////////////
    /** */
    public static final String FIND_COMPANIES = "findCompanies";

    /** */
    public static final String FIND_COMPANY_BY_ID = "findCompanyById";

    // ////////////////////////////////////////////////////////////////////////
    // Campos del backing bean
    // ////////////////////////////////////////////////////////////////////////
    @Id
    private Long id;
    /** */
    private String name;
    /** */
    private String urlIndex;
    
    
    // ////////////////////////////////////////////////////////////////////////
    // Getter/Setter de la clase
    // ////////////////////////////////////////////////////////////////////////
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the urlIndex
	 */
	public String getUrlIndex() {
		return urlIndex;
	}
	/**
	 * @param urlIndex the urlIndex to set
	 */
	public void setUrlIndex(String urlIndex) {
		this.urlIndex = urlIndex;
	}
	

}
