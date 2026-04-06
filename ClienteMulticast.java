import java.net.*;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClienteMulticast {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        // Elegir sala
        System.out.println("Elige sala:");
        System.out.println("1 - Sala 1");
        System.out.println("2 - Sala 2");
        System.out.println("3 - Sala 3");
        System.out.print("Número de sala: ");
        int sala = sc.nextInt();
        sc.nextLine(); 

        InetAddress group;
        int port;
		
		//Menu de Sala 
        switch (sala) {
            case 1: group = InetAddress.getByName("230.0.0.1"); port = 4446; break;
            case 2: group = InetAddress.getByName("230.0.0.2"); port = 4447; break;
            case 3: group = InetAddress.getByName("230.0.0.3"); port = 4448; break;
            default: group = InetAddress.getByName("230.0.0.1"); port = 4446; break;
        }

        // Crear socket multicast
        MulticastSocket socket = new MulticastSocket(port);
        NetworkInterface netIf = NetworkInterface.getByName("en0");
        socket.joinGroup(new InetSocketAddress(group, port), netIf);
		
		//Solicitamos el nombre del cliente 
        System.out.print("Nombre del cliente: ");
        String cliente = sc.nextLine();
        System.out.println(cliente + " conectado a la sala " + sala);

		//Formato Hora
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
            //Indicamos la hora del mensaje 
            String hora = LocalDateTime.now().format(formato);
            String mensaje = "[" + hora + "] " + cliente + ": " + texto;

            byte[] buffer = mensaje.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);
            socket.send(packet);
        }
    }
}
