import java.net.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServidorMulticast {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        // Elegir sala
        System.out.println("Elige sala del servidor:");
        System.out.println("1 - Sala 1");
        System.out.println("2 - Sala 2");
        System.out.println("3 - Sala 3");
        System.out.print("Número de sala: ");
        int sala = sc.nextInt();
        sc.nextLine(); // limpiar buffer

        InetAddress group;
        int port;

        switch (sala) {
            case 1: group = InetAddress.getByName("230.0.0.1"); port = 4446; break;
            case 2: group = InetAddress.getByName("230.0.0.2"); port = 4447; break;
            case 3: group = InetAddress.getByName("230.0.0.3"); port = 4448; break;
            default: group = InetAddress.getByName("230.0.0.1"); port = 4446; break;
        }

        MulticastSocket socket = new MulticastSocket(port);
        NetworkInterface netIf = NetworkInterface.getByName("en0");
        socket.joinGroup(new InetSocketAddress(group, port), netIf);

        System.out.println("Servidor conectado a la sala " + sala);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Hilo para recibir mensajes
        Thread receptor = new Thread(() -> {
            try {
                byte[] buffer = new byte[1024];
                while (true) {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    String mensaje = new String(packet.getData(), 0, packet.getLength());
                    System.out.println(mensaje);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        receptor.start();

        // Hilo principal para enviar mensajes
        while (true) {
            String texto = sc.nextLine();
            String hora = LocalDateTime.now().format(formato);
            String mensaje = "[" + hora + "] Servidor: " + texto;

            byte[] buffer = mensaje.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
            socket.send(packet);
        }
    }
}
