package coursework.bot.dailycaloriesbot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users_favourites")
@Getter
@Setter
public class UsersFavourites {

    @Id
    @Column(name = "telegram_id")
    private Long telegramId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "telegram_id")
    private Set<Favourites> favourites = new HashSet<>();

    protected UsersFavourites() {
    }

    public UsersFavourites(Long telegramId) {
        this.telegramId = telegramId;
    }
}
