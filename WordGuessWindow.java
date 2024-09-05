/* ****** автор АНАТОЛИЙ ГОЛОВНЕВ ******
Окно игры, показывающее неизвесное слово и две клавиатуры для ввода букв разными игроками.
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class WordGuessWindow extends JFrame implements KeyPadClient, ActionListener{
	
	private String PlayerName1 = "Игрок 1";
	private String PlayerName2 = "Игрок 2";
	private KeyPad PlayerOne;
	private KeyPad PlayerTwo;
	private JLabel[] output;
	private JLabel rules;
	private JLabel winner;
	private JButton close = new JButton("Выход");
	private JButton more = new JButton("Ещё играть");
	private int NLETTERS;
	private WordGuessGame game;
	
	public WordGuessWindow(){
		askName();//игроки могут ввести имена
		game = new WordGuessGame();// new WordGuessGame(getWord()); была возможность вводить новые слова. Я убрал пока. Слов в словаре и так полно.
		NLETTERS = game.lettersLeft();
		PlayerOne = new KeyPad(PlayerName1,this);
		PlayerTwo = new KeyPad(PlayerName2,this);
		PlayerOne.setActive(true);
		build();
		setTitle("Отгадайте слово");
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}//constructor
	
	private void build(){
		rules = new JLabel("Выбираем буквы - угадываем слово.\n",null,SwingConstants.HORIZONTAL);
					rules.setFont(new Font("times new roman",Font.BOLD,30));
		
		close.addActionListener(this);
		more.addActionListener(this);	
			more.setVisible(false);
		JPanel atEnd = new JPanel();;
		atEnd.add(close);atEnd.add(more);		
		
		JPanel middle = new JPanel();
			middle.add(PlayerOne);
			middle.add(buildOutputArea());
			middle.add(PlayerTwo);
				
		getContentPane().add(rules,BorderLayout.NORTH);
		getContentPane().add(middle,BorderLayout.CENTER);
		getContentPane().add(atEnd,BorderLayout.SOUTH);
				
	}//build()
	
	private JPanel buildOutputArea(){
		JPanel out = new JPanel();
		out.setLayout(new GridLayout((NLETTERS-1)/10+1,Math.round((NLETTERS)/((NLETTERS-1)/10+1)),2,6));//максимум 10 букв в строке.
		output = new JLabel[NLETTERS];																	//строки примерно одинаковой длинны
		for(int i=0;i<NLETTERS;i++){
			output[i]= new JLabel("?",JLabel.CENTER);
			output[i].setFont(new Font("times new roman",Font.BOLD,30));
			output[i].setBorder(BorderFactory.createLineBorder(Color.BLUE,2,true));
			output[i].setPreferredSize(new Dimension(40, 40));
		out.add(output[i]);
		}//for i
	return out;		
	}//buildOutputArea

	public void callBack(String str){
		char ch = str.charAt(0);
		int nButton = (int)ch-(int)'А';
		PlayerOne.dimKey(nButton);PlayerTwo.dimKey(nButton);
		boolean change = true;//если буква угадана, то игрок продолжает угадывать. Если не угадал, то игрок меняется. 
		if(ch=='Е')for(int k = game.findLetter('Ё',0);k!=-1;k = game.findLetter('Ё',k+1)){//ищем 'ё'
					output[k].setText(Character.toString('Ё'));
					change = false;}//for k
		for(int k = game.findLetter(ch,0);k!=-1;k = game.findLetter(ch,k+1)){
			output[k].setText(Character.toString(ch));
			change = false;
		}//for k
		if(change){ PlayerOne.setActive(!PlayerOne.isActive()); PlayerTwo.setActive(!PlayerTwo.isActive()); }
		if(game.gameOver()) gameOver();
		pack();
	}

	private void gameOver(){
		String win; if(PlayerOne.isActive()){PlayerOne.setWinner();PlayerTwo.setActive(false);win="Победил(а) "+PlayerName1;}
										else {PlayerOne.setActive(false);PlayerTwo.setWinner(); win="Победил(а) "+PlayerName2;} 		
		rules.setText(win);
		more.setVisible(true);
	}//gameOver

	public void openRandomLetter(){//открывает случайную букву, но теряет ход.
		PlayerOne.setActive(!PlayerOne.isActive());
		PlayerTwo.setActive(!PlayerTwo.isActive());
		callBack(game.randomLetter());
	}//openRandomLetter

	public void actionPerformed(ActionEvent e){
		if(e.getSource()==close) System.exit(0);
		
		getContentPane().removeAll();
		game = new WordGuessGame();
		NLETTERS = game.lettersLeft();
		PlayerOne = new KeyPad(PlayerName1,this);
		PlayerTwo = new KeyPad(PlayerName2,this);
		PlayerOne.setActive(true);
		build();
		pack();
		setVisible(true);
	}
	
	private void askName(){
		String[] names = new String[]{PlayerName1, PlayerName2};
		Prompt pr = new Prompt(names);
		PlayerName1 = names[0];
		PlayerName2 = names[1];
	}	

}//class

	/*	
				была возможность вводить новые слова в конструктор WordGuessGame. Я убрал пока. Слов и так полно.

	private String getWord(){ 
		StringBuffer strb = new StringBuffer("");
		while(!getWordTest(strb));//функция в условии while спрашивает пользователя ввести strb.
		return strb.toString();
	}
	
	private boolean getWordTest(StringBuffer strb){
		String text = new String("Введите слово, которое нужно отгадать.\n Только русские буквы. Без дефисов.\n\nИли не вводите - будет случайное слово.");
		String str = javax.swing.JOptionPane.showInputDialog(text);
		if(str==null) System.exit(0);	
		if(str.length()==0) return true;
			if(str.length()>35){//самое длинное слово, вроде как, по разным версиям, состоит из 35 букв.
				javax.swing.JOptionPane.showMessageDialog(null,"МноГа букАв. Не осилил. Это точно слово?","Проблемка", JOptionPane.WARNING_MESSAGE);
			return false;
			}//if
			str=str.toUpperCase();
		strb.append(str);
		for(int i=0;i<str.length();i++)if(strb.charAt(i)=='ё')strb.setCharAt(i,'Ё');//заменяем 'ё' на 'Ё'
		boolean ok = true;
		for(int i=0;i<str.length();i++) if(strb.charAt(i)<'А' || strb.charAt(i)>(char)((int)'А'+32))if(strb.charAt(i)!='Ё') ok=false;
		if(!ok){
			javax.swing.JOptionPane.showMessageDialog(null, "Принимаются только русские буквы без дефисов, без пробелов. "+str+" не подходит.","Проблемка",
			JOptionPane.WARNING_MESSAGE);
			strb.delete(0,strb.length());
			return false;
		}//if
		return true;
	}//getWordTest
*/