/*
 * Conexion UDP > se utilzan DATAGRAMAS
 * El cliente espera a la conexion con el cliente una vez conectado obtiene la direccion y puerto del cliente
 * Procede a enviar la informacion atravez de DATAGRAMAS
 */
package conexionudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Servidor {

    public static void main(String[] args) throws IOException {

        final int PUERTO = 2019;
        //array de Bytes
        byte[] buffer = new byte[1024];
        float resultado = 0;
        float numero1 = 0;
        float numero2 = 0;

        try {
            
            System.out.println("Servidor UDP iniciado");

            //crear el objeto sokect datagrama para transportar los datos
            DatagramSocket socketUDP = new DatagramSocket(PUERTO);

            while (true) {
                //RECEPCION PETICION DEL CLIENTE
                DatagramPacket peticion = new DatagramPacket(buffer, buffer.length);
                socketUDP.receive(peticion);
                System.out.println("Informacion del cliente recibida!");

                //guarda la peticion en variable mensaje e imprime el mensaje
                String mensaje = new String(peticion.getData());
                System.out.println(mensaje);

                //dentro el paquete datagram viaja la peticion e informacion del cliente
                //se obtiene el puerto y la direccion para enviar el mensaje
                int puertoCliente = peticion.getPort();
                InetAddress direccion = peticion.getAddress();
                
                //mensaje = "Hola desde el Servidor";
                //buffer = mensaje.getBytes();
                
                String linea [] = mensaje.split(" ");            
                numero1 = Float.valueOf(linea[0]);
                numero2 = Float.valueOf(linea[2]);
                
                //switch case que opera segun el caso
                switch (linea[1]){
                    case "+":
                        resultado = numero1 + numero2;
                        break;
                    case "-":
                        resultado = numero1 - numero2;
                        break;
                    case "*":
                        resultado = numero1 * numero2;
                        break;
                    case "/":
                        resultado = numero1 / numero2;
                        break;
                }
                

                //RESPUESTA DEL SERVIDOR AL CLIENTE
                //se crea un paquete con la informacion que se enviara al cliente con la direccion y puerto del cliente
                System.out.println("Envio la informacion al cliente! " + resultado);
                String enviar = Float.toString(resultado);
                buffer = enviar.getBytes();
                DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, direccion, puertoCliente);           
                socketUDP.send(respuesta);

            }

        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
