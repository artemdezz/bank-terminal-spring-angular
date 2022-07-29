package sber.ru.terminal.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "history_operations")
public class HistoryOperations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "history_message")
    private String historyMessage;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;




}
