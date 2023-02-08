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

    public String getWasRegistered() {
        return wasRegistered;
    }

    public void setWasRegistered(String wasRegistered) {
        this.wasRegistered = wasRegistered;
    }
}