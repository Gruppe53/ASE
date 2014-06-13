package connect;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import program.ITerminal;
import javax.swing.*;

import net.miginfocom.swing.MigLayout;

public class ConnectGUI extends JComponent {
	private static final long serialVersionUID = 1L;
	private static JFrame scaleFrame;
	private static JComponent newContentPane;
	// General
	private ITerminal Terminal;
	
	// Main panels
	private JPanel scaPanel = new JPanel(new MigLayout());
	private JPanel conPanel = new JPanel();
	private JPanel stsPanel = new JPanel();
	private JScrollPane txtPanel;
	
	// Connection panel
	private JTextField[] host = new JTextField[4];
	
	private JTextField port = new JTextField("8000");
	private JLabel hostLabel = new JLabel("Host");
	private JLabel portLabel = new JLabel("Port");
	private JButton conButton = new JButton("Connect");
	private JButton disButton = new JButton("Disconnect");
	
	// Text panel (Console)
	private JTextArea textArea = new JTextArea();

	public ConnectGUI() {
		
		// General setup
		setLayout(new MigLayout());
		
		scaPanel.setBorder(BorderFactory.createBevelBorder(1, Color.decode("#ffffff"), Color.decode("#898c95"), Color.decode("#898c95"), Color.decode("#f0f0f0")));
		scaPanel.setBackground(Color.WHITE);
		
		// Connection panel setup
		conPanel.setPreferredSize(new Dimension(300, 60));
		conPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Connection"));
		conPanel.setBackground(Color.white);
		conPanel.setLayout(new MigLayout());
		
		stsPanel.setPreferredSize(new Dimension(96, 60));
		stsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Status"));
		stsPanel.setBackground(Color.white);
		
		host[0] = new JTextField("169");
		host[1] = new JTextField("254");
		host[2] = new JTextField("2");
		host[3] = new JTextField("2");
		for(int i = 0; i < host.length; i++) {
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
		scaPanel.add(txtPanel, "span 2 1");
		
		// Button events
		conButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TerminalConnect();
			}
		});
		
		disButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TerminalDisconnect();
			}
		});
		
		
		add(scaPanel);
	}
	
	private void TerminalConnect() {
		String response = Terminal.terminalConnect(host[0].getText() + "." + host[1].getText() + "." + host[2].getText() + "." + host[3].getText(), port.getText());
		String compare = "Could not connect";
		
		if(!response.equals(compare)) {
			textArea.append("[" + getDate() + "]\tConnected to " + response + "\n");
			
			conButton.setEnabled(false);
			disButton.setEnabled(true);
			
			//enable scale fane
			//login.setEnabled(true);

			
			for(JTextField field : host)
				field.setEnabled(false);
			
			port.setEnabled(false);
		}
		else
			textArea.append("[" + getDate() + "]\t" + compare + "\n");
	}
	
	private void TerminalDisconnect() {
		textArea.append("[" + getDate() + "]\tDisconnected from " + Terminal.terminalDisconnect() + "\n");
		
		conButton.setEnabled(true);
		disButton.setEnabled(false);
		
		// disable scale fane
		//login.setEnabled(false);
		
		for(JTextField field : host)
			field.setEnabled(true);
		
		port.setEnabled(true);
	}
	
	
	
	private String getDate() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}

	private static void createAndShowGUI(String name) {
        // Create window
		scaleFrame = new JFrame(name);
		scaleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		scaleFrame.setBackground(Color.decode("#f0f0f0"));
		scaleFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("materials/icon.png"));
		scaleFrame.setResizable(false);

        // Create the content pane
        newContentPane = new ConnectGUI();
        newContentPane.setOpaque(true);
        scaleFrame.setContentPane(newContentPane);

        // Draw the window
        scaleFrame.setLocationRelativeTo(null);
        scaleFrame.pack();
        scaleFrame.setVisible(true);
    }
	 public static void main(String[] args) {
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI("Scale Console"); // Name of window
	            }
	        });
	    }
}
