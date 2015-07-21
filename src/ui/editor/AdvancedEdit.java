package ui.editor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdvancedEdit {

	private JFrame frame;
	private JTextField txtQuery;
	private JTextArea txtResponse;
	private EditorManager em = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdvancedEdit window = new AdvancedEdit();
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
	public AdvancedEdit() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JLabel lblQuery = new JLabel("Query:");
		springLayout.putConstraint(SpringLayout.NORTH, lblQuery, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblQuery, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblQuery);
		
		txtQuery = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, txtQuery, 6, SpringLayout.SOUTH, lblQuery);
		springLayout.putConstraint(SpringLayout.WEST, txtQuery, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, txtQuery, 430, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(txtQuery);
		txtQuery.setColumns(10);
		
		JButton btnExecute = new JButton("Execute");
		btnExecute.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ((em != null) && (txtQuery.getText() != "")){
					txtResponse.setText(em.execQuery(txtQuery.getText()));
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnExecute, 6, SpringLayout.SOUTH, txtQuery);
		springLayout.putConstraint(SpringLayout.WEST, btnExecute, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(btnExecute);
		
		JLabel lblResponse = new JLabel("Response:");
		springLayout.putConstraint(SpringLayout.NORTH, lblResponse, 18, SpringLayout.SOUTH, btnExecute);
		springLayout.putConstraint(SpringLayout.WEST, lblResponse, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblResponse);
		
		txtResponse = new JTextArea();
		txtResponse.setTabSize(4);
		txtResponse.setRows(8);
		txtResponse.setColumns(38);
		JScrollPane scrollpane = new JScrollPane(txtResponse);
		springLayout.putConstraint(SpringLayout.NORTH, scrollpane, 13, SpringLayout.SOUTH, lblResponse);
		springLayout.putConstraint(SpringLayout.WEST, scrollpane, 0, SpringLayout.WEST, lblQuery);
		frame.getContentPane().add(scrollpane);
	}
	
	public void show(){
		this.frame.setVisible(true);
	}
	
	public void setEditorManager(EditorManager em) {
		this.em = em;
	}
}
