package com.wireless.soft.indices.cfd.util;

/**
 * @author Francisco
 * Clase encargada de calculos matematicos para validar la compra o no de las acciones
 *
 */
public class UtilMath {
	
	
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

}
