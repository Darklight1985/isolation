package ru.kolesnev.csv;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@MappedSuperclass
public class CSVBean<T extends CSVBean> {
}
