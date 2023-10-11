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
		file = new File("Words.txt");
		
		try {
			fileScanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		wordList = AddWordListToSet();
	}
	
	private Set<String> AddWordListToSet() {
		Set<String> words = new HashSet<String>();
		
		while(fileScanner.hasNextLine()) {
			words.add(fileScanner.nextLine());
		}
		
		return words;
	}
	
	public Set<String> GetWordListSet() {
		return wordList;
	}
	
	public String GetWordListString() {
		String words = "";
		
		while(fileScanner.hasNextLine()) {
			words += fileScanner.nextLine() + "\n";
		}
		
		return words;
	}
	
	public void PrintSet() {
		for (String i: wordList) {
			System.out.println(i);
		}
	}
}
