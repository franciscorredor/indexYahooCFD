package com.wireless.soft.indices.cfd.collections;


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
		
		float calificacionVariable1 = (float)  0.55; 
		float calificacionVariable2 = (float)  0.45;
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
	 * @param compareCR
	 * @return
	 */
	@Override
	public int compareTo(CompanyRanking compareCR) {
		
		int compareQuantity = (int) ((CompanyRanking) compareCR).getVolumePercentageIncrement();
		
		//ascending order
		//return (int) this.volumePercentageIncrement - compareQuantity;
		
		//descending order
		return (int) ((int) compareQuantity - this.volumePercentageIncrement);
		
	}

	
	
	@Override
    public String toString() {
	StringBuffer s = new StringBuffer();
	s.append("\n idCompany [" + this.idCompany + "]");
	s.append(" companyName [" + this.companyName + "]");
	s.append(" OBV [" + this.OBV + "]");
	s.append(" pricePercentageincrement [" + this.pricePercentageincrement + "]");
	s.append(" volumePercentageIncrement [" + this.volumePercentageIncrement + "]");
	s.append(" notaPonderada [" + this.getNotaPonderada() + "]");

	return s.toString();
    }


}
