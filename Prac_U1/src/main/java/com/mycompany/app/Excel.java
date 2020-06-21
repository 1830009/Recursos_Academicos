package com.mycompany.app;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class Excel {

    static String rutaExcel="resources/BD_Excel.xlsx";

    public String[][] LeerExcel(String Tabla){
        String [][] M_Datos;
        int i,j;
        i=j=0;
        Workbook workbook = null;
        Sheet sheet=null;
        DataFormatter dataFor= new DataFormatter();
        Iterator<Row> rowIt=null;
        String HojaName;
        try{
            workbook = WorkbookFactory.create(new File(rutaExcel));
        }
        catch(IOException | InvalidFormatException e){

        }
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();

        while (sheetIterator.hasNext()) {
            sheet = sheetIterator.next();
            HojaName = sheet.getSheetName();

            Tabla= Tabla.toUpperCase();
            if (HojaName.equals(Tabla)) {
                System.out.println("=> " + HojaName);
                break;
            }
        }
            //Cambiar de Fila
            rowIt= sheet.rowIterator();
            int Filas= sheet.getLastRowNum();

            M_Datos= new String[Filas+1][20];
            while (rowIt.hasNext()) {
                Row row = rowIt.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                //Leer Celdas
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    String cellValue = dataFor.formatCellValue(cell);
                    System.out.print(cellValue + "\t");
                    if(i>Filas)
                        break;
                    else {
                        if (cellValue == null)
                            M_Datos[i][j] = "-";
                        else
                            M_Datos[i][j] = String.valueOf(cellValue);
                        j++;
                    }
                }
                i++;
                j=0;
                System.out.println();
            }

        return M_Datos;
    }
}
