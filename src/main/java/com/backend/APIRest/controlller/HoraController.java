package com.backend.APIRest.controlller;

import com.backend.APIRest.model.entidades.checador.Hora;
import com.backend.APIRest.service.Hora.HoraService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HoraController {

    @Autowired
    private HoraService horaService;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/subirHoras")
    public ResponseEntity<Void> subirHoras(@RequestParam("file") MultipartFile file) {
        try {
            List<Hora> horas = parseExcelFile(file.getInputStream());
            horaService.saveAll(horas);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<Hora> parseExcelFile(InputStream is) throws IOException {
        List<Hora> horas = new ArrayList<>();
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue; // skip header row
            }

            if (isRowEmpty(row)) {
                continue; // skip empty rows
            }

            Hora hora = new Hora();
            hora.setNoEmpleado(getCellValueAsString(row.getCell(0)));
            hora.setNombreEmpleado(getCellValueAsString(row.getCell(1)));
            hora.setEntradaSalida(getCellValueAsString(row.getCell(2)));
            hora.setLunes(getCellValueAsString(row.getCell(3)));
            hora.setMartes(getCellValueAsString(row.getCell(4)));
            hora.setMiercoles(getCellValueAsString(row.getCell(5)));
            hora.setJueves(getCellValueAsString(row.getCell(6)));
            hora.setViernes(getCellValueAsString(row.getCell(7)));
            hora.setSabado(getCellValueAsString(row.getCell(8)));
            hora.setDomingo(getCellValueAsString(row.getCell(9)));

            horas.add(hora);
        }

        workbook.close();
        return horas;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }

        for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }

        return true;
    }
}
