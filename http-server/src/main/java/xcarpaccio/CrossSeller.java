package xcarpaccio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 07.12.2016.
 */
public class CrossSeller {

    public List<String> getCrossSellingOpportunities(Order order) {
        List<String> crossSelling = new ArrayList<>();

        if (order.cover.equals("PREMIUM")) {
            crossSelling.add("yoga");
        }

        if(children(order)) {
            crossSelling.add("child care");
        }

        if(order.country.equals("US"))
        crossSelling.add("insurance");

        return crossSelling;
    }

    private boolean children(Order order) {
        if(order.travellerAges!=null && order.travellerAges.size()>0) {
            for (String travellerAge : order.travellerAges) {
                if(Integer.parseInt(travellerAge)<18) {
                    return true;
                }
            }
        }
        return false;
    }
}
