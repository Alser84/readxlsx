import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.commons.io.FileUtils;

public class ReadExcelDemo {

    public static void main(String[] args) throws IOException {
        File destinationDir = new File("C:/demo2/");
        FileInputStream inputStream = new FileInputStream(new File("C:/demo/ФОТО для каталога2.xlsx"));
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        //Поиск и преобразование данных из xlsx файла
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            XSSFRow row = sheet.getRow(rowIndex);
            if (row != null) {
                final Cell cell = row.getCell(0);

                if (cell != null) {
                    StringBuilder builder = new StringBuilder();
                    builder.append(cell);
                    String completedString = builder.toString();
                    final String format = completedString.replace(".0", ".txt");  //Заменили символы
                    Files.find(Paths.get("C://demo"),
                            Integer.MAX_VALUE,
                            (filePath, fileAttr) -> fileAttr.isRegularFile()&& filePath.toString().endsWith(format)).forEach(System.out::println);

                    Files.find(Paths.get("C://demo"),
                            Integer.MAX_VALUE,
                            (filePath, fileAttr) -> fileAttr.isRegularFile()&& filePath.toString().endsWith(format))
                            .forEach(path -> {
                                try {
                                    FileUtils.copyFileToDirectory(path.toFile(), destinationDir);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } );
                    }
                }
            }
        }
    }

