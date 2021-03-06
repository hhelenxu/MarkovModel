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
	public EfficientMarkov(){
		this(3);
		myMap = new HashMap<>();
	}

	/**
	 * Second constructor for EfficientMarkov
	 * @param order order of the Markov model
	 */
	public EfficientMarkov(int order) {
		super(order);
		myMap = new HashMap<>();
	}

	/**
	 * Fills myMap with single character strings that follow the k-gram in a list
	 * @param text the training text
	 */
	@Override
	public void setTraining(String text) {
		//sets myText using BaseMarkov method setTraining
		super.setTraining(text);

		//clears myMap
		myMap.clear();

		//iterates through all possible keys
		for (int i=0;i<myText.length()-myOrder+1;i++) {
			String key = myText.substring(i, i+myOrder);

			//create new ArrayList that key is mapped to
			if (!myMap.containsKey(key))
				myMap.put(key, new ArrayList<>());

			//if no letters follow key
			if (i==myText.length()-myOrder) {
				myMap.get(key).add(PSEUDO_EOS);
				break;
			}

			//adds single character string to myMap
			myMap.get(key).add(myText.substring(i+key.length(),i+key.length()+1));
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
