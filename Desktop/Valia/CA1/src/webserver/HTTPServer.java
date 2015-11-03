/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webserver;

import ChatServer.Server;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
//import webserver.HTTPServer.RequestForAFileHandler.OnlineHandler;

/**
 *
 * @author Valentina
 */
public class HTTPServer {
     static int port = 8080;
        static String ip = "localhost";
    static String publicFolder="public/";
    public static void main(String[] args) throws IOException {
        
        if(args.length>=2){
            ip=args[0];
            port=Integer.parseInt(args[1]);
        }
        if (args.length == 3) {
publicFolder=args[2];}
        InetSocketAddress i = new InetSocketAddress(ip, port);
        HttpServer server = HttpServer.create(i,0);
        server.createContext("/pages/public/online", new OnlineHandler());
      server.createContext("/pages/", new RequestForAFileHandler());
        server.setExecutor(null);
        server.start();
        Server.getInstance().start();
       // Server.stopServer();
        System.out.println("Started the server");
    }
            private static class RequestForAFileHandler implements HttpHandler {

        public RequestForAFileHandler() {
        }

        @Override
        public void handle(HttpExchange he) throws IOException {
      
      String requestedFile = he.getRequestURI().toString();
      //THEN WE EXTRACT THE FILE 
      String f = requestedFile.substring(requestedFile.lastIndexOf("/") + 1);
      //THEN WE ALLOW EXTENSIONS
      String extension = f.substring(f.lastIndexOf("."));
      String mime = "";
      switch (extension) {
        case ".pdf":
          mime = "application/pdf";
          break;
        case ".png":
          mime = "image/png";
          break;
        case ".html":
          mime = "text/html";
          break;
        case ".jar":
          mime = "application/java-archive";
          break;
            case ".docx":
          mime = "documents/text";
          break;
      }
      //FOLLOWING we create a new file to read derived from the path
      File file = new File(publicFolder + f);
      byte[] bytesToSend = new byte[(int) file.length()];
      String errorMsg = null;
      int responseCode = 200;
      try {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        bis.read(bytesToSend, 0, bytesToSend.length);
      } catch (IOException ie) {
        errorMsg = "<h1>404 Not Found</h1>No context found for request";
      }
      if (errorMsg == null) {
        Headers h = he.getResponseHeaders();
        h.set("Content-Type", mime);
      } else {
        responseCode = 404;
        bytesToSend = errorMsg.getBytes();

      }
      he.sendResponseHeaders(responseCode, bytesToSend.length);
      try (OutputStream os = he.getResponseBody()) {
        os.write(bytesToSend, 0, bytesToSend.length);
      }
        
    }

}
}
            
