package log;

public class LogManager {
	
	//TODO: Add Windows path
	private static final String logfile = "/var/log/cybersoul.log"; //UNIX systems
		
	private static Logger logger;
	
	public static Logger getLogger() {
		if (logger != null)
			return logger;
		else {
			logger = new Logger(logfile);
			return logger;
		}
	}
}
