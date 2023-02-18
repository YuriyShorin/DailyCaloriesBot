package coursework.bot.dailycaloriesbot.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Users")
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

    protected Users() {
    }

    public Users(Long telegramId, String wasRegistered) {
        this.telegramId = telegramId;
        this.wasRegistered = wasRegistered;
    }

    @Override
    public String toString() {
        return "Users{" +
                "telegramId=" + telegramId +
                ", wasRegistered='" + wasRegistered + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", height=" + height +
                ", goal='" + goal + '\'' +
                ", glassesOfWater=" + glassesOfWater +
                '}';
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

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getGlassesOfWater() {
        return glassesOfWater;
    }

    public void setGlassesOfWater(int glassesOfWater) {
        this.glassesOfWater = glassesOfWater;
    }
}