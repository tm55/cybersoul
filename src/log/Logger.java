package log;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class Logger {

	private PrintWriter writer;
	
	public Logger(String pathname){
		try {
			this.writer = new PrintWriter(pathname, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			//e.printStackTrace();
		}
	}
	
	private void write(String text){
		if (this.writer != null)
			this.writer.println(text);
	}
	
	public void writeInfo(String text){
		System.out.println("INFO: "+text);
		write("INFO: "+text);
	}
	
	public void writeWarn(String text){
		System.err.println("WARN: "+text);
		write("WARN: "+text);
	}
	
	public void writeErr(String text){
		System.err.println("ERROR: "+text);
		write("ERROR: "+text);
	}
	
	public void close(){
		this.writer.close();
	}
}
