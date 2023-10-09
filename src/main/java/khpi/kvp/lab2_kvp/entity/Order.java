package khpi.kvp.lab2_kvp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "'order'")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver", nullable = false)
    private User receiver;

    @Lob
    @Column(name = "manager", nullable = false)
    private String manager;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "good_id")
    private Good good;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "priceOrder", nullable = false)
    private Double priceOrder;

    @Column(name = "sendTake", nullable = false)
    private LocalDate sendTake;

    @Column(name = "sendTime")
    private LocalDate sendTime;

    public Order(User receiver, String manager, Good good, Integer count, Double priceOrder, LocalDate sendTake) {
        this.receiver = receiver;
        this.manager = manager;
        this.good = good;
        this.count = count;
        this.priceOrder = priceOrder;
        this.sendTake = sendTake;
    }

    public Order(User receiver, String manager, Good good, Integer count, Double priceOrder, LocalDate sendTake, LocalDate sendTime) {
        this.receiver = receiver;
        this.manager = manager;
        this.good = good;
        this.count = count;
        this.priceOrder = priceOrder;
        this.sendTake = sendTake;
        this.sendTime = sendTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Double getPriceOrder() {
        return priceOrder;
    }

    public void setPriceOrder(Double priceOrder) {
        this.priceOrder = priceOrder;
    }

    public LocalDate getSendTake() {
        return sendTake;
    }

    public void setSendTake(LocalDate sendTake) {
        this.sendTake = sendTake;
    }

    public LocalDate getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDate sendTime) {
        this.sendTime = sendTime;
    }

}