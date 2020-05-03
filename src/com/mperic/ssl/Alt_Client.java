package com.mperic.ssl;
import java.io.*;
import javax.net.ssl.*;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Scanner;

public class Alt_Client
{
    public static void main(String args[])
    {
        int port = 1818;
        String host = "localhost";
        SSLSocketFactory factory = null;
//        System.setProperty("javax.net.debug", "all");
//        System.setProperty("javax.net.ssl.trustStore","myTrustStore.jts");
//        System.setProperty("javax.net.ssl.trustStorePassword","123456");
        try
        {
            SSLContext ctx;
            KeyManagerFactory kmf;
            KeyStore ks;
            char[] passphrase = "123456".toCharArray(); // Next, work on encrypting the passphrase.

            ctx = SSLContext.getInstance("TLS");
            kmf = KeyManagerFactory.getInstance("SunX509");
            ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("myKeyStore.jks"), passphrase);
            kmf.init(ks, passphrase);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            factory = ctx.getSocketFactory();

            SSLSocket sslSocket = (SSLSocket) factory.createSocket(host, port);
            sslSocket.startHandshake();

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