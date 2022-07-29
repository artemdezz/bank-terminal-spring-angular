package sber.ru.terminal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "blocked_transfer")
public class BlockedTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "type_transfer")
    private String typeTransfer;

    @Column(name = "amount", precision = 8, scale = 2)
    private BigDecimal amount;

    @Column(name = "currency")
    private String currency;

    @Column(name = "sender_card")
    private String CardSender;

    @Column(name = "recepient_card")
    private String CardRecepient;

    @ManyToOne
    private Client client;

}
