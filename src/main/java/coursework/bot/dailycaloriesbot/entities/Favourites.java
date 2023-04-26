package coursework.bot.dailycaloriesbot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "favourites")
@Getter
@Setter
public class Favourites {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "telegram_id")
    private Long telegramId;
    @Column(name = "product")
    private String product;

    protected Favourites() {
    }

    public Favourites(Long telegramId, String product) {
        this.telegramId = telegramId;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favourites that = (Favourites) o;
        return telegramId.equals(that.telegramId) && product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramId, product);
    }
}
