package warriors.client.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import io.vavr.jackson.datatype.VavrModule;
import warriors.contracts.WarriorsAPI;
import warriors.engine.Warriors;

import java.io.*;
import java.net.InetSocketAddress;


public class ClientWeb {


    public static void main(String[] args) throws IOException {



        int serverPort = 8000;
        WarriorsAPI warriors = new Warriors();

        ObjectMapper mapper = new ObjectMapper().registerModule(new VavrModule());
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);


        /////////////////////////////////////////////////////////////////////////////
        //ROUTER

        //List of heroes
        server.createContext("/api/heroes", (exchange -> {
            String respText = mapper.writeValueAsString(warriors.availableHeroes());
            doResponse("GET", exchange, respText);
        }));

        //-------------------------------------------------------------------------//

        //List of maps
        server.createContext("/api/maps", (exchange -> {
            String respText = mapper.writeValueAsString(warriors.availableMaps());
            doResponse("GET", exchange, respText);
        }));

        //-------------------------------------------------------------------------//

        //Create game
        server.createContext("/api/games", (exchange -> {
            //String respText = mapper.writeValueAsString(game);
            InputStream input = exchange.getRequestBody();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            //reader.lines().forEach(System.out::println);

            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            //JSONObject json = new JSONObject(sb.toString());
            //Game game = mapper.readValue(reader.lines(), Game.class);


            //System.out.print(game);
            //doResponse("POST", exchange, respText);
            exchange.close();
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