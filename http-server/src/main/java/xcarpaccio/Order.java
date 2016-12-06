package xcarpaccio;

import java.util.Date;
import java.util.List;

public class Order {
	public String country;
	public Date departureDate;
	public Date returnDate;
	public List<String> travellerAges;
	public String cover;
	public List<String> options;
	
	@Override
	public String toString() {
		return "Order [country=" + country + ", departureDate=" + departureDate + ", returnDate=" + returnDate
				+ ", travellerAges=" + travellerAges + ", cover=" + cover + ", options=" + options + "]";
	}

	
}
