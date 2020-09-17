import java.net.*; //Provides the classes for implementing networking applications.

import java.io.*; //used to process the input and produce the output


public class Client

{


public static void main(String args[])throws Exception

	{
	
		Socket client=new Socket("localhost",3333); //Creating client Socket
		
		String str="";
		
		
		DataInputStream din=new DataInputStream(client.getInputStream());
		
		DataOutputStream dout=new DataOutputStream(client.getOutputStream());
		
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String res = null;
		while(true)
		{
			System.out.println("Client:");
			
			str=br.readLine();
			
			dout.writeUTF(str);
			
			System.out.println("Waiting for Server:");
			try
			{
			 res =din.readUTF();
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			System.out.println("Server:"+res);
			
			if(res.equals("exit"))
			{
				break;
			}
		}
		
		
		
		dout.flush();
		
		dout.close();
		
		client.close();
	
	}

}
