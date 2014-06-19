package terminal;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class TerminalGUI extends JComponent {
	private static final long serialVersionUID = 1L;
	// General setups
	private Dimension innerSize = new Dimension(500, 100);
	
	// Product Batch Panel
	private JPanel productBatchPanel = new JPanel(new MigLayout());
	
	private JLabel receptBatchLabel = new JLabel("Recept: ");
	private JLabel rececpBatchLabelTxt = new JLabel();
	
	private JLabel productBatchDropDownLabel = new JLabel("Produktbatch: ");
	private JComboBox<String> productBatchDropDown = new JComboBox<String>();
	
	private JButton productBatchBtn = new JButton("Vælg produktbatch");
	
	// Material Batch Panel
	private JPanel materialBatchPanel = new JPanel(new MigLayout());
	
	private JLabel materialBatchDropDownLabel = new JLabel("Råvarebatch: ");
	private JComboBox<String> materialBatchDropDown = new JComboBox<String>();
	
	private JButton materialBatchBtn = new JButton("Vælg råvarebatch");
	
	// Console Panel
	private JPanel consolePanel = new JPanel(new MigLayout());
	private JTextArea textArea = new JTextArea();
	private JScrollPane txtPanel;
	
	// Commands Panel
	private JPanel commandsPanel = new JPanel(new MigLayout());
	private JButton readBtn = new JButton("Aflæs");
	private JButton nextStateBtn = new JButton("Nulstil");

	public TerminalGUI() {
		setLayout(new MigLayout());
		setPreferredSize(new Dimension(500, 400));
		setOpaque(true);
		
		// Product Batch Panel Settings
		productBatchPanel.setPreferredSize(innerSize);
		productBatchPanel.setBackground(Color.decode("#ffffff"));
		productBatchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Produktbatch"));
		
		rececpBatchLabelTxt.setText("[50505050] Kødsovs");
		
		productBatchDropDown.addItem("12345678");
		productBatchDropDown.addItem("65478912");
		productBatchDropDown.addItem("32145687");
		productBatchDropDown.addItem("65425895");
		productBatchDropDown.addItem("15654895");
		
		productBatchPanel.add(productBatchDropDownLabel);
		productBatchPanel.add(productBatchDropDown);
		productBatchPanel.add(productBatchBtn, "wrap");
		productBatchPanel.add(receptBatchLabel);
		productBatchPanel.add(rececpBatchLabelTxt);
		
		add(productBatchPanel, "wrap");
		
		// Material Batch Panel Settings
		materialBatchPanel.setPreferredSize(innerSize);
		materialBatchPanel.setBackground(Color.decode("#ffffff"));
		materialBatchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Råvarebatch"));
		
		materialBatchDropDown.addItem("[12345678] Natrium (100.0 g)");
		materialBatchDropDown.addItem("[65478912] Chlorid (100.0 g)");
		materialBatchDropDown.addItem("[32145687] Jasmin ris (100.0 g)");
		materialBatchDropDown.addItem("[65425895] Gedeost (100.0 g)");
		materialBatchDropDown.addItem("[15654895] Paracetamol (100.0 g)");
		
		materialBatchPanel.add(materialBatchDropDownLabel);
		materialBatchPanel.add(materialBatchDropDown);
		materialBatchPanel.add(materialBatchBtn);
		
		add(materialBatchPanel, "wrap");
		
		// Commands Panel Settings
		commandsPanel.setPreferredSize(innerSize);
		commandsPanel.setBackground(Color.decode("#ffffff"));
		commandsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Vægtkommandoer"));
		
		commandsPanel.add(readBtn);
		commandsPanel.add(nextStateBtn);
		
		add(commandsPanel, "wrap");
		
		// Console Panel Settings
		consolePanel.setPreferredSize(innerSize);
		consolePanel.setBackground(Color.decode("#ffffff"));
		consolePanel.setBorder(new EmptyBorder(0,0,0,0));
		
		txtPanel = new JScrollPane(textArea);
		txtPanel.setBackground(Color.decode("#ffffff"));
		txtPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Console"));
		txtPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		txtPanel.setPreferredSize(innerSize);
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.append("[" + getDate() + "]\tType an IP to connect.\n");
		
		add(txtPanel);
	}
	
	private String getDate() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}
}