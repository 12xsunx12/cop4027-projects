/* @author: Regan O'Donnell
 * @class: COP4027
 * @professor: J. Coffey
 * 
 * Project 2
 * 
 */

import java.util.Arrays;

public class SpellChecker {
	private Dictionary dictionary;
	
	public SpellChecker() {
		dictionary = new Dictionary();
	}
	
	public String CheckSpelling(String word) {
		if (dictionary.Contains(word)) {
			return "-1";
		} else {
			return MissingLetter(word) + "\n" + ExtraLetter(word) + "\n" + ReversedLetter(word);
		}
	}
	
	public String MissingLetter(String inputWord) {
		for (String word : dictionary.GetWordListSet()) {
            if (word.length() == inputWord.length() + 1) {
                int mismatchCount = 0;
                for (int i = 0, j = 0; i < word.length() && j < inputWord.length(); ) {
                    if (word.charAt(i) != inputWord.charAt(j)) {
                        mismatchCount++;
                        i++;
                    } else {
                        i++;
                        j++;
                    }
                }
                if (mismatchCount == 1) {
                    return "Missing Letter: " + word;
                }
            }
        }
		return "Missing Letter: NA";
	}
	
	public String ExtraLetter(String inputWord) {
		for (String word : dictionary.GetWordListSet()) {
            if (word.length() == inputWord.length() - 1) {
                int mismatchCount = 0;
                for (int i = 0, j = 0; i < word.length() && j < inputWord.length(); ) {
                    if (word.charAt(i) != inputWord.charAt(j)) {
                        mismatchCount++;
                        j++; // Skip the extra letter in the input word
                    } else {
                        i++;
                        j++;
                    }
                }
                if (mismatchCount <= 1) {
                    return "Extra letter; "+ word;
                }
            }
        }
		return "Extra Letter: NA";
	}
	
	public String ReversedLetter(String inputWord) {
		for (String word : dictionary.GetWordListSet()) {
			if (ReversedHelper(word, inputWord)) {
				return "Reversed Letter: " + word;
			}
		}
		return "Reversed Letter: NA";
	}
	
	public Dictionary GetDictionary() {
		return dictionary;
	}
	
	private boolean ReversedHelper(String word1, String word2) {
		if (word1.length() != word2.length()) {
            return false;
        }
        char[] chars1 = word1.toCharArray();
        char[] chars2 = word2.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);
        return Arrays.equals(chars1, chars2);
	}
}
