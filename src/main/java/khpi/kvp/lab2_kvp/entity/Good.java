package khpi.kvp.lab2_kvp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "good")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "article", nullable = false, length = 10)
    private String article;

    @Column(name = "nazva", nullable = false)
    private String nazva;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "priceOpt", nullable = false)
    private Double priceOpt;

    @Column(name = "image_path")
    private String imagePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getNazva() {
        return nazva;
    }

    public void setNazva(String nazva) {
        this.nazva = nazva;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceOpt() {
        return priceOpt;
    }

    public void setPriceOpt(Double priceOpt) {
        this.priceOpt = priceOpt;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Good(String nazva, String article, double price) {
        this.nazva = nazva;
        this.article = article;
        this.price = price;
    }
}