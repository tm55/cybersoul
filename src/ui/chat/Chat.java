package ui.chat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultCaret;

import ui.editor.Editor;

import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JFileChooser;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Chat {

	private JFrame frame;
	private JTextField txtInput;
	private ChatManager cm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat window = new Chat();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Chat() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cm.closeSoul();
			}
		});
		frame.setBounds(100, 100, 654, 445);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JTextPane txtChat = new JTextPane();
		txtChat.setEditable(false);
		txtChat.setContentType("text/html");
		DefaultCaret caret = (DefaultCaret) txtChat.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane scrollpane = new JScrollPane(txtChat);
		springLayout.putConstraint(SpringLayout.NORTH, scrollpane, 25, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, scrollpane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollpane, -40, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollpane, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(scrollpane);
		
		cm = new ChatManager(txtChat);
		
		txtInput = new JTextField();
		txtInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cm.replyToQuestion(txtInput.getText());
					txtInput.setText("");
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, txtInput, 6, SpringLayout.SOUTH, scrollpane);
		springLayout.putConstraint(SpringLayout.WEST, txtInput, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, txtInput, -10, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtInput, 0, SpringLayout.EAST, scrollpane);
		frame.getContentPane().add(txtInput);
		txtInput.setColumns(10);
		
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar);
		
		JMenu mnTest = new JMenu("File");
		menuBar.add(mnTest);
		
		JMenuItem mntmOpenSoul = new JMenuItem("Open soul");
		mntmOpenSoul.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//create a file chooser
				final JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Select digital soul");
				fc.setFileFilter(new FileNameExtensionFilter("Soul file", "soul"));
				int returnVal = fc.showOpenDialog(frame);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            cm.openSoul(fc.getSelectedFile());
		        }
			}
		});

		mnTest.add(mntmOpenSoul);
		
		JMenuItem mntmEditSoul = new JMenuItem("Edit soul");
		mntmEditSoul.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Editor window = new Editor();
				window.show();
			}
		});
		mnTest.add(mntmEditSoul);
		
		JMenuItem mntmInfoSoul = new JMenuItem("Info soul");
		mntmInfoSoul.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InfoSoul window = new InfoSoul();
				window.loadInfo(cm);
				window.show();
			}
		});
		mnTest.add(mntmInfoSoul);
	}
}
