import java.io.*; 
import java.net.*;
import java.util.*;

public class lab10ServerThread extends Thread{
	protected Socket socket       = null;
	protected PrintWriter out     = null;
	protected BufferedReader in   = null;

	//Constructor
	public lab10ServerThread(Socket socket){
		super();
		this.socket = socket;
		try 
		{
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void run(){
		out.println("Connected!");	

		boolean endOfSession = false;
		while(!endOfSession) 
		{
			endOfSession = processCommand();
		}
		try 
		{
			socket.close();
		} catch(IOException ex) 
		{
			ex.printStackTrace();
		}
	}

	protected boolean processCommand(){
		String message = null;
		try 
		{
			message = in.readLine();
		} catch (IOException ex) 
		{
			ex.printStackTrace();
			return true;
		}
		if (message == null) 
		{
			return true;
		}		
	
		return processCommand(message);
	}

	protected boolean processCommand(String message){
		out.println(message);
		return true;
	}
}