/* ****** автор АНАТОЛИЙ ГОЛОВНЕВ ******
Сама игра. Букву Ё воспринимает как Е.

Игру вызывает WordGuessWindow, которое и предоставляет слово, которое нужно отгадать. Если слова в аргументе конструкотра нет,
то программа выбирает случайное слово из словаря (это предпочтительный вариант).
*/

import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import java.util.*;
import javax.swing.JOptionPane; 

public class WordGuessGame{
	
	private String word;
	private StringBuffer wordB;
	private int lettersLeft;
	
	WordGuessGame(){this("");}
	WordGuessGame(String str){
		word=(str.length()==0)?randomWord():str;
		lettersLeft = word.length();
		wordB = new StringBuffer();
		wordB.append(word);
	}//constructor
	
	public boolean isOver(){return (lettersLeft==0);}
	public int lettersLeft(){return lettersLeft;}
	
	public int findLetter(char ch, int from){
		int at = word.indexOf(ch,from);
		if(at!=-1)
			{lettersLeft--;
			 wordB.setCharAt(at,'.');}//если буква уже угадана, ее надо убрать/заменить.
		return at;
	}//findLetter
	
	public char randomLetter(){
		int pos = (int)(Math.random()*lettersLeft)+1;
		int at =0, i=0;
		while(at<pos)
			if(wordB.charAt(i)!='.')
				{at++; i++;}
		i--;
		if(wordB.charAt(i)=='Ё')
			return 'Е';
		return wordB.charAt(i);
	}//randomLetter
	
	private String randomWord(){
		//выбираю одно слово из файла "dictionary.txt".
		//Поскольку требуется знать число слов в файле, Stream in приходится использовать дважды. Поэтому сохраняю Stream в List
		try(
			Stream<String> in = Files.lines(Paths.get("dictionary.txt"))
									 .flatMap(s -> Arrays.stream(s.split(" ")));
			)
		{
			List<String> inn = in.collect(Collectors.toCollection(ArrayList::new));	
			Random rand = new Random();					
			return inn.stream()
					  .skip(rand.nextInt(inn.size()-1))
					  .findFirst()
					  .get();
		}//try
		catch (FileNotFoundException e){
				javax.swing.JOptionPane.showMessageDialog(null, "ERROR: словарь не найден (file: dictionary.txt)\n","Проблемка",
				JOptionPane.ERROR_MESSAGE);return "СЛОВО";}
		catch (IOException e){
				javax.swing.JOptionPane.showMessageDialog(null, "IOERROR: " + e.getMessage() + "\n","Проблемка",
				JOptionPane.ERROR_MESSAGE);return "СЛОВО";}
	}//randomWord
	
}//class
