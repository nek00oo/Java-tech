package ru.itmo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@JsonIgnoreProperties({"cats", "user"})
@Table(name = "Owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column (name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "owner")
    private final List<Cat> cats;

    private String password;

    private String roles;

    public Owner() {
        cats = new ArrayList<>();
    }

    public void addCat(Cat cat) {
        cats.add(cat);
        cat.setOwner(this);
    }
}

