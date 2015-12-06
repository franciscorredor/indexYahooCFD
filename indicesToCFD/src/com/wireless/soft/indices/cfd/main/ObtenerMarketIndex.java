package com.wireless.soft.indices.cfd.main;

import java.io.IOException;
import java.util.Hashtable;
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
import com.wireless.soft.indices.cfd.deserializable.json.object.ReturnIndexYahooFinanceObject;
import com.wireless.soft.indices.cfd.exception.BusinessException;
import com.wireless.soft.indices.cfd.util.UtilSession;

/**
 * Clase principal encargada de obtener los indices de diferentes compa�ias 
 * @author Francisco Corredor
 * https://sites.google.com/site/gson/gson-user-guide
 * TODO
 * Conseguir BD en la nube y realizar l�gica de negocio en CLOUD
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

	
	
	public static void main(String[] args) {
		
		
		if (null == args || args.length < 1){
			System.out.println("Debe especificar un argumento");
			return;
		}
		
		System.out.print("obtener indices de compa�ias");
		PropertyConfigurator.configure("log4j.properties");
		
		//http://finance.yahoo.com/webservice/v1/symbols/COALINDIA.NS/quote?format=json&view=detail
		//TODO
		//Obtener la siguiente consulta:
		//	SELECT	com.SCN_NAME, sci.SCI_URL_INDEX
		//	FROM		indexyahoocfd.iyc_stack_company_index sci  INNER JOIN  indexyahoocfd.iyc_stock_companies com   on com.SCN_CODIGO = sci.SCN_CODIGO

		
		//http://finance.yahoo.com/webservice/v1/symbols/COALINDIA.NS/quote?format=json&view=detail			
		String urlString = "http://finance.yahoo.com/webservice/v1/symbols/AMZN/quote?format=json&view=detail";
		//String urlString = "http://finance.yahoo.com/webservice/v1/symbols/NKE/quote?format=json";
		//String urlString = "http://www.google.com/finance/info?q=NSE:AIAENG,ATULAUTO";
		//String urlString = "http://www.google.com/finance/info?q=NSE:AIAENG,AMZN,NKE,YHOO";
		//String urlString = "http://www.google.com/finance/info?q=NYSE:NKE";
		//String urlString = "http://www.google.com/finance/info?infotype=infoquoteall&q=%s&q=NYSE:NKE";
		//http://finance.google.com/finance/info?client=ig&q=EEM,SCHE,AAPL
		//String urlString = "http://finance.google.com/finance/info?%20client=ig&q=NASDAQ:GOOG,NASDAQ:YHOO";
		//http://finance.google.com/finance/info?client=ig&q=AAPL
		//http://finance.google.com/finance/infoquoteall?client=ig&q=AAPL
		//http://www.google.com/finance/info?infotype=infoquoteall&q=NASDAQ:AAPL
		
		
		
		//execute(urlString, null);
		ObtenerMarketIndex omi = new ObtenerMarketIndex();
		try {
			//System.out.println(omi.execute(urlString, "{\"apikey\":\"1c0dd511df2319f26bccfaf5f679ed27-us7\"}"));
			//ReturnIndexYahooFinanceObject ri = omi.executeYahooIndex(urlString);
			//System.out.println(ri.toString());
			String accion = args[0];
			switch (accion){
			case "0":
				System.out.println("PErsiste info de las compa�ias, consultando de yahoo");
				omi.printCompanies();
				break;
			case "1":
				System.out.println("Inprime el indicador OBV");
				omi.printOBV();
				break;
				
			default:
				System.out.println("No realiza acci�n");
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
    	
    	System.out.println("--- Lista Compa�ias en la BD ---");
    	for (Company cmp : admEnt.getCompanies()) {
			System.out.println(cmp.getName());
			
			ReturnIndexYahooFinanceObject ri = this.executeYahooIndex(cmp.getUrlIndex());
			this.persistirCompaniesQuotes(ri, cmp);
			System.out.println("Persiste");
		}
    	
    }
    
    /**
     * @throws Exception 
     */
    private  void printOBV() {
    	
    	System.out.println("--- Imprime OBV On-Balance Volume ---");
    	//Itera por cada una de las compa�ias
    	try {
			for (Company cmp : admEnt.getCompanies()) {
				//System.out.println(cmp.getName());
				//Obtener el valor de los ultimos dos regitros para saber el OBV
				//Imprimir PRecio y Volume de los dos ultimps REgistros
				
				List<Object> listIdxCompany = admEnt.getCompIdxQuote(cmp);
				
				Object tmp[] = listIdxCompany.toArray();
				
				QuoteHistoryCompany qhcBefore = (QuoteHistoryCompany) tmp[0];
				QuoteHistoryCompany qhcNow = (QuoteHistoryCompany) tmp[1];
				
				
				Double valueBeforePrice = Double.valueOf( qhcBefore.getPrice() );
				Double valueNowPrice =  Double.valueOf( qhcNow.getPrice() );
				
				Double valueBeforeVolume = Double.valueOf(qhcBefore.getVolume());
				Double valueNowVolume = Double.valueOf(qhcNow.getVolume());
				
				/*
				 * TODO Calcular cuanto porcentaje subio y dar un ponderado
				 *  i. Si encuentra una noticia que contenga palabras positivas, dar una nota apreciativa al ponderad de 0,05%
				 */
				if (valueNowPrice > valueBeforePrice){
					if (   (((valueNowVolume*100)/valueBeforeVolume)-100) > 0){
					System.out.println( cmp.getName() + " " + "+ OBV =" + (valueBeforeVolume+valueNowVolume) + " -- Crecio un % en Volumen de: " + ( ((valueNowVolume*100)/valueBeforeVolume)-100) );
					System.out.println( "-------------------------- Crecio un % en Precio de: " + ( ((valueNowPrice*100)/valueBeforePrice)-100) );
					}
					
				}
//				else if (valueNowPrice < valueBeforePrice){
//					System.out.println("- OBV =" + (valueBeforeVolume-valueNowVolume) );
//				}else{
//					System.out.println("No existio Variacion");
//				}
//			
				
//				for (Object object : listIdxCompany) {
//					QuoteHistoryCompany qhc = ((QuoteHistoryCompany)object);
//					String valueBefore = "";
//					String valueAfter = "";
//					
//					System.out.println("id [" + qhc.getId() +"] Company Name ["+ qhc.getName() + "] Volume [" + qhc.getVolume() + "] price [" + qhc.getPrice() + "]"  );
//				}
				
				//if (listIdxCompany != null && listaCandidatoObject.size() > 0) {
				 //   for (Object o : listaCandidatoObject) {
					//CandidatoConvocatoria candidatoConvocatoria = ((CandidatoConvocatoria) o);
				
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

}
