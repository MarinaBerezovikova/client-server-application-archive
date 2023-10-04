package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        final int portNumber = 5000;

        XMLParser xmlParser = new XMLParser();
        xmlParser.readStudentsFilesFolder();

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Сервер запущен и ожидает клиентов...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Подключен клиент: " + clientSocket);

                // Обработка клиента в отдельном потоке
                Thread clientThread = new ClientHandler(clientSocket);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        xmlParser.writeStudentsFilesToFolder();
    }
}

//        StudentFile studentFile = new StudentFile("Марина", "Маринина", 125, 4, "Самая лучшая в мире студентка.");
//        xmlParser.getStudentFileList().add(studentFile);

//        StudentFile studentFile1 = new StudentFile("Рита", "Кузьмина", 125, 3, "Самая лучшая в мире студентка.");
//        xmlParser.getStudentFileList().add(studentFile1);