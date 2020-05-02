package com.mperic.ssl;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.util.Scanner;

public class Client
{
    public static void main(String args[])
    {
        int port = 35786;
        String host = "localhost";
        System.setProperty("javax.net.ssl.trustStore","myTrustStore.jts");
        System.setProperty("javax.net.ssl.trustStorePassword","123456");
        try
        {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
            SSLSocket sslSocket = (SSLSocket)sslsocketfactory.createSocket(host, port);
            DataOutputStream outputStream = new DataOutputStream(sslSocket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(sslSocket.getInputStream());
            System.out.println(inputStream.readUTF());

            Scanner s = new Scanner(System.in);
            while (true)
            {
                System.out.println("- ");
                String messageToSend = s.nextLine();
                outputStream.writeUTF(messageToSend);
                System.err.println(inputStream.readUTF());
                if(messageToSend.equals("close"))
                {
                    break;
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}