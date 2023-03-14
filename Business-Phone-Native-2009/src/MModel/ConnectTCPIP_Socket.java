package MModel;

import java.io.InputStream;
import java.io.IOException;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.Connector;
import java.io.OutputStream;

/**
 * <p>Title: Mobile Extesion</p>
 *
 * <p>Description: All PBX Include</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: mobisma ab</p>
 *
 * @author Peter Albertsson
 * @version 2.0
 */
public class ConnectTCPIP_Socket {

    // Portnummer för TCP/IP connection
    private static String url = "socket://127.0.0.1:8100";

    public String
    checkAlert,
    ResponceMessage,
    request; // TCP/IP connection

    /* Konstruktorn börjar här */
    public ConnectTCPIP_Socket() {

        /*TCP_IP_SOCKET*/
        this.ResponceMessage = "";

    }

    public void sendLCloseSRV(){

        System.out.println("Stänger SRV servern");

        String L = "l,";
        sendRequest(L);

    }
    public void sendRequest(String message) {

        this.request = message;
        new Thread() {
            public void run() {
                sendMessage();
            }
        }.start();
    }
    public void sendMessage() {
        try {

            StreamConnection conn = (StreamConnection) Connector.open(url);
            OutputStream out = conn.openOutputStream();
            byte[] buf = request.getBytes();
            out.write(buf, 0, buf.length);
            out.flush();
            out.close();


            byte[] data = new byte[256];
            InputStream in = conn.openInputStream();
            int actualLength = in.read(data);
            String response = new String(data, 0, actualLength);
            in.close();
            conn.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

}
