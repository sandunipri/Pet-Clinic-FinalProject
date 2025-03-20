package org.example.back_end.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "vet")
@Entity
public class Veterinarian {
    @Id
    private int id;
    private String name;
    private String phone;
    private String specialty;
}
