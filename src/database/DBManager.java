package database;


public interface DBManager {
	
	public boolean connectToDB(String dbname); //Create and open the database
	public void closeDB(); //Close the database
	public boolean init(String default_reply); //Initialize the database
	public int addQuestion(String question); //return question ID
	public boolean addReply(int questionID, String reply, boolean isExpr);
	public int getQuestionID(String question);
	public String getReply(int questionID);
	public int getNumOfQuestions();
	public int getNumOfReplies();
	public String execQuery(String query);
}
