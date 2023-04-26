package coursework.bot.dailycaloriesbot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users_recent")
@Getter
@Setter
public class UsersRecent {

    @Id
    @Column(name = "telegram_id")
    private Long telegramId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "telegram_id")
    private List<Recent> recentList = new ArrayList<>();

    protected UsersRecent() {
    }

    public UsersRecent(Long telegramId) {
        this.telegramId = telegramId;
    }
}
