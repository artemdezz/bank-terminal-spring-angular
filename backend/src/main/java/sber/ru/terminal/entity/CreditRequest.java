package sber.ru.terminal.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "credit_request")
public class CreditRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "limit_amount", precision = 8, scale = 2)
    private BigDecimal limitAmount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "status")
    private String status;

    @ManyToOne
    private Client client;


}
