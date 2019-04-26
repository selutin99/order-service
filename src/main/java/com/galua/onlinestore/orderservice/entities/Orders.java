package com.galua.onlinestore.orderservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    //TODO: Добавить внешний ключ после подключения модуля
    private int offerID;

    private String name;
    private Timestamp deliveryTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Status status;

    //TODO: Добавить внешний ключ после подключения модуля
    private int customerID;

    private boolean paid;
}
