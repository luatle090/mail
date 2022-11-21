package github.hluat.springcore.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelWriter {
   public static <T> void writeData(String output,List<T> lTs, WriteRow<T> f) throws IOException {
      try (Workbook workbook = WorkbookFactory.create(new File("src/main/resources/output-template.xlsx"))) {
         Sheet sheet = workbook.getSheetAt(0);
         int rowsIndex = 1;
         for (var t : lTs) {
            Row row = sheet.createRow(rowsIndex);
            f.writeRow(t, row);
            rowsIndex++;
         }
         try (OutputStream os = new FileOutputStream(output)) {
            workbook.write(os);
         }
      }
   }
}

