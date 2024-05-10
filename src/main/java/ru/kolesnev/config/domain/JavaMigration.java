package ru.kolesnev.config.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "java_migration")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JavaMigration {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "execution_date")
    private LocalDateTime execDate;
}
