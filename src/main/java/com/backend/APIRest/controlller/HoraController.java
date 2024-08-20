package com.backend.APIRest.controlller;


import com.backend.APIRest.model.dto.hora.HorarioDiaDto;
import com.backend.APIRest.model.entidades.checador.Empleado;
import com.backend.APIRest.model.entidades.checador.Hora;
import com.backend.APIRest.service.Hora.HoraService;
import com.backend.APIRest.service.empleado.EmpleadoService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/checadas")
public class HoraController {

    @Autowired
    private HoraService horaService;

    @Autowired
    private EmpleadoService empleadoService;

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

    @GetMapping("/buscarAsistencia")
    public List<HorarioDiaDto> buscarAsistencia(
            @RequestParam("dia") String dia,
            @RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        return horaService.buscarAsistencia(dia, fecha);
    }

    @GetMapping("/buscarAsistenciaPorRangoFechas")
    public List<HorarioDiaDto> buscarAsistenciaPorRangoFechas(
            @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin) {
        return horaService.buscarAsistenciaPorRangoFechas(fechaInicio, fechaFin);
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
            Empleado empleado= empleadoService.getEmpleadoById(Integer.valueOf(getCellValueAsString(row.getCell(0))));
            if(empleado != null) {
                hora.setEmpleado(empleado);
                hora.setEntradaSalida(getCellValueAsString(row.getCell(2)));

                DateTimeFormatter formatoTime = DateTimeFormatter.ofPattern("HH:mm:ss");

                hora.setLunes(LocalTime.parse(getCellValueAsString(row.getCell(3)), formatoTime));
                hora.setMartes(LocalTime.parse(getCellValueAsString(row.getCell(4)), formatoTime));
                hora.setMiercoles(LocalTime.parse(getCellValueAsString(row.getCell(5)), formatoTime));
                hora.setJueves(LocalTime.parse(getCellValueAsString(row.getCell(6)), formatoTime));
                hora.setViernes(LocalTime.parse(getCellValueAsString(row.getCell(7)), formatoTime));
                hora.setSabado(LocalTime.parse(getCellValueAsString(row.getCell(8)), formatoTime));
                hora.setDomingo(LocalTime.parse(getCellValueAsString(row.getCell(9)), formatoTime));

                horas.add(hora);
            }
            else{
                System.out.println("El empleado no existe");
            }

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