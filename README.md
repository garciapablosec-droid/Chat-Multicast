# Chat-Multicast

Este proyecto implementa un sistema de chat en tiempo real usando Multicast UDP en Java. Permite que múltiples clientes se conecten a “salas” específicas y envíen mensajes que son recibidos por todos los participantes de la sala, incluido el servidor.

## Funcionalidades
Selección de sala por cliente y servidor (Sala 1, 2 o 3)
Comunicación en tiempo real usando Multicast UDP
Visualización de la hora en cada mensaje
Soporte para múltiples clientes conectados a la misma sala
Hilos separados para recepción y envío de mensajes

## Tecnologías utilizadas

UDP Multicast – Comunicación de red en grupo

java.net.MulticastSocket – Para enviar y recibir paquetes multicast

java.time.LocalDateTime – Para mostrar hora de cada mensaje

java.util.Scanner – Entrada de usuario desde consola

## Instalación y ejecución
Clonar o descargar el proyecto en tu máquina.

### Compilar el proyecto con tu IDE o desde consola:
javac ClienteMulticast.java
javac ServidorMulticast.java

Ejecutar el servidor y seleccionar la sala deseada:

java ServidorMulticast
Ejecutar los clientes en diferentes terminales o equipos:

java ClienteMulticast
Cada cliente selecciona la misma sala que el servidor para recibir y enviar mensajes.
### Uso
1.El servidor inicia y se conecta a una sala específica.
2.Cada cliente elige la misma sala y proporciona un nombre de usuario.
3.Los mensajes enviados por cualquier cliente son recibidos por todos los participantes de la sala, incluido el servidor.
4.Cada mensaje muestra la hora local y el nombre del remitente, por ejemplo:

[15:42:10] Alice: Hola a todos
[15:42:12] Servidor: Bienvenida, Alice
