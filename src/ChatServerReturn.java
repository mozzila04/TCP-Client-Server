import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ChatServerReturn implements Runnable {

    Socket socket;
    String messageServ = "";

    public ChatServerReturn(Socket socket1) {
        this.socket = socket1;
    }

    public void checkConnection() throws IOException {
        if (!socket.isConnected()) {
            for (int i = 1; i<=ChatServer.connectionArray.size();
            i++){
                if(ChatServer.connectionArray.get(i)==socket){
                    ChatServer.connectionArray.remove(i);
                }
            }
            for(int i =1; i<= ChatServer.connectionArray.size(); i++){
                Socket temporarySocket = ChatServer.connectionArray.get(i-1);
                PrintWriter temporarySocketOut = new PrintWriter(temporarySocket.getOutputStream());
                temporarySocketOut.println(temporarySocket.getLocalAddress().getHostName() + " disconnected");
                temporarySocketOut.flush();
                // show that a user has disconnected at server
                System.out.println(temporarySocket.getLocalAddress().getHostName() + " disconnected");
            }
        }
    }
    @Override
    public void run(){
        try{
            try{
                Scanner input = new Scanner(socket.getInputStream());
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                while (true) {
                    checkConnection();

                    if (!input.hasNext()) {
                        return;
                    }
                    messageServ = input.nextLine();
                    System.out.println(messageServ);
                    for (int i = 1; i<= ChatServer.connectionArray.size(); i++){
                        Socket tempSocket = ChatServer.connectionArray.get(i-1);
                        PrintWriter tempOut = new PrintWriter(tempSocket.getOutputStream());
                        tempOut.println(messageServ);
                        tempOut.flush();
                        // System.out.println("Sent to : " + tempSocket.getLocalAddress().getHostName());
                    }
                }
            } finally {
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
