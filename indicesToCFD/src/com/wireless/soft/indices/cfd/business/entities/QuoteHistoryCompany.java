package com.wireless.soft.indices.cfd.business.entities;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author 	Francisco Corredor
 * @since  	01Dec2015
 * @version	1.0  
 * entity de la tabla indexyahoocfd.iyc_quote_company_history
 */
@NamedQueries(value = {				
		@NamedQuery(name = "findAllQuoteHistoryCompany", query = "SELECT s FROM QuoteHistoryCompany s ORDER BY s.id")
					})
@Entity
@Table(name = "indexyahoocfd.iyc_quote_company_history")
public class QuoteHistoryCompany implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4861537089116018142L;
    // ////////////////////////////////////////////////////////////////////////
    // Atributos de la clase
    // ////////////////////////////////////////////////////////////////////////
	/**Identificador del registro*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "QCH_CODIGO")
    private Long id;

    /** Informacion de la Compania */
    //@OneToOne(targetEntity = Company.class, fetch = FetchType.EAGER)
    //@JoinColumn(name = "SCN_CODIGO", nullable = false)
    //private Company company;
    @Column(name = "SCN_CODIGO", nullable = false)
    private Long company;
    
    @Column(name = "QHC_FECHA_CREACION", nullable = false)
    /** Fecha Creacion */
    private Calendar fechaCreacion;
    
    @Column(name = "name", nullable = true)
    private String name;
    
    @Column(name = "symbol", nullable = true)
    private String symbol;
    
    @Column(name = "ts", nullable = true)
    private String ts;
    
    @Column(name = "type", nullable = true)
    private String type;
    
    @Column(name = "utctime", nullable = true)
    private String utctime;
    
    @Column(name = "volume", nullable = true)
    private String volume;
    
    @Column(name = "syntaxis_change", nullable = true)
    private String change;
    
    @Column(name = "chg_percent", nullable = true)
    private String chg_percent; 
    
    @Column(name = "day_high", nullable = true)
    private String day_high;
    
    @Column(name = "day_low", nullable = true)
    private String day_low;
    
    @Column(name = "issuer_name", nullable = true)
    private String issuer_name;
    
    @Column(name = "issuer_name_lang", nullable = true)
    private String issuer_name_lang;
    
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
	 * @return the company
	 */
	public Long getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Long company) {
		this.company = company;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Calendar getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Calendar fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
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
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the ts
	 */
	public String getTs() {
		return ts;
	}

	/**
	 * @param ts the ts to set
	 */
	public void setTs(String ts) {
		this.ts = ts;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the utctime
	 */
	public String getUtctime() {
		return utctime;
	}

	/**
	 * @param utctime the utctime to set
	 */
	public void setUtctime(String utctime) {
		this.utctime = utctime;
	}

	/**
	 * @return the volume
	 */
	public String getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}

	/**
	 * @return the change
	 */
	public String getChange() {
		return change;
	}

	/**
	 * @param change the change to set
	 */
	public void setChange(String change) {
		this.change = change;
	}

	/**
	 * @return the chg_percent
	 */
	public String getChg_percent() {
		return chg_percent;
	}

	/**
	 * @param chg_percent the chg_percent to set
	 */
	public void setChg_percent(String chg_percent) {
		this.chg_percent = chg_percent;
	}

	/**
	 * @return the day_high
	 */
	public String getDay_high() {
		return day_high;
	}

	/**
	 * @param day_high the day_high to set
	 */
	public void setDay_high(String day_high) {
		this.day_high = day_high;
	}

	/**
	 * @return the day_low
	 */
	public String getDay_low() {
		return day_low;
	}

	/**
	 * @param day_low the day_low to set
	 */
	public void setDay_low(String day_low) {
		this.day_low = day_low;
	}

	/**
	 * @return the issuer_name
	 */
	public String getIssuer_name() {
		return issuer_name;
	}

	/**
	 * @param issuer_name the issuer_name to set
	 */
	public void setIssuer_name(String issuer_name) {
		this.issuer_name = issuer_name;
	}

	/**
	 * @return the issuer_name_lang
	 */
	public String getIssuer_name_lang() {
		return issuer_name_lang;
	}

	/**
	 * @param issuer_name_lang the issuer_name_lang to set
	 */
	public void setIssuer_name_lang(String issuer_name_lang) {
		this.issuer_name_lang = issuer_name_lang;
	}
	
	
	
	
	
	
	


}
