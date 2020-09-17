import java.net.*; //Provides the classes for implementing networking applications.

import java.io.*; //used to process the input and produce the output

public class Server

{

	public static void main(String args[])throws Exception
	{
	
		ServerSocket ss=new ServerSocket(3333);
		System.out.println("Server's Up");
		Socket s=ss.accept();
		
		String str="";
		
		
		DataInputStream din=new DataInputStream(s.getInputStream());
		
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());
		
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		while(true)
		{
			
			try
			{
				str=din.readUTF();
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			
			
			System.out.println("client says: "+str);
			
			if(str.equals("exit"))
				break;
			
			System.out.println("Server:");
			
			str=br.readLine();
			
			dout.writeUTF(str);
		}
		
		
		
		din.close();
		
		s.close();
		
		ss.close();
	
	}

}