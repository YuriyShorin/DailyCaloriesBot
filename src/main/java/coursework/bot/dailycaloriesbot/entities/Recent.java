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

    @Column(name = "product")
    private String product;


    protected Recent() {
    }

    public Recent(String product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recent that = (Recent) o;
        return product.equals(that.product);
    }
}
