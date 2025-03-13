/* ****** автор АНАТОЛИЙ ГОЛОВНЕВ ******
Запускается в Run. Обеспечивает слаженную работу остальных классов.

Окно игры показывает загаданное слово и две клавиатуры для ввода букв разными игроками.
Неотгаданные буквы заменены вопросительным знаком. 

Если загаданное слово длиннее 10 букв, то слово записывается не в одну очень длинную строку, а разбивается на несколько строк, примерно одинаковой
длинны, чтобы смотрелось красиво. 
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WordGuessWindow extends JFrame implements KeyPadClient, ActionListener{
	
	private String playerName1 = "Игрок 1";//Хотя WordGuessGame может принимать любое количество игроков, WordGuessWindow принимает только два игрока.
	private String playerName2 = "Игрок 2";//Поэтому игроки прописаны отдельно, а не в коллекциях. Более двух игроков требуют более двух клавиатур KeyPad,
	private KeyPad playerOne;              //которые не получается расположить красиво на экране.
	private KeyPad playerTwo;
	private JLabel[] output;
	private JLabel header;
	private JLabel winner;
	private JButton close = new JButton("Выход");
	private JButton again = new JButton("Ещё играть");
	private int NLETTERS;
	private WordGuessGame game;
	
	WordGuessWindow(){
		askName();//игроки могут ввести имена
		game = new WordGuessGame();
			NLETTERS = game.lettersLeft();
		playerOne = new KeyPad(playerName1,this);
		playerTwo = new KeyPad(playerName2,this);
		playerOne.setActive(true);//players aren't active by default
		build();
		setTitle("Отгадайте слово");
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}//constructor
	
	private void build(){
		header = new JLabel("Выбираем буквы - угадываем слово\n",null,SwingConstants.HORIZONTAL);
		header.setFont(new Font("times new roman",Font.BOLD,30));
		
		close.addActionListener(this);
		again.addActionListener(this);	
		again.setVisible(false);//кнопка появится после завершения игры
		
		JPanel atEnd = new JPanel();;
			atEnd.add(close);
			atEnd.add(again);		
		
		JPanel middle = new JPanel();
			middle.add(playerOne);
			middle.add(buildOutputArea());
			middle.add(playerTwo);
				
		getContentPane().add(header,BorderLayout.NORTH);
		getContentPane().add(middle,BorderLayout.CENTER);
		getContentPane().add(atEnd,BorderLayout.SOUTH);			
	}//build()
	
	private JPanel buildOutputArea(){
		JPanel out = new JPanel();
		out.setLayout(new GridLayout((NLETTERS-1)/10+1,Math.round((NLETTERS)/((NLETTERS-1)/10+1)),2,6));//максимум 10 букв в строке.
																										//строки примерно одинаковой длинны
		output = new JLabel[NLETTERS];
			for(int i=0;i<NLETTERS;i++){
				output[i]= new JLabel("?",JLabel.CENTER);
				output[i].setFont(new Font("times new roman",Font.BOLD,30));
				output[i].setBorder(BorderFactory.createLineBorder(Color.BLUE,2,true));
				output[i].setPreferredSize(new Dimension(40, 40));
					out.add(output[i]);
			}//for i
	return out;		
	}//buildOutputArea

	public void openRandomLetter(){//игрок открывает случайную букву, но теряет ход.
		playerOne.setActive(!playerOne.isActive());
		playerTwo.setActive(!playerTwo.isActive());
		checkLetter(game.randomLetter());
	}//openRandomLetter

	public void checkLetter(char ch){//может вызываться из KeyPad
		int nButton = (int)ch-(int)'А';
			playerOne.dimKey(nButton);
			playerTwo.dimKey(nButton);
		boolean change = true;//если буква угадана, то игрок продолжает угадывать. Если не угадал, то игрок меняется. 
		if(ch=='Е')//ищем 'Ё'
				for(int k = game.findLetter('Ё',0);k!=-1;k = game.findLetter('Ё',k+1)){
					output[k].setText(Character.toString('Ё'));
					change = false;
				}		//ищем букву так, поскольку WordGuessWindow не знает загаданное слово. Только WordGuessGame знает.
		for(int k = game.findLetter(ch,0);k!=-1;k = game.findLetter(ch,k+1)){
			output[k].setText(Character.toString(ch));
			change = false;
		}//for k
		if(change){ playerOne.setActive(!playerOne.isActive());
					playerTwo.setActive(!playerTwo.isActive()); }
		if(game.isOver())
			gameOver();
		pack();
	}//checkLetter

	public void actionPerformed(ActionEvent e){
		if(e.getSource()==close)
			System.exit(0);
		
		getContentPane().removeAll();
		game = new WordGuessGame();
		NLETTERS = game.lettersLeft();
		playerOne = new KeyPad(playerName1,this);
		playerTwo = new KeyPad(playerName2,this);
		playerOne.setActive(true);
		build();
		pack();
		setVisible(true);
	}//actionPerformed
	
	private void askName(){
		String[] names = new String[]{playerName1, playerName2};
		Prompt pr = new Prompt(names);
		playerName1 = names[0];
		playerName2 = names[1];
	}//askName	
	
	private void gameOver(){
		String win;
		if(playerOne.isActive()){
			playerOne.setWinner();
			playerTwo.setActive(false);
			win="Победил(а) "+playerName1;
		} else {
			playerOne.setActive(false);
			playerTwo.setWinner();
			win="Победил(а) "+playerName2;
			} 		
		header.setText(win);
		again.setVisible(true);
	}//gameOver	

}//class