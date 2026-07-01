import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;

import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class OrganizerFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String organizerId; // store the passed ID
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTable table;
	private JTable table_1;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Pass a sample organizer ID for testing
					OrganizerFrame frame = new OrganizerFrame("");
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
	
	
	public OrganizerFrame(String organizerId) { 
		this.organizerId = organizerId;
		setTitle("Organizer - ID: " + organizerId +"\t\t\t\t\t\tCONTROL PAGE"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 887, 783);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 0, 853, 736);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(133, 98, 67));
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabbedPane.addTab("Event", null, panel, null);//change tab name
		panel.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("ADD");
		btnNewButton_1.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			   public void actionPerformed(ActionEvent e) {
			        // Capture values from text fields
			        String eventId = textField.getText();       // Event_ID
			        String name = textField_1.getText();        // Event_Name
			        String description = textField_2.getText(); // description
			        String capacity = textField_5.getText();    // Capacity
			        String startTime = textField_3.getText();   // Start_Time
			        String endTime = textField_4.getText();     // End_Time
			        String budget = textField_8.getText();      // Fund_Budget
			        String sponsorId = textField_6.getText();   // Spr_ID
			        String venueId = textField_7.getText();     // Ven_ID 

			        // Database insert query (correcting column names and adding correct number of parameters)
			        String insertQuery = "INSERT INTO event (Event_ID, Event_Name, description, Capacity, Start_Time, End_Time, Fund_Budget, Spr_ID, Ven_ID) "
			                             + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			        // Connect to the database
			        Connection conn = MySQLConnection.getConnection();
			        PreparedStatement stmt = null;

			        try {
			            // Prepare statement and set parameters
			            stmt = conn.prepareStatement(insertQuery);
			            stmt.setString(1, eventId);
			            stmt.setString(2, name);
			            stmt.setString(3, description);
			            stmt.setInt(4, Integer.parseInt(capacity));  // Capacity is an INT in the database
			            stmt.setTime(5, Time.valueOf(startTime));    // Convert to SQL Time
			            stmt.setTime(6, Time.valueOf(endTime));      // Convert to SQL Time
			            stmt.setString(7, budget);
			            stmt.setString(8, sponsorId);
			            stmt.setString(9, venueId);

			            // Execute the query
			            int rowsAffected = stmt.executeUpdate();

			            // Provide feedback
			            if (rowsAffected > 0) {
			                JOptionPane.showMessageDialog(null, "Event added successfully!");
			            } else {
			                JOptionPane.showMessageDialog(null, "Failed to add event.");
			            }

			        } catch (SQLException ex) {
			            ex.printStackTrace();
			            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
			        } catch (IllegalArgumentException ex) {
			            // Handle invalid time format or other input errors
			            JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage());
			        } finally {
			            // Close resources
			            MySQLConnection.closeConnection(null, stmt, conn);
			        }
			    }
		});
		btnNewButton_1.setBounds(651, 42, 161, 30);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("DELETE");
		btnNewButton_1_1.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		btnNewButton_1_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String eventId = textField.getText().trim(); // Get Event_ID from the text field

		        if (eventId.isEmpty()) {
		            javax.swing.JOptionPane.showMessageDialog(null, "Please enter an Event ID to delete.");
		            return;
		        }

		        Connection conn = null;
		        PreparedStatement stmt = null;

		        try {
		            conn = MySQLConnection.getConnection();
		            String sql = "DELETE FROM EVENT WHERE Event_ID = ?";
		            stmt = conn.prepareStatement(sql);
		            stmt.setString(1, eventId);

		            int rowsAffected = stmt.executeUpdate();

		            if (rowsAffected > 0) {
		                javax.swing.JOptionPane.showMessageDialog(null, "Event deleted successfully.");
		            } else {
		                javax.swing.JOptionPane.showMessageDialog(null, "No event found with the given ID.");
		            }

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            javax.swing.JOptionPane.showMessageDialog(null, "Error while deleting the event.");
		        } finally {
		            MySQLConnection.closeConnection(null, stmt, conn);
		        }
		    }
		});
		
		btnNewButton_1_1.setBounds(651, 162, 161, 30);
		panel.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_1_1 = new JButton("UPDATE");
		btnNewButton_1_1_1.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String eventId = textField.getText().trim(); // Event_ID (used in WHERE clause)

		        if (eventId.isEmpty()) {
		            javax.swing.JOptionPane.showMessageDialog(null, "Please enter Event ID to update.");
		            return;
		        }

		        // Collect field values
		        String eventName = textField_1.getText().trim();     // Event_Name
		        String startTime = textField_3.getText().trim();     // Start_Time
		        String endTime = textField_4.getText().trim();       // End_Time
		        String capacity = textField_5.getText().trim();      // Capacity
		        String description = textField_2.getText().trim();   // Description
		        String sponsorId = textField_6.getText().trim();     // Spr_ID
		        String venueId = textField_7.getText().trim();       // Ven_ID
		        String budget = textField_8.getText().trim();        // Fund_Budget

		        // Dynamically build SET clause
		        StringBuilder sqlBuilder = new StringBuilder("UPDATE EVENT SET ");
		        java.util.List<String> fields = new java.util.ArrayList<>();
		        java.util.List<String> values = new java.util.ArrayList<>();

		        if (!eventName.isEmpty()) {
		            fields.add("Event_Name = ?");
		            values.add(eventName);
		        }
		        if (!startTime.isEmpty()) {
		            fields.add("Start_Time = ?");
		            values.add(startTime);
		        }
		        if (!endTime.isEmpty()) {
		            fields.add("End_Time = ?");
		            values.add(endTime);
		        }
		        if (!capacity.isEmpty()) {
		            fields.add("Capacity = ?");
		            values.add(capacity);
		        }
		        if (!description.isEmpty()) {
		            fields.add("description = ?");
		            values.add(description);
		        }
		        if (!sponsorId.isEmpty()) {
		            fields.add("Spr_ID = ?");
		            values.add(sponsorId);
		        }
		        if (!venueId.isEmpty()) {
		            fields.add("Ven_ID = ?");
		            values.add(venueId);
		        }
		        if (!budget.isEmpty()) {
		            fields.add("Fund_Budget = ?");
		            values.add(budget);
		        }

		        if (fields.isEmpty()) {
		            javax.swing.JOptionPane.showMessageDialog(null, "Please fill at least one field to update.");
		            return;
		        }

		        // Final SQL query
		        sqlBuilder.append(String.join(", ", fields));
		        sqlBuilder.append(" WHERE Event_ID = ?");

		        Connection conn = null;
		        PreparedStatement stmt = null;

		        try {
		            conn = MySQLConnection.getConnection();
		            stmt = conn.prepareStatement(sqlBuilder.toString());

		            // Set values dynamically
		            int index = 1;
		            for (String val : values) {
		                stmt.setString(index++, val);
		            }
		            stmt.setString(index, eventId); // For WHERE clause

		            int rowsAffected = stmt.executeUpdate();

		            if (rowsAffected > 0) {
		                javax.swing.JOptionPane.showMessageDialog(null, "Event updated successfully.");
		            } else {
		                javax.swing.JOptionPane.showMessageDialog(null, "No event found with that ID.");
		            }

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            javax.swing.JOptionPane.showMessageDialog(null, "Error while updating the event.");
		        } finally {
		            MySQLConnection.closeConnection(null, stmt, conn);
		        }
		    }
		});
		btnNewButton_1_1_1.setBounds(651, 288, 161, 30);
		panel.add(btnNewButton_1_1_1);
		
		textField = new JTextField();
		textField.setBounds(110, 43, 112, 30);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblNewLabel.setBounds(55, 51, 45, 13);
		panel.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(110, 94, 112, 30);
		panel.add(textField_1);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblName.setForeground(new Color(255, 255, 255));
		lblName.setBounds(31, 102, 69, 13);
		panel.add(lblName);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(359, 176, 198, 103);
		panel.add(textField_2);
		
		JLabel lblDescrption = new JLabel("Description");
		lblDescrption.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblDescrption.setForeground(new Color(255, 255, 255));
		lblDescrption.setBounds(247, 210, 90, 13);
		panel.add(lblDescrption);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(110, 144, 112, 30);
		panel.add(textField_3);
		
		JLabel lblCapacity = new JLabel("Start Time");
		lblCapacity.setForeground(new Color(255, 255, 255));
		lblCapacity.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblCapacity.setBounds(10, 152, 90, 13);
		panel.add(lblCapacity);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(110, 191, 112, 30);
		panel.add(textField_4);
		
		JLabel lblEndTime = new JLabel("End Time");
		lblEndTime.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblEndTime.setForeground(new Color(255, 255, 255));
		lblEndTime.setBounds(10, 197, 90, 13);
		panel.add(lblEndTime);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(110, 302, 112, 30);
		panel.add(textField_5);
		
		JLabel lblBudget = new JLabel("Capacity");
		lblBudget.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblBudget.setForeground(new Color(255, 255, 255));
		lblBudget.setBounds(31, 305, 69, 13);
		panel.add(lblBudget);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(359, 43, 112, 30);
		panel.add(textField_6);
		
		JLabel lblSponderId = new JLabel("Sponder ID");
		lblSponderId.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblSponderId.setForeground(new Color(255, 255, 255));
		lblSponderId.setBounds(259, 51, 90, 13);
		panel.add(lblSponderId);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(359, 94, 112, 30);
		panel.add(textField_7);
		
		JLabel lblVenueId = new JLabel("Venue ID");
		lblVenueId.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblVenueId.setForeground(new Color(255, 255, 255));
		lblVenueId.setBounds(259, 102, 90, 13);
		panel.add(lblVenueId);
		
		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(110, 249, 112, 30);
		panel.add(textField_8);
		
		JLabel lblBudget_1 = new JLabel("Budget");
		lblBudget_1.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblBudget_1.setForeground(new Color(255, 255, 255));
		lblBudget_1.setBounds(31, 255, 69, 13);
		panel.add(lblBudget_1);
		
		JButton btnNewButton = new JButton("DISPLAY ALL EVENTS");
		btnNewButton.setBounds(39, 371, 262, 35);
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            // Establish the database connection
		            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/event", "root", "Maha123123");

		            // SQL query to fetch event data from the 'event' table
		            String sql = "SELECT * FROM event";
		            PreparedStatement pst = conn.prepareStatement(sql);
		            ResultSet rs = pst.executeQuery();
		            table.setModel(DbUtils.resultSetToTableModel(rs));
		            
		           

		        } catch (Exception ee) {
		            ee.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Error: " + ee.getMessage());
		        }
		    }
		});
		
				btnNewButton.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
				
				JButton btnClear = new JButton("CLEAR");
				btnClear.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        // Get the current table model
				        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
				       
				        // Remove all rows but keep the columns
				        model.setRowCount(0);
				    }
				});
				btnClear.setBounds(550, 371, 262, 35);
				panel.add(btnClear);
				btnClear.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(31, 416, 793, 265);
				panel.add(scrollPane);
				
				table = new JTable();
				scrollPane.setViewportView(table);
				
				JLabel lblNewLabel_3 = new JLabel("");
				lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\mahac\\Downloads\\image (5) (1).jpg"));
				lblNewLabel_3.setBounds(0, -17, 861, 726);
				panel.add(lblNewLabel_3);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(133, 98, 67));
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabbedPane.addTab("Participant", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(32, 415, 793, 265);
		panel_1.add(scrollPane_1);
		
		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		
		JButton btnDisplayEvent = new JButton("DISPLAY ALL PARTICIPANTS");
		btnDisplayEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
			            // Establish the database connection
			            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/event", "root", "Maha123123");

			            // SQL query to fetch event data from the 'event' table
			            String sql = "SELECT * FROM participant";
			            PreparedStatement pst = conn.prepareStatement(sql);
			            ResultSet rs = pst.executeQuery();
			            table_1.setModel(DbUtils.resultSetToTableModel(rs));
			            
			           

			        } catch (Exception ee) {
			            ee.printStackTrace();
			            JOptionPane.showMessageDialog(null, "Error: " + ee.getMessage());
			        }
			}
		});
		btnDisplayEvent.setBounds(32, 359, 292, 35);
		btnDisplayEvent.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		panel_1.add(btnDisplayEvent);
		
		JButton btnClear_1 = new JButton("CLEAR");
		btnClear_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Get the current table model
		        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table_1.getModel();
		       
		        // Remove all rows but keep the columns
		        model.setRowCount(0);
		    }
		});
		
		btnClear_1.setBounds(561, 359, 262, 35);
		btnClear_1.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		panel_1.add(btnClear_1);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setBounds(75, 68, 45, 13);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblPhone = new JLabel("Name");
		lblPhone.setForeground(new Color(255, 255, 255));
		lblPhone.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblPhone.setBounds(75, 118, 45, 13);
		panel_1.add(lblPhone);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setBounds(95, 178, 45, 13);
		panel_1.add(lblNewLabel_1_1);
		
		JLabel lblPhone_2 = new JLabel("Phone");
		lblPhone_2.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblPhone_2.setForeground(new Color(255, 255, 255));
		lblPhone_2.setBounds(73, 178, 67, 13);
		panel_1.add(lblPhone_2);
		
		textField_9 = new JTextField();
		textField_9.setColumns(10);
		textField_9.setBounds(150, 60, 112, 30);
		panel_1.add(textField_9);
		
		textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(150, 115, 112, 30);
		panel_1.add(textField_10);
		
		textField_11 = new JTextField();
		textField_11.setColumns(10);
		textField_11.setBounds(150, 175, 112, 30);
		panel_1.add(textField_11);
		
		JButton btnNewButton_1_2 = new JButton("ADD");
		btnNewButton_1_2.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		btnNewButton_1_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String id = textField_9.getText().trim();
		        String name = textField_10.getText().trim();
		        String phone = textField_11.getText().trim();

		        if (id.isEmpty() || name.isEmpty() || phone.isEmpty()) {
		            javax.swing.JOptionPane.showMessageDialog(null, "Please fill all participant fields.");
		            return;
		        }

		        Connection conn = null;
		        PreparedStatement stmt = null;

		        try {
		            conn = MySQLConnection.getConnection();
		            String sql = "INSERT INTO PARTICIPANT (Participant_ID, PName, Phone) VALUES (?, ?, ?)";
		            stmt = conn.prepareStatement(sql);
		            stmt.setString(1, id);
		            stmt.setString(2, name);
		            stmt.setString(3, phone);

		            int rows = stmt.executeUpdate();
		            if (rows > 0) {
		                javax.swing.JOptionPane.showMessageDialog(null, "Participant added successfully.");
		                textField_9.setText("");
		                textField_10.setText("");
		                textField_11.setText("");
		            } else {
		                javax.swing.JOptionPane.showMessageDialog(null, "Failed to add participant.");
		            }
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            javax.swing.JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
		        } finally {
		            MySQLConnection.closeConnection(null, stmt, conn);
		        }
		    }
		});
		btnNewButton_1_2.setBounds(613, 51, 195, 30);
		panel_1.add(btnNewButton_1_2);
		
		JButton btnNewButton_1_1_2 = new JButton("DELETE");
		btnNewButton_1_1_2.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		btnNewButton_1_1_2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String id = textField_9.getText().trim();

		        if (id.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Enter Participant ID to delete.");
		            return;
		        }

		        Connection conn = null;
		        PreparedStatement stmt = null;

		        try {
		            conn = MySQLConnection.getConnection();
		            
		            // First check if participant exists
		            String checkSql = "SELECT COUNT(*) FROM PARTICIPANT WHERE Participant_ID = ?";
		            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
		            checkStmt.setString(1, id);
		            ResultSet rs = checkStmt.executeQuery();
		            rs.next();
		            int count = rs.getInt(1);
		            
		            if (count == 0) {
		                JOptionPane.showMessageDialog(null, "No participant found with ID: " + id);
		                return;
		            }

		            // Disable foreign key checks temporarily (if needed)
		            Statement disableFK = conn.createStatement();
		            disableFK.execute("SET FOREIGN_KEY_CHECKS=0");
		            
		            // Delete from related tables first if needed
		            // Example: 
		            // String deleteRegistrations = "DELETE FROM EVENT_REGISTRATION WHERE Participant_ID = ?";
		            // PreparedStatement delRegStmt = conn.prepareStatement(deleteRegistrations);
		            // delRegStmt.setString(1, id);
		            // delRegStmt.executeUpdate();
		            
		            // Now delete the participant
		            String sql = "DELETE FROM PARTICIPANT WHERE Participant_ID = ?";
		            stmt = conn.prepareStatement(sql);
		            stmt.setString(1, id);

		            int rows = stmt.executeUpdate();
		            
		            // Re-enable foreign key checks
		            disableFK.execute("SET FOREIGN_KEY_CHECKS=1");
		            
		            if (rows > 0) {
		                JOptionPane.showMessageDialog(null, "Participant deleted successfully.");
		                textField_9.setText("");
		                textField_10.setText("");
		                textField_11.setText("");
		                
		                // Refresh the table
		                btnDisplayEvent.doClick();
		            } else {
		                JOptionPane.showMessageDialog(null, "Failed to delete participant.");
		            }

		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
		        } finally {
		            MySQLConnection.closeConnection(null, stmt, conn);
		        }
		    }
		});

		btnNewButton_1_1_2.setBounds(613, 161, 195, 30);
		panel_1.add(btnNewButton_1_1_2);
		
		JButton btnNewButton_1_1_1_1 = new JButton("UPDATE");
		btnNewButton_1_1_1_1.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		btnNewButton_1_1_1_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String id = textField_9.getText().trim();
		        String name = textField_10.getText().trim();
		        String phone = textField_11.getText().trim();

		        if (id.isEmpty()) {
		            javax.swing.JOptionPane.showMessageDialog(null, "Enter Participant ID to update.");
		            return;
		        }

		        // Build dynamic update
		        StringBuilder sqlBuilder = new StringBuilder("UPDATE PARTICIPANT SET ");
		        java.util.List<String> fields = new java.util.ArrayList<>();
		        java.util.List<String> values = new java.util.ArrayList<>();

		        if (!name.isEmpty()) {
		            fields.add("PName = ?");
		            values.add(name);
		        }
		        if (!phone.isEmpty()) {
		            fields.add("Phone = ?");
		            values.add(phone);
		        }

		        if (fields.isEmpty()) {
		            javax.swing.JOptionPane.showMessageDialog(null, "Please enter at least one field to update.");
		            return;
		        }

		        sqlBuilder.append(String.join(", ", fields));
		        sqlBuilder.append(" WHERE Participant_ID = ?");

		        Connection conn = null;
		        PreparedStatement stmt = null;

		        try {
		            conn = MySQLConnection.getConnection();
		            stmt = conn.prepareStatement(sqlBuilder.toString());

		            int index = 1;
		            for (String val : values) {
		                stmt.setString(index++, val);
		            }
		            stmt.setString(index, id); // for WHERE clause

		            int rows = stmt.executeUpdate();
		            if (rows > 0) {
		                javax.swing.JOptionPane.showMessageDialog(null, "Participant updated successfully.");
		            } else {
		                javax.swing.JOptionPane.showMessageDialog(null, "No participant found with that ID.");
		            }

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            javax.swing.JOptionPane.showMessageDialog(null, "Error updating participant.");
		        } finally {
		            MySQLConnection.closeConnection(null, stmt, conn);
		        }
		    }
		});
		btnNewButton_1_1_1_1.setBounds(613, 274, 195, 30);
		panel_1.add(btnNewButton_1_1_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\mahac\\Downloads\\image (4) (1).jpg"));
		lblNewLabel_2.setBounds(0, 0, 848, 709);
		panel_1.add(lblNewLabel_2);
	}
}
