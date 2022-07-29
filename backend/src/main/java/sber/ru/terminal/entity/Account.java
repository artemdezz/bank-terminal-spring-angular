package sber.ru.terminal.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "account_number", unique = true)
    private String accountNumber;

    @Column(name = "currency")
    private String currency;

    @Column(name = "amount", precision = 8, scale = 2)
    private BigDecimal amount;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private List<Card> cards;

    @ManyToOne
    private Client client;
}
