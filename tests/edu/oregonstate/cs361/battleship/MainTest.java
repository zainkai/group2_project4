package edu.oregonstate.cs361.battleship;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static spark.Spark.awaitInitialization;


/**
 * Created by michaelhilton on 1/26/17.
 */
class MainTest {

    @BeforeAll
    public static void beforeClass() {
        Main.main(null);
        awaitInitialization();
    }

    @AfterAll
    public static void afterClass() {
        Spark.stop();
    }

    @Test
    public void testGetModel() {
        TestResponse res = request("GET", "/model");
        String newModel = ("{\"aircraftCarrier\":{\"stealth\":false,\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"sunk\":false,\"health\":5,\"placed\":false},\"battleship\":{\"stealth\":true,\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"sunk\":false,\"health\":4,\"placed\":false},\"submarine\":{\"stealth\":true,\"name\":\"Submarine\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"sunk\":false,\"health\":3,\"placed\":false},\"clipper\":{\"name\":\"Clipper\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"sunk\":false,\"health\":1,\"placed\":false},\"dinghy\":{\"name\":\"Dinghy\",\"length\":1,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"sunk\":false,\"health\":1,\"placed\":false},\"computer_aircraftCarrier\":{\"stealth\":false,\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":4,\"Down\":3},\"end\":{\"Across\":4,\"Down\":7},\"sunk\":false,\"health\":5,\"placed\":false},\"computer_battleship\":{\"stealth\":true,\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":5,\"Down\":5},\"end\":{\"Across\":5,\"Down\":8},\"sunk\":false,\"health\":4,\"placed\":false},\"computer_submarine\":{\"stealth\":true,\"name\":\"Computer_Submarine\",\"length\":3,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":4,\"Down\":2},\"sunk\":false,\"health\":3,\"placed\":false},\"computer_clipper\":{\"name\":\"Computer_Clipper\",\"length\":3,\"start\":{\"Across\":8,\"Down\":1},\"end\":{\"Across\":8,\"Down\":3},\"sunk\":false,\"health\":1,\"placed\":false},\"computer_dinghy\":{\"name\":\"Computer_Dinghy\",\"length\":1,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":2},\"sunk\":false,\"health\":1,\"placed\":false},\"playerHits\":[],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[],\"scanResult\":false,\"hardMode\":false,\"hasFlipped\":false,\"fireMode\":1,\"direction\":0,\"currentDirection\":0,\"random\":{\"seed\":{\"value\":21981617999272},\"nextNextGaussian\":0.0,\"haveNextNextGaussian\":false}}");
        assertEquals(200, res.status);
        assertEquals(newModel, res.body);
    }

    @Test
    public void testPlaceShip() {
        BattleshipModel test = new BattleshipModel();
        Gson gson = new Gson();
        String model = gson.toJson(test);

        TestResponse res = request_post("POST", "/placeShip/aircraftCarrier/1/1/horizontal",model);
        assertEquals(200, res.status);
        assertEquals("{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"stealth\":false},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"stealth\":true},\"submarine\":{\"name\":\"Submarine\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"stealth\":true},\"clipper\":{\"name\":\"Clipper\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"stealth\":false},\"dinghy\":{\"name\":\"Dinghy\",\"length\":1,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"stealth\":false},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":2,\"Down\":2},\"end\":{\"Across\":2,\"Down\":6},\"stealth\":false},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":2,\"Down\":8},\"end\":{\"Across\":5,\"Down\":8},\"stealth\":true},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":3,\"start\":{\"Across\":9,\"Down\":6},\"end\":{\"Across\":9,\"Down\":8},\"stealth\":true},\"computer_clipper\":{\"name\":\"Computer_Clipper\",\"length\":3,\"start\":{\"Across\":1,\"Down\":10},\"end\":{\"Across\":3,\"Down\":10},\"stealth\":false},\"computer_dinghy\":{\"name\":\"Computer_Dinghy\",\"length\":1,\"start\":{\"Across\":1,\"Down\":1},\"end\":{\"Across\":1,\"Down\":1},\"stealth\":false},\"playerHits\":[],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[],\"scanResult\":false}",res.body);
    }

    @Test
    public void testScan() {
        BattleshipModel test = new BattleshipModel();
        Gson gson = new Gson();
        String model = gson.toJson(test);

        TestResponse res = request_post("POST", "/scan/6/6",model);

        System.out.println(res.body);

        assertEquals(200, res.status);
        assertEquals("{\"aircraftCarrier\":{\"name\":\"AircraftCarrier\",\"length\":5,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"sunk\":false,\"health\":5,\"stealth\":false,\"placed\":false},\"battleship\":{\"name\":\"Battleship\",\"length\":4,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"sunk\":false,\"health\":4,\"stealth\":false,\"placed\":false},\"submarine\":{\"name\":\"Submarine\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"sunk\":false,\"health\":3,\"stealth\":false,\"placed\":false},\"clipper\":{\"name\":\"Clipper\",\"length\":3,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"sunk\":false,\"health\":3,\"stealth\":false,\"placed\":false},\"dinghy\":{\"name\":\"Dinghy\",\"length\":1,\"start\":{\"Across\":0,\"Down\":0},\"end\":{\"Across\":0,\"Down\":0},\"sunk\":false,\"health\":1,\"stealth\":false,\"placed\":false},\"computer_aircraftCarrier\":{\"name\":\"Computer_AircraftCarrier\",\"length\":5,\"start\":{\"Across\":4,\"Down\":9},\"end\":{\"Across\":8,\"Down\":9},\"sunk\":false,\"health\":5,\"stealth\":false,\"placed\":false},\"computer_battleship\":{\"name\":\"Computer_Battleship\",\"length\":4,\"start\":{\"Across\":5,\"Down\":5},\"end\":{\"Across\":5,\"Down\":8},\"sunk\":false,\"health\":4,\"stealth\":false,\"placed\":false},\"computer_submarine\":{\"name\":\"Computer_Submarine\",\"length\":3,\"start\":{\"Across\":6,\"Down\":7},\"end\":{\"Across\":8,\"Down\":7},\"sunk\":false,\"health\":3,\"stealth\":false,\"placed\":false},\"computer_clipper\":{\"name\":\"Computer_Clipper\",\"length\":3,\"start\":{\"Across\":6,\"Down\":1},\"end\":{\"Across\":8,\"Down\":1},\"sunk\":false,\"health\":3,\"stealth\":false,\"placed\":false},\"computer_dinghy\":{\"name\":\"Computer_Dinghy\",\"length\":1,\"start\":{\"Across\":7,\"Down\":1},\"end\":{\"Across\":7,\"Down\":1},\"sunk\":false,\"health\":1,\"stealth\":false,\"placed\":false},\"playerHits\":[],\"playerMisses\":[],\"computerHits\":[],\"computerMisses\":[],\"scanResult\":false,\"hardMode\":false,\"fireMode\":1,\"direction\":0,\"directionCount\":0,\"currentDirection\":1}",res.body);
    }


    @Test
    public void testValidFire(){
        BattleshipModel test = new BattleshipModel();
        Gson gson = new Gson();
        String model = gson.toJson(test);

        TestResponse res = request_post( "POST", "/fire/4/1", model);
        assertEquals( 200, res.status);
    }


    private TestResponse request_post(String method, String path, String body) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            if(body != null) {
                connection.setDoInput(true);
                byte[] outputInBytes = body.getBytes("UTF-8");
                OutputStream os = connection.getOutputStream();
                os.write(outputInBytes);
            }
            connection.connect();
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    private TestResponse request(String method, String path) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();
            String body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    private static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public Map<String,String> json() {
            return new Gson().fromJson(body, HashMap.class);
        }
    }
}