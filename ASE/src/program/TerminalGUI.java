package program;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;


import net.miginfocom.swing.MigLayout;


public class TerminalGUI {
	private static final long serialVersionUTD = 1L;
	
	private ITerminal terminal;
	
	private JPanel scaPanel = new JPanel(new MigLayout());
	private JPanel conPanel = new JPanel();
	private JPanel stsPanel = new JPanel();
	private JPanel cmdPanel = new JPanel();
	private JScrollPane txtPanel;
	
	// Forbindelse
	private JTextField[] host = new JTextField[4];
	private JTextField port = new JTextField();
	private JLabel hostLabel = new JLabel("Host");
	private JLabel portLabel = new JLabel("Port");
	private JButton conButton = new JButton("Connect");
	private JButton disButton = new JButton("Disconnect");
	
	// knapper
	private JButton scaleRead = new JButton("Read");
	private JButton scaleTare = new JButton("Tare");
	private JButton scaleZero = new JButton("Zero");
	private JButton scaleMessage = new JButton("Message");
	private JButton scaleDisplay = new JButton("Display");
	
	// Udskrift
	private JTextArea textArea = new JTextArea();

	public TerminalGUI(ITerminal scale) {
		this.terminal = scale;
		
		// General setup
		setLayout(new MigLayout());
		
		scaPanel.setBorder(BorderFactory.createBevelBorder(1, Color.decode("#ffffff"), Color.decode("#898c95"), Color.decode("#898c95"), Color.decode("#f0f0f0")));
		scaPanel.setBackground(Color.WHITE);
		
		// Forbindelses panel setup
		conPanel.setPreferredSize(new Dimension(300, 60));
		conPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Connection"));
		conPanel.setBackground(Color.white);
		conPanel.setLayout(new MigLayout());
		
		stsPanel.setPreferredSize(new Dimension(96, 60));
		stsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Status"));
		stsPanel.setBackground(Color.white);
		
		for(int i = 0; i < host.length; i++) {
			host[i] = new JTextField("");
			host[i].setPreferredSize(new Dimension(27,14));
		}
	
	
	
	

	}

}