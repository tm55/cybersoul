package database;

import java.sql.*;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import log.LogManager;

public class DBMsqlite implements DBManager {

	private Statement statement = null;

	@Override
	public boolean init(String default_reply) {
		default_reply = filter(default_reply);
		//Question Table: (ID, question)
		String question_table = "create table question (id integer primary key autoincrement, question text)";
		//Reply Table: (ID, expr, reply, question_id)
		//expr it's a boolean and is true if reply isn't a string but an expression to evaluate.
		String reply_table = "create table reply (id integer  primary key autoincrement, expr boolean, reply text, question_id integer)";
		try {
			this.statement.executeUpdate(question_table);
			this.statement.executeUpdate(reply_table);
			//Insert default reply
			this.statement.executeUpdate("insert into question values(0, '^.*$')");
			this.statement.executeUpdate("insert into reply values(0,0,'"+default_reply+"',0)");
			return true;
		} catch (SQLException e) {
			LogManager.getLogger().writeErr(e.getMessage());
			return false;
		}
		
	}
	
	//Format the string in the right way
	private String filter(String s){
		s = s.replace("'", "&#39;");
		return s;
	}
	
	@Override
	public int addQuestion(String question) {
		question = filter(question);
		question = question.toLowerCase();
		int qID = -1;
		try{
			//Check if already exists the question
			qID = getExactQuestionID(question);
			if (qID >= 0)
				return qID;
			else {
				this.statement.executeUpdate("insert into question values(NULL, '"+question+"')");
				return getExactQuestionID(question);
			}
		}catch (SQLException e){
			LogManager.getLogger().writeErr(e.getMessage());
		}
		return -1;
	}
	
	//Get the ID for the exact question (without using regular expression)
	private int getExactQuestionID(String question) {
		question = filter(question);
		question = question.toLowerCase();
		try{
			ResultSet rs = this.statement.executeQuery("select * from question where question = '"+question+"'");
			if (rs.next()) {
				return rs.getInt("ID");
			}
		}catch (SQLException e){
			LogManager.getLogger().writeErr(e.getMessage());
		}
		return -1;
	}

	@Override
	public boolean addReply(int questionID, String reply, boolean isExpr) {
		reply = filter(reply);
		int value = 0; //boolean in sqlite is 0 (false) or 1 (true)
		if (isExpr)
			value = 1;
		try {
			this.statement.executeUpdate("insert into reply values(NULL,"+value+",'"+reply+"',"+questionID+")");
			return true;
		} catch (SQLException e) {
			LogManager.getLogger().writeErr(e.getMessage());
			return false;
		}
	}

	@Override
	public int getQuestionID(String question) {
		question = filter(question);
		question = question.toLowerCase();
		try{
			ResultSet rs = this.statement.executeQuery("select * from question order by ID desc");
			while (rs.next()) {
				String q = rs.getString("question"); //q is a regular expression
				if (question.matches(q)) //check if question is matched by q
					return rs.getInt("ID");
			}
		}catch (SQLException e){
			LogManager.getLogger().writeErr(e.getMessage());
		}
		return -1;
	}

	@Override
	public String getReply(int questionID) {
		try{
			ResultSet rs = this.statement.executeQuery("select * from reply where question_id = "+questionID);
			//SQLite only supports TYPE_FORWARD_ONLY cursors
			RepliesManager rm = new RepliesManager();
			//Get random reply
			while(rs.next()){
				rm.addReply(rs.getString("reply"), rs.getBoolean("expr"));
			}
			rm.selectRandomReply();
			boolean isExpr = rm.getSelectedReplyExpr();
			String reply = rm.getSelectedReplyText();
			if (isExpr) {
				//Reference: https://blogs.oracle.com/sundararajan/entry/dynamic_source_code_in_java
			    ScriptEngineManager mgr = new ScriptEngineManager();
			    ScriptEngine engine = mgr.getEngineByName("JavaScript");
			    Runnable r = (Runnable) (engine.eval(reply));
			    r.run();
			    return (String) engine.get("reply");
			} else {
				return reply;
			}
		}catch (SQLException e){
			LogManager.getLogger().writeErr(e.getMessage());
		} catch (ScriptException e) {
			LogManager.getLogger().writeErr(e.getMessage());
		}
		return null;
	}

	@Override
	public void closeDB() {
		if (this.statement == null)
			return;
		
		try {
			Connection c = this.statement.getConnection();
			if (!c.isClosed())
				c.close();
		} catch (SQLException e) {
			LogManager.getLogger().writeErr(e.getMessage());
		}
	}

	/* Initialize connection to the database */
	public boolean connectToDB(String dbname) {
		if (this.statement == null){
			Connection connection = null;
			try
			{
				// load the sqlite-JDBC driver
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:"+dbname);
				this.statement = connection.createStatement();
				this.statement.setQueryTimeout(60);
				return true;
			}catch(SQLException e){
				// if the error message is "out of memory", 
				// it probably means no database file is found
				LogManager.getLogger().writeErr(e.getMessage());
				closeDB();
			}catch(ClassNotFoundException e){
				LogManager.getLogger().writeErr(e.getMessage());
			}
		}
		return false;
	}

	@Override
	public int getNumOfQuestions() {
		try{
			ResultSet rs = this.statement.executeQuery("select * from question");
			int count = 0;
			while (rs.next()) { count++; }
			return count;
		}catch (SQLException e){
			LogManager.getLogger().writeErr(e.getMessage());
		}
		return -1;
	}

	@Override
	public int getNumOfReplies() {
		try{
			ResultSet rs = this.statement.executeQuery("select * from reply");
			int count = 0;
			while (rs.next()) { count++; }
			return count;
		}catch (SQLException e){
			LogManager.getLogger().writeErr(e.getMessage());
		}
		return -1;
	}

	@Override
	public String execQuery(String query) {
		try {
			if (query.startsWith("select") || query.startsWith("SELECT")){
				ResultSet rs = this.statement.executeQuery(query);
				String result = "";
				int cols = rs.getMetaData().getColumnCount();
				int i;
				for (i=1;i<=cols;i++)
					result+=rs.getMetaData().getColumnName(i)+"\t";
				result+="\n";
				while(rs.next()){
					for (i=1;i<=cols;i++)
						result+=rs.getObject(i).toString()+"\t";
					result+="\n";
				}
				return result;
			}else{
				this.statement.executeUpdate(query);
				return "OK";	
			}
		} catch (SQLException e) {
			LogManager.getLogger().writeErr(e.getMessage());
			return "Error: "+e.getMessage();
		}
	}

}
