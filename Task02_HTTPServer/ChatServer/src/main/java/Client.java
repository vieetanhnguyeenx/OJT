import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String myText = "";
        String serverText = "";
        Scanner sc1 = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = sc1.nextLine();
        Socket s = new Socket("localhost", 8888);
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());

        dout.writeUTF(name);

        while (true) {
            String s1 = din.readUTF();
            System.out.println("Server: " + s1);
        }


    }
}
