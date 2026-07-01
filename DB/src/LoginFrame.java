import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

public class LoginFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textField;
    private JPasswordField passwordField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginFrame frame = new LoginFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public LoginFrame() {
    	setTitle("LOGIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 562, 427);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(192, 192, 192));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnNewButton = new JButton("LOG IN");
        btnNewButton.setForeground(new Color(133, 98, 67));
        btnNewButton.setBackground(Color.WHITE);
        btnNewButton.setFont(new Font("Bookman Old Style", Font.BOLD, 21));
        btnNewButton.setBounds(191, 285, 161, 45);
        contentPane.add(btnNewButton);

        JLabel lblNewLabel = new JLabel("LOG IN PAGE");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBackground(new Color(153, 102, 51));
        lblNewLabel.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 32));
        lblNewLabel.setBounds(158, 35, 247, 25);
        contentPane.add(lblNewLabel);

        JComboBox comboBox = new JComboBox();
        comboBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Organizer", "Visitor"}));
        comboBox.setBounds(191, 99, 171, 33);
        contentPane.add(comboBox);

        textField = new JTextField();
        textField.setBounds(191, 142, 171, 33);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(191, 185, 171, 33);
        contentPane.add(passwordField);

        JLabel lblNewLabel_2 = new JLabel("USER ID");
        lblNewLabel_2.setForeground(Color.WHITE);
        lblNewLabel_2.setFont(new Font("Bookman Old Style", Font.BOLD, 18));
        lblNewLabel_2.setBounds(51, 144, 103, 25);
        contentPane.add(lblNewLabel_2);

        JLabel lblNewLabel_2_1 = new JLabel("PASSWORD");
        lblNewLabel_2_1.setForeground(Color.WHITE);
        lblNewLabel_2_1.setFont(new Font("Bookman Old Style", Font.BOLD, 18));
        lblNewLabel_2_1.setBounds(51, 186, 140, 25);
        contentPane.add(lblNewLabel_2_1);
        
        JLabel lblNewLabel_2_2 = new JLabel("ENTRY TYPE");
        lblNewLabel_2_2.setForeground(Color.WHITE);
        lblNewLabel_2_2.setFont(new Font("Bookman Old Style", Font.BOLD, 18));
        lblNewLabel_2_2.setBounds(51, 103, 130, 25);
        contentPane.add(lblNewLabel_2_2);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\mahac\\Downloads\\image (3) (1).jpg"));
        lblNewLabel_1.setBounds(22, 0, 704, 400);
        contentPane.add(lblNewLabel_1);

        // Adding the ActionListener to the button
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userId = textField.getText();
                String selectedRole = comboBox.getSelectedItem().toString();

                Connection conn = null;
                PreparedStatement stmt = null;
                ResultSet rs = null;

                try {
                    conn = MySQLConnection.getConnection(); // Use your custom class

                    if (selectedRole.equals("Organizer")) {
                        stmt = conn.prepareStatement("SELECT * FROM organizer WHERE Badge_ID = ?");
                    } else {
                        stmt = conn.prepareStatement("SELECT * FROM visitor WHERE Visitor_ID = ?");
                    }

                    stmt.setString(1, userId);
                    rs = stmt.executeQuery();

                    if (rs.next()) {
                        if (selectedRole.equals("Organizer")) {
                            new OrganizerFrame(userId).setVisible(true);
                        } else {
                            new VisitorFrame(userId).setVisible(true);
                        }
                        dispose(); // Close login frame
                    } else {
                        JOptionPane.showMessageDialog(null, "ID not found in the " + selectedRole + " table.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // Clean up resources using your method
                    MySQLConnection.closeConnection(rs, stmt, conn);
                }
            }
        });

    }
}
