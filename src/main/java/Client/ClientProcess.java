package Client;

import Client.messages_types.ClientRoleMessage;
import Client.messages_types.Message;
import Server.ClientRole;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientProcess {

    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private static final Scanner scanner = new Scanner(System.in);


    ClientProcess(Socket socket) {
        try {
            this.socket = socket;
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startClientProcess(Socket socket) {

        ClientProcess clientProcess = new ClientProcess(socket);
        boolean isWorking = true;

        while (isWorking) {
            // клиент отправляет сообщение о роли клиента
            System.out.println("Введите роль клиента: ");

            ClientRole clientRole = receiveClientRole();
            Message clientRoleMessage = new ClientRoleMessage(Flag.CLIENT_ROLE, clientRole);
            clientProcess.sendObject(clientRoleMessage);

            //клиент видит
        }
    }

    private static ClientRole receiveClientRole() {

        String clientRoleString = scanner.next();
        clientRoleString = clientRoleString.toUpperCase();

        return ClientRole.valueOf(clientRoleString);
    }

    public void sendObject(Object object) {
        try {
            outputStream.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object receiveObject() {
        try {
            return inputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeConnection() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


//        Message clientRole = new ClientRoleMessage()
// создание соощения с клиент ролью
// отправка сообщения на сервер

//выбор пункта из меню. как? сканер или сообщение с строкой? и то и то считать записать отправить

// сервер получает сообщение, смотрит что надо, свичается к нужному меню

// выбор просмотра студентов = 1
// сервер отправляет лист со студентами клиенту и тут он отображается
// или сам сервер пишет его в клиент - консоль нужным образом

//выбор добавить студента = 2
// начинает работать метод создания нового studentFile, после отправляется его всообщении как обьект
//или получает данные построчно от клиента в сервер и на стороне сервера создается studentFile

//выбор изменить файл студента = 3
//показывает снова всех студентов, так как необяз что клиент просил показать их ранее
//клиент вводит номер студента, которого хочет изменить, сообщ отправляется на сервер (если построчное создание)
// или в клиентс программе работаем с листом и вызываем метод изменить студента и сетаем новые данные в нужн объекь
//в конце сетаем объект или отправляем обновленный объект - лист студентов (а если их много?)
// лучше лист не отправлять ибо студаков может быть много, только отображать и отпралять одного, или опять же сетать
//построчно.


//на каждом этапе пункт назад или выход