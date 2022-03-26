import javax.swing.*;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientGUI {
    private static ChatClient aChatClient;
    public static String userName = "Anonymous";

    // main window
    public static JFrame mainWindow = new JFrame();
    private static JButton buttonAbout = new JButton();
    private static JButton buttonConnect = new JButton();
    private static JButton buttonDisconnect = new JButton();
    private static JButton buttonSend = new JButton();
    private static JLabel labelMessage = new JLabel("Message : ");
    public static JTextField textFieldMessage = new JTextField(20);
    private static JLabel labelConversation = new JLabel();
    public static JTextArea textAreaConversation = new JTextArea();
    public static JScrollPane scrollPaneCHAT = new JScrollPane();
    private static JLabel userOnlineLABEL = new JLabel(); // lonline
    public static JList jListOnline = new JList(); // jlonline
    static JScrollPane scrollPaneOnline = new JScrollPane();
    private static JLabel labelLoggedInAs = new JLabel();
    private static JLabel labelLoggedInasBox = new JLabel();

    // login window
    public static JFrame loginWindow = new JFrame();
    public static JTextField textFieldUsernameBox = new JTextField(20);
    private static JButton buttonEnter = new JButton("Enter");
    private static JLabel labelEnterUsername = new JLabel("Enter username : ");
    private static JPanel panelLogin = new JPanel();


    public static void main(String[] args) {
        buildMainWindow();
        initialize();

    }

    public static void connect() {
        try {

            Socket socket = new Socket("localhost", 1234);
            aChatClient = new ChatClient(socket);

            // adds the name to online list
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(userName);
            textAreaConversation.setText("Welcome to the chat " + userName + " \n");
            textAreaConversation.append("You are connected to host  : " + 1234 + " \n");

            printWriter.flush();
            Thread thread = new Thread(aChatClient);
            thread.start();

        } catch (Exception X) {
            System.out.println(X);
            JOptionPane.showMessageDialog(null, " Server does not respond");
            System.exit(0);
        }
    }

    public static void initialize() {
        buttonSend.setEnabled(false);
        buttonDisconnect.setEnabled(false);

        buttonConnect.setEnabled(true);
    }

    public static void buildLoginWindow() {
        loginWindow.setTitle("What is your name?");
        loginWindow.setSize(300, 150);
        loginWindow.setLocation(550, 300);
        loginWindow.setResizable(false);
        panelLogin = new JPanel();
        panelLogin.add(labelEnterUsername);
        panelLogin.add(textFieldUsernameBox);
        panelLogin.add(buttonEnter);
        loginWindow.add(panelLogin);
        loginAction();
        loginWindow.setVisible(true);
    }

    public static void buildMainWindow() {
        mainWindow.setTitle(userName + "'s chat box");
        mainWindow.setSize(550, 650);
        mainWindow.setLocation(500, 250);
        mainWindow.setResizable(false);
        configureMainWindow();
        mainWindowAction();
        mainWindow.setVisible(true);
    }

    public static void configureMainWindow() {
        mainWindow.setBackground(new java.awt.Color(255, 255, 255));
        mainWindow.setSize(700, 620);
        mainWindow.getContentPane().setLayout(null);


        buttonConnect.setBackground(new java.awt.Color(46, 46, 255));
        buttonConnect.setForeground(new java.awt.Color(255, 255, 255));
        buttonConnect.setText("CONNECT");
        mainWindow.getContentPane().add(buttonConnect);
        buttonConnect.setBounds(500, 310, 150, 35);

        buttonDisconnect.setBackground(new java.awt.Color(46, 46, 255));
        buttonDisconnect.setForeground(new java.awt.Color(255, 255, 255));
        buttonDisconnect.setText("DISCONNECT");
        mainWindow.getContentPane().add(buttonDisconnect);
        buttonDisconnect.setBounds(500, 355, 150, 35);

        buttonAbout.setBackground(new java.awt.Color(46, 46, 255));
        buttonAbout.setForeground(new java.awt.Color(255, 255, 255));
        buttonAbout.setText("ABOUT");
        mainWindow.getContentPane().add(buttonAbout);
        buttonAbout.setBounds(500, 400, 150, 35);


        buttonSend.setBackground(new java.awt.Color(46, 46, 255));
        buttonSend.setForeground(new java.awt.Color(255, 255, 255));
        buttonSend.setText("SEND");
        mainWindow.getContentPane().add(buttonSend);
        buttonSend.setBounds(500, 470, 150, 70);

        labelMessage.setText("Message");
        mainWindow.getContentPane().add(labelMessage);
        labelMessage.setBounds(200, 450, 70, 20);

        textFieldMessage.setForeground(new java.awt.Color(46, 46, 255));
        textFieldMessage.requestFocus();
        mainWindow.getContentPane().add(textFieldMessage);
        textFieldMessage.setBounds(25, 470, 450, 70);

        labelConversation.setHorizontalAlignment(SwingConstants.CENTER);
        labelConversation.setText("Chat logs");
        mainWindow.getContentPane().add(labelConversation);
        labelConversation.setBounds(150, 5, 150, 20);

        textAreaConversation.setColumns(20);
        textAreaConversation.setFont(new java.awt.Font("Tahoma", 8, 16));
        textAreaConversation.setForeground(new java.awt.Color(0, 0, 255));
        textAreaConversation.setLineWrap(true);
        textAreaConversation.setRows(5);
        textAreaConversation.setEditable(false);

        scrollPaneCHAT.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneCHAT.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneCHAT.setViewportView(textAreaConversation);
        mainWindow.getContentPane().add(scrollPaneCHAT);
        scrollPaneCHAT.setBounds(25, 35, 450, 400);


        userOnlineLABEL.setHorizontalAlignment(SwingConstants.CENTER);
        userOnlineLABEL.setText("Users Online: ");
        userOnlineLABEL.setToolTipText("");
        mainWindow.getContentPane().add(userOnlineLABEL);
        userOnlineLABEL.setBounds(500, 65, 135, 20);


        jListOnline.setForeground(new java.awt.Color(0, 0, 255));
        jListOnline.setBounds(500, 15, 135, 20);


        scrollPaneOnline.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneOnline.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneOnline.setViewportView(jListOnline);
        mainWindow.getContentPane().add(scrollPaneOnline);
        scrollPaneOnline.setBounds(500, 90, 150, 205);

        labelLoggedInAs.setText("Logged in as");
        mainWindow.getContentPane().add(labelLoggedInAs);
        labelLoggedInAs.setBounds(530, 5, 145, 20);

        labelLoggedInasBox.setHorizontalAlignment(SwingConstants.CENTER);
        labelLoggedInasBox.setFont(new java.awt.Font("Tahoma", 0, 16));
        labelLoggedInasBox.setForeground(new java.awt.Color(255, 0, 0));
        labelLoggedInasBox.setBorder(BorderFactory.createLineBorder(new java.awt.Color(56, 54, 54)));
        mainWindow.getContentPane().add(labelLoggedInasBox);
        labelLoggedInasBox.setBounds(500, 35, 150, 30);
    }

    public static void loginAction() {
        buttonEnter.addActionListener(
                e -> actionEnter()
        );
    }

    public static void actionEnter() {
        if (!textFieldUsernameBox.getText().equals("") &&
                (!ChatServer.onlineUsers.contains(textFieldUsernameBox.getText().trim()))) {
            userName = textFieldUsernameBox.getText().trim();
            labelLoggedInasBox.setText(userName);
            ChatServer.onlineUsers.add(userName);
            mainWindow.setTitle(userName + "'s Chat box");

            loginWindow.setVisible(false);
            buttonSend.setEnabled(true);
            buttonDisconnect.setEnabled(true);
            buttonConnect.setEnabled(false);
            connect();
        } else {
            JOptionPane.showMessageDialog(null, "Enter your name!");
        }
    }

    public static void mainWindowAction() {
        buttonSend.addActionListener(
                e -> actionButtonSend()
        );
        buttonConnect.addActionListener(
                e -> buildLoginWindow()
        );
        buttonDisconnect.addActionListener(
                e -> actionButtonDisconnect()
        );
        buttonAbout.addActionListener(
                e -> actionButtonAbout()
        );
    }

    public static void actionButtonSend() {
        if (!textFieldMessage.getText().equals("")) {
            aChatClient.send(textFieldMessage.getText());
            textFieldMessage.requestFocus();
        }
    }

    public static void actionButtonDisconnect() {
        try {
            aChatClient.disconnect();
        } catch (Exception Y) {
            Y.printStackTrace();
        }
    }

    public static void actionButtonAbout() {
        JOptionPane.showMessageDialog(null,
                "Advanced Programming CourseWork \n" +
                        " International team ™");
    }
}
