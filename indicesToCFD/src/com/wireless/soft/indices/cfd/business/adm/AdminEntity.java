package com.wireless.soft.indices.cfd.business.adm;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.wireless.soft.indices.cfd.business.entities.Company;
import com.wireless.soft.indices.cfd.exception.BusinessException;

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
     * @see org.colfuturo.semillero.business.interfaces.ISemillero#guardarSemillero(org.colfuturo.semillero.model.to.SemilleroTO)
     */
    public List<Company> getCompanies() throws BusinessException {
		return null;
	
			// Tiene idPersona y es mayor a 0, se busca la persona
    	   //Realizar consulta de entity, basarse en Ejemplo..
			//persona = em.find(Persona.class, semilleroTO.getPersona().getId());
	
    }



}
