import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Dictionary {
	private Set<String> wordList;
	private File file;
	private Scanner fileScanner;
	
	public Dictionary() {
		wordList = new HashSet<String>();
		file = new File("Words.txt");
		
	    try {
			fileScanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Set<String> getWordList() {
		Set<String> words = new HashSet<String>();
		
		while(fileScanner.hasNextLine()) {
			words.add(fileScanner.nextLine());
		}
		
		return words;
	}
}
