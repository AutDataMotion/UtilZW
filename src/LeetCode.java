import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zw on 17-2-15.
 */
public class LeetCode {

    //
    String characters = "qwertyuiopasdfghjklzxcvbnm";
    Map<Character, Integer> mapline1 = new HashMap<Character, Integer>(10);
    Map<Character, Integer> mapline2 = new HashMap<Character, Integer>(9);
    Map<Character, Integer> mapline3 = new HashMap<Character, Integer>(7);

    
    {
        for (int i = 0; i < characters.length(); i++) {
            if (i < 10) {
                mapline1.put(characters.charAt(i), 1);
                mapline1.put(characters.charAt(i), 1);
            } else if (i < 19) {
                mapline2.put(characters.charAt(i), 2);
            } else {
                mapline3.put(characters.charAt(i), 3);
            }

        }
    }

    public String[] findWords(String[] words) {

        ArrayList<String> res = new ArrayList<>(words.length);

        for (int i = 0; i < words.length; i++) {

            Map<Character, Integer> mapTemp = null;
            String lowWord = words[i].toLowerCase();
            if (mapline1.containsKey(lowWord.charAt(0))) {
                mapTemp = mapline1;
            } else if (mapline2.containsKey(lowWord.charAt(0))) {
                mapTemp = mapline2;
            } else if (mapline3.containsKey(lowWord.charAt(0))) {
                mapTemp = mapline3;
            }
            boolean flag = true;
            for (int j = 1; j < lowWord.length(); j++) {
               if (!mapTemp.containsKey(lowWord.charAt(j))){
                   flag  = false;
                   break;
               }
            }
            if (flag){
                res.add(words[i]);
            }
        }

        return res.toArray(new String[0]);
    }

    public static void main(String[] args) {

        LeetCode code = new LeetCode();
        String[] words = {"Hell", "Alaska", "Dad", "Peace"};

        String[] res = code.findWords(words);

        for (String s : res) {
            System.out.println(s);
        }
   }
}

