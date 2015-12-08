package com.wireless.soft.indices.cfd.collections;

import java.util.Calendar;

import com.wireless.soft.indices.cfd.util.UtilGeneral;


/**
 * @author Francisco
 *
 */
public class CompanyRanking  implements Comparable<CompanyRanking> {  
	


	
	public Long idCompany;
	
	public String companyName;
	
	public double volumePercentageIncrement;
	
	public double pricePercentageincrement;
	
	private double OBV;
	
	private double notaPonderada;
	
	private double precioEvaluado;
	
	private double dayHigh;
	
	private double dayLow;
	
	private Calendar fechaIteracion1;
	
	private Calendar fechaIteracion2;
	

	/**
	 * @return the idCompany
	 */
	public Long getIdCompany() {
		return idCompany;
	}

	/**
	 * @param idCompany the idCompany to set
	 */
	public void setIdCompany(Long idCompany) {
		this.idCompany = idCompany;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the volumePercentageIncrement
	 */
	public double getVolumePercentageIncrement() {
		return volumePercentageIncrement;
	}

	/**
	 * @param volumePercentageIncrement the volumePercentageIncrement to set
	 */
	public void setVolumePercentageIncrement(double volumePercentageIncrement) {
		this.volumePercentageIncrement = volumePercentageIncrement;
	}

	/**
	 * @return the pricePercentageincrement
	 */
	public double getPricePercentageincrement() {
		return pricePercentageincrement;
	}

	/**
	 * @param pricePercentageincrement the pricePercentageincrement to set
	 */
	public void setPricePercentageincrement(double pricePercentageincrement) {
		this.pricePercentageincrement = pricePercentageincrement;
	}
	
	/**
	 * @return the oBV
	 */
	public double getOBV() {
		return OBV;
	}

	
	public void setPrecioEvaluado(double precioEvaluado) {
		this.precioEvaluado = precioEvaluado;
	}
	
	public double getPrecioEvaluado() {
		return this.precioEvaluado;
	}

	/**
	 * @param oBV the oBV to set
	 */
	public void setOBV(double oBV) {
		OBV = oBV;
	}

	/**
	 * @return the notaPonderada
	 */
	public double getNotaPonderada() {
		
		float calificacionVariable1 = (float)  0.61; 
		float calificacionVariable2 = (float)  0.39;
		//55%  precio
		//45% volumen
		this.notaPonderada =  this.pricePercentageincrement  * calificacionVariable1
							+ this.volumePercentageIncrement  * calificacionVariable2; 
		
		return notaPonderada;
	}

	/**
	 * @param notaPonderada the notaPonderada to set
	 */
	private void setNotaPonderada(double notaPonderada) {
		this.notaPonderada = notaPonderada;
	}

	/**
	 * @return the dayHigh
	 */
	public double getDayHigh() {
		return dayHigh;
	}

	/**
	 * @param dayHigh the dayHigh to set
	 */
	public void setDayHigh(double dayHigh) {
		this.dayHigh = dayHigh;
	}

	/**
	 * @return the dayLow
	 */
	public double getDayLow() {
		return dayLow;
	}

	/**
	 * @param dayLow the dayLow to set
	 */
	public void setDayLow(double dayLow) {
		this.dayLow = dayLow;
	}

	/**
	 * @return the fechaIteracion1
	 */
	public Calendar getFechaIteracion1() {
		return fechaIteracion1;
	}

	/**
	 * @param fechaIteracion1 the fechaIteracion1 to set
	 */
	public void setFechaIteracion1(Calendar fechaIteracion1) {
		this.fechaIteracion1 = fechaIteracion1;
	}

	/**
	 * @return the fechaIteracion2
	 */
	public Calendar getFechaIteracion2() {
		return fechaIteracion2;
	}

	/**
	 * @param fechaIteracion2 the fechaIteracion2 to set
	 */
	public void setFechaIteracion2(Calendar fechaIteracion2) {
		this.fechaIteracion2 = fechaIteracion2;
	}

	/**
	 * @param compareCR
	 * @return
	 * Compara Volumen
	 */
	@Override
	public int compareTo(CompanyRanking compareCR) {
		
		int compareQuantity = (int) ((CompanyRanking) compareCR).getVolumePercentageIncrement();
//		
//		//ascending order
//		//return (int) this.volumePercentageIncrement - compareQuantity;
//		
		//descending order
		return (int) ((int) compareQuantity - this.volumePercentageIncrement);
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * Compara Ponderado
	 */
//	@Override
//	public int compareTo(CompanyRanking compareCR) {
//		
//		int comparePonderado = (int) ((CompanyRanking) compareCR).getNotaPonderada();
//		
//		//ascending order
//		//return (int) this.volumePercentageIncrement - compareQuantity;
//		
//		//descending order
//		return (int) ((int) comparePonderado - this.getNotaPonderada());
//		
//	}

	
	
	@Override
    public String toString() {
	StringBuffer s = new StringBuffer();
	s.append("\n idCompany [" + this.idCompany + "]");
	s.append(" companyName [" + this.companyName + "]");
	s.append(" OBV [" + this.OBV + "]");
	s.append(" pricePercentageincrement [" + this.pricePercentageincrement + "]");
	s.append(" volumePercentageIncrement [" + this.volumePercentageIncrement + "]");
	s.append("\n notaPonderada [" + this.getNotaPonderada() + "]");
	s.append(" precioEvaluado [" + this.precioEvaluado + "]");
	s.append(" dayHigh [" + this.dayHigh + "]");
	s.append(" dayLow [" + this.dayLow + "]");
	s.append(" isPriceBetweenHighLow [" + this.isPriceBetweenHighLow() + "]");
	s.append("\n fechaIteracion1 [" +  UtilGeneral.printFormat(this.fechaIteracion1, "yyyy-MM-dd HH:mm:ss")  + "]"); 
	s.append(" fechaIteracion2 [" + UtilGeneral.printFormat(this.fechaIteracion2, "yyyy-MM-dd HH:mm:ss")  + "]");

	return s.toString();
    }
	
	private Boolean isPriceBetweenHighLow(){
		return  UtilGeneral.isPriceBetweenHighLow(this.precioEvaluado, this.dayHigh, this.dayLow);
	}


}
