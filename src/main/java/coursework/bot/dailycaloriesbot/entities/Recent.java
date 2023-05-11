package coursework.bot.dailycaloriesbot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "recent")
@Getter
@Setter
public class Recent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "telegram_id")
    private Long telegramId;
    @Column(name = "product")
    private String product;

    protected Recent() {
    }

    public Recent(Long telegramId, String product) {
        this.telegramId = telegramId;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recent recent = (Recent) o;
        return telegramId.equals(recent.telegramId) && product.equals(recent.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramId, product);
    }
}
