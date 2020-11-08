package com.file;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public abstract class POIFile {
    protected Workbook workbook;
    protected abstract void createWorkBook();
    protected abstract void writeFile(String filename) throws IOException;
}
