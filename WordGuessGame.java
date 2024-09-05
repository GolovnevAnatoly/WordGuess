/* ****** автор АНАТОЛИЙ ГОЛОВНЕВ ******
Сама игра. Букву Ё восплинимает как Е.
*/

import java.io.*;
import java.util.Random;
import javax.swing.JOptionPane; 

public class WordGuessGame{
	
	private String word;
	private StringBuffer wordB = new StringBuffer();
	private int lettersLeft;
	
	public WordGuessGame(){this("");}
	public WordGuessGame(String str){
		if(str.length()==0) word = randomWord(); else word = str;
		lettersLeft = word.length();
		wordB.append(word);
		}//constructor
	
	public boolean gameOver(){return (lettersLeft==0);}
	public int lettersLeft(){return lettersLeft;}
	
	public int findLetter(char ch, int from){
		int at = word.indexOf(ch,from);
		if(at!=-1){lettersLeft--; wordB.setCharAt(at,'.');}
		return at;
	}
	
	public String randomLetter(){
		int pos = (int)(Math.random()*lettersLeft)+1;
		int at =0, i=0;
		while(at<pos){if(wordB.charAt(i)!='.')at++; i++;}
		i--;
		if(wordB.charAt(i)=='Ё') return "Е";
		return Character.toString(wordB.charAt(i));
	}
	
	private String randomWord(){
		String out = new String();
		try{DataInputStream inStream = new DataInputStream(new FileInputStream("dic"));
			try{
				String name = inStream.readUTF();
				String[] nameOut=name.split(" ");
				out=nameOut[(int)(Math.random()*nameOut.length)];
				}	catch(EOFException e){}
					finally {inStream.close();}
		} 
		catch (FileNotFoundException e){
			javax.swing.JOptionPane.showMessageDialog(null, "ERROR: словарь не найден (file: dic)\n","Проблемка",
			JOptionPane.ERROR_MESSAGE);return "СЛОВО";}
		catch (IOException e){
			javax.swing.JOptionPane.showMessageDialog(null, "IOERROR: " + e.getMessage() + "\n","Проблемка",
			JOptionPane.ERROR_MESSAGE);return "СЛОВО";}
			
		return out;
	}
	
}//class