 /* ****** автор АНАТОЛИЙ ГОЛОВНЕВ ******
Клавиатура для ввода букв. есть кнопка открытия случайной буквы из слова.
Буквы только заглавные
*/
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class KeyPad extends JPanel implements ActionListener{
	
	private final static  int NLETTERS = 32;//Ё не учитывается
	private KeyPadClient kpc;
	private JButton[] buttons;
	private JButton randomLetter = new JButton("случайная буква");
	private char[] lables;
	private boolean active = false;
	private JPanel pad = new JPanel();
	private Border borderActive;
	private Border borderNotActive;
	private Border borderWinner;	
	
	public KeyPad(KeyPadClient kpc){this("Игрок", kpc);}
	public KeyPad(String name, KeyPadClient kpc){
		if(name.length()>15) name=name.substring(0,15); //не пропускаем имена длиннее 15 символов.
		
		this.kpc = kpc;		
		borderActive = BorderFactory.createLineBorder(Color.GREEN,3 , true);
		borderNotActive = BorderFactory.createLineBorder(Color.GRAY,3 , true);
		borderWinner = BorderFactory.createLineBorder(Color.RED,3 , true);
		
		lables  = new char[NLETTERS];
		buttons  = new JButton[NLETTERS];
		JPanel spad = new JPanel();
		spad.setLayout(new GridLayout(6,5,2,2));
		for(int i=0;i<NLETTERS;i++){
			if(i==NLETTERS-6 || i==NLETTERS-4)continue; //это твёрдый знак и мягкий знак. они разместятся отдельно внизу
			lables[i] = (char)((int)'А'+i);
			buttons[i]=new JButton(Character.toString(lables[i]));
			buttons[i].addActionListener(this);
			spad.add(buttons[i]);		
		}//fot i
		
		for(int i=NLETTERS-6;i<NLETTERS-3;i+=2){
			lables[i] = (char)((int)'А'+i);
			buttons[i]=new JButton(Character.toString(lables[i]));
			buttons[i].addActionListener(this);		
		}//fot i
		JPanel restButtons = new JPanel();
			restButtons.setLayout(new BorderLayout());
			randomLetter.addActionListener(this);
			randomLetter.setBackground(new Color(173,216,230)); randomLetter.setForeground(new Color(70,70,70));
			restButtons.add(randomLetter, BorderLayout.CENTER);
			restButtons.add(buttons[NLETTERS-6], BorderLayout.WEST);
			restButtons.add(buttons[NLETTERS-4], BorderLayout.EAST);
			
		pad.setLayout(new BoxLayout(pad, BoxLayout.Y_AXIS));
		pad.add(spad);
		pad.add(restButtons);
		pad.setBorder(borderNotActive);
		
		JLabel title = new JLabel(name,null,SwingConstants.HORIZONTAL);
		title.setFont(new Font("times new roman",Font.ITALIC | Font.BOLD,20));		
		
		setLayout(new BorderLayout());
		add(title,BorderLayout.NORTH);
		add(pad,BorderLayout.CENTER);
	
	}//constructor
	
	public void actionPerformed(ActionEvent e){
		if(active){
			if(e.getSource()==randomLetter)kpc.openRandomLetter();
			else kpc.callBack( ((JButton)e.getSource()).getText() );
		}//if(active)
	}
	
	public void dimKey(int n){buttons[n].setEnabled(false);}
	public boolean isActive(){return active;}
	
	public void setActive(boolean bul){
		if(bul){active=true;pad.setBorder(borderActive);}
		else {active=false;pad.setBorder(borderNotActive);}	
	}
	
	public void setWinner(){active=false;pad.setBorder(borderWinner);}
	
}//class