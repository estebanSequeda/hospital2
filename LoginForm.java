
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class LoginForm extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel imageLabel;
    private final String CREDENTIALS_FILE = "Base/Credenciales.csv";
    private Map<String, User> credenciales;

    public LoginForm() {
        setTitle("Formulario de Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setLayout(new BorderLayout());

        // Cargar credenciales de archivo CSV o crear archivo si no existe
        cargarCredenciales();

        // Panel de la imagen
        JPanel imagePanel = new JPanel();
        imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("logo.png");
        imageLabel.setIcon(imageIcon);
        imagePanel.add(imageLabel);

        // Panel de los campos de login
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JLabel userLabel = new JLabel("Usuario:");
        JLabel passLabel = new JLabel("Contraseña:");
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loginPanel.add(userLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passLabel);
        loginPanel.add(passwordField);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Iniciar sesión");
        JButton cancelButton = new JButton("Cancelar");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticar();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Cierra la aplicación
            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        // Añadimos los paneles al JFrame
        add(imagePanel, BorderLayout.NORTH);
        add(loginPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void autenticar() {
        String usuario = usernameField.getText();
        String contrasena = new String(passwordField.getPassword());

        // Verifica si el usuario y la contraseña existen en el archivo de credenciales
        if (credenciales.containsKey(usuario) && credenciales.get(usuario).getPassword().equals(contrasena)) {
            User user = credenciales.get(usuario);
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
            this.dispose(); // Cierra el frame actual
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarCredenciales() {
        credenciales = new HashMap<>();
        File archivo = new File(CREDENTIALS_FILE);

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 6) { // usuario, contraseña, nombre, edad, id, cargo
                    String usuario = partes[0];
                    String contrasena = partes[1];
                    String nombre = partes[2];
                    int edad = Integer.parseInt(partes[3]);
                    String id = partes[4];
                    String cargo = partes[5];
                    credenciales.put(usuario, new User(usuario, contrasena, nombre, edad, id, cargo));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });
    }
}


