package co.edu.uniquindio;

import co.edu.uniquindio.model.AsistenteFinanciero;
import co.edu.uniquindio.model.BilleteraVirtual;
import co.edu.uniquindio.model.Transaccion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;

public class ChatFinanciero extends JFrame {
    private BilleteraVirtual billetera = BilleteraVirtual.getInstance();
    private JTextArea chatArea;
    private JTextField inputField;
    private JTextField idField;

    public ChatFinanciero() {
        setTitle("Asistente Financiero");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);

        idField = new JTextField("Ingrese su ID de usuario", 20);
        inputField = new JTextField();

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(idField, BorderLayout.NORTH);
        panel.add(inputField, BorderLayout.CENTER);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idUsuario = idField.getText().trim();
                String pregunta = inputField.getText().trim();
                chatArea.append("Tú: " + pregunta + "\n");

                if (!idUsuario.isEmpty()) {
                    // Obtener el historial de transacciones del usuario
                    List<Transaccion> transaccionesUsuario = (List<Transaccion>) billetera.obtenerTransaccionesUsuario(idUsuario);
                    // Analizar historial para proporcionar recomendaciones
                    String historialRecomendaciones = AsistenteFinanciero.analizarHistorialTransacciones(transaccionesUsuario);
                    String respuesta = AsistenteFinanciero.responderConNLP(pregunta);
                    chatArea.append("Asistente: " + historialRecomendaciones + "\nAsistente: " + respuesta + "\n");
                } else {
                    chatArea.append("Asistente: Por favor, ingrese su ID de usuario.\n");
                }
                inputField.setText("");
            }
        });

        JScrollPane scrollPane = new JScrollPane(chatArea);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatFinanciero().setVisible(true);
            }
        });
    }
}


