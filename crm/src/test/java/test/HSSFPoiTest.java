package test;

import com.bjpowernode.crm.commons.utils.HSSFUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @Date 2022/8/27 20:01
 */
public class HSSFPoiTest {
    @Test
    public void test01() throws Exception {
        HSSFWorkbook wb=new HSSFWorkbook();
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        HSSFSheet sheet = wb.createSheet("学生列表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("学号");
        cell=row.createCell(1);
        cell.setCellValue("姓名");
        cell=row.createCell(2);
        cell.setCellValue("年龄");
        for (int i = 1; i <=10 ; i++) {
            row = sheet.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(100+i);
            cell=row.createCell(1);
            cell.setCellValue("姓名"+i);
            cell=row.createCell(2);
            cell.setCellStyle(style);
            cell.setCellValue(20+i);
        }
        FileOutputStream out=new FileOutputStream("F:\\test\\student.xls");
        wb.write(out);
        out.flush();
        out.close();
        wb.close();
        System.out.println("===========create ok============");
    }
    @Test
    public void test02() throws Exception{
        HSSFWorkbook wb=new HSSFWorkbook(new FileInputStream("F:\\test\\activityList.xls"));
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row= null;
        for(int i=0;i<=sheet.getLastRowNum();i++){
            row=sheet.getRow(i);
            HSSFCell cell=null;
            for(int j=0;j<row.getLastCellNum();j++){
                cell=row.getCell(j);
                System.out.print(HSSFUtils.getCellValues(cell)+"\t");
            }
            System.out.println();
        }
    }

}
