package github.hluat.springcore.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;

public class ExcelReader {
   public static <T> List<T> readData(String filename, Class<T> typeClass) {
      PoijiOptions options = PoijiOptions.PoijiOptionsBuilder.settings()
            .addListDelimiter(";").build();

      InputStream stream = ExcelReader.class.getClassLoader().getResourceAsStream(filename);
      if (stream == null){
         throw new IllegalArgumentException("file not found! " + "input.xlsx");
      }
      List<T> mails = (Poiji.fromExcel(stream, PoijiExcelType.XLSX, typeClass, options));

      if (mails.size() == 1){
         return mails;
      }
      return new ArrayList<>();
   }
}
