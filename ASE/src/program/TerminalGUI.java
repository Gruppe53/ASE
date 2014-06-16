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
	private JScrollPane txtPanel;
	private JLabel productBatch;
	private JScrollPane recept;
	private JScrollPane materialBatch;
	private JTextField productBatchInput;
		
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
		scaPanel.setBackground(Color.WHITE);
		
		
		// Scale command buttons
		cmdPanel.setPreferredSize(new Dimension(400, 60));
		cmdPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Terminal commands"));
		cmdPanel.setBackground(Color.white);
		
		TerminalRead.setEnabled(false);

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
		
		// Produktbatch panel
		materialBatch = new JScrollPane(textAreaMaterialBatch);
		materialBatch.setBackground(Color.white);
		materialBatch.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")),"Råvarebatch"));
		materialBatch.setPreferredSize(new Dimension(200,50));
		
		recept = new JScrollPane(textAreaPrescription);
		recept.setBackground(Color.white);
		recept.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")),"Recept"));
		recept.setPreferredSize(new Dimension(200,50));
		
		productBatch = new JLabel("Produktbatch");
		productBatchInput = new JTextField();
		productBatchInput.setPreferredSize(new Dimension (98,20));
		
		TerminalOkProductBatch.setEnabled (true);

		proPanel.add(productBatch); 
		proPanel.add(productBatchInput, "span 3 1");
		proPanel.add(recept);
		proPanel.add(materialBatch);
		proPanel.add(TerminalOkProductBatch);
		
		
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
                TerminalOk();
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
		textAreaConsole.append("[" + getDate() + "]\t" + terminal.terminalRead() + "\n");
	}
	
	private void TerminalTare() {
		textAreaConsole.append("[" + getDate() + "]\t" + terminal.terminalTare() + "\n");
	}
	
	private void TerminalZero() {
		textAreaConsole.append("[" + getDate() + "]\t" + terminal.terminalZero() + "\n");
	}
	
	private void TerminalOk() {
		textAreaConsole.append("[" + getDate() + "]\t" + terminal.terminalOkWeight() + "\n");
	}
	
	private void TerminalOkProductBatch(String productBatchNumber) {
		textAreaConsole.append("[" + getDate() + "]\t" + terminal.terminalOkProductBatch(productBatchNumber) + "\n");
		textAreaPrescription.append(terminal.terminalOkProductBatch(productBatchNumber));
	}
	
	private String getDate() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}


	
	
	

	

}