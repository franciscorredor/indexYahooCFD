package com.wireless.soft.indices.cfd.main;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.PropertyConfigurator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wireless.soft.indices.cfd.business.adm.AdminEntity;
import com.wireless.soft.indices.cfd.business.entities.Company;
import com.wireless.soft.indices.cfd.business.entities.FundamentalHistoryCompany;
import com.wireless.soft.indices.cfd.business.entities.QuoteHistoryCompany;
import com.wireless.soft.indices.cfd.collections.CompanyRanking;
import com.wireless.soft.indices.cfd.collections.RelativeStrengthIndexData;
import com.wireless.soft.indices.cfd.deserializable.json.object.ReturnIndexYahooFinanceObject;
import com.wireless.soft.indices.cfd.deserializable.json.object.ReturnYahooFinanceQuoteObject;
import com.wireless.soft.indices.cfd.exception.BusinessException;
import com.wireless.soft.indices.cfd.util.UtilGeneral;

/**
 * Clase principal encargada de obtener los indices de diferentes compañias 
 * @author Francisco Corredor
 * https://sites.google.com/site/gson/gson-user-guide
 * TODO
 * Conseguir BD en la nube y realizar lógica de negocio en CLOUD
 */

enum Evalua {ZERO, ONE, TWO, THREE}

public class ObtenerMarketIndex {
	
	// ////////////////////////////////////////////////////////////////////////
    // Atributos de la clase
    // ////////////////////////////////////////////////////////////////////////
    /** */
    private final Gson gson = this.createGson();
    
    private AdminEntity admEnt = null;
    
    
    
    public ObtenerMarketIndex(){
    	admEnt = new AdminEntity();
    }

	
	
	/**
	 * @param args
	 * args[0]  --> Persistir o consultar (1/0)
	 * args[1]  --> Numero de iteraciones anteriores a ver
	 * args[2]  --> Porcentaje [1-100], indicador del TOP de las mejores compañias a imprimir, dependiendo del porcentaje
	 * Samples:
	 * 	java -jar indicesToCFD.jar 1 2 10
	 *  java -jar indicesToCFD.jar 0
	 *  java -jar indicesToCFD.jar 3 1  ===> imprime chart cada minuto de la compania 1
	 */
	public static void main(String[] args) {
		
		if (null == args || args.length < 1){
			System.out.println("Debe especificar un argumento");
			return;
		}
		Integer argumento2 = null;
		Integer cortePorcentajePonderado = null;
		PropertyConfigurator.configure("log4j.properties");
		//Valida si hay un 2do argumento 
		//		i.  	# Iteraciones antes
		//		ii.		Id compania	
		if (null != args && args.length >1 && null != args[1]){
			try{
				argumento2 = Integer.parseInt( args[1] );
			}catch (Exception e){
				argumento2 = null;
			}
		}
		//Valida si hay un 3er argumento (# Corte pocentaje ponderado)
		if (null != args && args.length >2 && null != args[2]){
			try{
				cortePorcentajePonderado = Integer.parseInt( args[2] );
			}catch (Exception e){
				cortePorcentajePonderado = null;
			}
		}
		
		//Inicializa la clase principal.
		ObtenerMarketIndex omi = new ObtenerMarketIndex();
		try {
			String accion = args[0];
			switch (accion){
			case "0":
				System.out.println("\n Persiste info de las compañias, consultando de yahoo");
				omi.printPERatio();
				System.out.println("Precio accion menor = & % volumen mayor a cero!");
				omi.printOBV(argumento2, cortePorcentajePonderado, Evalua.ONE);
				System.out.println("Precio accion mayor & % volumen mayor a cero!");
				omi.printOBV(argumento2, cortePorcentajePonderado, Evalua.THREE);
				break;
			case "1":
				System.out.println("\n Imprime el indicador OBV");
				System.out.println("Precio accion menor = & % volumen mayor a cero!");
				omi.printOBV(argumento2, cortePorcentajePonderado, Evalua.ONE);
				System.out.println("Precio accion mayor & % volumen mayor a cero!");
				omi.printOBV(argumento2, cortePorcentajePonderado, Evalua.THREE);
				break;
			case "2":
				System.out.println("\n Indica si el mecado esta en Bull o Bear");
				omi.printIndicadorMercado();
				break;
			case "3":
				System.out.println("\n Print Chart Company UP&Down Price");
				omi.printChartCompany(argumento2);
				break;
			case "4":
				System.out.println("\n Persistir cada 10 minutos información de la compañias");
				omi.persisteVariasIteraciones();
				break;
			case "5":
				System.out.println("\n test Call PE Ratio");
				omi.printPERatio();
				break;	
			case "6":
				System.out.println("\n Call relativeStrengthIndex");
				omi.relativeStrengthIndex();
				break;	
				
			default:
				System.out.println("\n No realiza acción");
				break;
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
	
	
	
	
	/**
	 * @param url
	 * @return el indice de yahoo
	 * @throws IOException
	 */
	private ReturnIndexYahooFinanceObject executeYahooIndex(String url) throws IOException {


		    JsonElement result = executeJ(url);
		    if (result.isJsonObject()) {
			JsonElement error = result.getAsJsonObject().get("error");
			if (error != null) {
			    JsonElement code = result.getAsJsonObject().get("code");
			    System.out.println("[Error] code:" + code);
			}
		    }


		    return gson.fromJson(result, ReturnIndexYahooFinanceObject.class);

		
	   }
	
	/**
	 * @param url
	 * @return el Quote de yahooIndex
	 * @throws IOException
	 */
	private ReturnYahooFinanceQuoteObject executeYahooIndexQuote(String url)
			throws IOException {
		try {
			
			JsonElement result = executeJ(url);
			if (result.isJsonObject()) {
				JsonElement error = result.getAsJsonObject().get("error");
				if (error != null) {
					JsonElement code = result.getAsJsonObject().get("code");
					System.out.println("[Error] code:" + code);
				}
			}

			return gson.fromJson(result, ReturnYahooFinanceQuoteObject.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	private JsonElement executeJ(String url) throws IOException {
		return new JsonParser().parse(execute(url));
	    }
	
	
	/**
     * @param url
     * @param request
     * @return
     * @throws IOException
     */
	private String execute(String url) throws IOException {
		String response = null;
		try {
			// _logger.info("Post to " + url + " : " + request);

			DefaultHttpClient http = new DefaultHttpClient();

			// HttpPost post = new HttpPost(url);
			// post.setEntity(new StringEntity(URLEncoder.encode(request,
			// "UTF-8")));
			HttpGet get = new HttpGet(url);
			// String response = http.execute(post, new BasicResponseHandler());
			response = http.execute(get, new BasicResponseHandler());
			// _logger.info("Response: " + response);
		} catch (IOException io) {
			System.out.println("url No responde:" + url);
			io.printStackTrace();
			throw io; 
		}
		return response;
	}
    
    /**
     * Creates a new {@link Gson} object.
     */
    private Gson createGson() {
	GsonBuilder builder = new GsonBuilder();
	return builder.create();
    }
    
    
    
    /**
     * @throws Exception 
     */
	private void printOBV(Integer numIteracionAntes,
			Integer cortePorcentajePonderado, Evalua ev) {
		List<CompanyRanking> cr = new LinkedList<CompanyRanking>();
		List<Double> lstMediaSearch = new ArrayList<Double>();
		try {
				/*
				 * 0 --> Accion disminuye, volumen disminuye
				 * 1 --> Accion disminuye, volumen aumenta
				 * 2 --> Accion aumenta, volumen disminuye
				 * 3 --> Accion aumenta, volumen aumenta
				 */
			for (Company cmp : admEnt.getCompanies()) {
				List<Object> listIdxCompany = admEnt.getCompIdxQuote(cmp);
				Object tmp[] = listIdxCompany.toArray();
				if (null != tmp && tmp.length > 1) {
					//TODO --> Realizar comparación con el primer regitro del dia
					QuoteHistoryCompany qhcBefore = (QuoteHistoryCompany) tmp[numIteracionAntes == null ? 1 : numIteracionAntes];
					QuoteHistoryCompany qhcNow = (QuoteHistoryCompany) tmp[0];
					
					
					Double valueNowVolume = Double.valueOf(qhcNow.getVolume());
					lstMediaSearch.add(valueNowVolume);
					
					//Obtencion de PE ratio by company
					FundamentalHistoryCompany fc = admEnt.getLastFundamentalRecord(cmp);
					Double PERatio = Double.valueOf(fc.getpERatio()!=null?fc.getpERatio():"-1" );
					
					//Obtencion precio mas bajo
					Double price = Double.valueOf(qhcNow.getPrice()!=null?qhcNow.getPrice():"0");
					Double supportLevel = Double.valueOf(qhcNow.getYear_low()!=null?qhcNow.getYear_low():"0");
					
					
					//System.out.println("Company{"+cmp.getName()+"} PERatio{"+PERatio+"}");

					/*
					 * TODO Calcular cuanto porcentaje subio y dar un ponderado
					 * i. Si encuentra una noticia que contenga palabras
					 * positivas, dar una nota apreciativa al ponderad de 0,05%
					 * //Guaardar la informacion que se itera en Collections y
					 * que realize ordenamiento, para que imprima en linea el
					 * resultado y no tener que almacenarlo en ls BD para
					 * despues leerlo o calcularlo. Realixar el calculo de las
					 * mejoras comañias depues de la iteración por cada uno de
					 * las compañias que estan cumplienod con el // *
					 * calculo/estrategia definida en el algoritmo! adicionar la
					 * variable /indice P/e usando http://jsoup.org/
					 * 2015Dec24--> Tener en cuenta el laboratorio de analisis fundamental realizado {https://drive.google.com/drive/u/0/folders/0BwJXnohKnxjbfmNJV2NsYm4zT1Zqb0VlUC1zaUlfcjRaM2VIX1E2WmZ6cU1MN1J2WWJhTGs}
					 */
					if ((PERatio > 0 && PERatio <= 17)
							&& (price > supportLevel)) {
						
						CompanyRanking addAR = null;

						/*
						 * Variable valor --> A Variable volumen --> B 
						 * A B 
						 * 0 0	==0 Evalua.ZERO 
						 * 0 1 	==1 Evalua.ONE 
						 * 1 0 	==2 Evalua.TWO
						 * 1 1 	==3 Evalua.THREE
						 */
						//Evalua ev = Evalua.THREE;
						switch (ev) {
						case THREE:
							addAR = evalua03(qhcBefore, qhcNow, cmp);
							break;
						case TWO:
							addAR = evalua02(qhcBefore, qhcNow, cmp);
							break;
						case ONE:
							addAR = evalua01(qhcBefore, qhcNow, cmp);
							break;
						case ZERO:
							addAR = evalua00(qhcBefore, qhcNow, cmp);
							break;
						default:
							break;

						}
						
						if (null != addAR){
							addAR.setPeRatio(PERatio);
							addAR.setCapitalization(fc.getMarketCapitalization());
							cr.add(addAR);
						}
						
						

					}//END --> PERatio validation
										
				


				}
				
				
			}//END --> for companies
			
			//Imprime la media
			UtilGeneral.imprimirMedia(lstMediaSearch);

			// Imprime Arreglo ordenado
			Collections.sort(cr);
			// TODO persistir la informacion del resultado, con la fecha de la
			// ejecuión del proceso
			int i = 0;
			for (CompanyRanking companyRanking : cr) {
				if (null != companyRanking
						&& companyRanking.getNotaPonderada() > (cortePorcentajePonderado == null ? 25
								: cortePorcentajePonderado)) {
					// TODO --> PErsisistir la información para saber cada
					// cuanto aparece una compañia en la impresion del lsitado
					// en un determinado tiempo.
					System.out.println((++i) + " " + companyRanking.toString());
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    
    /**
     * @throws BusinessException
     * @throws IOException 
     */
    private  void persistirCompaniesQuotes(ReturnYahooFinanceQuoteObject ri, Company cmp) throws BusinessException, IOException{
    	
    	admEnt.persistirCompaniesQuotes(ri, cmp);
    	
    }
    
    /**
     * @throws BusinessException
     * @throws IOException 
     */
    private  void persistirCompaniesFundamental(ReturnYahooFinanceQuoteObject rf, Company cmp) throws BusinessException, IOException{
    	
    	admEnt.persistirCompaniesFundamental(rf, cmp);
    	
    }
    
    /**
     * Imprime si el mercado esta en Bull o Bear
     * @throws Exception 
     * @throws BusinessException 
     */
	private void printIndicadorMercado() {
		try {
			Company cmp = new Company();
			cmp.setId(43l);
			QuoteHistoryCompany qFirstHC = admEnt.getFirstRecordDay(cmp);
			if (null != qFirstHC) {
				System.out.println(qFirstHC.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * @param idCompany
	 * imprime resultado cada instante de tiempo 1 minuto
	 * [nombreCompania], [%inc], estaSubiendo[true/false(numeroVeces)*Volver a iteracion uno si cambia la bandera]
	 */
	private void printChartCompany(Integer idCompany) {
		if (null != idCompany) {
			try {
				Company cmp = new Company();
				cmp.setId(idCompany.longValue());
				cmp = admEnt.getCompanyById(cmp);
				// Raaliza iteracion infinita
				int iteracionUpDown = 0;
				boolean banderaUpDown = false;
				CompanyRanking crIteracion1 = this.evaluaRanking(cmp);
				if (null != crIteracion1) {
					while (true) {
						iteracionUpDown++;
						// 1. Persiste info compania
						ReturnYahooFinanceQuoteObject ri = this.executeYahooIndexQuote(cmp.getUrlQuote());
						//System.out.println(cmp.getUrlQuote());
						//Persiste en loa BD	
						this.persistirCompaniesFundamental(ri, cmp);
						this.persistirCompaniesQuotes(ri, cmp);
						// 2. espera un instante de tiempo 1minuto
						Thread.sleep(60000l);
						// 3. Consulta si la compania subio o bajo
						CompanyRanking cr = this.evaluaRanking(cmp);
						if (null != cr) {
							// 4. imprime
							System.out.println("["+iteracionUpDown+"] "+cr.printToChart());
							System.out.println("["+iteracionUpDown+"] "+cr.toString());
							if (cr.getPrecioEvaluado() >  crIteracion1.getPrecioEvaluado()){
								banderaUpDown = !banderaUpDown;	
							}
							if (banderaUpDown){
								iteracionUpDown = 0;
							}
						}
						crIteracion1 = cr;
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * @param cmp
	 * Evalua el rankig para ser impreso en el chart
	 * @throws Exception 
	 */
	private CompanyRanking evaluaRanking(Company cmp) throws Exception {

		CompanyRanking addAR = null;
		List<Object> listIdxCompany = admEnt.getCompIdxQuote(cmp);
		Object tmp[] = listIdxCompany.toArray();
		if (null != tmp && tmp.length > 1) {

			QuoteHistoryCompany qhcBefore = (QuoteHistoryCompany) tmp[1];
			QuoteHistoryCompany qhcNow = (QuoteHistoryCompany) tmp[0];
			Double valueBeforePrice = Double.valueOf(qhcBefore.getPrice());
			Double valueNowPrice = Double.valueOf(qhcNow.getPrice());

			addAR = new CompanyRanking();
			addAR.setCompanyName(cmp.getName());
			addAR.setIdCompany(cmp.getId());

			addAR.setPricePercentageincrement((((valueNowPrice * 100) / valueBeforePrice) - 100));
			addAR.setDayLow(Double.valueOf(qhcNow.getDay_low()));
			addAR.setPrecioEvaluado(valueNowPrice);
			addAR.setVolumenEvaluado(valueNowPrice);
			addAR.setDayHigh(Double.valueOf(qhcNow.getDay_high()));
			addAR.setFechaIteracion1(qhcBefore.getFechaCreacion());
			addAR.setFechaIteracion2(qhcNow.getFechaCreacion());
			addAR.setSymbol(qhcNow.getSymbol());

		}

		return addAR;
	}
	
	/**
	 * Persiste la información varias veces
	 */
	private void persisteVariasIteraciones(){
		try {
		while (true) {
			// Persiste cada instante de tiempo 10 minuto
			Thread.sleep(60000l * 9);
			this.printPERatio();
		}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private  void printPERatio() throws BusinessException, IOException{
    	
			//ReturnYahooFinanceQuoteObject ri = this.executeYahooIndexQuote("http://query.yahooapis.com/v1/public/yql?q=select%20PERatio%20from%20yahoo.finance.quotes%20where%20symbol%20IN%20(%22LLOY.L%22)&format=json&env=http://datatables.org/alltables.env");
			//ReturnYahooFinanceQuoteObject ri = this.executeYahooIndexQuote("http://query.yahooapis.com/v1/public/yql?q=select%20PERatio%20from%20yahoo.finance.quotes%20where%20symbol%20IN%20(%22888.L%22)&format=json&env=http://datatables.org/alltables.env");			
			for (Company cmp : admEnt.getCompanies()) {
				
				if (null != cmp && null != cmp.getUrlIndex() 
						&& cmp.getUrlIndex().length() > 3){
				
				ReturnYahooFinanceQuoteObject ri = this.executeYahooIndexQuote(cmp.getUrlQuote());
				//System.out.println(cmp.getUrlQuote());
				//Persiste en loa BD	
				this.persistirCompaniesFundamental(ri, cmp);
				this.persistirCompaniesQuotes(ri, cmp);
				//System.out.println("ReturnYahooFinanceQuoteObject: " + ri.toString());
				
				}
			}
			System.out.println("Persistio el analisis fundamental");
			
    	
    	
    }
	
	//TODO: --> REalizar algoritmo cuando el precio este bajando , recuerde el laboratiorio q realizo con internet y q tiene q 
	//comprar barato para vender caro. En el ponderado evaluar cuanto a disminuido el precio.
	
	/**
	 * @param qhcBefore
	 * @param qhcNow
	 * @param cmp
	 * @return
	 * Evalua e = Evalua.ZERO;
	 * System.out.println("Precio accion menor = & % volumen menor = a cero!");
	 */
	private CompanyRanking evalua00(QuoteHistoryCompany qhcBefore, QuoteHistoryCompany qhcNow, Company cmp){
		
		
		CompanyRanking addAR = null;
		
		Double valueBeforePrice = Double.valueOf(qhcBefore.getPrice());
		Double valueNowPrice = Double.valueOf(qhcNow.getPrice());
		Double valueBeforeVolume = Double.valueOf(qhcBefore.getVolume());
		Double valueNowVolume = Double.valueOf(qhcNow.getVolume());
		
		
		if (valueNowPrice <= valueBeforePrice){
			//Obtine las compañias q tienen un volumen superior!
			//TODO obtner la media y dar mas calificación a los valores que esten 
			//    dentro mas cerca a la media
			//Realizar pruebas con la información que tiene en la BD
			//Idea01: 27Dec2015 --> De la BD sacar la media
			if ((((valueNowVolume * 100) / valueBeforeVolume) - 100) <= 0) {

				addAR = new CompanyRanking();
				addAR.setCompanyName(cmp.getName());
				addAR.setIdCompany(cmp.getId());
				addAR.setOBV((valueBeforeVolume + valueNowVolume));
				addAR.setVolumePercentageIncrement((((valueNowVolume * 100) / valueBeforeVolume) - 100));
				addAR.setPricePercentageincrement((((valueNowPrice * 100) / valueBeforePrice) - 100));
				addAR.setDayLow(Double.valueOf(qhcNow.getDay_low()));
				addAR.setPrecioEvaluado(valueNowPrice);
				addAR.setVolumenEvaluado(valueNowVolume);
				addAR.setDayHigh(Double.valueOf(qhcNow.getDay_high()));
				addAR.setFechaIteracion1(qhcBefore.getFechaCreacion());
				addAR.setFechaIteracion2(qhcNow.getFechaCreacion());
				addAR.setSymbol(qhcNow.getSymbol());
			}

		}
		
		return addAR;
		
	}
	
	/**
	 * @param qhcBefore
	 * @param qhcNow
	 * @param cmp
	 * @return
	 * Evalua e = Evalua.ONE;
	 * System.out.println("Precio accion menor = & % volumen mayor a cero!");
	 */
	private CompanyRanking evalua01(QuoteHistoryCompany qhcBefore, QuoteHistoryCompany qhcNow, Company cmp){
		
		
		CompanyRanking addAR = null;
		
		try{
			
	
		
		Double valueBeforePrice = Double.valueOf(qhcBefore.getPrice());
		Double valueNowPrice = Double.valueOf(qhcNow.getPrice());
		Double valueBeforeVolume = Double.valueOf(qhcBefore.getVolume());
		Double valueNowVolume = Double.valueOf(qhcNow.getVolume());
		
		
		if (valueNowPrice <= valueBeforePrice){
			//Obtine las compañias q tienen un volumen superior!
			//TODO obtner la media y dar mas calificación a los valores que esten 
			//    dentro mas cerca a la media
			//Realizar pruebas con la información que tiene en la BD
			//Idea01: 27Dec2015 --> De la BD sacar la media
			if ((((valueNowVolume * 100) / valueBeforeVolume) - 100) > 0) {

				addAR = new CompanyRanking();
				addAR.setCompanyName(cmp.getName());
				addAR.setIdCompany(cmp.getId());
				addAR.setOBV((valueBeforeVolume + valueNowVolume));
				addAR.setVolumePercentageIncrement((((valueNowVolume * 100) / valueBeforeVolume) - 100));
				addAR.setPricePercentageincrement((((valueNowPrice * 100) / valueBeforePrice) - 100));
				addAR.setDayLow(Double.valueOf(qhcNow.getDay_low()));
				addAR.setPrecioEvaluado(valueNowPrice);
				addAR.setVolumenEvaluado(valueNowVolume);
				addAR.setDayHigh(Double.valueOf(qhcNow.getDay_high()));
				addAR.setFechaIteracion1(qhcBefore.getFechaCreacion());
				addAR.setFechaIteracion2(qhcNow.getFechaCreacion());
				addAR.setSymbol(qhcNow.getSymbol());
			}

		}
		}catch (Exception e){
			//System.out.println("Error en : evalua01 --> " + e.getMessage());
		}
		
		return addAR;
		
	}
	
	/**
	 * @param qhcBefore
	 * @param qhcNow
	 * @param cmp
	 * @return
	 * Evalua e = Evalua.TWO;
	 * System.out.println("Precio accion mayor & % volumen <= a cero!");
	 */
	private CompanyRanking evalua02(QuoteHistoryCompany qhcBefore, QuoteHistoryCompany qhcNow, Company cmp){
		
		
		CompanyRanking addAR = null;
		
		Double valueBeforePrice = Double.valueOf(qhcBefore.getPrice());
		Double valueNowPrice = Double.valueOf(qhcNow.getPrice());
		Double valueBeforeVolume = Double.valueOf(qhcBefore.getVolume());
		Double valueNowVolume = Double.valueOf(qhcNow.getVolume());
		
		
		if (valueNowPrice > valueBeforePrice){
			//Obtine las compañias q tienen un volumen superior!
			//TODO obtner la media y dar mas calificación a los valores que esten 
			//    dentro mas cerca a la media
			//Realizar pruebas con la información que tiene en la BD
			//Idea01: 27Dec2015 --> De la BD sacar la media
			if ((((valueNowVolume * 100) / valueBeforeVolume) - 100) <= 0) {

				addAR = new CompanyRanking();
				addAR.setCompanyName(cmp.getName());
				addAR.setIdCompany(cmp.getId());
				addAR.setOBV((valueBeforeVolume + valueNowVolume));
				addAR.setVolumePercentageIncrement((((valueNowVolume * 100) / valueBeforeVolume) - 100));
				addAR.setPricePercentageincrement((((valueNowPrice * 100) / valueBeforePrice) - 100));
				addAR.setDayLow(Double.valueOf(qhcNow.getDay_low()));
				addAR.setPrecioEvaluado(valueNowPrice);
				addAR.setVolumenEvaluado(valueNowVolume);
				addAR.setDayHigh(Double.valueOf(qhcNow.getDay_high()));
				addAR.setFechaIteracion1(qhcBefore.getFechaCreacion());
				addAR.setFechaIteracion2(qhcNow.getFechaCreacion());
				addAR.setSymbol(qhcNow.getSymbol());
			}

		}
		
		return addAR;
		
	}
	
	/**
	 * @param qhcBefore
	 * @param qhcNow
	 * @param cmp
	 * @return
	 * Evalua e = Evalua.THREE;
	 * System.out.println("Precio accion mayor & % volumen mayor a cero!");
	 */
	private CompanyRanking evalua03(QuoteHistoryCompany qhcBefore, QuoteHistoryCompany qhcNow, Company cmp){
		
		
		
		CompanyRanking addAR = null;
		
		try{
		
		Double valueBeforePrice = Double.valueOf(qhcBefore.getPrice());
		Double valueNowPrice = Double.valueOf(qhcNow.getPrice());
		Double valueBeforeVolume = Double.valueOf(qhcBefore.getVolume());
		Double valueNowVolume = Double.valueOf(qhcNow.getVolume());
		
		
		if (valueNowPrice > valueBeforePrice){
			//Obtine las compañias q tienen un volumen superior!
			//TODO obtner la media y dar mas calificación a los valores que esten 
			//    dentro mas cerca a la media
			//Realizar pruebas con la información que tiene en la BD
			//Idea01: 27Dec2015 --> De la BD sacar la media
			if ((((valueNowVolume * 100) / valueBeforeVolume) - 100) > 0) {

				addAR = new CompanyRanking();
				addAR.setCompanyName(cmp.getName());
				addAR.setIdCompany(cmp.getId());
				addAR.setOBV((valueBeforeVolume + valueNowVolume));
				addAR.setVolumePercentageIncrement((((valueNowVolume * 100) / valueBeforeVolume) - 100));
				addAR.setPricePercentageincrement((((valueNowPrice * 100) / valueBeforePrice) - 100));
				addAR.setDayLow(Double.valueOf(qhcNow.getDay_low()));
				addAR.setPrecioEvaluado(valueNowPrice);
				addAR.setVolumenEvaluado(valueNowVolume);
				addAR.setDayHigh(Double.valueOf(qhcNow.getDay_high()));
				addAR.setFechaIteracion1(qhcBefore.getFechaCreacion());
				addAR.setFechaIteracion2(qhcNow.getFechaCreacion());
				addAR.setSymbol(qhcNow.getSymbol());
			}

		}
		
		}catch(Exception e){
			//System.out.println("Error en: evalua03 --> " + e.getMessage());
		}
		
		return addAR;
		
	}



	
	/*
	 * TODO
	 * Obtine el indicador, para saber que tan costosa o overbuy esta la accion
	 */
	private void relativeStrengthIndex(){
		//obtener el historico de 14 dias o iteraciones!
		System.out.println("obtener el historico de 14 dias o iteraciones!");
		
		List<RelativeStrengthIndexData> lstRSI = null;
		lstRSI = UtilGeneral.getListaRSI();
		
		//ordena descendente ID, porque el formamo de la data esta de mayor a menor
		//y las fechas deben ordenarse de menor a Mayor
		Collections.sort(lstRSI);

		//Obtener el valor maximo y minimo en el cierre de la accion al dia
		double max = 0;
		double min = 0;
		
		//obtener el promedio de H&L
		double avgHigh = 0;
		double avgLow  = 0;
		//Iteracion 2 change = close today - close yesterday
		for (int i = 0; i < lstRSI.size(); i++) {
			if (i == 0){
				RelativeStrengthIndexData relativeStrengthIndexMM =  lstRSI.get(i);
				if (null != relativeStrengthIndexMM){
					max = relativeStrengthIndexMM.getClose();
					min = relativeStrengthIndexMM.getClose();
					avgHigh += relativeStrengthIndexMM.getHigh();
					avgLow += relativeStrengthIndexMM.getLow();
				}
			  
			}
			if ( i > 0){
				RelativeStrengthIndexData relativeStrengthIndexDataA = lstRSI.get(i-1);
				RelativeStrengthIndexData relativeStrengthIndexDataB = lstRSI.get(i);
				//System.out.println(relativeStrengthIndexDataA.getClose());
				//System.out.println(relativeStrengthIndexDataB.getClose());
				relativeStrengthIndexDataB.setChange(-relativeStrengthIndexDataA.getClose()+relativeStrengthIndexDataB.getClose());
				//System.out.println(relativeStrengthIndexDataB.toString());
				lstRSI.set(i, relativeStrengthIndexDataB);
				
				//Valida valor Mayor y menor
				if (relativeStrengthIndexDataB.getClose() > max){
					max = relativeStrengthIndexDataB.getClose(); 
				}
				if  (relativeStrengthIndexDataB.getClose() < min){
					min = relativeStrengthIndexDataB.getClose(); 
				}
				
				//sumar el average
				avgHigh += relativeStrengthIndexDataB.getHigh();
				avgLow += relativeStrengthIndexDataB.getLow();
				
			}
			
		}
		
		avgHigh = avgHigh/lstRSI.size();
		avgLow = avgLow/lstRSI.size();
		
		
		// print Resultado
//		for (RelativeStrengthIndexData relativeStrengthIndexData : lstRSI) {			
//					System.out.println(relativeStrengthIndexData.toString());
//		}

		
		//Iteracion 3 suma gain and lost
		BigDecimal gain = new BigDecimal(0);
		gain.setScale(10, BigDecimal.ROUND_UNNECESSARY);
		BigDecimal lost = new BigDecimal(0);
		lost.setScale(10, BigDecimal.ROUND_UNNECESSARY);
		for (int i = 0; i < 14; i++) {
			double change = lstRSI.get(i).getChange();
			if (change > 0){
				//System.out.println("change (+) >" + change);
				gain = gain.add(new BigDecimal (change));
				//System.out.println("gain >" + gain);
			}else if (change <0){
				//System.out.println("change (-) >" + change);
				lost = lost.add(new BigDecimal( Math.abs(change)));
				//System.out.println("lost >" + lost);
			}
		}
		//System.out.println(gain + "<-- g");
		//System.out.println(lost + "<-- l");
		
		double rs =  (gain.doubleValue()/14)/(lost.doubleValue()/14);
		double rsi = 100 - (100/(1+rs));
		
		System.out.println("RSI14:" + rsi);
		System.out.println("max:" + max);
		System.out.println("min:" + min);
		System.out.println("diff:" + (max - min));
		System.out.println("Porcentaje Incremento High:" + (((100*avgHigh)/avgLow)-100)   );
		
		
	}
	
	
	
	


}
