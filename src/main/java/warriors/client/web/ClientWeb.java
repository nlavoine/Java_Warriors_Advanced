package warriors.client.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.vavr.jackson.datatype.VavrModule;
import warriors.contracts.WarriorsAPI;
import warriors.engine.Warriors;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Scanner;


public class ClientWeb {


    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        Web web = new Web(sc);



        int serverPort = 8000;
        WarriorsAPI warriors = new Warriors();

        ObjectMapper mapper = new ObjectMapper().registerModule(new VavrModule());
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);



        /////////////////////////////////////////////////////////////////////////////
        //ROUTER

        server.createContext("/api/heroes", (exchange -> {
            String respText = mapper.writeValueAsString(warriors.availableHeroes());
            doResponse("GET", exchange, respText);
        }));

        //-------------------------------------------------------------------------//
        server.createContext("/api/maps", (exchange -> {
            String respText = mapper.writeValueAsString(warriors.availableMaps());
            doResponse("GET", exchange, respText);
        }));


        /////////////////////////////////////////////////////////////////////////////


        server.setExecutor(null); // creates a default executor
        server.start();
    }

    private static void doResponse(String Method, HttpExchange exchange, String respText) throws IOException {
        if (Method.equals(exchange.getRequestMethod())) {
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        } else {
            exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
        }
        exchange.close();
    }


}