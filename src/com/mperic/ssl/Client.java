package com.mperic.ssl;
import java.io.*;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.util.Scanner;

public class Client
{
    public static void main(String args[])
    {
        int port = 1818;
        String host = "localhost";
        System.setProperty("javax.net.ssl.trustStore","myTrustStore.jts");
        System.setProperty("javax.net.ssl.trustStorePassword","123456");
        try
        {
            SSLSocketFactory sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
            SSLSocket sslSocket = (SSLSocket)sslsocketfactory.createSocket(host, port);
            PrintWriter output = new PrintWriter(sslSocket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
            System.out.println(input.readLine());

            Scanner s = new Scanner(System.in);
            while (true)
            {
                System.out.println(":");
                String messageToSend = s.nextLine();
                output.println(messageToSend);
                System.err.println(input.readLine());
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