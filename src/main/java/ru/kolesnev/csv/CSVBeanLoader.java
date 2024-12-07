package ru.kolesnev.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;

@Data
@Slf4j
public abstract class CSVBeanLoader<T extends CSVBean> {

    private String fileName;
    private Class<T> clazz;

    public abstract void run();

    public abstract String getPath();

    public List<T> simplePositionBeanExample() throws Exception {
        InputStream is = getFileAsIOStream(getFileName());

        List<T> lines;
        try (InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);) {
            CsvToBean<T> cb = new CsvToBeanBuilder<T>(bufferedReader)
                    .withType(getClazz())
                    .withSeparator(' ')
                    .build();

             lines =  cb.parse();
        }
        return lines;
    }

    private InputStream getFileAsIOStream(final String fileName)
    {
        InputStream ioStream = super.getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);


        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }
}
