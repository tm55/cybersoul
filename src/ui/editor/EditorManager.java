package ui.editor;

import java.io.File;

import database.DBManager;
import database.DBMsqlite;

public class EditorManager {
	
	private DBManager dbm = null;
	private int questionID;
	
	public EditorManager() {
		this.dbm = new DBMsqlite();
	}

	public boolean createSoul(String name, String default_reply){
		name += ".soul";
		if ((name != ".soul") && (!(new File(name)).exists())) {
			this.dbm.connectToDB(name);
			this.dbm.init(default_reply);
			return true;
		}
		return false;
	}
	
	public boolean openSoul(String name) {
		name += ".soul";
		if (this.dbm != null)
			this.dbm.closeDB(); //eventually close previous connection
		return this.dbm.connectToDB(name);
	}
	
	public boolean addQuestion(String question){
		this.questionID = this.dbm.addQuestion(question);
		if (this.questionID>=0)
			return true;
		else
			return false;
	}
	
	public boolean addReply(String reply, boolean isExpr){
		System.out.println("DEBUG: Add reply to "+ questionID);
		return this.dbm.addReply(this.questionID, reply, isExpr);
	}
	
	public void closeSoul(){
		this.dbm.closeDB();
	}
	
	public String execQuery(String query){
		return this.dbm.execQuery(query);
	}
	
}
