package sber.ru.terminal.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "client")
public class Client{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @OneToMany
    @JoinColumn(name = "client_id")
    private List<Account> account;

    @OneToMany
    @JoinColumn(name = "client_id")
    private List<CreditRequest> creditRequests;

    @OneToMany
    @JoinColumn(name = "client_id")
    private List<CreditApproved> creditApproved;

    @OneToMany
    @JoinColumn(name = "client_id")
    private List<HistoryOperations> historyOperations;

    @OneToMany
    @JoinColumn(name = "client_id")
    private List<BlockedTransfer> blockedTransfers;



//    @Column(name = "history_operations", columnDefinition = "text[]")
//    @Column(name = "history_operations", columnDefinition = "varchar[](Types#ARRAY)")
//    private String[] historyOperations;



}