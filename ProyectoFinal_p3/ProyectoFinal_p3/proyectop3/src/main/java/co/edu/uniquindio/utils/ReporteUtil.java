package co.edu.uniquindio.utils;

import co.edu.uniquindio.exception.ArchivoNoEncontradoException;
import co.edu.uniquindio.model.Transaccion;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ReporteUtil {

    public static void generarReporteCSV(String key, List<Transaccion> transacciones) throws IOException {
        String rutaArchivo = Persistencia.obtenerRutaProperties(key);
        if (rutaArchivo == null || rutaArchivo.isEmpty()) {
            throw new ArchivoNoEncontradoException("La ruta del archivo no puede estar vacía");
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte");
            int rowNum = 0;
            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("ID Transacción");
            headerRow.createCell(1).setCellValue("Fecha");
            headerRow.createCell(2).setCellValue("Monto");
            headerRow.createCell(3).setCellValue("Descripción");
            headerRow.createCell(4).setCellValue("Categoría");

            for (Transaccion transaccion : transacciones) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(transaccion.getIdTransaccion());
                row.createCell(1).setCellValue(transaccion.getFecha().toString());
                row.createCell(2).setCellValue(transaccion.getMontoTransaccion());
                row.createCell(3).setCellValue(transaccion.getDescripcion());
                row.createCell(4).setCellValue(transaccion.getCategoria().getNombre());
            }

            try (FileOutputStream fileOut = new FileOutputStream(rutaArchivo)) {
                workbook.write(fileOut);
            }
        }
    }

    public static void generarReportePDF(String key, List<Transaccion> transacciones) throws IOException {
        String rutaArchivo = Persistencia.obtenerRutaProperties(key);
        if (rutaArchivo == null || rutaArchivo.isEmpty()) {
            throw new ArchivoNoEncontradoException("La ruta del archivo no puede estar vacía");
        }

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 750);

                contentStream.showText("ID Transacción | Fecha | Monto | Descripción | Categoría");
                contentStream.newLine();
                contentStream.showText("-----------------------------------------------------------------");
                contentStream.newLine();

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                for (Transaccion transaccion : transacciones) {
                    String transaccionTexto = String.format("%s | %s | %.2f | %s | %s",
                            transaccion.getIdTransaccion(),
                            transaccion.getFecha().toString(),
                            transaccion.getMontoTransaccion(),
                            transaccion.getDescripcion(),
                            transaccion.getCategoria().getNombre());
                    contentStream.showText(transaccionTexto);
                    contentStream.newLine();
                }

                contentStream.endText();
            }

            document.save(rutaArchivo);
        }
    }
}

