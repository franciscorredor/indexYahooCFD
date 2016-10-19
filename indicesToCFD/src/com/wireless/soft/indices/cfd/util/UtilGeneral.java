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
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wireless.soft.indices.cfd.collections.RelativeStrengthIndexData;

/**
 * @author Francisco
 * Clase encargada de calculos matematicos para validar la compra o no de las acciones
 * e impresi_n de formatos de fecha, entre otros. General
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
	
	
	/**
	 * @return
	 */
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
	
	
	/**
	 * Toma info de bloomberg para indicar si hay retornos positivos o negativos
	 * @return
	 */
	public static String getYearReturn(String urlBloomberg) {
		String retornoYTD = "";

		try {
			Document doc;
			doc = Jsoup.connect(urlBloomberg).get();
			Elements newsHeadlines = doc.select("div.cell__value_up");
			int itera = 0;

			for (Element element : newsHeadlines) {
				//System.out.print((++itera) + ". ");
				retornoYTD += (++itera) + ". ";
				//System.out.print("[" + element.text() + "]");
				retornoYTD += "[" + element.text() + "]";
			}

		} catch (IOException e) {
			System.out.println("Error al obtener indicador de Bloomberg: " + e.getMessage());
			System.out.println("{"+urlBloomberg+"}");
			//e.printStackTrace();
		}
		
		if (retornoYTD != null && retornoYTD.length() > 2){
			retornoYTD = urlBloomberg + "\n" + retornoYTD;
		}

		return retornoYTD;
	}
	
	
	/**
	 * Obtiene la fecha de hoy en formato "yyyy-mm-DD"
	 * @return
	 */
	public static String obtenerToday(){
		
		String fh = null;
		DateFormat formatter1;
		formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		fh = formatter1.format(new Date());
		
		
		return fh;
		
	}
	
	/**
	 * Obtiene la fecha de hoy en formato "yyyy-mm-DD"
	 * @return
	 */
	public static String obtenerTodayMinusNDays(int ndays){
		
		String dateOneMothAgo = null;
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, ndays); // I just want date before 90 days. you can give that you want.

//		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd"); // you can specify your format here...
//		Log.d("DATE","Date before 90 Days: " + s.format(new Date(cal.getTimeInMillis())));
//		
//		
//		Date today = new Date();
		DateFormat formatter1;
		formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		dateOneMothAgo = formatter1.format(new Date(cal.getTimeInMillis()));
		
		
		return dateOneMothAgo;
		
	}
	
	
	/**
	 * Obtiene la fecha de hoy hace un mes en formato "yyyy-mm-DD"
	 * @return
	 */
	public static String obtenerTodayMinusMonth(){
		
		String dateOneMothAgo = null;
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -27); // I just want date before 90 days. you can give that you want.

//		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd"); // you can specify your format here...
//		Log.d("DATE","Date before 90 Days: " + s.format(new Date(cal.getTimeInMillis())));
//		
//		
//		Date today = new Date();
		DateFormat formatter1;
		formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		dateOneMothAgo = formatter1.format(new Date(cal.getTimeInMillis()));
		
		
		return dateOneMothAgo;
		
	}
	
	
	
	/**
	 * @return
	 */
	public static String obtenerTodayMinusThree(){
		
		String dateThreeDaysAgo = null;
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -5); // I just want date before 90 days. you can give that you want.
		DateFormat formatter1;
		formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		dateThreeDaysAgo = formatter1.format(new Date(cal.getTimeInMillis()));
		
		return dateThreeDaysAgo;
		
	}
	
	/**
	 * @return
	 */
	public static String obtenerFirstDateOftheYearMinusOne(){
		
		String firstDayYearMinusOne = null;
		String year = null;
		
		DateFormat formatterYear;
		formatterYear = new SimpleDateFormat("yyyy");
		year = formatterYear.format(new Date());
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.add(Calendar.DATE, -2); 
		
		DateFormat formatter1;
		formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		firstDayYearMinusOne = formatter1.format(new Date(cal.getTimeInMillis()));
		
		
		return firstDayYearMinusOne;
		
	}
	
	
	/**
	 * @return
	 */
	public static String obtenerFirstDateOftheYearPlusOne(){
		
		String firstDayYearPlusOne = null;
		String year = null;
		
		DateFormat formatterYear;
		formatterYear = new SimpleDateFormat("yyyy");
		year = formatterYear.format(new Date());
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.add(Calendar.DATE, 2); 
		
		DateFormat formatter1;
		formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		firstDayYearPlusOne = formatter1.format(new Date(cal.getTimeInMillis()));
		
		
		return firstDayYearPlusOne;
		
	}
	
	

}
