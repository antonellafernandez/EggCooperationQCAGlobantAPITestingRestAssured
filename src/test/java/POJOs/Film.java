package POJOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Film {
    private String release_date;
    private List<String> characters;
    private List<String> planets;
    private List<String> starships;
    private List<String> vehicles;
    private List<String> species;
}