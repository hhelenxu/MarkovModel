import java.util.*;

/**
 * More efficient version of BaseMarkov using HashMap.
 * Only requires scanning the training text once, making
 * EfficientMarkov text generation O(N+T).
 *
 * @author Helen
 */
public class EfficientMarkov extends BaseMarkov {
	private Map<String,ArrayList<String>> myMap;

	/**
	 * Default constructor for EfficientMarkov
	 */
	EfficientMarkov(){
		this(3);
		myMap = new HashMap<>();
	}

	/**
	 * Second constructor for EfficientMarkov
	 * @param order order of the Markov model
	 */
	EfficientMarkov(int order) {
		super(order);
		myMap = new HashMap<>();
	}

	/**
	 * Fills myMap with single character strings that follow the k-gram in a list
	 * @param text the training text
	 */
	@Override
	public void setTraining(String text) {
		super.setTraining(text);
		myMap.clear();
		for (int i=0;i<myText.length()-myOrder+1;i++) {
			String key = myText.substring(i, i+myOrder);
			if (!myMap.containsKey(key)) {
				myMap.put(key, new ArrayList<>());
				int pos = 0;  // location where search for key in text starts
				while (pos < myText.length()) {
					int start = myText.indexOf(key, pos);
					if (start == -1) {
						break;
					}
					if (start + key.length() >= myText.length()) {
						myMap.get(key).add(PSEUDO_EOS);
						//System.out.println(key);
						break;
					}
					// next line is string equivalent of myText.charAt(start+key.length())
					String next = myText.substring(start + key.length(), start + key.length() + 1);
					myMap.get(key).add(next);
					pos = start + 1;  // search continues after this occurrence
				}
			}
		}
	}

	/**
	 * Looks up key in myMap and returns the corresponding ArrayList
	 * Throws exception if key is not in myMap
	 * @param key key to search up in myMap
	 * @return ArrayList of single-character strings from myMap corresponding to key
	 */
	@Override
	public ArrayList<String> getFollows(String key) {
		if (myMap.containsKey(key))
			return myMap.get(key);
		else
			throw new NoSuchElementException(key+" not in map");
	}
}	
