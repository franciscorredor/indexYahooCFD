package com.wireless.soft.indices.cfd.collections;

import java.util.Calendar;
import java.util.Date;

import com.wireless.soft.indices.cfd.util.UtilGeneral;

/**
 * @author Francisco
 *
 */
public class RelativeStrengthIndexData {
	
	private long id;
	
	private long idCompany;
	
	private Date fecha;
	
	private double close;
	
	private double change;
	
	private double gain;
	
	private double loss;
	
	private double avgGain;
	
	private double avgLoss;
	
	private double rs;
	
	private double fourteenDayRSI;
	
	
	/*
	 * Getters and 
	 */

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(long idCompany) {
		this.idCompany = idCompany;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getChange() {
		return change;
	}

	public void setChange(double change) {
		this.change = change;
	}

	public double getGain() {
		return gain;
	}

	public void setGain(double gain) {
		this.gain = gain;
	}

	public double getLoss() {
		return loss;
	}

	public void setLoss(double loss) {
		this.loss = loss;
	}

	public double getAvgGain() {
		return avgGain;
	}

	public void setAvgGain(double avgGain) {
		this.avgGain = avgGain;
	}

	public double getAvgLoss() {
		return avgLoss;
	}

	public void setAvgLoss(double avgLoss) {
		this.avgLoss = avgLoss;
	}

	public double getRs() {
		return rs;
	}

	public void setRs(double rs) {
		this.rs = rs;
	}

	public double getFourteenDayRSI() {
		return fourteenDayRSI;
	}

	public void setFourteenDayRSI(double fourteenDayRSI) {
		this.fourteenDayRSI = fourteenDayRSI;
	}
	
	@Override
    public String toString() {
	StringBuffer s = new StringBuffer();
	
	s.append("\n id [" + this.id + "]");
	s.append(" close [" + this.close + "]");
	s.append(" change [" + this.change + "]");
	s.append(" fecha [" + this.fecha + "]");
	return s.toString();
    }
	
	

}
