package com.wireless.soft.indices.cfd.main;

import java.io.IOException;
import java.util.Collections;
import java.util.Hashtable;
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
import com.wireless.soft.indices.cfd.business.entities.QuoteHistoryCompany;
import com.wireless.soft.indices.cfd.collections.CompanyRanking;
import com.wireless.soft.indices.cfd.deserializable.json.object.ReturnIndexYahooFinanceObject;
import com.wireless.soft.indices.cfd.exception.BusinessException;
import com.wireless.soft.indices.cfd.util.UtilGeneral;
import com.wireless.soft.indices.cfd.util.UtilSession;

/**
 * Clase principal encargada de obtener los indices de diferentes compañias 
 * @author Francisco Corredor
 * https://sites.google.com/site/gson/gson-user-guide
 * TODO
 * Conseguir BD en la nube y realizar lógica de negocio en CLOUD
 */
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
	 */
	public static void main(String[] args) {
		
		if (null == args || args.length < 1){
			System.out.println("Debe especificar un argumento");
			return;
		}
		Integer numIteracionesAntes = null;
		Integer cortePorcentajePonderado = null;
		PropertyConfigurator.configure("log4j.properties");
		//Valida si hay un 2do argumento (# Iteraciones antes)
		if (null != args && args.length >1 && null != args[1]){
			try{
			numIteracionesAntes = Integer.parseInt( args[1] );
			}catch (Exception e){
				numIteracionesAntes = null;
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
				omi.printCompanies();
				omi.printOBV(numIteracionesAntes, cortePorcentajePonderado);
				break;
			case "1":
				System.out.println("\n Imprime el indicador OBV");
				omi.printOBV(numIteracionesAntes, cortePorcentajePonderado);
				break;
			case "2":
				System.out.println("\n Indica si el mecado esta en Bull o Bear");
				omi.printIndicadorMercado();
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

	// _logger.info("Post to " + url + " : " + request);

	DefaultHttpClient http = new DefaultHttpClient();

	//HttpPost post = new HttpPost(url);
	//post.setEntity(new StringEntity(URLEncoder.encode(request, "UTF-8")));
	HttpGet get = new HttpGet(url);
	//String response = http.execute(post, new BasicResponseHandler());
	String response = http.execute(get, new BasicResponseHandler());
	// _logger.info("Response: " + response);
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
     * @throws BusinessException
     * @throws IOException 
     */
    private  void printCompanies() throws BusinessException, IOException{
    	for (Company cmp : admEnt.getCompanies()) {
			
			if (null != cmp && null != cmp.getUrlIndex() 
					&& cmp.getUrlIndex().length() > 3){
			
			ReturnIndexYahooFinanceObject ri = this.executeYahooIndex(cmp.getUrlIndex());
			this.persistirCompaniesQuotes(ri, cmp);
			System.out.println("Persiste: " + cmp.getName());
			}
		}
    	
    }
    
    /**
     * @throws Exception 
     */
	private void printOBV(Integer numIteracionAntes,
			Integer cortePorcentajePonderado) {
		List<CompanyRanking> cr = new LinkedList<CompanyRanking>();
		try {
			for (Company cmp : admEnt.getCompanies()) {
				List<Object> listIdxCompany = admEnt.getCompIdxQuote(cmp);
				Object tmp[] = listIdxCompany.toArray();
				if (null != tmp && tmp.length > 1) {

					QuoteHistoryCompany qhcBefore = (QuoteHistoryCompany) tmp[numIteracionAntes == null ? 1 : numIteracionAntes];
					QuoteHistoryCompany qhcNow = (QuoteHistoryCompany) tmp[0];
					Double valueBeforePrice = Double.valueOf(qhcBefore.getPrice());
					Double valueNowPrice = Double.valueOf(qhcNow.getPrice());
					Double valueBeforeVolume = Double.valueOf(qhcBefore.getVolume());
					Double valueNowVolume = Double.valueOf(qhcNow.getVolume());

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
					 */
					if (valueNowPrice > valueBeforePrice
					// && UtilMath.isPriceBetweenHighLow(qhcNow.getPrice(), qhcNow.getDay_high(), qhcNow.getDay_low())
					) {
						if ((((valueNowVolume * 100) / valueBeforeVolume) - 100) > 0) {

							CompanyRanking addAR = new CompanyRanking();
							addAR.setCompanyName(cmp.getName());
							addAR.setIdCompany(cmp.getId());
							addAR.setOBV((valueBeforeVolume + valueNowVolume));
							addAR.setVolumePercentageIncrement((((valueNowVolume * 100) / valueBeforeVolume) - 100));
							addAR.setPricePercentageincrement((((valueNowPrice * 100) / valueBeforePrice) - 100));
							addAR.setDayLow(Double.valueOf(qhcNow.getDay_low()));
							addAR.setPrecioEvaluado(valueNowPrice);
							addAR.setDayHigh(Double.valueOf(qhcNow.getDay_high()));
							addAR.setFechaIteracion1(qhcBefore.getFechaCreacion());
							addAR.setFechaIteracion2(qhcNow.getFechaCreacion());
							cr.add(addAR);
						}

					}

				}
			}

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
    private  void persistirCompaniesQuotes(ReturnIndexYahooFinanceObject ri, Company cmp) throws BusinessException, IOException{
    	
    	admEnt.persistirCompaniesQuotes(ri, cmp);
    	
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

}
