import java.util.*;

/**
 * More efficient version of BaseWordMarkov
 *
 * @author Helen
 */
public class EfficientWordMarkov extends BaseWordMarkov{
    private Map<WordGram, ArrayList<String>> myMap;

    /**
     * Default constructor for EfficientWordMarkov
     */
    public EfficientWordMarkov(){
        this(2);
        myMap = new HashMap<>();
    }

    /**
     * Second constructor for EfficientWordMarkov
     * @param order order of the Markov model
     */
    public EfficientWordMarkov(int order) {
        super(order);
        myMap = new HashMap<>();
    }

    /**
     * Fills myMap with words that follow the k-gram in a list
     * @param text the training text
     */
    @Override
    public void setTraining(String text) {
        //sets myWords using BaseWordMarkov method setTraining
        super.setTraining(text);

        //clears myMap
        myMap.clear();

        //creates new WordGram object
        WordGram wg = new WordGram(myWords,0,myOrder);
        for (int i=myOrder-1;i<myWords.length;i++)
        {
            //calls shiftAdd from WordGram for all but first iteration
            if (i!=myOrder-1)
                wg = wg.shiftAdd(myWords[i]);

            //create new ArrayList that wg is mapped to
            if (!myMap.containsKey(wg))
                myMap.put(wg,new ArrayList<>());

            //if no words follow wg
            if (i==myWords.length-1) {
                myMap.get(wg).add(PSEUDO_EOS);
                break;
            }

            //adds string to myMap
            myMap.get(wg).add(myWords[i+1]);
        }
    }

    /**
     * Looks up key in myMap and returns the corresponding ArrayList
     * Throws exception if key is not in myMap
     * @param key key to search up in myMap
     * @return ArrayList of single-character strings from myMap corresponding to key
     */
    @Override
    public ArrayList<String> getFollows(WordGram key) {
        if (myMap.containsKey(key))
            return myMap.get(key);
        else
            throw new NoSuchElementException(key+" not in map");
    }
}
