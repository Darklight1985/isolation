package ru.kolesnev.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Slf4j
@Entity
@Table(name = "csv_migration")
public class CSVMigration {

    @Id
    @Column(name = "name", updatable = false)
    private String name;

    @Column(name = "execution_date", updatable = false)
    private LocalDateTime execDate;

    public CSVMigration(String name, LocalDateTime execDate) {
        this.name = name;
        this.execDate = execDate;
    }

    public CSVMigration() {

    }
}
