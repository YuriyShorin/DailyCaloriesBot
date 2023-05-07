package coursework.bot.dailycaloriesbot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users_statistics")
@Getter
@Setter
public class UsersStatistics {
    @Id
    @Column(name = "telegram_id")
    private Long telegramId;
    @Column(name = "daily_glasses_of_water")
    private int dailyGlassesOfWater;
    @Column(name = "daily_calorie_intake")
    private double dailyCalorieIntake;
    @Column(name = "daily_proteins_intake")
    private double dailyProteinsIntake;
    @Column(name = "daily_fats_intake")
    private double dailyFatsIntake;
    @Column(name = "daily_carbohydrates_intake")
    private double dailyCarbohydratesIntake;
    @Column(name = "weekly_glasses_of_water")
    private int weeklyGlassesOfWater;
    @Column(name = "weekly_calorie_intake")
    private double weeklyCalorieIntake;
    @Column(name = "weekly_proteins_intake")
    private double weeklyProteinsIntake;
    @Column(name = "weekly_fats_intake")
    private double weeklyFatsIntake;
    @Column(name = "weekly_carbohydrates_intake")
    private double weeklyCarbohydratesIntake;
    @Column(name = "monthly_glasses_of_water")
    private int monthlyGlassesOfWater;
    @Column(name = "monthly_calorie_intake")
    private double monthlyCalorieIntake;
    @Column(name = "monthly_proteins_intake")
    private double monthlyProteinsIntake;
    @Column(name = "monthly_fats_intake")
    private double monthlyFatsIntake;
    @Column(name = "monthly_carbohydrates_intake")
    private double monthlyCarbohydratesIntake;
    @Column(name = "all_time_glasses_of_water")
    private int allTimeGlassesOfWater;
    @Column(name = "all_time_calorie_intake")
    private double allTimeCalorieIntake;
    @Column(name = "all_time_proteins_intake")
    private double allTimeProteinsIntake;
    @Column(name = "all_time_fats_intake")
    private double allTimeFatsIntake;
    @Column(name = "all_time_carbohydrates_intake")
    private double allTimeCarbohydratesIntake;
    @Column(name = "days_in_bot")
    private int daysInBot;

    @Column(name = "start_weight")
    private double startWeight;
    protected UsersStatistics() {
    }

    public UsersStatistics(Long telegramId) {
        this.telegramId = telegramId;
        this.daysInBot = 1;
    }
}
