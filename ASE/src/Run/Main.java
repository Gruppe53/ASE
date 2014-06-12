package Run;

import java.awt.*;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;
import program.*;
import Users.*;

public class Main extends JPanel{
	private static final long serialVersionUID = 1L;
	private static JFrame terminalFrame;
	private static JComponent newContentPane;
	
	private JTabbedPane tab;
	
	private ITerminalConnection con;
	private ITerminal scale;
	private TerminalGUI TGUI;
	
	private IOperatorDAO oprDAO;
	private GuiLogin GLogin;
	
	public Main() {
		setLayout(new MigLayout());
	}
}