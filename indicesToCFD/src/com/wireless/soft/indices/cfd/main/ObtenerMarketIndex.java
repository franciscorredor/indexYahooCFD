package com.wireless.soft.indices.cfd.main;

import java.io.IOException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wireless.soft.indices.cfd.deserializable.json.object.ReturnIndexYahooFinanceObject;

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

	
	
	public static void main(String[] args) {
		
		System.out.print("obtener indices de compañias");
		
					
		String urlString = "http://finance.yahoo.com/webservice/v1/symbols/AMZN/quote?format=json";
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
			ReturnIndexYahooFinanceObject ri = omi.executeYahooIndex(urlString);
			System.out.println(ri.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

}
