import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {

    private JPanel leftPanel;
    private JPanel rightPanel;
    private DefaultTableModel tableModel;
    private String estado = "null";
    private boolean registroClicado = false; // Variable de control
    private String pacienteSeleccionadoId = null; // Variable para almacenar el ID del paciente seleccionado

    public MainFrame() {
        setTitle("Main Frame");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setLayout(new BorderLayout());

        // Panel superior (botones)
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(900, 30));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        JButton registroButton = new JButton("Registro");
        JButton hospitalizacionButton = new JButton("Hospitalización");
        JButton generalsButton = new JButton("Med.General");
        JButton urgenciasButton = new JButton("Urgencias");
        JButton laboratorioButton = new JButton("Laboratorio");

        registroButton.setPreferredSize(new Dimension(100, 30));
        hospitalizacionButton.setPreferredSize(new Dimension(100, 30));
        generalsButton.setPreferredSize(new Dimension(100, 30));
        urgenciasButton.setPreferredSize(new Dimension(100, 30));
        laboratorioButton.setPreferredSize(new Dimension(100, 30));

        topPanel.add(registroButton);
        topPanel.add(hospitalizacionButton);
        topPanel.add(generalsButton);
        topPanel.add(urgenciasButton);
        topPanel.add(laboratorioButton);

        // Panel izquierdo
        leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBackground(Color.LIGHT_GRAY);

        // Panel derecho
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.DARK_GRAY);

        // Panel extra (cuarto panel)
        JPanel extraPanel = new JPanel();
        extraPanel.setLayout(new GridLayout(3, 1, 5, 5));
        extraPanel.setPreferredSize(new Dimension(100, 30));

        JButton ingresarButton = new JButton("Ingresar");
        JButton verDatosButton = new JButton("Ver Datos");
        JButton modificarDatosButton = new JButton("Modificar Datos");

        ingresarButton.setEnabled(false); // Deshabilitado inicialmente
        extraPanel.add(ingresarButton);
        extraPanel.add(verDatosButton);
        extraPanel.add(modificarDatosButton);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(500);
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);

        add(topPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(extraPanel, BorderLayout.EAST);

        // Acción del botón "Registro"
        registroButton.addActionListener(e -> {
            registroClicado = true; // Marcar como clicado
            ingresarButton.setEnabled(true); // Habilitar botón "Ingresar"
            mostrarTablaDeCredenciales();
        });

        // Acción del botón "Ingresar"
        ingresarButton.addActionListener(e -> {
            if (registroClicado) {
                abrirFormularioIngreso();
            } else {
                mostrarMensajeError("Debe hacer clic en 'Registro' antes de ingresar.");
            }
        });
        verDatosButton.addActionListener(e -> {
            if (registroClicado && pacienteSeleccionadoId != null) {
                mostrarDetallesPaciente(pacienteSeleccionadoId);
            } else {
                mostrarMensajeError("Debe hacer clic en 'Registro' y seleccionar un paciente antes de ver los datos.");
            }
        });
        

    }

    private void abrirFormularioIngreso() {
        // Crear el panel de solicitud del ID que simula un JOptionPane
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        JLabel inputLabel = new JLabel("Ingrese el ID:");
        JTextField inputField = new JTextField(15);
        JButton submitButton = new JButton("Aceptar");

        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        inputPanel.add(submitButton);

        // Limpiar el rightPanel antes de agregar contenido
        rightPanel.removeAll();
        rightPanel.add(inputPanel, BorderLayout.CENTER);

        // Actualizar la vista para que se vea el panel de entrada
        rightPanel.revalidate();
        rightPanel.repaint();

        submitButton.addActionListener(e -> {
            String idIngresado = inputField.getText().trim();

            if (idIngresado.isEmpty()) {
                // Mostrar un mensaje de error en el rightPanel
                mostrarMensajeError("El ID no puede estar vacío.");
            } else {
                // Verificar si el ID ya está registrado
                if (existeIdEnDirectorio(idIngresado)) {
                    mostrarMensajeError("El ID ya está registrado.");
                } else {
                    // Si el ID no está registrado, mostrar el formulario de ingreso
                    mostrarFormularioIngreso(idIngresado);
                }
            }
        });
    }

    private void mostrarFormularioIngreso(String idIngresado) {
        // Limpiar el panel derecho
        rightPanel.removeAll();

        // Crear el panel de ingreso con GridBagLayout
        JPanel ingresoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacio entre los componentes

        // Crear los campos de texto
        JTextField nombreField = new JTextField(15);
        JTextField primerApellidoField = new JTextField(15);
        JTextField tipoIdField = new JTextField(15);
        JTextField idField = new JTextField(15);
        idField.setText(idIngresado); // Prellenar el ID con el valor ingresado
        JTextField nacimientoField = new JTextField(15);
        JTextField generoField = new JTextField(15);
        JTextField direccionField = new JTextField(15);
        JTextField telefonoField = new JTextField(15);
        JTextField correoField = new JTextField(15);
        JTextField nombreContactoField = new JTextField(15);
        JTextField relacionField = new JTextField(15);
        JTextField telefonoContactoField = new JTextField(15);

        // Configurar posiciones y añadir etiquetas y campos
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        ingresoPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        ingresoPanel.add(nombreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        ingresoPanel.add(new JLabel("Primer Apellido:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        ingresoPanel.add(primerApellidoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        ingresoPanel.add(new JLabel("Tipo de ID:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JComboBox<String> tipoIdComboBox = new JComboBox<>(new String[] { "C.C", "C.E", "T.I", "Otro" });
        ingresoPanel.add(tipoIdComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        ingresoPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        ingresoPanel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        ingresoPanel.add(new JLabel("Nacimiento:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        ingresoPanel.add(nacimientoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        ingresoPanel.add(new JLabel("Género:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JComboBox<String> generoComboBox = new JComboBox<>(new String[] { "Femenino", "Masculino" });
        ingresoPanel.add(generoComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        ingresoPanel.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        ingresoPanel.add(direccionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.EAST;
        ingresoPanel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        ingresoPanel.add(telefonoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.EAST;
        ingresoPanel.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        ingresoPanel.add(correoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.EAST;
        ingresoPanel.add(new JLabel("Nombre Contacto:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        ingresoPanel.add(nombreContactoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.anchor = GridBagConstraints.EAST;
        ingresoPanel.add(new JLabel("Relación:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        ingresoPanel.add(relacionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.anchor = GridBagConstraints.EAST;
        ingresoPanel.add(new JLabel("Teléfono Contacto:"), gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        ingresoPanel.add(telefonoContactoField, gbc);

        // Botón de guardar
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton guardarButton = new JButton("Guardar");
        ingresoPanel.add(guardarButton, gbc);

        guardarButton.addActionListener(e -> {
            guardarDatosEnCSV(nombreField.getText(), primerApellidoField.getText(),
                    tipoIdComboBox.getSelectedItem().toString(),
                    idField.getText(), nacimientoField.getText(), generoComboBox.getSelectedItem().toString(),
                    direccionField.getText(), telefonoField.getText(), correoField.getText(),
                    nombreContactoField.getText(), relacionField.getText(), telefonoContactoField.getText());
            mostrarTablaDeCredenciales(); // Mostrar los datos en la tabla
        });

        rightPanel.add(ingresoPanel, BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private void mostrarMensajeError(String mensaje) {
        // Crear un panel con el mensaje de error
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new FlowLayout());

        JLabel errorLabel = new JLabel(mensaje);
        errorLabel.setForeground(Color.RED);
        JButton cerrarButton = new JButton("Cerrar");

        errorPanel.add(errorLabel);
        errorPanel.add(cerrarButton);

        rightPanel.removeAll();
        rightPanel.add(errorPanel, BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();

        cerrarButton.addActionListener(e -> {
            rightPanel.removeAll(); // Limpiar el panel de error
            rightPanel.revalidate();
            rightPanel.repaint();
        });
    }

    // Método para verificar si el ID ya existe
    private boolean existeIdEnDirectorio(String id) {
        File directory = new File("base/Pacientes");
        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".csv")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        reader.readLine(); // Saltar la cabecera
                        String line = reader.readLine();
                        if (line != null) {
                            String[] data = line.split(",");
                            if (data[3].equals(id)) {
                                return true; // ID ya registrado
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Error al leer el archivo: " + file.getName());
                    }
                }
            }
        }
        return false; // El ID no está registrado
    }

    private void guardarDatosEnCSV(String nombre, String primerApellido, String tipoId, String id,
            String nacimiento, String genero, String direccion, String telefono,
            String correo, String nombreContacto, String relacion, String telefonoContacto) {
        File directory = new File("base/Pacientes");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String fileName = "base/Pacientes/" + id + ".csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println(
                    "nombre,primerApellido,tipoId,id,nacimiento,genero,direccion,telefono,correo,nombreContacto,relacion,telefonoContacto");
            writer.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s%n", nombre, primerApellido, tipoId, id, nacimiento,
                    genero, direccion, telefono, correo, nombreContacto, relacion, telefonoContacto);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    private void mostrarTablaDeCredenciales() {
        leftPanel.removeAll();

        String[] columnNames = { "ID", "Nombre", "Apellido", "Género", "Teléfono" };
        tableModel = new DefaultTableModel(columnNames, 0);

        File directory = new File("base/Pacientes");
        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".csv")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        reader.readLine(); // Saltar la cabecera
                        String line = reader.readLine();
                        if (line != null) {
                            String[] data = line.split(",");
                            String id = data.length > 3 ? data[3] : "";
                            String nombre = data.length > 0 ? data[0] : "";
                            String apellido = data.length > 1 ? data[1] : "";
                            String genero = data.length > 5 ? data[5] : "";
                            String telefono = data.length > 7 ? data[7] : "";

                            tableModel.addRow(new Object[] { id, nombre, apellido, genero, telefono });
                        }
                    } catch (IOException e) {
                        System.err.println("Error al leer el archivo: " + file.getName());
                    }
                }
            }
        }

        JTable table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(event -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                pacienteSeleccionadoId = table.getValueAt(selectedRow, 0).toString(); // Guardar ID del paciente
                                                                                      // seleccionado
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        leftPanel.add(scrollPane, BorderLayout.CENTER);

        leftPanel.revalidate();
        leftPanel.repaint();
    }
    private void mostrarDetallesPaciente(String idPaciente) {
        rightPanel.removeAll(); // Limpiar el panel derecho antes de mostrar los detalles
    
        File file = new File("base/Pacientes/" + idPaciente + ".csv");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                reader.readLine(); // Saltar la cabecera
                String line = reader.readLine();
                if (line != null) {
                    String[] data = line.split(",");
    
                    // Crear el panel de detalles
                    JPanel detallesPanel = new JPanel(new GridLayout(12, 2, 5, 5));
                    detallesPanel.add(new JLabel("Nombre:"));
                    detallesPanel.add(new JLabel(data.length > 0 ? data[0] : ""));
                    detallesPanel.add(new JLabel("Primer Apellido:"));
                    detallesPanel.add(new JLabel(data.length > 1 ? data[1] : ""));
                    detallesPanel.add(new JLabel("Tipo de ID:"));
                    detallesPanel.add(new JLabel(data.length > 2 ? data[2] : ""));
                    detallesPanel.add(new JLabel("ID:"));
                    detallesPanel.add(new JLabel(data.length > 3 ? data[3] : ""));
                    detallesPanel.add(new JLabel("Nacimiento:"));
                    detallesPanel.add(new JLabel(data.length > 4 ? data[4] : ""));
                    detallesPanel.add(new JLabel("Género:"));
                    detallesPanel.add(new JLabel(data.length > 5 ? data[5] : ""));
                    detallesPanel.add(new JLabel("Dirección:"));
                    detallesPanel.add(new JLabel(data.length > 6 ? data[6] : ""));
                    detallesPanel.add(new JLabel("Teléfono:"));
                    detallesPanel.add(new JLabel(data.length > 7 ? data[7] : ""));
                    detallesPanel.add(new JLabel("Correo:"));
                    detallesPanel.add(new JLabel(data.length > 8 ? data[8] : ""));
                    detallesPanel.add(new JLabel("Nombre Contacto:"));
                    detallesPanel.add(new JLabel(data.length > 9 ? data[9] : ""));
                    detallesPanel.add(new JLabel("Relación:"));
                    detallesPanel.add(new JLabel(data.length > 10 ? data[10] : ""));
                    detallesPanel.add(new JLabel("Teléfono Contacto:"));
                    detallesPanel.add(new JLabel(data.length > 11 ? data[11] : ""));
    
                    rightPanel.add(detallesPanel, BorderLayout.CENTER);
                } else {
                    mostrarMensajeError("No se encontraron datos para el paciente seleccionado.");
                }
            } catch (IOException e) {
                mostrarMensajeError("Error al cargar los datos del paciente.");
                System.err.println("Error al leer el archivo: " + file.getName());
            }
        } else {
            mostrarMensajeError("Archivo de datos no encontrado para el paciente.");
        }
    
        rightPanel.revalidate();
        rightPanel.repaint();
    }
    
}