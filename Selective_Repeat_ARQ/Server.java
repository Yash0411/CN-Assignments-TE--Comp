import java.io.*; 
import java.net.*; 
import java.math.*;
import java.util.*;
  
public class Server
{ 
    public static void main(String[] args) throws IOException 
    { 
        
        System.out.println("\n_________________ SERVER SIDE ________________\n");

        
        InetAddress addr = InetAddress.getByName("LocalHost");
        ServerSocket ss = new ServerSocket(1500);
        Socket server = new Socket();
        server = ss.accept();

        // Reading data from socket
        BufferedInputStream din = new BufferedInputStream(server.getInputStream());
        DataOutputStream dout = new DataOutputStream(server.getOutputStream());

        // Reading form input stream
        int p = din.read();
        System.out.println("\n Number of frames to be recieved is : " + p);
        int pc = din.read();

        boolean frames[] = new boolean[p];
        if(pc==0) // No error
        {
            for(int i=0;i<p;i++)
            {
                System.out.println("\nSending Frame "+i);
                dout.write(i);
                System.out.println("Waiting for Acknowledgement...");
                try{Thread.sleep(7000);}
                catch(Exception e){}
                int a = din.read();
                System.out.println("Received Acknowledgement for frame "+i+" as "+a);
            }
        }
        else // With Error
        {
            for(int i=0;i<p;i++)
            {
                if(i==2)
                {
                    System.out.println("\nSending Frame "+i);
                    System.out.println("Waiting for Acknowledgement...");
                }
                else
                {
                    System.out.println("\nSending Frame "+i);
                    dout.write(i);
                    System.out.println("Waiting for Acknowledgement...");
                    try{Thread.sleep(7000);}
                    catch(Exception e){}
                    int a = din.read();
                    if(a!=255)
                    {
                        System.out.println("Received Acknowledgement for frame "+i+" as "+a);
                        frames[i] = true;
                    }
                }
            }
            
            for(int a=0;a<p;a++)
            {
                if(frames[a]==false)
                {
                    System.out.println("\nResending Frame "+a);
                    dout.write(a);
                    System.out.println("Waiting for Acknowledgement...");
                    try{Thread.sleep(7000);}
                    catch(Exception e){}
                    int b = din.read();
                    System.out.println("Received Acknowledgement for frame "+a+" as "+b);
                    frames[a]=true;
                }
            }
        }
        din.close();
        dout.close();
        ss.close();
        server.close();
    } 
} 
