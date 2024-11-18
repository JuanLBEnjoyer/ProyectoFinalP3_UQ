package co.edu.uniquindio.model;

import java.util.List;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;

public class AsistenteFinanciero {

    public static String responderConNLP(String pregunta) {
        try {
            // Cargar el modelo de tokenización
            InputStream modeloTokenizacion = new FileInputStream("src/main/resources/models/en-token.bin");
            TokenizerModel model = new TokenizerModel(modeloTokenizacion);
            Tokenizer tokenizer = new TokenizerME(model);

            // Tokenizar la pregunta del usuario
            String[] tokens = tokenizer.tokenize(pregunta.toLowerCase());

            // Lista de palabras clave para categorías
            List<String> ahorroKeywords = Arrays.asList("ahorrar", "economizar", "presupuesto");
            List<String> inversionKeywords = Arrays.asList("invertir", "fondos", "acciones", "dinero");
            List<String> deudaKeywords = Arrays.asList("deuda", "pagar", "reducir");

            // Analizar palabras clave
            if (Arrays.stream(tokens).anyMatch(ahorroKeywords::contains)) {
                return "Para ahorrar más dinero, crea un presupuesto y reduce gastos innecesarios.";
            } else if (Arrays.stream(tokens).anyMatch(inversionKeywords::contains)) {
                return "Puedes invertir en fondos mutuos, bonos o bienes raíces. Consulta con un asesor financiero.";
            } else if (Arrays.stream(tokens).anyMatch(deudaKeywords::contains)) {
                return "Prioriza pagar tus deudas con mayor interés y evita adquirir nuevas.";
            } else {
                return "Lo siento, no tengo información sobre esa consulta. Por favor, consulta con un asesor financiero.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al procesar tu pregunta.";
        }
    }

    public static String analizarHistorialTransacciones(List<Transaccion> transacciones) {
        double totalGastos = transacciones.stream()
                .filter(t -> t instanceof Retiro || t instanceof Transferencia)
                .mapToDouble(Transaccion::getMontoTransaccion)
                .sum();

        double totalIngresos = transacciones.stream()
                .filter(t -> t instanceof Deposito)
                .mapToDouble(Transaccion::getMontoTransaccion)
                .sum();

        double balance = totalIngresos - totalGastos;

        StringBuilder recomendaciones = new StringBuilder();
        recomendaciones.append("Resumen Financiero:\n");
        recomendaciones.append("Total Ingresos: ").append(totalIngresos).append("\n");
        recomendaciones.append("Total Gastos: ").append(totalGastos).append("\n");
        recomendaciones.append("Balance: ").append(balance).append("\n\n");

        if (balance > 0) {
            recomendaciones.append("Recomendaciones:\n");
            recomendaciones.append("1. Considera invertir tu saldo positivo en un fondo de inversión de bajo riesgo.\n");
            recomendaciones.append("2. Abre una cuenta de ahorro con intereses para aprovechar tu saldo positivo.\n");
        } else {
            recomendaciones.append("Recomendaciones:\n");
            recomendaciones.append("1. Reduce tus gastos no esenciales para mejorar tu balance.\n");
            recomendaciones.append("2. Busca oportunidades de ingresos adicionales para equilibrar tus finanzas.\n");
        }

        return recomendaciones.toString();
    }
}



