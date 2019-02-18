/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionudp;

import java.io.IOException;
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

        try {
            InetAddress direccionServidor = InetAddress.getByName("localhost");
            DatagramSocket socketUDP = new DatagramSocket();
            String mensaje = "Hola desde el Cliente";
            buffer = mensaje.getBytes();

            //ENVIO solicitud al cliente
            DatagramPacket solicitud = new DatagramPacket(buffer, buffer.length, direccionServidor, PUERTO_SERVIDOR);
            System.out.println("Envio datagrama");
            socketUDP.send(solicitud);

            
            //RECIBO peticion o mensaje desde el server
            DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
            System.out.println("Recibo peticion");
            socketUDP.receive(peticion);
            mensaje = new String(peticion.getData());
            System.out.println(mensaje);

            socketUDP.close();

        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
