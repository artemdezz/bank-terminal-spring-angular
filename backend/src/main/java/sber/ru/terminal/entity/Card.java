package sber.ru.terminal.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "card_number", unique = true)
    private String cardNumber;

    @Column(name = "currency")
    private String currency;

    @Column(name = "amount", precision = 8, scale = 2)
    private BigDecimal amount;

    @Column(name = "valid")
    private Date valid;

    @ManyToOne
    private Account account;

}