package Client;

import java.io.*;
import java.net.*;

public class Main {

    public static void main(String[] args) {

        final String serverHost = "localhost";
        final int serverPort = 5000;

        try {
            Socket socket = new Socket(serverHost, serverPort);
            ClientProcess.startClientProcess(socket);






            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Чтение ответов от сервера
            String response;
            while ((response = reader.readLine()) != null) {
                System.out.println("Сервер: " + response);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//            // Примеры команд:
//            writer.println("просмотреть");
//            writer.println("добавить Иван 20 ivan@example.com");
//            writer.println("изменить Иван 20 new_email@example.com");
