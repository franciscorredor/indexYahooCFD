package com.wireless.soft.indices.cfd.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.wireless.soft.indices.cfd.collections.RelativeStrengthIndexData;

/**
 * @author Francisco
 * Clase encargada de calculos matematicos para validar la compra o no de las acciones
 * e impresi�n de formatos de fecha, entre otros. General
 *
 */
public class UtilGeneral {
	
	
	/**
	 * @param precioBuy
	 * @param precioHigh
	 * @param PrecioLow
	 * @return 
	 * Sret
	 */
	public static Boolean isPriceBetweenHighLow(String precioBuy, String precioHigh, String PrecioLow){
		Boolean retorno = false;
		try{
		double pB = Double.valueOf(precioBuy);
		double ph = Double.valueOf(precioHigh);
		double pl = Double.valueOf(PrecioLow);
		//Valida que el precio se almenos igual al valor mas bajo
		//y que este dentro del valor -2 dolares al mas alto,
		//Recuerde la estrategia de comprar barato para vender alto
		//Buy when price reaches its low, sell when prices reaches high
		if (pB >= pl && pB < (ph-2)){
			retorno = true;
		}else{
			retorno = false;
		}
				
				
		}catch (Exception e){
			retorno = false;
		}
		
		
		return retorno;
	}
	
	/**
	 * @param precioBuy
	 * @param precioHigh
	 * @param PrecioLow
	 * @return 
	 * Sret
	 */
	public static Boolean isPriceBetweenHighLow(double precioBuy, double precioHigh, double PrecioLow){
		Boolean retorno = false;
		try{
		double pB = Double.valueOf(precioBuy);
		double ph = Double.valueOf(precioHigh);
		double pl = Double.valueOf(PrecioLow);
		//Valida que el precio se almenos igual al valor mas bajo
		//y que este dentro del valor -2 dolares al mas alto,
		//Recuerde la estrategia de comprar barato para vender alto
		//Buy when price reaches its low, sell when prices reaches high
		if (pB >= pl && pB < (ph-2)){
			retorno = true;
		}else{
			retorno = false;
		}
				
				
		}catch (Exception e){
			retorno = false;
		}
		
		
		return retorno;
	}
	
	/**
	 * @param calendar
	 * @param format
	 * @return
	 * Retorna el string de fecha dado un formato
	 */
	public static String printFormat(Calendar calendar, String format){
		
		String retornoFF = null;
		
		SimpleDateFormat format1 = new SimpleDateFormat(format); //"yyyy-MM-dd HH:mm:ss.SSS0"
		retornoFF = format1.format(calendar.getTime());    
		
		
		return retornoFF;
		
	}
	
	public static String printNumberFormat(double valNum, String format){
		String retNF = null;
		
//		customFormat("###,###.###", 123456.789);
//	      customFormat("###.##", 123456.789);
//	      customFormat("000000.000", 123.78);
//	      customFormat("$###,###.###", 12345.67);  
		DecimalFormat myFormatter = new DecimalFormat(format);
		retNF = myFormatter.format(valNum);
				
		return retNF;
	}
	
	/**
	 * Calcula la media
	 * @param lstMediaSearch
	 */
	public static double imprimirMedia(List<Double> lstMediaSearch){
		Double med[] = new Double[lstMediaSearch.size()];
		med = lstMediaSearch.toArray(med);
		Arrays.sort(med);
	    int middle = med.length / 2;
	    if (med.length % 2 == 0)
	    {
	      double left = med[middle - 1];
	      double right = med[middle];
	      System.out.println("middle [LR]:" + ((left + right) / 2) );
	      return ((left + right) / 2);
	    }
	    else
	    {
	      System.out.println("middle:" + (med[middle]) );
	      return (med[middle]);
	    }
	}
	
	
	public static List<RelativeStrengthIndexData> getListaRSI(){
		List<RelativeStrengthIndexData> lstRSI = null;
		lstRSI = new ArrayList<RelativeStrengthIndexData>();
		try(BufferedReader br = new BufferedReader(new FileReader("/nbr/relativeStrengthIndex/table_888.L.csv"))) {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    int ctd = 0;
		    while (line != null) {
		    	
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		        if (null != line){
		        	//System.out.println(line);
		        	RelativeStrengthIndexData rsid = new RelativeStrengthIndexData();
		        	String[] torsid = line.split(",");
			        rsid.setId(++ctd);
			        DateFormat formatter1;
			        formatter1 = new SimpleDateFormat("yyyy-mm-DD");
			        rsid.setFecha(  formatter1.parse(torsid[0]) ) ;
			        rsid.setClose(Double.parseDouble(torsid[6]));
			        rsid.setHigh(Double.parseDouble(torsid[2]));
			        rsid.setLow(Double.parseDouble(torsid[3]));
			        lstRSI.add(rsid);
		        }
		        
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lstRSI;
	}

}
