import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class server {

    public static void main(String args[]) throws IOException {

        System.out.println("......Server.....");
        System.out.println("Waiting For connection");
        
        ServerSocket ss = new ServerSocket(1500);

        Socket ser = ss.accept();

        DataInputStream din = new DataInputStream(ser.getInputStream());
        DataOutputStream dout = new DataOutputStream(ser.getOutputStream());

        System.out.println("\nReceived request for sending frames");
        int sf = din.read();

        boolean f[] = new boolean[sf];

        int pc = din.read();
        System.out.println("\nSending ....");

        if( pc == 0 ) {
            for(int i=0; i < sf; i++) {
                System.out.println("\nSending frame no. : "+i);
                dout.write(i);
                dout.flush();
                System.out.println("Wating For Acknowledgement");

                try {
                    Thread.sleep(3000);
                }
                catch (Exception e){
                }

                int a = din.read();
                System.out.println("\nRecieved Acknowlwdgement Frame "+i+" as "+a);
            }
            dout.flush();
        } 
        else{

            for(int i=0;i < sf; i++) {
                if(i==2) {
                    System.out.println("\nSending Frame no. : "+i);
                }
                else{
                    System.out.println("\nSending Frame no. : "+i);
                    dout.write(i);
                    dout.flush();
                    System.out.println("Waiting For Acknowledgement");

                    try {
                    Thread.sleep(3000);
                    }
                    catch (Exception e){
                    }

                    int a = din.read();
                    if(a != 255 ) {
                        System.out.println("\nRecieved Acknowlwdgement Frame "+i+" as "+a);
                        f[i] = true;
                    }
                }
            }

            for(int i=0; i < sf; i++) {

                if(f[i] == false) {
                    System.out.println("\nResending Frame "+i);
                    dout.write(i);
                    dout.flush();
                    System.out.println("Waiting For Acknowledgement");

                    try {
                    Thread.sleep(3000);
                    }
                    catch (Exception e){
                    }

                    int a = din.read();
                    System.out.println("\nReceived Acknowledgement for frame no: " + i + " as " + a);
                    f[i] = true;
                }
            }

            dout.flush();
        }

        din.close();
        dout.close();
        ser.close();
        ss.close();
        System.out.println("Quiting");
    }
}