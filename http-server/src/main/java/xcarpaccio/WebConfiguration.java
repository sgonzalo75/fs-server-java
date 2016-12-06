package xcarpaccio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.codestory.http.Configuration;
import net.codestory.http.payload.Payload;
import net.codestory.http.routes.Routes;

public class WebConfiguration implements Configuration {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1655485158255384468L;
	private final Logger logger = new Logger();

    @Override
    public void configure(Routes routes) {
        routes.
                get("/ping", "pong").
                post("/feedback", (context) -> {
                    Message message = context.extract(Message.class);
                    logger.log(message.type + ": " + message.content);
                    return new Payload(200);
                }).
                post("/quote", (context -> {
                    String method = context.method();
                    String uri = context.uri();
                    String body = context.extract(String.class);
                    logger.log("INPUT:***************************");
                    logger.log(method + " " + uri + " " + body);
                    Answer answer=null;
                    Order order=null;
                    logger.log("*****************");
					try {
						
						order = context.extract(Order.class);
						
						answer=calculateQuote(order);
					} catch (Exception e) {
						e.printStackTrace();
						logger.log("Unserialized order: " + order);
						return new Payload(400);
					}
                    
                    
                    return new Payload("application/json", answer, 200);
                }))
        ;
    }

	private Answer calculateQuote(Order order) throws Exception 
	{
		Map<String,Nation> mapNation=getMapNation();
		Answer result=new Answer(42.0d);
		double dCountry=mapNation.get(order.country).getValue();
		double cover=getCoverValue(order.cover);
		double ageRisk=getAgeRisk(order);
		double optionsTotal=getToalOptions(order.options);
		logger.log("country param-->"+dCountry);
		logger.log("cover param-->"+cover);
		logger.log("age risk param (sum)-->"+ageRisk);
		logger.log("total option (sum)-->"+ optionsTotal);
		long nbDays=getnbDays(order);
		if(nbDays<7)
			throw new Exception("first week is  indivisible");
		logger.log("NbDays param-->"+nbDays);
		double app=(cover*dCountry*ageRisk*romanNumb(nbDays))+optionsTotal;
		result=new Answer(app);
		return result; 
	}

	private double romanNumb(long nbDays) 
	{
		return 1;
	}

	private long getnbDays(Order order) {
		
		
		long diff = order.returnDate.getTime()-order.departureDate.getTime();
		long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		return days;
	}

	private double getToalOptions(List<String> options) {
		
		double result=0;
		for (String string : options) {
			double app=getValueOptions(string);
			result=result+app;
		}
		return result;
	}

	private double getValueOptions(String string) {
		if(string.equalsIgnoreCase("SCUBA"))
			return 36;
		if(string.equalsIgnoreCase("MEDICAL"))
			return 72;
		if(string.equalsIgnoreCase("SKIING"))
			return 24;
		if(string.equalsIgnoreCase("SPORTS"))
			return 25;
		if(string.equalsIgnoreCase("YOGA"))
			return -3;
		return 0;
	}

	private double getAgeRisk(Order order) {
		
		double result=0;
		List<String> persons = order.travellerAges;
		for (String string : persons) {
			double dApp=getRisk(string);
			result=result+dApp;
		}
		
		
		return result;
	}

	private double getRisk(String string) {
		
		Integer i=new Integer(string);
		if(i<18)
			return 1.1;
		if(i>=18 && i<=24)
			return 0.9d;
		if(i>=25 && i<=65)
			return 1.0d;
		if(i>65)
			return 1.5d;
		return 0;
	}

	private double getCoverValue(String cover) 
	{
		if (cover.equalsIgnoreCase("BASIC"))
			return 1.8d;
		if (cover.equalsIgnoreCase("PREMIUM"))
			return 4.2d;
		if (cover.equalsIgnoreCase("EXTRA"))
			return 2.4d;
		return 0;
	}

	private Map<String, Nation> getMapNation() throws IOException 
	{
		ObjectMapper mapper=new ObjectMapper();
		HashMap<String, Nation> result=new HashMap<String,Nation>();
		byte[] jsonData = Files.readAllBytes(Paths.get("countries.json"));
		
		Nation[] nationsArr=mapper.readValue(jsonData, Nation[].class);
		List<Nation> myNation=Arrays.asList(nationsArr); 
		for (Nation nation : myNation) {
			result.put(nation.getCode(), nation);
		}
		logger.log("Map size is-->"+result.size());
		return result;
	}
	
	
}
