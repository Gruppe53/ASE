package program;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import databaseAccess.DBAccess;
import net.miginfocom.swing.MigLayout;


public class TerminalGUI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ITerminal terminal;
	
	private JPanel productBatchPanel = new JPanel (new MigLayout());
	private JPanel materialBatchPanel = new JPanel();
	private JPanel consolePanel = new JPanel();
	private JPanel commandsPanel = new JPanel();
			
	private Dimension innerSize = new Dimension(500, 100);
	
	private JScrollPane txtPanel = new JScrollPane();
	private JLabel productBatch = new JLabel("Produktbatch: ");
	private JScrollPane recept = new JScrollPane();
	private JLabel receptLabel = new JLabel ("Receptnummer: ");
	private JLabel materialBatch = new JLabel ("Råvarebatch: ");
	
	private JComboBox<Integer> dropDownProductBatch = new JComboBox<Integer>();
	private JComboBox<String> dropDownMaterialBatch = new JComboBox<String>();
	
	//instances used
	int buttonPressedCount = 0;
		
	// Buttons
	private JButton TerminalRead = new JButton("Read");
	private JButton TerminalOkWeight = new JButton("Ok");
	private JButton TerminalOkProductBatch = new JButton("Ok");
	private JButton TerminalOkMaterialBatch= new JButton("Ok");
	

	// Printout
	private JTextArea textAreaConsole = new JTextArea();
	private JTextArea textAreaPrescription = new JTextArea();

	public TerminalGUI(ITerminal Terminal) {
		this.terminal = Terminal;
		
		// General setup
		setLayout(new MigLayout());
		setPreferredSize(new Dimension(500, 400));
		setOpaque(true);
		

		TerminalRead.setEnabled(false);

		TerminalOkWeight.setEnabled(false);

		// Productbatch panel
		productBatchPanel.setPreferredSize(innerSize);
		productBatchPanel.setBackground(Color.decode("#ffffff"));
		productBatchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Produktbatch"));
		
		ArrayList<Integer> productBatchIDs = new ArrayList<Integer>();
		
		DBAccess con = new DBAccess();
						
		dropDownProductBatch = new JComboBox<Integer>();
		try {
			ResultSet rs = con.doSqlQuery("SELECT * FROM productbatch WHERE status = 0 OR 1");
			
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
		
		productBatchPanel.add(productBatch);
		productBatchPanel.add(dropDownProductBatch);
		productBatchPanel.add(TerminalOkProductBatch, "wrap");
		productBatchPanel.add(receptLabel);
		productBatchPanel.add(recept);
	
		add(productBatchPanel, "wrap");
		
		// Material Batch panel settings
		materialBatchPanel.setPreferredSize(innerSize);
		materialBatchPanel.setBackground(Color.decode("#ffffff"));
		materialBatchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Råvarebatch"));
		
		materialBatchPanel.add(materialBatch);
		materialBatchPanel.add(dropDownMaterialBatch);
		materialBatchPanel.add(TerminalOkMaterialBatch);
		
		add(materialBatchPanel, "wrap");
		
		// Commands panel settings
		commandsPanel.setPreferredSize(innerSize);
		commandsPanel.setBackground(Color.decode("#ffffff"));
		commandsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Vægtkommandoer"));
		
		commandsPanel.add(TerminalRead);
		commandsPanel.add(TerminalOkWeight);
		
		add(commandsPanel, "wrap");


		// Console panel
		consolePanel.setPreferredSize(innerSize);
		consolePanel.setBackground(Color.decode("#ffffff"));
		consolePanel.setBorder(new EmptyBorder(0,0,0,0));
		
		txtPanel = new JScrollPane(textAreaConsole);
		txtPanel.setBackground(Color.decode("#ffffff"));
		txtPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Console"));
		txtPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		txtPanel.setPreferredSize(innerSize);
		
		textAreaConsole.setLineWrap(true);
		textAreaConsole.setWrapStyleWord(true);
		textAreaConsole.setEditable(false);
		textAreaConsole.append("[" + getDate() + "]\tType an IP to connect.\n");
		
		add(txtPanel);
		
		/*ArrayList<Integer> productBatchIDs = new ArrayList<Integer>();
		
		DBAccess con = new DBAccess();
						
		dropDownProductBatch = new JComboBox<Integer>();
		try {
			ResultSet rs = con.doSqlQuery("SELECT * FROM productbatch WHERE status = 0 OR 1");
			
	    	while(rs.next()) {
	    		productBatchIDs.add(rs.getInt("pb_id"));
	    	}
	    	con.closeSql();
		} catch(Exception e) {
			e.printStackTrace();
		}

		
		for(int i : productBatchIDs) {
			dropDownProductBatch.addItem(i);
		

		}*/
		
		
		recept = new JScrollPane(textAreaPrescription);
		recept.setBackground(Color.white);
		recept.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")),"Recept"));
		recept.setPreferredSize(new Dimension(200,50));
		
		textAreaPrescription.setLineWrap(true);
		textAreaPrescription.setWrapStyleWord(true);
		textAreaPrescription.setEditable(false);
		
		
		
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
        	 	DBAccess con2 = new DBAccess();
        		
        		dropDownMaterialBatch = new JComboBox<String>();
        		
        		ResultSet rs = null;
        		try {
        			rs = con2.doSqlQuery("SELECT mb_id, m_name, amount FROM matbatch NATURAL JOIN materials NATURAL JOIN precomponent WHERE pre_id = " + terminal.terminalOkGetPrescription(dropDownProductBatch.getSelectedItem().toString()) + " AND amount >= netto AND m_id NOT IN (SELECT m_id FROM pbcomponent NATURAL JOIN matbatch WHERE pb_id = "+ dropDownProductBatch.getSelectedItem().toString() + ")");
        			
        	    	while(rs.next()) {
        	    		dropDownMaterialBatch.addItem("["+rs.getInt("mb_id")+"] " + rs.getString("m_name") + " ("+rs.getDouble("amount")+" g)");
        	    	}
        	    	
        		} catch(Exception f) {
        			f.printStackTrace();
        		}
        		
        		materialBatchPanel.removeAll();
        		materialBatchPanel.add(dropDownMaterialBatch);
        		
            	String productBatchNumber = dropDownProductBatch.getSelectedItem().toString();
            	
                try {
					TerminalOkProductBatch(productBatchNumber);
					TerminalOkProductBatch.setEnabled(false);
					dropDownProductBatch.setEnabled(false);
					con2.doSqlUpdate("UPDATE productbatch SET status = '1' WHERE pb_id = '" + "'");
				    con2.closeSql();
				} catch (Exception f) {
					f.printStackTrace();
				}
            
            }
		});
		
		TerminalOkMaterialBatch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					TerminalOkMaterialBatch();
				} catch (Exception f) {
					f.printStackTrace();
				}
            }
		});
		

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
			textAreaConsole.append("[" + getDate() + "]\t" + "Du kan nu begynde din afvejning. Tryk ok." + "\n");
			TerminalOkWeight.setEnabled(true);
			TerminalRead.setEnabled(false);
			buttonPressedCount++;
		}
			//second time you press
			if(buttonPressedCount == 1){
				textAreaConsole.append("[" + getDate() + "]\t" + "Dette er den tarerede vægt. Tryk ok." + "\n");
				TerminalOkWeight.setEnabled(true);
				TerminalRead.setEnabled(false);
				buttonPressedCount++;
			}
				if(buttonPressedCount > 1 && terminal.tolerableWeight(productBatchNumber)) {
					textAreaConsole.append("[" + getDate() + "]\t" + "Du er inden for den tolererede vægt. Tryk ok." + "\n");
					TerminalOkWeight.setEnabled(true);
					TerminalRead.setEnabled(false);	
					buttonPressedCount++;
				}
				
		else {
			if(buttonPressedCount > 1 && !(terminal.tolerableWeight(productBatchNumber))){
				textAreaConsole.append("[" + getDate() + "]\t" + "Du er ikke inden for den ønskede mængde, tilføj eller fjern råvaren fra beholderen, kom inden for den ønskede tolerence mængde og tryk READ igen." + "\n");
			}
			textAreaConsole.setText("[" + getDate() + "]\t rengør vægt eller fjern evt. beholdere på vægten og tryk READ igen.");
		}
	}
	
	private void TerminalOkMaterialBatch() throws Exception {
		textAreaConsole.append("[" + getDate() + "]\n Råvare num.: " + terminal.terminalOkGetMaterialId() + "\n Råvare navn: " + terminal.terminalOkGetMaterialName() + "\n");
		dropDownMaterialBatch.setEnabled(false);
		TerminalOkMaterialBatch.setEnabled(false);
		TerminalRead.setEnabled(true);
	}
	
	private void TerminalOkWeight() throws Exception {
		String productBatchNumber = dropDownProductBatch.getSelectedItem().toString();
		textAreaConsole.append("[" + getDate() + "]\t" + terminal.terminalOkWeight(productBatchNumber) + "\n");
		DBAccess con = new DBAccess();
		
		ResultSet rsi = con.doSqlQuery("SELECT COUNT(*) FROM precomponent WHERE pre_id = '" + terminal.terminalOkGetPrescription(productBatchNumber) + "'");
		ResultSet rsj = con.doSqlQuery("SELECT COUNT(*) FROM pbcomponent WHERE pb_id = '" + productBatchNumber + "'");
		
		int i = rsi.getInt("COUNT(*)");
		int j = rsj.getInt("COUNT(*)");
		
		if(buttonPressedCount > 1 && i==j ){
			con.doSqlUpdate("UPDATE productbatch SET status = '2' WHERE pb_id = '" + "'");
			TerminalOkWeight.setEnabled(false);
			TerminalRead.setEnabled(false);
			// TODO afvejning er færdig her - alt skal nulstilles
			
		}
		else
			if(buttonPressedCount > 1){
				TerminalOkWeight.setEnabled(false);
				TerminalRead.setEnabled(false);
				dropDownMaterialBatch.removeAll();
				//dropDownMaterialBatch.add(comp);
				//dropDownMaterial
				//TODO denne råvarebatch er færdig - skift den.
			}
		else{
		TerminalOkWeight.setEnabled(false);
		TerminalRead.setEnabled(true);
		}
		con.closeSql();
	}
	
	private void TerminalOkProductBatch(String productBatchNumber) throws Exception {
		String togp = terminal.terminalOkGetPrescription(productBatchNumber);
		
		if(togp!=null){	
			textAreaConsole.append("[" + getDate() + "]\t Recept num.: " + togp + "\n");
			textAreaPrescription.setText(togp);
		}
		
		TerminalOkMaterialBatch.setEnabled(true);
	}
	private String getDate() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

}
