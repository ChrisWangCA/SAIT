package sait.bankonit.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import ca.bankonit.manager.*;
import ca.bankonit.models.*;

/**
 * Renders the login window
 * @author Chris Wang
 * @version June 25, 2022
 */
public class LoginWindow extends JFrame {
	
	/**
	 * Initializes the login window.
	 */
	public LoginWindow() {
		super("Bank On It Login");
		
		// Set window size to 500x150
		this.setSize(500, 150);
		
		// Cause process to exit when X is clicked.
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// Center login window in screen
		this.setLocationRelativeTo(null);
		
		//Tag Bank On It Login
		JLabel labelLogin=new JLabel("Bank On It Login");
		labelLogin.setFont(new Font("Arial",Font.PLAIN,30));
		JPanel panel1=new JPanel();
		panel1.add(labelLogin);
		
		//Tag Card Number:
		JLabel labelCardNumber=new JLabel("Card Number: ");
		//Text field for card number
		JTextField textCardNumber=new JTextField(16);
		//Tag PIN:
		JLabel labelPin=new JLabel("PIN: ");
		//Text field for pin
		JPasswordField passwordPin=new JPasswordField(4);
		JPanel panel2=new JPanel();
		panel2.add(labelCardNumber);
		panel2.add(textCardNumber);
		panel2.add(labelPin);
		panel2.add(passwordPin);
		
		//Button:login
		JButton buttonLogin=new JButton("login");
		//Add listener for the login button
		buttonLogin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String inputCardNumber=textCardNumber.getText();
				String inputPin=passwordPin.getText();
				long cardNumber=0;
				short pin=0;
				try {
					cardNumber=Long.parseLong(inputCardNumber);
					if(inputCardNumber.length()!=16) {
						throw new Exception();
					}
				}
				catch(Exception E){
					 JOptionPane.showMessageDialog(null,"You entered an invalid card number!");
					 return;
				}
				try {
					if(inputPin.length()!=4) {
						throw new Exception();	
					}
					pin=Short.parseShort(inputPin);
				}
				catch(Exception E){
					 JOptionPane.showMessageDialog(null,"You entered an invalid pin code!");
					 return;
				}
				Account account = BankManagerBroker.getInstance().login(cardNumber, pin);
				if(account==null) {
					JOptionPane.showMessageDialog(null,"You entered an incorrect card number or pin code!");
					return ;
				}
				JOptionPane.showMessageDialog(null,"Login successful!");
		 		AccountWindow accountWindow = new AccountWindow(account);
				accountWindow.setVisible(true);
				setVisible(false);
			}
		});
		JPanel panel3=new JPanel();
		panel3.add(buttonLogin);
		
		//Add the above components to the window
		add(panel1,BorderLayout.NORTH);
		add(panel2);
		add(panel3,BorderLayout.SOUTH);
	}
}
