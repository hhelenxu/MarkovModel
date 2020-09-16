import java.util.*;

public class EfficientMarkov extends BaseMarkov {
	private Map<String,ArrayList<String>> myMap;
	
	EfficientMarkov(){
		this(3);
	}

	EfficientMarkov(int order) {
		super(order);
		myMap = new HashMap<>();
	}

	@Override
	public void setTraining(String text) {
		super.setTraining(text);
		myMap.clear();
		for (int i=0;i<myText.length()-myOrder;i++) {
			String key = myText.substring(i, i+myOrder);
			myMap.put(key, new ArrayList<>());
			int pos = 0;  // location where search for key in text starts

			while (pos < myText.length()) {
				int start = myText.indexOf(key, pos);
				if (start == -1) {
					//System.out.println("didn't find "+key);
					break;
				}
				if (start + key.length() >= myText.length()) {
					//System.out.println("found end with "+key);
					myMap.get(key).add(PSEUDO_EOS);
					break;
				}
				// next line is string equivalent of myText.charAt(start+key.length())
				String next = myText.substring(start + key.length(), start + key.length() + 1);
				myMap.get(key).add(next);
				pos = start + 1;  // search continues after this occurrence
			}
		}
	}

	@Override
	public ArrayList<String> getFollows(String key) {
		if (myMap.containsKey(key)) {
			return myMap.get(key);
		}
		else
			throw new NoSuchElementException(key+" not in map");
	}


}	
