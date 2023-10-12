/* @author: Regan O'Donnell
 * @class: COP4027
 * @professor: J. Coffey
 * 
 * Project 2
 * 
 */

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
	
	public boolean Contains(String word) {
		return wordList.contains(word);
	}
	
	public void PrintSet() {
		for (String i: wordList) {
			System.out.println(i);
		}
	}
	
	private Set<String> AddWordListToSet() {
		Set<String> words = new HashSet<String>();
		while(fileScanner.hasNextLine()) {
			words.add(fileScanner.nextLine());
		}
		return words;
	}
}
