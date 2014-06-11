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
port.setPreferredSize(new Dimension(40,14));
		
		disButton.setEnabled(false);
		conButton.setPreferredSize(new Dimension(98, 26));
		
		JSeparator y = new JSeparator(SwingConstants.VERTICAL);
		y.setPreferredSize(new Dimension(1,60));
		y.setForeground(Color.decode("#d5dfe5"));
		
		conPanel.add(hostLabel);
		conPanel.add(host[0]);
		conPanel.add(new JLabel("."));
		conPanel.add(host[1]);
		conPanel.add(new JLabel("."));
		conPanel.add(host[2]);
		conPanel.add(new JLabel("."));
		conPanel.add(host[3]);
		conPanel.add(y, "span 1 2");
		conPanel.add(conButton, "wrap");
		conPanel.add(portLabel);
		conPanel.add(port, "span 7 1");
		conPanel.add(disButton, "wrap");
		
		// Button panel
		cmdPanel.setPreferredSize(new Dimension(400, 60));
		cmdPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Scale commands"));
		cmdPanel.setBackground(Color.white);
		
		scaleRead.setEnabled(false);
		scaleTare.setEnabled(false);
		scaleZero.setEnabled(false);
		scaleMessage.setEnabled(false);
		scaleDisplay.setEnabled(false);
		
		cmdPanel.add(scaleRead);
		cmdPanel.add(scaleTare);
		cmdPanel.add(scaleZero);
		cmdPanel.add(scaleMessage);
		cmdPanel.add(scaleDisplay);
		
		
		// Console panel
		txtPanel = new JScrollPane(textArea);
		txtPanel.setBackground(Color.white);
		txtPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Console"));
		txtPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		txtPanel.setPreferredSize(new Dimension(400, 200));
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.append("[" + getDate() + "]\tType an IP to connect.\n");
		
		// Add to Main panel
		scaPanel.add(conPanel);
		scaPanel.add(stsPanel, "wrap");
		scaPanel.add(cmdPanel, "span 2 1, wrap");
		scaPanel.add(txtPanel, "span 2 1");
		
		// Button events
		conButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaleConnect();
			}
		});
		
		disButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scaleDisconnect();
			}
		});
		
		
		scaleRead.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaleRead();
            }
        });
		
		scaleTare.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaleTare();
            }
        });
		
		scaleZero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaleZero();
            }
        });
		
		scaleMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaleMessage(JOptionPane.showInputDialog("Message"));
            }
        });
		
		scaleDisplay.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaleDisplay();
            }
        });
		
		add(scaPanel);
	}
	
	private void scaleConnect() {
		String response = Terminal.terminalConnect(host[0].getText() + "." + host[1].getText() + "." + host[2].getText() + "." + host[3].getText(), port.getText());
		String compare = "Could not connect";
		
		if(!response.equals(compare)) {
			textArea.append("[" + getDate() + "]\tConnected to " + response + "\n");
			
			conButton.setEnabled(false);
			disButton.setEnabled(true);
			
			scaleRead.setEnabled(true);
			scaleTare.setEnabled(true);
			scaleZero.setEnabled(true);
			scaleMessage.setEnabled(true);
			scaleDisplay.setEnabled(true);
			
			for(JTextField field : host)
				field.setEnabled(false);
			
			port.setEnabled(false);
		}
		else
			textArea.append("[" + getDate() + "]\t" + compare + "\n");
	}
	
	private void scaleDisconnect() {
		textArea.append("[" + getDate() + "]\tDisconnected from " + Terminal.scaleDisconnect() + "\n");
		
		conButton.setEnabled(true);
		disButton.setEnabled(false);
		
		scaleRead.setEnabled(false);
		scaleTare.setEnabled(false);
		scaleZero.setEnabled(false);
		scaleMessage.setEnabled(false);
		scaleDisplay.setEnabled(false);
		
		for(JTextField field : host)
			field.setEnabled(true);
		
		port.setEnabled(true);
	}
	
	private void scaleRead() {
		textArea.append("[" + getDate() + "]\t" + Terminal.terminalRead() + "\n");
	}
	
	private void scaleTare() {
		textArea.append("[" + getDate() + "]\t" + Terminal.terminalTare() + "\n");
	}
	
	private void scaleZero() {
		textArea.append("[" + getDate() + "]\t" + Terminal.terminalZero() + "\n");
	}
	
	private void scaleMessage(String msg) {
		textArea.append("[" + getDate() + "]\t" + Terminal.terminalMessage(msg) + "\n");
	}
	
	private void scaleDisplay() {
		textArea.append("[" + getDate() + "]\t" + Terminal.terminalDisplay() + "\n");
	}
	
	private String getDate() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}
}

	
	
	

	}

}