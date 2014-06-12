package Users;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.sql.*;
import DBaccess.*;
import javax.swing.*;

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
	
	private JTextArea textArea = new JTextArea();;
	
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
		
		userName = new JLabel("Operat�r nr.:");
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
            	userLogin();
            }
        });
		
		userLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	userLogout();
            }
        });
	}
			
	private void userLogin() {
		Connector cn = new Connector();
		String databaseUsername = "";
		String databasePassword = "";
		String oprNr = oprId.getText();
		char[] pw = pass.getPassword();
		try {
			if (oprNr != null && pass!=null) {
	    String sql = "SELECT * FROM users WHERE uid='" + oprNr + "' and password='" + pw + "'";
	    ResultSet rs = cn.doQuery(sql);
	    	while( rs.next()){
	           databaseUsername = rs.getString("uid");
	           databasePassword = rs.getString("Password");
	    	}
	    	if (oprNr.equals(databaseUsername) && pw.equals(databasePassword)){
	    		userLogin.setEnabled(false);
				oprId.setEnabled(false);
				pass.setEnabled(false);
				
				try {
					textArea.append("[" + getDate() + "] Logged in as " + opr.getOperator(oprNr) + "\n");
				}
				catch (DALException e) {
					e.printStackTrace();
	
	   
	
	
	private void userLogout() {
		if(opr.getActive()) {
			tab.setEnabledAt(1, false);
			
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
	
	/*String pw = pass.getText();
		try {
			if ( != null && pass!=null) {
		Statement stmt = Connector.CreateStatement();
	    String sql = "SELECT * FROM users WHERE uid='" + oprNr + "' and password='" + pw + "'";
	    ResultSet rs = stmt.executeQuery(sql);
	    	if( rs.next()){
	           
	    	} else {
	            //in this case enter when  result size is zero  it means user is invalid
	       }
	   }

	//You can also validate user by result size if its comes zero user is invalid else user is valid


	    } catch (SQLException err) {
	        JOptionPane.showMessageDialog(this, err.getMessage());
	    }*/

}
