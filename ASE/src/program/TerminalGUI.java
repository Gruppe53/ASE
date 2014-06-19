package program;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

import databaseAccess.DBAccess;
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
	private JLabel materialBatch;
	
	private JComboBox<Integer> dropDownProductBatch;
	private JComboBox<Integer> dropDownMaterialBatch;
	
	//instances used
	int buttonPressedCount = 0;
		
	// Buttons
	private JButton TerminalRead = new JButton("Read");
	private JButton TerminalOkWeight = new JButton("Ok");
	private JButton TerminalOkProductBatch = new JButton("Ok");
	private JButton TerminalOkMaterialBatch= new JButton("Ok");
	

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
		//productBatchInput = new JTextField();
		//productBatchInput.setPreferredSize(new Dimension (98,20));
		
		
		productPanel.add(productBatch);
		//productPanel.add(productBatchInput);
		
		ArrayList<Integer> productBatchIDs = new ArrayList<Integer>();
		
		DBAccess con = new DBAccess("72.13.93.206", 3307, "gruppe55", "gruppe55", "55gruppe");
						
		dropDownProductBatch = new JComboBox<Integer>();
		try {
			ResultSet rs = con.doSqlQuery("SELECT * FROM productbatch");
			
	    	while(rs.next()) {
	    		productBatchIDs.add(rs.getInt("pb_id"));
	    	}
	    	con.closeSql();
		} catch(Exception e) {
			e.printStackTrace();
		}

		
		for(int i : productBatchIDs) {
			dropDownProductBatch.addItem(i);
		

		}
		productPanel.add(dropDownProductBatch);
		
		productPanel.setBackground(Color.white);
		
		recept = new JScrollPane(textAreaPrescription);
		recept.setBackground(Color.white);
		recept.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")),"Recept"));
		recept.setPreferredSize(new Dimension(200,50));
		
		textAreaPrescription.setLineWrap(true);
		textAreaPrescription.setWrapStyleWord(true);
		textAreaPrescription.setEditable(false);
		
		repPanel.add(recept);
		repPanel.setBackground(Color.white);
		
		/*materialBatch = new JScrollPane(textAreaMaterialBatch);
		materialBatch.setBackground(Color.white);
		materialBatch.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")),"Råvarebatch"));
		materialBatch.setPreferredSize(new Dimension(200,50));
		
		textAreaMaterialBatch.setLineWrap(true);
		textAreaMaterialBatch.setWrapStyleWord(true);
		textAreaMaterialBatch.setEditable(false);*/
		
		okpPanel.add(TerminalOkMaterialBatch);
		materialBatch = new JLabel("Råvarebatch");
		matPanel.add(materialBatch);
		
		
		
		matPanel.setBackground(Color.white);
	
		// Add to Main panel
		scaPanel.add(proPanel);
		scaPanel.add(stsPanel, "wrap");
		scaPanel.add(cmdPanel, "span 2 1, wrap");
		scaPanel.add(txtPanel, "span 2 1");
		
		TerminalRead.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					TerminalRead();
				} catch (Exception f) {
					f.printStackTrace();
				}
            	
            }
        });
		
		
		TerminalOkWeight.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
					TerminalOkWeight();
				} catch (Exception f) {
					f.printStackTrace();
				}
                TerminalOkWeight.setEnabled(false);
            }
		});
		
		
		TerminalOkProductBatch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	ArrayList<Integer> materialBatchIDs = new ArrayList<Integer>();
        	 	DBAccess con2 = new DBAccess("72.13.93.206", 3307, "gruppe55", "gruppe55", "55gruppe");
        		
        		dropDownMaterialBatch = new JComboBox<Integer>();
        		ResultSet rs = null;
        		try {
        			rs = con2.doSqlQuery("SELECT mb_id, m_name FROM matbatch NATURAL JOIN materials NATURAL JOIN precomponent WHERE pre_id = " + terminal.terminalOkGetPrescription(dropDownProductBatch.getSelectedItem().toString()) + " AND (amount >= netto) AND m_id NOT IN (SELECT m_id FROM pbcomponent NATURAL JOIN matbatch WHERE pb_id = "+ dropDownProductBatch.getSelectedItem().toString() + ")");
        			
        	    	while( rs.next()) {
        	    		materialBatchIDs.add(rs.getInt("mb_id"));
        	    	}
        	    	con2.closeSql();
        		} catch(Exception f) {
        			f.printStackTrace();
        		}
        		
        		
        		for(int i : materialBatchIDs) {
        			dropDownMaterialBatch.addItem(i);
        		}
        		
        		matPanel.add(dropDownMaterialBatch);
        		// Get text from JTextField
            	String productBatchNumber = dropDownProductBatch.getSelectedItem().toString();
            	
        		//String productBatchNumber = productBatchInput.getText();
        		//returns Prescription
        		//returns materialId and Name
                try {
					TerminalOkProductBatch(productBatchNumber);
				} catch (Exception f) {
					f.printStackTrace();
				}
            }
		});
		
		TerminalOkMaterialBatch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            }
		});
		
		add(scaPanel);   	
	}
	public String getMaterialBatchId () {
		String MaterialBatchId = dropDownProductBatch.getSelectedItem().toString();
		return MaterialBatchId;
		
	}
	
	private void TerminalRead() throws Exception {
		String productBatchNumber = dropDownProductBatch.getSelectedItem().toString();
		double currentWeight = terminal.getCurrentWeight();
		textAreaConsole.append("[" + getDate() + "]\t" + terminal.terminalRead() + "\n");
		if(buttonPressedCount == 3) {
			buttonPressedCount = 0;
		}
			
		//makes sure that it can only begin weighing if nothings on the scale
		if(buttonPressedCount == 0 && currentWeight <= 50) {
			TerminalOkWeight.setEnabled(true);
			TerminalRead.setEnabled(false);
			buttonPressedCount++;
		}
			//second time you press
			if(buttonPressedCount == 1){
				TerminalOkWeight.setEnabled(true);
				TerminalRead.setEnabled(false);
				buttonPressedCount++;
			}
				if(buttonPressedCount > 1 && terminal.tolerableWeight(productBatchNumber)) {
					TerminalOkWeight.setEnabled(true);
					TerminalRead.setEnabled(false);	
					buttonPressedCount++;
				}
				
		else {
			textAreaConsole.setText("[" + getDate() + "]\t rengør vægt eller fjern evt. beholdere på vægten og tryk READ igen");
			TerminalOkWeight.setEnabled(false);
		}
	}
	
	private void TerminalOkMaterial() throws Exception {
		textAreaConsole.append("[" + getDate() + "]\t" + terminal.terminalOkGetMaterialId() + "\n");
		TerminalOkWeight.setEnabled(false);
		TerminalRead.setEnabled(true);
	}
	
	private void TerminalOkWeight() throws Exception {
		String productBatchNumber = dropDownProductBatch.getSelectedItem().toString();
		textAreaConsole.append("[" + getDate() + "]\t" + terminal.terminalOkWeight(productBatchNumber) + "\n");
		TerminalOkWeight.setEnabled(false);
		TerminalRead.setEnabled(true);
	}
	
	private void TerminalOkProductBatch(String productBatchNumber) throws Exception {
		if(terminal.terminalOkGetPrescription(productBatchNumber)!=null){	
		textAreaConsole.append("[" + getDate() + "]\n Recept num.: " + terminal.terminalOkGetPrescription(productBatchNumber) + "\n\n");
		textAreaPrescription.setText(terminal.terminalOkGetPrescription(productBatchNumber));
		}
		TerminalOkMaterialBatch.setEnabled (true);
	}
	private String getDate() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

}