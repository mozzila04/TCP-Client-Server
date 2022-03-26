import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ChatClient implements Runnable {
    Socket socket;
    Scanner scanner;
    PrintWriter printWriter;

    public ChatClient(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            try {
                scanner = new Scanner(socket.getInputStream());
                printWriter = new PrintWriter(socket.getOutputStream());
                printWriter.flush();
                checkStream();
            } finally {
                socket.close();
            }
        } catch (Exception X) {
            System.out.println(X);
        }
    }

    public void disconnect() throws IOException {

        printWriter.println(ChatClientGUI.userName + " has disconnected from the chat");
        printWriter.flush();
        JOptionPane.showMessageDialog(null, "You disconnected from the server");
       //  need a method that will remove the userName that disconnected from the Jlistonline
        // ChatClientGUI.jListOnline.remove();
        delete_user();
        ChatClientGUI.mainWindow.dispose();
    }

    public static void delete_user() {
        // doesn't work so far
        // here it revalidates firstly the area
        ChatClientGUI.jListOnline.revalidate();
        ChatClientGUI.jListOnline.repaint();
        ChatClientGUI.scrollPaneCHAT.revalidate();
        ChatClientGUI.scrollPaneCHAT.repaint();
    }


    public void checkStream() {
        while (true) {
            receive();
        }
    }

    public void receive() {
        if (scanner.hasNext()) {
            String message = scanner.nextLine();
            if (message.contains("#?!")) {
                String temp = message.substring(3);
                temp = temp.replace("[", "");
                temp = temp.replace("]", "");

                String[] onlineUsers = temp.split(", ");
                ChatClientGUI.jListOnline.setListData(onlineUsers);
            } else {
                ChatClientGUI.textAreaConversation.append(message + "\n");
            }
        }
    }

    public void send(String message) {


        DateTimeFormatter hhmm = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.now();

        printWriter.println(time.format(hhmm) + "  " + ChatClientGUI.userName + ": " + message);
        printWriter.flush();
        ChatClientGUI.textFieldMessage.setText("");
    }
}
