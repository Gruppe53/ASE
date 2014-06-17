package program;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;


public class TerminalGUI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ITerminal terminal;
	
	private JPanel scaPanel = new JPanel(new MigLayout());
	private JPanel proPanel = new JPanel();
	private JPanel stsPanel = new JPanel();
	private JPanel cmdPanel = new JPanel();
	private JPanel matPanel = new JPanel();
	private JPanel repPanel = new JPanel();
	private JPanel okpPanel = new JPanel();
	private JPanel leftPanel = new JPanel();
	private JPanel productPanel = new JPanel();
			
	private JScrollPane txtPanel;
	private JLabel productBatch;
	private JScrollPane recept;
	private JScrollPane materialBatch;
	private JTextField productBatchInput;
	
	//instances used
	int buttonPressedCount = 0;
		
	// Buttons
	private JButton TerminalRead = new JButton("Read");
	private JButton TerminalOkWeight = new JButton("Ok");
	private JButton TerminalOkProductBatch = new JButton("Ok");
	

	// Printout
	private JTextArea textAreaConsole = new JTextArea();
	private JTextArea textAreaMaterialBatch = new JTextArea();
	private JTextArea textAreaPrescription = new JTextArea();

	public TerminalGUI(ITerminal Terminal) {
		this.terminal = Terminal;
		
		// General setup
		setLayout(new MigLayout());
		
		scaPanel.setBorder(BorderFactory.createBevelBorder(1, Color.decode("#ffffff"), Color.decode("#898c95"), Color.decode("#898c95"), Color.decode("#f0f0f0")));
		scaPanel.setBackground(Color.white);
		
		
		// Scale command buttons
		cmdPanel.setPreferredSize(new Dimension(300, 60));
		cmdPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Terminal commands"));
		cmdPanel.setBackground(Color.white);
		//TODO husk at gøre dem false igen
		TerminalRead.setEnabled(true);

		TerminalOkWeight.setEnabled(false);

		
		cmdPanel.add(TerminalRead);

		cmdPanel.add(TerminalOkWeight);


		
		
		// Console panel
		txtPanel = new JScrollPane(textAreaConsole);
		txtPanel.setBackground(Color.white);
		txtPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Console"));
		txtPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		txtPanel.setPreferredSize(new Dimension(400, 150));
		
		textAreaConsole.setLineWrap(true);
		textAreaConsole.setWrapStyleWord(true);
		textAreaConsole.setEditable(false);
		
		// Productbatch panel
		proPanel.add(leftPanel);
		proPanel.add(okpPanel);
		proPanel.setBackground(Color.white);
		
		// The small panels in productbatch panel
		leftPanel.setPreferredSize(new Dimension (200,175));
		leftPanel.setBackground(Color.white);
		
		leftPanel.add(productPanel);
		leftPanel.add(repPanel);
		leftPanel.add(matPanel);

		okpPanel.setPreferredSize(new Dimension (100,175));
		okpPanel.setBackground(Color.white);
		
		okpPanel.add(TerminalOkProductBatch);
		TerminalOkProductBatch.setEnabled (true);
		
		productBatch = new JLabel("Produktbatch");
		productBatchInput = new JTextField();
		productBatchInput.setPreferredSize(new Dimension (98,20));
		
		
		productPanel.add(productBatch);
		productPanel.add(productBatchInput);
		productPanel.setBackground(Color.white);
		
		recept = new JScrollPane(textAreaPrescription);
		recept.setBackground(Color.white);
		recept.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")),"Recept"));
		recept.setPreferredSize(new Dimension(200,50));
		
		repPanel.add(recept);
		repPanel.setBackground(Color.white);
		
		materialBatch = new JScrollPane(textAreaMaterialBatch);
		materialBatch.setBackground(Color.white);
		materialBatch.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")),"Råvarebatch"));
		materialBatch.setPreferredSize(new Dimension(200,50));
		
		matPanel.add(materialBatch);
		matPanel.setBackground(Color.white);
		
		// Add to Main panel
		scaPanel.add(proPanel);
		scaPanel.add(stsPanel, "wrap");
		scaPanel.add(cmdPanel, "span 2 1, wrap");
		scaPanel.add(txtPanel, "span 2 1");
		
		TerminalRead.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TerminalRead();
            	
            }
        });
		
		
		TerminalOkWeight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TerminalOkWeight();
            }
		});
		
		
		TerminalOkProductBatch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Get text from JTextField
        		String productBatchNumber = productBatchInput.getText();
        		//returns Prescription
                TerminalOkProductBatch(productBatchNumber);
            }
        });
		
		add(scaPanel);
	}
	

	
	private void TerminalRead() {
		String productBatchNumber = productBatchInput.getText();
		double currentWeight = terminal.getCurrentWeight();
		textAreaConsole.append("[" + getDate() + "]\t" + terminal.terminalRead() + "\n");
		if(buttonPressedCount == 3) {
			buttonPressedCount = 0;
		}
			
		//makes sure that it can only begin weighing if nothings on the scale
		if(buttonPressedCount == 0 && currentWeight <= 50 && currentWeight >= -20) {
			TerminalOkWeight.setEnabled(true);
		}
		//	terminal.terminalOkWeight();
		//	buttonPressedCount++;
		//}
		//	//second time you press
		//	if(buttonPressedCount == 1){
		//		terminal.terminalOkWeight();
		//		buttonPressedCount++;
		//	}
		//		if(buttonPressedCount > 1 && terminal.tolerableWeight(productBatchNumber)) {
		//			terminal.terminalOkWeight();
		//			buttonPressedCount++;
		//		}
		//		else {
		//			
		//		}
		else {
			textAreaConsole.append("[" + getDate() + "]\t rengør vægt eller fjern evt. beholdere på vægten og tryk READ igen");
			TerminalOkWeight.setEnabled(false);
		}
	}
	private void TerminalZero() {
		textAreaConsole.append("[" + getDate() + "]\t" + terminal.terminalZero() + "\n");
	}
	
	private void TerminalOkWeight() {
		textAreaConsole.append("[" + getDate() + "]\t" + terminal.terminalOkWeight() + "\n");
		TerminalOkWeight.setEnabled(false);
	}
	
	private void TerminalOkProductBatch(String productBatchNumber) {
		if(terminal.terminalOkGetPrescription(productBatchNumber)!=null){	
		textAreaConsole.append("[" + getDate() + "]\n Recept num.: " + terminal.terminalOkGetPrescription(productBatchNumber) + 
								"\n Råvare num.: " + terminal.terminalOkGetMaterialId(productBatchNumber) + 
								"\n Råvare Navn: " + terminal.terminalOkGetMaterialName(productBatchNumber));
		textAreaPrescription.append(terminal.terminalOkGetPrescription(productBatchNumber));
		textAreaMaterialBatch.append(terminal.terminalOkGetMaterialId(productBatchNumber) + terminal.terminalOkGetMaterialName(productBatchNumber));
		TerminalRead.setEnabled(true);
		}
	}
	private String getDate() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

}