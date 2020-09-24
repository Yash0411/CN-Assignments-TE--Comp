import java.io.*; 
import java.net.*; 
import java.math.*;
import java.util.*;
  
public class Client 
{ 
    public static void main(String args[]) throws IOException 
    { 

        System.out.println("\n_________________ CLIENT SIDE ________________\n");

        
        InetAddress addr = InetAddress.getByName("LocalHost");
        Socket client = new Socket(addr,1500);

        // Reading data from socket
        BufferedInputStream din = new BufferedInputStream(client.getInputStream());
        DataOutputStream dout = new DataOutputStream(client.getOutputStream());

        // Get input form user
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter No of frames: ");
        int c = sc.nextInt();
        dout.write(c);
        System.out.print("\nEnter type of transmission(error-1, errorless-0): ");
        int choice = sc.nextInt();
        dout.write(choice);

        int check=0,i=0,j=0;
        boolean frames[] = new boolean[c];
        if(choice==0)  //No error
        {
            for(j=0;j<c;j++)
            {
                i = din.read();
                System.out.println("\nReceived Frame "+i);
                System.out.println("Sending Acknowledgement "+i);
                dout.write(i);
            }
        }
        else // With Error
        {
            while(true)
            {
                for(j=0;j<c;j++)
                {
                    i = din.read();
                    if(i!=255)
                    {
                        System.out.println("\nReceived Frame "+i);
                        System.out.println("Sending Acknowledgement "+i);
                        dout.write(i);
                        frames[j] = true;
                    }
                    else
                    {
                        System.out.println("Sending Negative Acknowledgement "+i);
                        dout.write(-1);
                    }
                }
                boolean end=true;
                for(boolean b:frames){
                    if(b==false)
                    {
                        end=false;
                        break;
                    }
                }
                if(end)break;
            }
        }
        din.close();
        dout.close();
        client.close();
    } 
} 