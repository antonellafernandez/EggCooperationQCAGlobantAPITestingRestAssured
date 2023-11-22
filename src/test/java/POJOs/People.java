package POJOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class People {
    private String name;
    private String skin_color;
    private List<String> films;
}