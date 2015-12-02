package com.wireless.soft.indices.cfd.business.adm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.wireless.soft.indices.cfd.business.entities.Company;
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

			List<Object> list = UtilSession.getObjectsByNamedQuery(this.em,
					Company.FIND_COMPANIES, null);

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



}
