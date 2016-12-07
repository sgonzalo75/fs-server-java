package xcarpaccio;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 07.12.2016.
 */
public class CrossSeller {

    public List<String> getCrossSellingOpportunities(Order order) {
        List<String> crossSelling = new ArrayList<>();
        crossSelling.add("insurance");

        return crossSelling;
    }
}
