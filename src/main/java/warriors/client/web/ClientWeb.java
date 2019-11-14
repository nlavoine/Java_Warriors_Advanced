package warriors.client.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.vavr.control.Option;
import io.vavr.jackson.datatype.VavrModule;
import org.reactivestreams.Publisher;
import ratpack.handling.Context;
import ratpack.server.RatpackServer;
import ratpack.sse.ServerSentEvents;
import warriors.contracts.*;
import warriors.engine.Warriors;
import warriors.serializers.HeroSerializer;
import warriors.serializers.MapSerializer;


public class ClientWeb {

    private WarriorsAPI warriors;

    public ClientWeb() {
        this.warriors = new Warriors();
    }

    public static void main(String[] args) throws Exception {

        ClientWeb client = new ClientWeb();


        RatpackServer.start(server -> server
                .handlers(chain -> chain
                        //.get("api", ctx -> ctx.render("Hello World"))
                        //.get("toto/:name", ctx -> ctx.render("Hello "+ ctx.getPathTokens().get("name") + "!"))

                        .all(ctx -> {
                            ctx.getResponse().getHeaders().add("Access-Control-Allow-Origin", "*");
                            ctx.getResponse().getHeaders().add("Access-Control-Allow-Headers", "*");
                            ctx.getResponse().getHeaders().add("content-type", "application/json");
                            ctx.next();

                        })

                        .get("heroes", ctx -> {

                            ctx.render(client.getAvailableHeroes());
                        })
                        .get("maps", ctx -> {
                            ctx.render(client.getAvailableMaps());
                        })
                        .path("games", ctx -> ctx
                                .byMethod(s -> s
                                        .get(() -> {
                                            ctx.render(client.getGamesFromApi(ctx));

                                        })
                                        .post(() -> {
                                            client.createGameFromApi(ctx);
                                        })
                                )
                        )

                        .path("games/:gameId", ctx -> ctx
                                .byMethod(s -> s
                                        .get(() -> {
                                            client.getGameFromApi(ctx);
                                        })
                                )
                        )
                        .post("games/:gameId/turns", ctx -> client.nextTurnFromApi(ctx))

                )
        );

    }






    private String getAvailableHeroes() throws JsonProcessingException {

        HeroSerializer heroSerializer = new HeroSerializer(Hero.class);
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("HeroSerializer", new Version(2, 1, 3, null, null, null));
        module.addSerializer(Hero.class, heroSerializer);
        mapper.registerModule(new VavrModule());
        mapper.registerModule(module);
        Iterable<Hero> ListHero = this.warriors.availableHeroes();
        return mapper.writeValueAsString(ListHero);
    }

    private String getAvailableMaps() throws JsonProcessingException {
        MapSerializer mapSerializer = new MapSerializer(Map.class);
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module =
                new SimpleModule("MapSerializer", new Version(2, 1, 3, null, null, null));
        module.addSerializer(Map.class, mapSerializer);
        mapper.registerModule(new VavrModule());
        mapper.registerModule(module);
        return mapper.writeValueAsString(this.warriors.availableMaps());
    }

    private String getGamesFromApi(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VavrModule());
        return mapper.writeValueAsString(this.warriors.listGames());


    }



    private void createGameFromApi(Context ctx) {

        ctx.parse(CreateGameAPI.class).then(game -> {

                    int indexHero = 0;
                    Hero selectedHero = null;
                    for (Hero hero : warriors.availableHeroes()) {
                        if (game.getHero() == indexHero) {
                            selectedHero = hero;
                            break;
                        }
                        indexHero++;
                    }

                    int indexMap = 0;
                    Map selectedMap = null;
                    for (Map map : this.warriors.availableMaps()) {
                        if (game.getMap() == indexMap) {
                            selectedMap = map;
                            break;
                        }
                        indexMap++;
                    }

                    GameState gameState = warriors.createGame(game.playerName, selectedHero, selectedMap);
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.registerModule(new VavrModule());
                    String JsonGamestate = mapper.writeValueAsString(gameState);
                    ctx.render(JsonGamestate);
                }
        );

    }

    private void getGameFromApi(Context ctx) throws JsonProcessingException {

        String gameId = ctx.getPathTokens().get("gameId");


        Publisher<GameState> publisher = warriors.observe(GameId.parse(gameId));


        ServerSentEvents serverSentEvents = ServerSentEvents.serverSentEvents(publisher,
                gameEvent -> gameEvent.data(gameToJson(gameEvent.getItem())));
        ctx.render(serverSentEvents);
    }

    private String gameToJson(GameState gameState) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VavrModule());
        return mapper.writeValueAsString(gameState);

    }

    private void nextTurnFromApi(Context ctx) throws JsonProcessingException {
        String gameId = ctx.getPathTokens().get("gameId");
        Option<GameState> gameState = this.warriors.nextTurn(GameId.parse(gameId));
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VavrModule());
        String JsonGamestate = mapper.writeValueAsString(gameState);
        ctx.render(JsonGamestate);
    }




}