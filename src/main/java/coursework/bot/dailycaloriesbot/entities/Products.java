package coursework.bot.dailycaloriesbot.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Products")
@Getter
@Setter
public class Products {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "product")
    private String product;
    @Column(name = "proteins")
    private double proteins;
    @Column(name = "fats")
    private double fats;
    @Column(name = "carbohydrates")
    private double carbohydrates;
    @Column(name = "kilocalories")
    private double kilocalories;

    protected Products() {
    }
}
