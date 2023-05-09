import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        Scanner sc1 = new Scanner(System.in);
        Socket s = ss.accept();
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        String name = din.readUTF();
        Scanner sc = new Scanner(s.getInputStream());
        System.out.println( name + " has joined");
        String myStr = "";
        String str = "";
        while (!myStr.trim().equals("/stop")) {
            if (sc1.hasNextLine()) {
                myStr = sc1.nextLine();
                dout.writeUTF(myStr);
            }
            System.out.println("next");
            str = din.readUTF();
            System.out.println(name + ": " + str);
            dout.flush();
        }
    }
}
