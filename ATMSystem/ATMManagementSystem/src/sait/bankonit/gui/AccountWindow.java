package sait.bankonit.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import ca.bankonit.exceptions.*;
import ca.bankonit.manager.*;
import ca.bankonit.models.*;
import javax.swing.table.DefaultTableModel;

/**
 * Renders the account window.
 * @author Chris Wang
 * @version June 25, 2022
 */
public class AccountWindow extends JFrame {
	private Account account;
	long cardNumber;
	short pin;
	//Information written into the file
	String infos="";
	double Balance;
	JTextArea table=new JTextArea();
	JScrollPane sc ;
	
	/**
	 * Initializes the account window
	 * @param account Account to manage
	 */
	public AccountWindow(Account account) {
		super("Bank On It Account");
		
		// Store account as field.
		this.account = account;
		cardNumber=this.account.getCardNumber();
		pin=this.account.getPin();
		
		// Set size to 600x500
		this.setSize(600, 500);
		
		populateTransactions();
	}
	
	/**
	 * Clears and re-populates transactions as well as updates balance.
	 */
	private void populateTransactions() {
		//Set header and font for the number of card
				JLabel labelCardNumber=new JLabel("Card #"+cardNumber);
				labelCardNumber.setFont(new Font("Arial",Font.PLAIN,30));
				//Set balance
				JLabel labelBalance=new JLabel("Balance: $"+Balance);
				JPanel panelForCard=new JPanel();
				panelForCard.add(labelCardNumber);
				JPanel panelForBalance=new JPanel();
				panelForBalance.add(labelBalance);
				JPanel panel1=new JPanel(new BorderLayout());
				panel1.add(panelForCard,BorderLayout.NORTH);
				panel1.add(panelForBalance);
				add(panel1,BorderLayout.NORTH);
				
				//Set a table for type
				String[] columnNames={"Date","Type","Amount"};
				table=new JTextArea();
				sc= new JScrollPane(table);
				
				//Tag Type:
				JLabel labelType=new JLabel("Type:");
				//Set radio button
				JRadioButton radioDeposit=new JRadioButton("Deposit",false);
				JRadioButton radioWithdraw=new JRadioButton("Withdraw",false);
				//Set radio group for these buttons
				ButtonGroup group=new ButtonGroup();
				group.add(radioDeposit);
				group.add(radioWithdraw);
				//Tag Amount:
				JLabel labelAmount=new JLabel("Amount:");
				//Set text field for input amount
				JTextField textAmount=new JTextField(10);
				//Submit button
				JButton buttonSubmit=new JButton("Submit");
				JPanel panelForSubmit=new JPanel();
				panelForSubmit.add(labelType);
				panelForSubmit.add(radioDeposit);
				panelForSubmit.add(radioWithdraw);
				panelForSubmit.add(labelAmount);
				panelForSubmit.add(textAmount);
				panelForSubmit.add(buttonSubmit);
				//Set action listener for submit button
				buttonSubmit.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						double amount=0;
						//Get text field data
						String inputAmount=textAmount.getText();
						if(!radioDeposit.isSelected()&&!radioWithdraw.isSelected()) {
							 JOptionPane.showMessageDialog(null,"You must select at least one 'deposit' or 'withdraw'!");
							 return;
						}
						if(inputAmount.trim().equals("")) {
							 JOptionPane.showMessageDialog(null,"You entered an invalid character!");
							 return;
						}
						try {
							amount=Double.parseDouble(inputAmount);
						}
						catch(Exception E){
							 JOptionPane.showMessageDialog(null,"You entered an invalid character!");
							 return;
						}
						if(amount<=0) {
							JOptionPane.showMessageDialog(null,"Amount must be greater than 0!");
							return;
						}
						JOptionPane.showMessageDialog(null,"Submitted successfully!");
						//Determine the Deposit button is selected. If true, add money. If false, withdraw money
						BankManager bm=BankManagerBroker.getInstance();
						if(radioDeposit.isSelected()) {
							Balance+=amount;
							try {
								bm.deposit(account,amount);
							} catch (InvalidAccountException e1) {
								e1.printStackTrace();
							}
						}else {
							Balance-=amount;
							try {
								bm.withdraw(account,amount);
							} catch (InvalidAccountException e1) {
								e1.printStackTrace();
							}
						}
						//Reset Balance
						labelBalance.setText("Balance: $"+Math.round(Balance*100)*1.0/100);
						textAmount.setText("");
						ArrayList<Transaction> transactions=null;
						try {			
							transactions=BankManagerBroker.getInstance().getTransactionsForAccount(account);
						} catch (InvalidAccountException e2) {
							e2.printStackTrace();
						}
						infos="";
						transactions.forEach(transaction->{
							infos+=transaction+"\r\n";
						});
						table.setText(infos);
					}
				});
				
				//Button for Signout
				JButton buttonSignout=new JButton("Signout");
				//add listener for Signout 
				buttonSignout.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						BankManagerBroker.getInstance().persist();
						System.exit(0);
					}
				});
				JPanel panel2=new JPanel();
				panel2.add(buttonSignout);
				JPanel panel3=new JPanel(new BorderLayout());
				panel3.add(panelForSubmit,BorderLayout.NORTH);
				panel3.add(panel2);

				//add to the window
				add(panel1,BorderLayout.NORTH);
				add(sc);
				add(panel3,BorderLayout.SOUTH);
				
				//Obtain and display the original saved transaction data
				ArrayList<Transaction> transactions=null;
				try {			
					transactions=BankManagerBroker.getInstance().getTransactionsForAccount(account);
				} catch (InvalidAccountException e2) {
					e2.printStackTrace();
				}
				
				infos="";
				transactions.forEach(transaction->{
					double amount2=transaction.getAmount();
					if(transaction.getTransactionType()=='D') {
						Balance+=amount2;
					}
					if(transaction.getTransactionType()=='W') {
						Balance-=amount2;
					}
					infos+=transaction+"\r\n";
				});
				table.setText(infos);
				labelBalance.setText("Balance: $"+Math.round(Balance*100)*1.0/100);	
				

				
	}
	
}
