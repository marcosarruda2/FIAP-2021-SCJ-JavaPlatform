package api;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import enums.MethodEnum;
import enums.PropertiesEnum;
import util.Propriedades;

/**
 * 
 * @author rafael
 * Classe responsável por acessar API externa de cripto
 * gerar uma chave em https://nomics.com
 * 
 * 
 */
public class Cripto extends API{
	
	public final String URL_PRECO = "https://api.nomics.com/v1/currencies/ticker?key=%s&ids=%s&interval=%s,30d&convert=BRL&per-page=1&page=1";

	private final String INTERVALOS = "1d"; //1d,30d
	private List<String> moedas  = new ArrayList();
	String token= null;
	
	public Cripto() {
		token = Propriedades.getValue(PropertiesEnum.NOMICS_API);
	}
	
	public List<String> getMoedas() {
		return moedas;
	}


	public void setMoedas(List<String> moedas) {
		this.moedas = moedas;
	}

	/**
	 * Exemplo de retorno
	{
		"currency": "BTC",
		"id": "BTC",
		"status": "active",
		"price": "8451.36516421",
		"price_date": "2019-06-14T00:00:00Z",
		"price_timestamp": "2019-06-14T12:35:00Z",
		"symbol": "BTC",
		"circulating_supply": "17758462",
		"max_supply": "21000000",
		"name": "Bitcoin",
		"logo_url": "https://s3.us-east-2.amazonaws.com/nomics-api/static/images/currencies/btc.svg",
		"market_cap": "150083247116.70",
		"market_cap_dominance": "0.4080",
		"transparent_market_cap": "150003247116.70",
		"num_exchanges": "357",
		"num_pairs": "42118",
		"num_pairs_unmapped": "4591",
		"first_candle": "2011-08-18T00:00:00Z",
		"first_trade": "2011-08-18T00:00:00Z",
		"first_order_book": "2017-01-06T00:00:00Z",
		"first_priced_at": "2017-08-18T18:22:19Z",
		"rank": "1",
		"rank_delta": "0",
		"high": "19404.81116899",
		"high_timestamp": "2017-12-16",
		"1d": {
				"price_change": "269.75208019",
				"price_change_pct": "0.03297053",
				"volume": "1110989572.04",
				"volume_change": "-24130098.49",
				"volume_change_pct": "-0.02",
				"market_cap_change": "4805518049.63",
				"market_cap_change_pct": "0.03",
				"transparent_market_cap_change": "4800518049.00",
				"transparent_market_cap_change_pct": "0.0430",
				"volume_transparency": []
				}
		}
		*/
	
	public String getPrecoAgora(List<String> moedas) {
		setMoedas(moedas);
		try {
			String json = callAPIExternas(montaURL(token), MethodEnum.GET, null);
			//TODO: Ajustar codigo para pegar transformar em Object
//			Gson gson = new Gson();
//			String json = gson.toJson(obj);
			//gato para acelerar o desenvolvimento
			String[] dadosJson  =json.split(",");
			for (String dado : dadosJson) {
				if(dado.contains("\"price\"")) {
					System.out.println(dado);
					return formataValorMonetario(dado.replace("\"price\":", "").replace("\"", ""));
				}
			}
			return "";


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private String formataValorMonetario(String valor) {
		Double amount = new Double(valor);
		Locale locale = new Locale("pt", "BR");      
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
		System.out.println(currencyFormatter.format(amount));
		return currencyFormatter.format(amount);
	}

	/**
	 * Monta a URL que vai acessar a API externa
	 * @param token2 - token de acesso
	 * @return
	 */
	private String montaURL(String token2) {
		String moedas = "";
		//se tiver mais de uma moeda ajusta elas
		for (String moeda : getMoedas()) {
			moedas +=moeda+",";
		}
		//monta url
		return String.format(URL_PRECO, token2, moedas.substring(0,moedas.length()-1), INTERVALOS);
	}

}
