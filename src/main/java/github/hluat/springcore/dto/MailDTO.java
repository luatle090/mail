package github.hluat.springcore.dto;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;

import lombok.Data;

@Data
public class MailDTO {
    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private int id;

    @ExcelCell(1)
    private String name;
    
    @ExcelCell(2)
    private String address;

    @ExcelCell(3)
    private String subject;

    private String errorMessage;
}
