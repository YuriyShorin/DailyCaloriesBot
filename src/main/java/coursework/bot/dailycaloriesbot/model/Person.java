package coursework.bot.dailycaloriesbot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private String name;
    private int age;

    private double weight;

    private double height;

    private String gender;
}
