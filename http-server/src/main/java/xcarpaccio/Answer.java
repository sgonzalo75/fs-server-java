package xcarpaccio;

import java.util.List;

public class Answer {
	public Double quote;
	public List<String> offers;

	public Answer(double quote) {
		this.quote = quote;
	}

	public void addOffers(List<String> offers) {
		this.offers = offers;
	}
}
