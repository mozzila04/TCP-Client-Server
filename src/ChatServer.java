import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class ChatServer {
    public static ArrayList<Socket> connectionArray = new ArrayList<Socket>();
    public static ArrayList<String> onlineUsers = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
        //  ExecutorService pool = Executors.newFixedThreadPool(500);

        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server is online, waiting for connections ");

            while (true) {
                //   pool.execute(new ChatServerReturn(serverSocket.accept()));
                Socket socket = serverSocket.accept();
                connectionArray.add(socket);
                addUsername(socket);

                ChatServerReturn dialogue = new ChatServerReturn(socket);
                Thread X = new Thread(dialogue);
                X.start();
                // takes the last person that joined and prints it
                String lastJoined = onlineUsers.get(onlineUsers.size() - 1);
                System.out.println("User " + lastJoined + " connected from " + socket.getLocalAddress().getHostName());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addUsername(Socket thread) throws IOException {
        Scanner scanner = new Scanner(thread.getInputStream());
        String userName = scanner.nextLine();
        onlineUsers.add(userName);

        for (int i = 1; i <= ChatServer.connectionArray.size(); i++) {
            Socket temporarySocket = ChatServer.connectionArray.get(i - 1);
            PrintWriter printWriter = new PrintWriter(temporarySocket.getOutputStream());
            printWriter.println("#?!" + onlineUsers);
            printWriter.flush();
        }
    }
}
