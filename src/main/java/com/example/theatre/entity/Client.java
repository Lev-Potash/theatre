package com.example.theatre.entity;




import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "client")
@NoArgsConstructor
@Transactional
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=1, message="Поле Имя должно иметь хотя бы 1 символ")
    private String name;

    @NotNull
    @Size(min=1, message="Поле Фамилия должно иметь хотя бы 1 символ")
    private String surname;

    @NotNull
//    @Email(message = "Адрес электронной почты введен не корректно")
    @Size(min=5, message="Поле Email должно иметь хотя бы 5 символов")
    private String email;


// @JsonIgnore позволяет исключить определенное свойство объекта из процесса сериализации.
// Однако, применение этой аннотации также приводит к исключению свойства из процесса десериализации
// @ToString.Exclude - исключить поле в методе toString()
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Ticket> tickets = new ArrayList<>();

    public void addTicket(Ticket ticket) {
        ticket.setClient(this);
        this.tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        this.tickets.remove(ticket);
    }

    public Client(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
