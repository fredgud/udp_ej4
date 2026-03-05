package simple_udp2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedHashMap;
import java.util.Map;

public class Receiver {

    public Receiver() throws Exception {

        DatagramSocket socket = new DatagramSocket(2020);
        System.out.println("Receiver running...");

        byte[] buffer = new byte[1500];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        socket.receive(packet);

        String message = new String(
                packet.getData(),
                0,
                packet.getLength()
        );

        System.out.println("Received: " + message);

        // ---- CONTAR CARACTERES ----
        Map<Character, Integer> counter = new LinkedHashMap<>();

        for (char c : message.toCharArray()) {
            counter.put(c, counter.getOrDefault(c, 0) + 1);
        }

        StringBuilder response = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : counter.entrySet()) {
            response.append(entry.getKey())
                    .append(":")
                    .append(entry.getValue())
                    .append(", ");
        }

        String finalMessage = response.substring(0, response.length() - 2);
        // ----------------------------

        InetAddress senderAddress = packet.getAddress();
        int senderPort = packet.getPort();

        buffer = finalMessage.getBytes();
        packet = new DatagramPacket(buffer, buffer.length, senderAddress, senderPort);

        socket.send(packet);
        System.out.println("Sent: " + finalMessage);

        socket.close();
    }

    public static void main(String[] args) {
        try {
            new Receiver();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}