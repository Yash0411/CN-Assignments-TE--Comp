import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

class client {
    public static void main(String args[]) throws IOException {

        Socket cl = new Socket ("localhost", 1500);

        DataInputStream din = new DataInputStream(cl.getInputStream());
        DataOutputStream dout = new DataOutputStream(cl.getOutputStream());
        Scanner scr = new Scanner(System.in);
        
        System.out.println(".....Client.....");
        System.out.println("Connected to server");
        System.out.println("\nEnter the number of frames to be requested to the server : ");

        int sf = scr.nextInt();

        dout.write(sf);
        dout.flush();

        System.out.println("\nEnter the type of trans. Error = 1 ; No-Error = 0\n =>");
        int choice = scr.nextInt();
        dout.write(choice);

        int check = 0;
        int i = 0;
        int j = 0;

        if(choice == 0) {
            for(j = 0; j < sf; j++) {
                i = din.read();
                System.out.println("\nRecieved Frame no. : "+i);
                System.out.println("Sending Acknowlwdgement for frame no. : "+i);
                dout.write(i);
                dout.flush();
            }
            dout.flush();
        }
        else{
            for(j = 0; j < sf; j++) {
                i = din.read();

                if(check == i) {
                    System.out.println("\nRecieved Frame no. : "+i);
                    System.out.println("Sending Acknowlwdgement for frame no. : "+i);
                    dout.write(i);
                    check++;
                }
                else{
                    j--;
                    System.out.println("\nDiscarded Frame no. : "+i);
                    System.out.println("Sending Negative acknowledgement");
                    dout.write(-1);
                }
                dout.flush();
            }
        }


        din.close();
        dout.close();
        System.out.println("Quitting....");

    }
}