package coursework.bot.dailycaloriesbot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Getter
@Setter
public class Users {
    @Id
    @Column(name = "telegram_id")
    private Long telegramId;
    @Column(name = "was_registered")
    private String wasRegistered;
    @Column(name = "gender")
    private String gender;
    @Column(name = "age")
    private int age;
    @Column(name = "weight")
    private double weight;
    @Column(name = "height")
    private double height;
    @Column(name = "goal")
    private String goal;

    @Column(name = "activity")
    private String activity;

    @Column(name = "glasses_of_water")
    private int glassesOfWater;

    @Column(name = "daily_calorie_intake")
    private double dailyCalorieIntake;

    protected Users() {
    }

    public Users(Long telegramId, String wasRegistered) {
        this.telegramId = telegramId;
        this.wasRegistered = wasRegistered;
    }
}