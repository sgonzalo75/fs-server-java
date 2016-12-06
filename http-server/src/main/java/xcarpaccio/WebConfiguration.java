package xcarpaccio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.yaml.snakeyaml.reader.ReaderException;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.codestory.http.Configuration;
import net.codestory.http.payload.Payload;
import net.codestory.http.routes.Routes;

public class WebConfiguration implements Configuration {

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
                    logger.log(method + " " + uri + " " + body);
                   
                    Order order=null;
					try {
						order = context.extract(Order.class);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return new Payload(400);
					}
                    
                    logger.log("Unserialized order: " + order);

                    // Use the following line to choose not to handle an order
                    //return new Payload("application/json", "", 200);

                    // Use the following lines to return a quote:
                    //Answer answer = new Answer(total);
                    Answer answer=calculateQuote(order);
                    return new Payload("application/json", answer, 200);
                }))
        ;
    }

	private Answer calculateQuote(Order order) throws IOException 
	{
		Map<String,Nation> mapNation=getMapNation();
		//System.out.println(mapNation.get(order.country).getValue());
		Answer result=new Answer(42.0d);
		double dCountry=mapNation.get(order.country).getValue();
		double cover=getCoverValue(order.cover);
		double ageRisk=getAgeRisk(order);
		double optionsTotal=getToalOptions(order.options);
		//(cover*country*sum(ageRIsk)*nbDays)+options
		long nbDays=getnbDays(order);
		
		double app=(cover*dCountry*ageRisk*romanNumb(nbDays))+optionsTotal;
		result=new Answer(app);
		// TODO Auto-generated method stub
		return result; 
	}

	private double romanNumb(long nbDays) 
	{
		// TODO Auto-generated method stub
		return 1;
	}

	private long getnbDays(Order order) {
		
		
		long diff = order.returnDate.getTime()-order.departureDate.getTime();
		long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		
		// TODO Auto-generated method stub
		return days;
	}

	private double getToalOptions(List<String> options) {
		
		double result=0;
		for (String string : options) {
			double app=getValueOptions(string);
			result=result+app;
		}
		// TODO Auto-generated method stub
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
		
		// TODO Auto-generated method stub
		return 0;
	}

	private double getAgeRisk(Order order) {
		
		double result=0;
		List<String> persons = order.travellerAges;
		for (Iterator iterator = persons.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			double dApp=getRisk(string);
			result=result+dApp;
		}
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
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
		
		
	// TODO Auto-generated method stub
		return 0;
	}

	private Map<String, Nation> getMapNation() throws IOException 
	{
		ObjectMapper mapper=new ObjectMapper();
		HashMap<String, Nation> result=new HashMap<String,Nation>();
		byte[] jsonData = Files.readAllBytes(Paths.get("countries.json"));
		
		//System.out.println(new String(jsonData));
		Nation[] nationsArr=mapper.readValue(jsonData, Nation[].class);
		List<Nation> myNation=Arrays.asList(nationsArr); 
		for (Iterator iterator = myNation.iterator(); iterator.hasNext();) {
			Nation nation = (Nation) iterator.next();
			result.put(nation.getCode(), nation);
			
		}
		//List<Nation> listNation=mapper.readValue(src, valueType)
		// TODO Auto-generated method stub
		//System.out.println(result.size());
		return result;
	}
	
	
}
