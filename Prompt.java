/* ****** автор АНАТОЛИЙ ГОЛОВНЕВ ******
спрашивает имена двух игроков. обратно передает имена через String[].
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Prompt extends JFrame implements FocusListener{
	
	private String[] args;
	private JTextField one = new JTextField(10);
	private JTextField two = new JTextField(10);
	private Font opac = new Font("Arial", Font.ITALIC | Font.BOLD, 12);
	private Font norm = new Font("Arial", Font.BOLD, 12);
	
	
	public Prompt(String[] in){
		
		args=in;
		one.setText(args[0]);one.addFocusListener(this);
		one.setForeground(new Color(0.0f, 0.0f, 0.0f, 0.7f)); 
		one.setFont(opac);
		
		two.setText(args[1]);two.addFocusListener(this);
		two.setForeground(new Color(0.0f, 0.0f, 0.0f, 0.7f));
		two.setFont(opac);
		
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		JLabel enter = new JLabel("Введите имена игроков");
		enter.setAlignmentX(Component.CENTER_ALIGNMENT);
		myPanel.add(enter);
		JPanel names = new JPanel();
		names.add(one);names.add(two);
		myPanel.add(names);

      int result = JOptionPane.showConfirmDialog(null, myPanel, "Имена игроков", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
         if(one.getText()!=null && one.getText().length()!=0) args[0] = one.getText();
		 if(two.getText()!=null && two.getText().length()!=0) args[1] = two.getText();
      } else System.exit(0);
		
	}//askname
	
	public void focusGained(FocusEvent e) {
        if(e.getSource()==one){one.setText("");one.setForeground(new Color(0.0f, 0.0f, 0.0f, 1.0f)); one.setFont(norm);}
		if(e.getSource()==two){two.setText("");two.setForeground(new Color(0.0f, 0.0f, 0.0f, 1.0f)); two.setFont(norm);}
    }

    public void focusLost(FocusEvent e) {
        if(e.getSource()==one)
			if(one.getText().equals("")){ 
			one.setText("Игрок 1");one.setForeground(new Color(0.0f, 0.0f, 0.0f, 0.7f)); one.setFont(opac);
			}
        
		if(e.getSource()==two)
			if(two.getText().equals("")){
			two.setText("Игрок 2");two.setForeground(new Color(0.0f, 0.0f, 0.0f, 0.7f)); two.setFont(opac);
			}	
    }
	
	
}//class