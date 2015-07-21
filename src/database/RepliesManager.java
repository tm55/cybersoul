package database;

import java.util.ArrayList;
import java.util.Random;

//This is a support class for DBMsqlite class, because sqlite has too limitations
public class RepliesManager {

	private ArrayList<String> reptext;
	private ArrayList<Boolean> repexpr;
	
	private int selectedReply = 0;
	
	public RepliesManager() {
		this.reptext = new ArrayList<String>();
		this.repexpr = new ArrayList<Boolean>();
	}
	
	public void addReply(String text, boolean isExpr) {
		this.reptext.add(text);
		this.repexpr.add(isExpr);
	}
	
	public void selectRandomReply(){
		int size = this.reptext.size();
		if (size > 1) {
			Random r = new Random();
			this.selectedReply = r.nextInt(size);
		}else
			this.selectedReply = 0;
	}
	
	public String getSelectedReplyText() {
		return this.reptext.get(this.selectedReply);
	}
	
	public boolean getSelectedReplyExpr() {
		return this.repexpr.get(this.selectedReply).booleanValue();
	}
	
}
