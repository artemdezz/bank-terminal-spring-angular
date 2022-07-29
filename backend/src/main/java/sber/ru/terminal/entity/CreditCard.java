package sber.ru.terminal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "credit_card")
public class CreditCard extends Card{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "limit_amount", precision = 8, scale = 2)
    private BigDecimal limitAmount;

    @Column(name = "credit_percentage", precision = 8, scale = 2)
    private BigDecimal creditPercentage;

    @Column(name = "penalty", precision = 8, scale = 2)
    private BigDecimal penalty;


//    @ManyToOne
//    private CreditAccount creditAccount;
}