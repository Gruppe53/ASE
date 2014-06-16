package users;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.sql.*;

import javax.swing.*;

import databaseAccess.*;
import net.miginfocom.swing.MigLayout;

public class GuiLogin extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private JPanel userPanel;
	private JPanel ctrlPanel;
	private JScrollPane txtPanel;
	
	private JLabel userName;
	private JLabel userPass;
	private JTextField oprId;
	private JPasswordField pass;
	private JButton userLogin;
	private JButton userLogout;

	private IOperatorDAO opr;
	
	private JTabbedPane tab;
	
	private JTextArea textArea = new JTextArea();
	
	public GuiLogin(IOperatorDAO opr, JTabbedPane tab) {
		this.opr = opr;
		this.tab = tab;
		
		setLayout(new MigLayout());

		userPanel = new JPanel(new MigLayout());
		userPanel.setBorder(BorderFactory.createBevelBorder(1, Color.decode("#ffffff"), Color.decode("#898c95"), Color.decode("#898c95"), Color.decode("#f0f0f0")));
		userPanel.setBackground(Color.WHITE);
		
		ctrlPanel = new JPanel(new MigLayout());
		ctrlPanel.setPreferredSize(new Dimension(400,120));
		ctrlPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Login"));
		ctrlPanel.setBackground(Color.white);
		
		userName = new JLabel("Operateor nr.:");
		userPass = new JLabel("Password:");
		oprId = new JTextField(8);
		oprId.setPreferredSize(new Dimension(98,20));
		pass = new JPasswordField(8);
		pass.setPreferredSize(new Dimension(98,20));
		
		userLogin = new JButton("Login");
		userLogin.setPreferredSize(new Dimension(80,20));
		userLogout = new JButton("Logout");
		userLogout.setPreferredSize(new Dimension(80,20));
		userLogout.setEnabled(false);
		
		JSeparator y = new JSeparator(SwingConstants.VERTICAL);
		y.setPreferredSize(new Dimension(1,60));
		y.setForeground(Color.decode("#d5dfe5"));
		
		ctrlPanel.add(userName);
		ctrlPanel.add(oprId);
		ctrlPanel.add(y, "span 1 2");
		ctrlPanel.add(userLogin, "wrap");
		ctrlPanel.add(userPass);
		ctrlPanel.add(pass, "span 1 2");
		ctrlPanel.add(userLogout, "wrap");		
		
		txtPanel = new JScrollPane(textArea);
		txtPanel.setBackground(Color.white);
		txtPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#d5dfe5")), "Console"));
		txtPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		txtPanel.setPreferredSize(new Dimension(400, 250));
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.append("[" + getDate() + "] Welcome...\n");
				
		userPanel.add(ctrlPanel, "wrap");
		userPanel.add(txtPanel);
		
		add(userPanel);
		
		userLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					userLogin();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
		
		userLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	userLogout();
            }
        });
	}
			
	private void userLogin() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		int databaseUsername = -1;
		String databasePassword = "";
		
		DBAccess con = new DBAccess("72.13.93.206", 3307, "gruppe55", "gruppe55", "55gruppe");
		// Syntaksen er: DBAccess("host", port, "database", "username", "password") - port er en int, resten string
		
		int oprNr = -1;
		
		try {
			oprNr = Integer.parseInt(oprId.getText());
		} catch(Exception e) {
			oprNr = -1;
		}
		
		String pw = new String(pass.getPassword());
		
		try {
			if (oprNr != -1 && pass != null) {
				ResultSet rs = con.doSqlQuery("SELECT * FROM user WHERE u_id = " + oprNr + " AND password = '" + pw + "'");
				
		    	while( rs.next()) {
		    		databaseUsername = rs.getInt("u_id");
		    		databasePassword = rs.getString("password");
		    		opr = new OperatorDAO(rs.getString("u_name"));
		    	}
		    	
		    	if (oprNr == databaseUsername && pw.equals(databasePassword)) {
		    		userLogin.setEnabled(false);
					oprId.setEnabled(false);
					pass.setEnabled(false);
					userLogout.setEnabled(true);
					
					tab.setEnabledAt(2, true);
					
					try {
						textArea.append("[" + getDate() + "] Logged in as " + opr.getOperator(oprNr) + "\n");
					}
					catch (DALException e) {
						e.printStackTrace();
					}
		    	}
	    	}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	   
	
	
	private void userLogout() {
		if(opr.getActive()) {
			//tab.setEnabledAt(2, false);
			// Fjern kommentering her også, når I er done.
			// 0 = Connection tab, 1 = Login tab, 2 = Scale tab. Når du logger ud, siger du at Login tab skal være deaktiveret... ^_^
			
			userLogout.setEnabled(false);
			userLogin.setEnabled(true);
			oprId.setEnabled(true);
			pass.setEnabled(true);
			
			opr.userLogout();
			
			textArea.append("[" + getDate() + "] Logged out.\n");
		}
	}

	private String getDate() {
		return new SimpleDateFormat("HH:mm:ss").format(new Date());
	}
}
