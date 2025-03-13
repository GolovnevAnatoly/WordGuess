/* ****** автор АНАТОЛИЙ ГОЛОВНЕВ ******
Спрашивает имена игроков и передает их обратно через String[], полученный в аргументе конструктора.

Может, в принципе, принимать имена любого количества игроков. Для этого использую Array а не ArrayList, поскольку
количество игроков заранее известно через аргумент в конструкторе. То есть, заранее известен размер массива и он изменяться точно не будет. 

Максимальная длинна имени 10 символов. Символы любые.
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Prompt extends JFrame implements FocusListener{
	
	private JTextField[] players;
	private String[] namePlayers;
	private static final Font opac = new Font("Arial", Font.ITALIC | Font.BOLD, 12);
	private static final Font norm = new Font("Arial", Font.BOLD, 12);
	private static final Color focused = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	private static final Color notFocused = new Color(0.0f, 0.0f, 0.0f, 0.7f);
	
	
	Prompt(String[] args){
		namePlayers = args;	//чтобы использовать имена из аргумента конструктора в focusLost().
		players = new JTextField[args.length];
		for(int i=0;i<args.length;i++){
			players[i]= new JTextField(10);
			players[i].setText(args[i]);
			players[i].addFocusListener(this);
			players[i].setForeground(notFocused);
			players[i].setFont(opac);
		}//for i
					
		JPanel myPanel = new JPanel();
			myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		JLabel enter = new JLabel("Введите имена игроков");
			enter.setAlignmentX(Component.CENTER_ALIGNMENT);
			myPanel.add(enter);
		JPanel names = new JPanel();
			for(int i=0;i<players.length;i++)
				names.add(players[i]);
			myPanel.add(names);

		 int result = JOptionPane.showConfirmDialog(null, myPanel, "Имена игроков", JOptionPane.OK_CANCEL_OPTION);
		 if (result == JOptionPane.OK_OPTION) {
			 for(int i=0;i<players.length;i++)
				if(players[i].getText()!=null && players[i].getText().length()!=0)
					args[i] = players[i].getText();
		 } else
		 	System.exit(0);
		
	}//constructor
	
	public void focusGained(FocusEvent e) {
		JTextField ref = (JTextField)e.getSource();
			ref.setText("");
			ref.setForeground(focused);
			ref.setFont(norm);
	}//focusGained

	public void focusLost(FocusEvent e) {//если новое имя не введено, то отображается имя по умолчанию из аргумента конструктора.
		JTextField ref = (JTextField)e.getSource();
		if(ref.getText().equals("")){
			for(int i=0;i<players.length;i++){
				if(ref==players[i]){
					ref.setText(namePlayers[i]);
					ref.setForeground(notFocused);
					ref.setFont(opac);
					break;
				}//if
			}//for i
		}//if
	}//focusLost
	
	
}//class
