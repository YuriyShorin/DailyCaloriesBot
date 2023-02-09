package coursework.bot.dailycaloriesbot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
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
    private int goal;

    protected Users() {
    }

    public Users(Long telegramId, String wasRegistered) {
        this.telegramId = telegramId;
        this.wasRegistered = wasRegistered;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, telegram_id='%s', was_registered='%s']",
                id, telegramId, wasRegistered);
    }

    public Long getId() {
        return id;
    }

    public Long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Long telegramId) {
        this.telegramId = telegramId;
    }

    public String getWasRegistered() {
        return wasRegistered;
    }

    public void setWasRegistered(String wasRegistered) {
        this.wasRegistered = wasRegistered;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }
}