package ru.kolesnev.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
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
@MappedSuperclass
public abstract class CSVBean {

    @Column(name = "file_name", updatable = false)
    private String fileName;

    @Column(name = "execution_date", updatable = false)
    private LocalDateTime execDate;

    public abstract void run();

    public List<ThermalResistanceCSVBean> simplePositionBeanExample() throws Exception {
        URL is = getFileAsIOStream(getFileName());
        Path uri = Paths.get(is.toURI());

        List<ThermalResistanceCSVBean> lines;
        try (Reader reader = Files.newBufferedReader(uri)) {
            CsvToBean<ThermalResistanceCSVBean> cb = new CsvToBeanBuilder<ThermalResistanceCSVBean>(reader)
                    .withType(ThermalResistanceCSVBean.class)
                    .withSeparator(' ')
                    .build();

            lines =  cb.parse();
        }
        return lines;
    }

    private URL getFileAsIOStream(final String fileName)
    {
        URL ioStream = super.getClass()
                .getClassLoader()
                .getResource(fileName);


        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }

    private void printFileContent(InputStream is) throws IOException
    {
        try (InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr);)
        {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            is.close();
        }
    }
}
