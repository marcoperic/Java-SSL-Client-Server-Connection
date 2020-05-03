package com.mperic.ssl;
import java.io.*;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class Server
{
    public static void main(String args[])
    {
        int port = 1818;
        System.setProperty("javax.net.ssl.keyStore","myKeyStore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","123456");
        try
        {
            SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
            SSLServerSocket sslServerSocket = (SSLServerSocket)sslServerSocketfactory.createServerSocket(port);
//            sslServerSocket.setNeedClientAuth(true);
            System.out.println("Server is live.");
            SSLSocket sslSocket = (SSLSocket)sslServerSocket.accept();
            System.out.println("Connection established");
//            DataInputStream inputStream = new DataInputStream(sslSocket.getInputStream());
            BufferedReader input = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            PrintWriter output = new PrintWriter(sslSocket.getOutputStream(), true);
//            DataOutputStream outputStream = new DataOutputStream(sslSocket.getOutputStream());
            output.println("Handshake successful!");

            while(true)
            {
                String recivedMessage = input.readLine();
                System.out.println("User: " + recivedMessage);
                if(recivedMessage.equals("close"))
                {
                    output.println("Goodbye!");
                    output.close();
                    input.close();
                    sslSocket.close();
                    sslServerSocket.close();
                    break;
                }
                else
                {
                    output.println("Server: "+ recivedMessage);
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}