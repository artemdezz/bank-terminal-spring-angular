package sber.ru.terminal.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "credit_approved")
public class CreditApproved {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "limit_amount", precision = 8, scale = 2)
    private BigDecimal limitAmount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "credit_percentage", precision = 8, scale = 2)
    private BigDecimal creditPercentage;

    @ManyToOne
    private Client client;

}
