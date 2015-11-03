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
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Valentina
 */
class OnlineHandler implements HttpHandler {

    public OnlineHandler() {
    }

    @Override
    public void handle(HttpExchange he) throws IOException {
                    String response = "Welcome to my first fantastic server!";
            StringBuilder sb = new StringBuilder();
sb.append("<!DOCTYPE html>\n");
sb.append("<html>\n");
sb.append("<head>\n");
sb.append("<title>My fancy Web Site</title>\n");
sb.append("<meta charset='UTF-8'>\n");
sb.append("</head>\n");
sb.append("<body>\n");
sb.append("<h2>Number of Online users:").append(Server.outputNumber()).append("</h2>\n");
sb.append("</body>\n");
sb.append("</html>\n");
response = sb.toString();
Headers h = he.getResponseHeaders();
h.add("Content-Type", "text/html");

          he.getRequestHeaders();
            he.sendResponseHeaders(200, response.length());
            try(PrintWriter pw = new PrintWriter(he.getResponseBody())){
                pw.print(response);

    }
    
}
}
