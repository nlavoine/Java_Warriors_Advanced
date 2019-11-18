package warriors;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ratpack.http.client.ReceivedResponse;
import ratpack.test.embed.EmbeddedApp;
import warriors.client.web.ClientWeb;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;


public class WarriorsTest {

    private ClientWeb app = new ClientWeb();


    @Test
    void testHeroes() throws Exception {
        EmbeddedApp.fromHandlers(app.handlers())
                .test(testHttpClient -> {
                    String expected = "[{\"name\":\"Guerrier\",\"life\":5,\"attack\":10,\"index\":0},{\"name\":\"Magicien\",\"life\":3,\"attack\":6,\"index\":1}]";
                    assertEquals(expected, testHttpClient.get("heroes/")
                            .getBody()
                            .getText());
                });
    }

    @Test
    void testMaps() throws Exception {
        EmbeddedApp.fromHandlers(app.handlers())
                .test(testHttpClient -> {
                    String expected = "[{\"name\":\"Default Map\",\"numberOfCases\":64,\"index\":0}]";
                    assertEquals(expected, testHttpClient.get("maps/")
                            .getBody()
                            .getText());
                });
    }



    @Test
    void testCreateGames() throws Exception {
        byte[] newGameParams = "{\"hero\": 0,\"map\": 0, \"playerName\": \"foo\"}".getBytes(StandardCharsets.UTF_8);
        EmbeddedApp.fromHandlers(app.handlers())
                .test(testHttpClient -> {
                    String expected = "";
                    testHttpClient.requestSpec(requestSpec -> requestSpec.body(
                            body -> {
                                body.type("application/json")
                                        .bytes(newGameParams);

                            }
                    ));


                    assertNotSame(expected, testHttpClient.post("games/")
                            .getBody()
                            .getText());

                    assertEquals(expected, testHttpClient.get("games/")
                            .getBody()
                            .getText());
                });
    }

    @Test
    void testRetrieveGames() throws Exception {
        byte[] newGameParams = "{\"hero\": 0,\"map\": 0, \"playerName\": \"foo\"}".getBytes(StandardCharsets.UTF_8);
        EmbeddedApp.fromHandlers(app.handlers())
                .test(testHttpClient -> {
                    String expected = "";


                    ReceivedResponse response = testHttpClient.request("games/",
                    requestSpec -> requestSpec.body(
                            body -> {
                                body.type("application/json")
                                        .bytes(newGameParams);


                            }
                    ).post()
                    );

                    /*testHttpClient.requestSpec(requestSpec -> requestSpec.body(
                            body -> {
                                body.type("application/json")
                                        .bytes(newGameParams);

                            }
                    ));*/


                    //testHttpClient.post("games/");
                    //String response =  testHttpClient.getResponse().getBody().getText();

                    //System.out.println(response.getBody().getText());

                    ObjectMapper mapper = new ObjectMapper();
                    //JsonNode jsonNode = mapper.readTree(response);
                    JsonNode jsonNode = mapper.readTree(response.getBody().getText());
                    String gameId = jsonNode.get("gameId").asText();


                    ReceivedResponse response2 = testHttpClient.request("games/"+gameId+"/turns",
                            requestSpec -> requestSpec.body(
                                    body -> body.type("application/json")).post()
                    );

                    System.out.println(response2.getBody().getText());
                    /*assertEquals(expected, testHttpClient.get(path)
                            .getBody()
                            .getText());*/
                });
    }




}
