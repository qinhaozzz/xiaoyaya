package com.lim.xyyutil.util;

import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author qinhao
 */
public class ExportUtil {

    private final Logger logger = LoggerFactory.getLogger(ExportUtil.class);

    public static void export(List<? extends Object> data, String[] titles, String[] fields, String fileName, HttpServletResponse response) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet");
        HSSFRow row;
        HSSFCell cell;
        // style
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);

        //title
        row = sheet.createRow(0);
        for (int i = 0; i < titles.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(cellStyle);
            sheet.setColumnWidth(i, titles[i].getBytes().length * 2 * 200);
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //data
        for (int i = 0; i < data.size(); i++) {
            row = sheet.createRow(i + 1);
            Object obj = data.get(i);
            for (int j = 0; j < fields.length; j++) {
                Field field = obj.getClass().getDeclaredField(fields[j]);
                cell = row.createCell(j);
                if (field.getType() == Date.class) {
                    cell.setCellValue(formatter.format(field.get(obj)));
                } else {
                    cell.setCellValue(field.get(obj).toString());
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

    }
}
