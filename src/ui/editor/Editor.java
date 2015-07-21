package ui.editor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSeparator;
import java.awt.Font;
import javax.swing.JCheckBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Editor {

	private JFrame frame;
	private JTextField txtQuestion;
	private JTextField txtSoulName;
	private JTextField txtDefaultReply;
	private JLabel lblStatusbar;
	private JCheckBox chkExpr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor window = new Editor();
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
	public Editor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		EditorManager em = new EditorManager();
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				em.closeSoul();
				frame.setVisible(false);
				frame.dispose();
			}
		});
		frame.setBounds(100, 100, 450, 476);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JLabel lblQuestion = new JLabel("Question:");
		springLayout.putConstraint(SpringLayout.WEST, lblQuestion, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblQuestion);
		
		txtQuestion = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtQuestion, 6, SpringLayout.SOUTH, lblQuestion);
		springLayout.putConstraint(SpringLayout.WEST, txtQuestion, 0, SpringLayout.WEST, lblQuestion);
		springLayout.putConstraint(SpringLayout.SOUTH, txtQuestion, -236, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtQuestion, -20, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(txtQuestion);
		txtQuestion.setColumns(10);
		
		JButton btnAddQuestion = new JButton("Add");
		btnAddQuestion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (em.addQuestion(txtQuestion.getText()))
					lblStatusbar.setText("Question added");
				else
					lblStatusbar.setText("Error: Can't add question, maybe already exists.");
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnAddQuestion, 4, SpringLayout.SOUTH, txtQuestion);
		springLayout.putConstraint(SpringLayout.WEST, btnAddQuestion, 0, SpringLayout.WEST, lblQuestion);
		springLayout.putConstraint(SpringLayout.SOUTH, btnAddQuestion, -207, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(btnAddQuestion);
		
		JLabel lblReply = new JLabel("Reply:");
		springLayout.putConstraint(SpringLayout.NORTH, lblReply, 13, SpringLayout.SOUTH, btnAddQuestion);
		springLayout.putConstraint(SpringLayout.WEST, lblReply, 0, SpringLayout.WEST, lblQuestion);
		springLayout.putConstraint(SpringLayout.SOUTH, lblReply, -179, SpringLayout.SOUTH, frame.getContentPane());
		frame.getContentPane().add(lblReply);
		
		JTextArea txtReply = new JTextArea();
		txtReply.setRows(7);
		
		JScrollPane scrollpane = new JScrollPane(txtReply);
		springLayout.putConstraint(SpringLayout.NORTH, scrollpane, 6, SpringLayout.SOUTH, lblReply);
		springLayout.putConstraint(SpringLayout.WEST, scrollpane, 0, SpringLayout.WEST, lblQuestion);
		springLayout.putConstraint(SpringLayout.SOUTH, scrollpane, -68, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollpane, -20, SpringLayout.EAST, frame.getContentPane());
		
		frame.getContentPane().add(scrollpane);
		
		JButton btnAddReply = new JButton("Add");
		springLayout.putConstraint(SpringLayout.NORTH, btnAddReply, 22, SpringLayout.SOUTH, scrollpane);
		springLayout.putConstraint(SpringLayout.WEST, btnAddReply, 10, SpringLayout.WEST, frame.getContentPane());
		btnAddReply.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean isExpr = chkExpr.isSelected();
				if (em.addReply(txtReply.getText(), isExpr))
					lblStatusbar.setText("Reply added");
				else
					lblStatusbar.setText("Error: Can't add reply.");
			}
		});
		frame.getContentPane().add(btnAddReply);
		
		JLabel lblSoulName = new JLabel("Soul Name:");
		springLayout.putConstraint(SpringLayout.NORTH, lblSoulName, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblSoulName, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblSoulName);
		
		txtSoulName = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtSoulName, 9, SpringLayout.SOUTH, lblSoulName);
		springLayout.putConstraint(SpringLayout.WEST, txtSoulName, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtSoulName, 225, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(txtSoulName);
		txtSoulName.setColumns(10);
		
		JButton btnCreateSoul = new JButton("Create/Connect");
		springLayout.putConstraint(SpringLayout.WEST, btnCreateSoul, 10, SpringLayout.WEST, frame.getContentPane());
		btnCreateSoul.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (em.createSoul(txtSoulName.getText(), txtDefaultReply.getText()))
					JOptionPane.showMessageDialog(frame, "Digital soul created!");
				
				if (em.openSoul(txtSoulName.getText()))
					lblStatusbar.setText("Connected to digital soul");
				else
					lblStatusbar.setText("Error: Not connected to digital soul!");
			}
		});
		frame.getContentPane().add(btnCreateSoul);
		
		JLabel lblDefaultReply = new JLabel("Default reply:");
		springLayout.putConstraint(SpringLayout.NORTH, lblDefaultReply, 13, SpringLayout.SOUTH, txtSoulName);
		springLayout.putConstraint(SpringLayout.WEST, lblDefaultReply, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblDefaultReply);
		
		txtDefaultReply = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, txtDefaultReply, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtDefaultReply, -20, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnCreateSoul, 6, SpringLayout.SOUTH, txtDefaultReply);
		springLayout.putConstraint(SpringLayout.NORTH, txtDefaultReply, 6, SpringLayout.SOUTH, lblDefaultReply);
		frame.getContentPane().add(txtDefaultReply);
		txtDefaultReply.setColumns(10);
		
		JSeparator separator = new JSeparator();
		springLayout.putConstraint(SpringLayout.NORTH, separator, 18, SpringLayout.SOUTH, btnCreateSoul);
		springLayout.putConstraint(SpringLayout.SOUTH, separator, -289, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, lblQuestion, 13, SpringLayout.SOUTH, separator);
		springLayout.putConstraint(SpringLayout.WEST, separator, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, separator, -20, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(separator);
		
		lblStatusbar = new JLabel("");
		springLayout.putConstraint(SpringLayout.SOUTH, btnAddReply, -6, SpringLayout.NORTH, lblStatusbar);
		springLayout.putConstraint(SpringLayout.EAST, lblStatusbar, -10, SpringLayout.EAST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblStatusbar, 0, SpringLayout.WEST, lblQuestion);
		springLayout.putConstraint(SpringLayout.SOUTH, lblStatusbar, -10, SpringLayout.SOUTH, frame.getContentPane());
		lblStatusbar.setFont(new Font("Dialog", Font.PLAIN, 11));
		frame.getContentPane().add(lblStatusbar);
		
		chkExpr = new JCheckBox("Evalutate as expression");
		springLayout.putConstraint(SpringLayout.NORTH, chkExpr, 1, SpringLayout.SOUTH, scrollpane);
		springLayout.putConstraint(SpringLayout.EAST, chkExpr, -124, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(chkExpr);
		
		JButton btnAdvanced = new JButton("Advanced");
		btnAdvanced.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AdvancedEdit aewindow = new AdvancedEdit();
				aewindow.setEditorManager(em);
				aewindow.show();
			}
		});
		springLayout.putConstraint(SpringLayout.SOUTH, btnAdvanced, 0, SpringLayout.SOUTH, btnAddReply);
		springLayout.putConstraint(SpringLayout.EAST, btnAdvanced, 0, SpringLayout.EAST, txtQuestion);
		frame.getContentPane().add(btnAdvanced);
	}
	
	public void show(){
		this.frame.setVisible(true);
	}
}
