package simple_udp2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Sender {

    public Sender() throws Exception {

        DatagramSocket socket = new DatagramSocket();
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Enter text: ");
        String message = keyboard.nextLine();

        byte[] buffer = message.getBytes();

        DatagramPacket packet = new DatagramPacket(
                buffer,
                buffer.length,
                InetAddress.getByName("IP_DEL_RECEIVER"), // IP del Fedora clon
                2020
        );

        socket.send(packet);
        System.out.println("Sent: " + message);

        buffer = new byte[1500];
        packet = new DatagramPacket(buffer, buffer.length);

        socket.receive(packet);

        message = new String(
                packet.getData(),
                0,
                packet.getLength()
        );

        System.out.println("Received: " + message);

        socket.close();
        keyboard.close();
    }

    public static void main(String[] args) {
        try {
            new Sender();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}