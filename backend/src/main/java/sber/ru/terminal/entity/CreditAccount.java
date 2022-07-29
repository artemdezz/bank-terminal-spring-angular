package sber.ru.terminal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "credit_account")
public class CreditAccount extends Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "begin_credit")
    private Date beginCredit;

    @Column(name = "end_credit")
    private Date endCredit;

//    @OneToMany
//    @JoinColumn(name = "account_id")
//    private List<CreditCard> creditCards;

}