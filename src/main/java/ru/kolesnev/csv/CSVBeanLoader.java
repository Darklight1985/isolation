package ru.kolesnev.csv;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Data
@Slf4j
public abstract class CSVBeanLoader<T extends CSVBean> {

    private String fileName;
    private Class<T> clazz;

    public abstract void run();

    public abstract String getPath();

    public List<T> simplePositionBeanExample() throws Exception {
        //ClassLoader classLoader = getClass().getClassLoader();
      //  File file = new File(classLoader.getResource(getPath()).getFile());

        InputStream is = getFileAsIOStream(getFileName());
    //    Path uri = Paths.get(is.toURI());
       // String string = file.getAbsolutePath();
      //  Path uri = Paths.get(string);

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
