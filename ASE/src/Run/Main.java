package Run;

import java.awt.*;

import javax.swing.*;

import connect.*;
import program.*;
import Users.*;
import net.miginfocom.swing.MigLayout;

public class Main extends JPanel{
	private static final long serialVersionUID = 1L;
	private static JFrame terminalFrame;
	private static JComponent newContentPane;
	
	private JTabbedPane tab;
	
	private ITerminalConnection con;
	private ITerminal terminal;
	private TerminalGUI TGUI;
	private ConnectGUI CGUI;
	
	private IOperator oprDTO;
	private IOperatorDAO oprDAO;
	private GuiLogin GLogin;
	
	
	
	public Main() {
		setLayout(new MigLayout());
		
		UIManager.put("tabbedPane.tabAreaBackground", Color.decode("#f0f0f0"));
		UIManager.put("tabbedPane.focus", Color.decode("#c8ddf2"));
		tab = new JTabbedPane();
		tab.setOpaque(true);
		
		
		con = new TerminalConnection();
		terminal = new Terminal(con);
		TGUI = new TerminalGUI(terminal);
		
		oprDTO = new OperatorDTO();
		oprDAO = new OperatorDAO(oprDTO);
		GLogin = new GuiLogin(oprDAO, tab);
		
		tab.addTab("Connect", CGUI);
		tab.addTab("Log in", GLogin);
		tab.addTab("Scale", TGUI);
		
		tab.setEnabledAt(1, false);
		tab.setEnabledAt(2, false);
		
		add(tab);
		
	}
	
	private static void createAndShowGUI(String name){
		terminalFrame = new JFrame (name);
		terminalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		terminalFrame.setBackground(Color.decode("#f0f0f0"));
		terminalFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("materials/icon.png"));
		terminalFrame.setResizable(false);
		
	
		newContentPane = new Main();
		newContentPane.setOpaque(true);
		terminalFrame.setContentPane(newContentPane);
		
		terminalFrame.setLocationRelativeTo(null);
		terminalFrame.pack();
		terminalFrame.setVisible(true);
		
		}
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				createAndShowGUI("Scale");
				
			}
		});
	}
		
		
}
