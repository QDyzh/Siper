package com.file;

import com.util.Util;
import com.vo.ShopVo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExcelFile<T> extends POIFile{

    private Sheet sheet;
    private List<T> dataList;
    private Row row;
    private Cell cell;

    public ExcelFile(List<T> dataList) {
        createWorkBook();
        this.dataList = dataList;
        this.sheet = super.workbook.createSheet("1");
    }

    public void importData(String filename) throws Exception {
        for(int i = 0; i < dataList.size(); i++) {
            T data = dataList.get(i);
            if(i == 0) {
                row = this.sheet.createRow(i);
                List<Map<String, Object>> fields = Util.getCommonValueForClass(data);
                for(int j = 0; j < fields.size(); j++) {
                    Map<String, Object> field = fields.get(j);
                    cell = row.createCell(j);
                    for (Map.Entry entry : field.entrySet()) {
                        cell.setCellValue((String) entry.getKey());
                    }
                }
            }
            row = this.sheet.createRow(i + 1);
            List<Map<String, Object>> fields = Util.getCommonValueForClass(data);
            for(int j = 0; j < fields.size(); j++) {
                Map<String, Object> field = fields.get(j);
                cell = row.createCell(j);
                for(Map.Entry entry: field.entrySet()){
                    cell.setCellValue((String)entry.getValue());
                }
            }
        }
        writeFile(filename);
    }

    public static void main(String[] args) throws Exception {
        ShopVo vo = new ShopVo();
        vo.setName("2222");
        List<ShopVo> list = new ArrayList<>();
        list.add(vo);
        ExcelFile<ShopVo> excel = new ExcelFile(list);
        excel.importData("D:\\迅雷下载\\text.xlsx");
    }


    @Override
    protected void createWorkBook() {
        super.workbook = new XSSFWorkbook();
    }

    @Override
    protected void writeFile(String filename) throws IOException {
        workbook.write(new FileOutputStream(new File(filename)));
        workbook.close();
        System.out.print("Excel文件创建成功,路径:" + filename);
    }
}