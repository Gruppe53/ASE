package program;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;


public class TerminalGUI extends JPanel {
	private static final long serialVersionUTD = 1L;
	
	private ITerminal terminal;
	Terminal Terminal = new Terminal(null); 
	
	private JPanel scaPanel = new JPanel(new MigLayout());
	private JPanel conPanel = new JPanel();
	private JPanel stsPanel = new JPanel();
	private JPanel cmdPanel = new JPanel();
	private JScrollPane txtPanel;
		
	// knapper
	private JButton TerminalRead = new JButton("Read");
	private JButton TerminalOk = new JButton("Ok");

	
	// Udskrift
	private JTextArea textArea = new JTextArea();

	public TerminalGUI(ITerminal Terminal) {
		this.terminal = Terminal;
		
		// General setup
		setLayout(new MigLayout());
		
		scaPanel.setBorder(BorderFactory.createBevelBorder(1, Color.decode("#ffffff"), Color.decode("#898c95"), Color.decode("#898c95"), Color.decode("#f0f0f0")));
		scaPanel.setBackground(Color.WHITE);
		
		
		// Button panel
		cmdPanel.setPreferredSize(new Dimension(400, 60));
		cmdPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Terminal commands"));
		cmdPanel.setBackground(Color.white);
		
		TerminalRead.setEnabled(false);

		TerminalOk.setEnabled(false);

		
		cmdPanel.add(TerminalRead);

		cmdPanel.add(TerminalOk);

		
		
		// Console panel
		txtPanel = new JScrollPane(textArea);
		txtPanel.setBackground(Color.white);
		txtPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Console"));
		txtPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		txtPanel.setPreferredSize(new Dimension(400, 150));
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.append("[" + getDate() + "]\tType an IP to connect.\n");
		
		// Add to Main panel
		scaPanel.add(conPanel);
		scaPanel.add(stsPanel, "wrap");
		scaPanel.add(cmdPanel, "span 2 1, wrap");
		scaPanel.add(txtPanel, "span 2 1");
		
		
		TerminalRead.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TerminalRead();
            }
        });
		

		
		TerminalOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TerminalOk();
            }
        });
		

		
		add(scaPanel);
	}
	

	
	private void TerminalRead() {
		textArea.append("[" + getDate() + "]\t" + Terminal.terminalRead() + "\n");
	}
	
	private void TerminalTare() {
		textArea.append("[" + getDate() + "]\t" + Terminal.terminalTare() + "\n");
	}
	
	private void TerminalZero() {
		textArea.append("[" + getDate() + "]\t" + Terminal.terminalZero() + "\n");
	}
	
	private void TerminalOk() {
		textArea.append("[" + getDate() + "]\t" + Terminal.terminalOk() + "\n");
	}
	
	
	
	private String getDate() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}


	
	
	

	

}