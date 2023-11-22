package tests;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import java.util.regex.Pattern;
import static org.hamcrest.Matchers.greaterThan;
import org.testng.Assert;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ServicesTest {
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://swapi.dev/api/";
    }
    
    /*    
    1. Test the endpoint people/2/ and check the success response,
    the skin color to be gold, and the amount of films it appears on 
    (should be 6).
    */
    
    @Test
    public void testGetPeople2() {
        Response response = given().get("people/2/");
        Assert.assertEquals(response.getStatusCode(), 200);

        String skinColor = response.jsonPath().getString("skin_color");
        Assert.assertEquals(skinColor, "gold", "Skin color should be 'gold'");

        int filmsCount = response.jsonPath().getList("films").size();
        Assert.assertEquals(filmsCount, 6, "The amount of films it appears on should be '6'");
    }
    
    /*
    2. Request the endpoint of the second movie in which people/2/ 
    was present (using the response from people/2/). 
    Check the release date to be in the correct date format, 
    and the response to include characters, planets, starships, vehicles 
    and species, each element including more than 1 element.
    */
    
    @Test
    public void testFilm2ForPeople2(){
        Response peopleResponse = given().get("people/2/");

        String film2Url = peopleResponse.path("films[1]");
        Response film2Response = given().get(film2Url);
        Assert.assertEquals(film2Response.getStatusCode(), 200);

        String releaseDate = film2Response.path("release_date");
        String dateFormatRegex = "\\d{4}-\\d{2}-\\d{2}";
        assertTrue(Pattern.matches(dateFormatRegex, releaseDate),"Release Date is not in the correct format");
        film2Response.then().body("characters.size()", greaterThan(1));
        film2Response.then().body("planets.size()", greaterThan(1));
        film2Response.then().body("starships.size()", greaterThan(1));
        film2Response.then().body("vehicles.size()", greaterThan(1));
        film2Response.then().body("species.size()", greaterThan(1));
    }
    
    /*
    3. Request the endpoint of the first planet present in the last film's 
    response (using the previous response). Check the gravity and the terrains 
    matching the exact same values returned by the request 
    (Use fixtures to store and compare the data of terrains and gravity).
    */
    
    @Test
    public void testPlanetFromFilm2() {
        Response peopleResponse = given().get("people/2/");

        String film2Url = peopleResponse.path("films[1]");
        Response film2Response = given().get(film2Url);

        String planetUrl = film2Response.path("planets[0]");
        Response planetResponse = given().get(planetUrl);
        Assert.assertEquals(planetResponse.getStatusCode(), 200);

        String gravity = planetResponse.jsonPath().getString("gravity");
        Assert.assertEquals(gravity, "1.1 standard", "Gravity should be '1.1 standard'");

        String terrain = planetResponse.jsonPath().getString("terrain");
        Assert.assertEquals(terrain, "tundra, ice caves, mountain ranges", "Terrain should be 'tundra, ice caves, mountain ranges'");
    }
    
    /*
    4. On the same response from the planet, grab the url element on the 
    response, and request it. Validate the response being exactly the same 
    from the previous one.
    */
    
    @Test
    public void testPlanetFromFilm2Bis() {
        Response peopleResponse = given().get("people/2/");

        String film2Url = peopleResponse.path("films[1]");
        Response film2Response = given().get(film2Url);

        String planetUrl = film2Response.path("planets[0]");
        Response planetResponse = given().get(planetUrl);
        Assert.assertEquals(planetResponse.getStatusCode(), 200);

        String gravity = planetResponse.jsonPath().getString("gravity");
        Assert.assertEquals(gravity, "1.1 standard", "Gravity should be '1.1 standard'");

        String terrain = planetResponse.jsonPath().getString("terrain");
        Assert.assertEquals(terrain, "tundra, ice caves, mountain ranges", "Terrain should be 'tundra, ice caves, mountain ranges'");
    }
    
    /*
    5. Request the /films/7/ and check the response having a 404 code.
    */
    
    @Test
    public void testGetFilms7() {
        Response response = given().get("/films/7/");
        Assert.assertEquals(response.getStatusCode(), 404);
    }
}