/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionudp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) throws UnknownHostException {

        final int PUERTO_SERVIDOR = 2019;
        //array de Bytes
        byte[] buffer = new byte[1024];
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));

        try {
            InetAddress direccionServidor = InetAddress.getByName("localhost");
            DatagramSocket socketUDP = new DatagramSocket();
            System.out.print("\n Opciones de ingreso: numero1 +|-|/|* numero2\n Ejm:  1 * 1\n");
            System.out.print("\n Ingrese su operacion:");
            String enviar = entrada.readLine();
            buffer = enviar.getBytes();
            //String mensaje = "Hola desde el Cliente";
            //buffer = mensaje.getBytes();

            //ENVIO solicitud al cliente
            DatagramPacket solicitud = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO_SERVIDOR);
            System.out.println("Envio datagrama");
            socketUDP.send(solicitud);

            
            //RECIBO mensaje desde el server e imprimo
            DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
            socketUDP.receive(peticion);
            System.out.println("Recibo respues del server");
            String mensaje = new String(peticion.getData()); 
            System.out.println(mensaje);

            socketUDP.close();

        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
