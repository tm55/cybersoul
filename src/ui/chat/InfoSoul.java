package ui.chat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InfoSoul {

	private JFrame frame;
	private JLabel lblName;
	private JLabel lblQuestions;
	private JLabel lblReplies;
	private JLabel lblSign;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InfoSoul window = new InfoSoul();
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
	public InfoSoul() {
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
				frame.setVisible(false);
				frame.dispose();
			}
		});
		frame.setBounds(100, 100, 336, 253);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		lblName = new JLabel("Name:");
		springLayout.putConstraint(SpringLayout.NORTH, lblName, 22, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblName, 135, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblName);
		
		lblQuestions = new JLabel("Number of questions:");
		springLayout.putConstraint(SpringLayout.NORTH, lblQuestions, 22, SpringLayout.SOUTH, lblName);
		springLayout.putConstraint(SpringLayout.EAST, lblQuestions, 0, SpringLayout.EAST, lblName);
		frame.getContentPane().add(lblQuestions);
		
		lblReplies = new JLabel("Number of replies:");
		springLayout.putConstraint(SpringLayout.NORTH, lblReplies, 12, SpringLayout.SOUTH, lblQuestions);
		springLayout.putConstraint(SpringLayout.EAST, lblReplies, 0, SpringLayout.EAST, lblName);
		frame.getContentPane().add(lblReplies);
		
		JLabel lblDigitalSign = new JLabel("Digital Sign:");
		springLayout.putConstraint(SpringLayout.NORTH, lblDigitalSign, 18, SpringLayout.SOUTH, lblReplies);
		springLayout.putConstraint(SpringLayout.EAST, lblDigitalSign, 0, SpringLayout.EAST, lblName);
		frame.getContentPane().add(lblDigitalSign);
		
		lblSign = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblSign, 16, SpringLayout.SOUTH, lblDigitalSign);
		springLayout.putConstraint(SpringLayout.WEST, lblSign, 148, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(lblSign);
	}
	
	public void show(){
		this.frame.setVisible(true);
	}
	
	public void loadInfo(ChatManager cm){
		if (cm != null){
			this.lblName.setText("Name: "+cm.getSoulName());
			this.lblQuestions.setText("Number of questions: "+cm.getNumOfQuestions());
			this.lblReplies.setText("Number of replies: "+cm.getNumOfReplies());
			//TODO put MD5 into lblSign
		}
	}
}
