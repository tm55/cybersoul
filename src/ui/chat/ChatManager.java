package ui.chat;

import java.io.File;
import java.io.IOException;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import database.DBManager;
import database.DBMsqlite;
import log.LogManager;

public class ChatManager {

	private HTMLEditorKit kit;
	private HTMLDocument doc;
	private DBManager dbm = null;
	private String soulName = "Unknow";
	
	public ChatManager(JTextPane chat) {
		this.kit = new HTMLEditorKit();
	    doc = new HTMLDocument();
	    chat.setEditorKit(kit);
	    chat.setDocument(doc);
	    this.dbm = new DBMsqlite();
	}
	
	public void openSoul(File soul){
		if (!(this.dbm.connectToDB(soul.getAbsolutePath()))) {
			printOnChat("<b><font color=\"red\">Can't open digital soul file.</font><b>");
		}else
			this.soulName = soul.getName().substring(0, soul.getName().length()-5);
	}
	
	public String getSoulName() {
		return this.soulName;
	}
	
	public int getNumOfQuestions() {
		return this.dbm.getNumOfQuestions();
	}
	
	public int getNumOfReplies() {
		return this.dbm.getNumOfReplies();
	}
	
	public void closeSoul(){
		this.dbm.closeDB();
	}
	
	private boolean printOnChat(String s){
		try {
			this.kit.insertHTML(doc, doc.getLength(), s, 0, 0, null);
			return true;
		} catch (BadLocationException e) {
			LogManager.getLogger().writeErr(e.getMessage());
		} catch (IOException e) {
			LogManager.getLogger().writeErr(e.getMessage());
		}
		return false;
	}
	
	public void replyToQuestion(String question) {
		//Add the question to the chat
		printOnChat("<i>You:</i>");
		printOnChat(question+"<br><br>");
		//Add the reply
		int question_id = this.dbm.getQuestionID(question);
		String rep = this.dbm.getReply(question_id);
		if (rep!=""){
			printOnChat("<i>"+this.soulName+":</i>");
			printOnChat(rep+"<br><br>");
		}
	}

}
