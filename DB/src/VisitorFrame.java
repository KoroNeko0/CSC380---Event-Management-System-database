import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

public class VisitorFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String visitorID;
	private JTable table;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VisitorFrame frame = new VisitorFrame("");
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
	public VisitorFrame(String visitorId) {
		this.visitorID = visitorId;
		setTitle("Visitor - ID: " + visitorId +"\t\t\t\t\t\t     CONTROL PAGE");
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
		panel.setBackground(new Color(25, 25, 112));
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabbedPane.addTab("VISITOR", null, panel, null);
		panel.setLayout(null);
		
		// Fetch visitor name from database
	    String visitorName = fetchVisitorName(visitorId);

	    // Display visitor name (non-editable)
	    JLabel lblVisitorName = new JLabel("Welcome " + visitorName + " !");
	    lblVisitorName.setFont(new Font("Bookman Old Style", Font.BOLD, 30));
	    lblVisitorName.setForeground(Color.WHITE);
	    lblVisitorName.setBounds(234, 24, 522, 30);
	    panel.add(lblVisitorName);
		
		JButton btnDisplayAllTickets = new JButton("VIEW TICKET");
		btnDisplayAllTickets.setBackground(new Color(0, 0, 64));
		btnDisplayAllTickets.setForeground(new Color(255, 255, 255));
		btnDisplayAllTickets.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        Connection conn = null;
		        PreparedStatement stmt = null;
		        ResultSet rs = null;

		        try {
		            conn = MySQLConnection.getConnection();
		            // Query by Visitor_ID instead of name
		            String sql = "SELECT V.Visitor_ID AS 'Visitor ID', V.VName AS 'Name', V.VPhone AS 'Phone', " +
		                         "T.Ticket_Num AS 'Ticket No', T.Price, T.Start_Date AS 'Start Date', " +
		                         "T.End_Date AS 'End Date', T.Tick_Ev_ID AS 'Event ID' " +
		                         "FROM VISITOR V JOIN TICKET T ON V.Tick_No = T.Ticket_Num " +
		                         "WHERE V.Visitor_ID = ?";
		            stmt = conn.prepareStatement(sql);
		            stmt.setString(1, visitorID); // Use visitorID instead of name
		            rs = stmt.executeQuery();
		            table.setModel(DbUtils.resultSetToTableModel(rs));
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        } finally {
		            MySQLConnection.closeConnection(rs, stmt, conn);
		        }
		    }
		});
		btnDisplayAllTickets.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		btnDisplayAllTickets.setBounds(45, 364, 262, 35);
		panel.add(btnDisplayAllTickets);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.setBackground(new Color(0, 0, 64));
		btnClear.setForeground(new Color(255, 255, 255));
		btnClear.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Get the current table model
		        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) table.getModel();
		       
		        // Remove all rows but keep the columns
		        model.setRowCount(0);
		    }
		});
		btnClear.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		btnClear.setBounds(552, 364, 262, 35);
		panel.add(btnClear);
		
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"25", "20", "15", "30"}));
		comboBox.setBounds(388, 140, 156, 25);
		panel.add(comboBox);
		
		JButton btnNewButton_1 = new JButton("BUY TICKET");
		btnNewButton_1.setBackground(new Color(0, 0, 64));
		btnNewButton_1.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		btnNewButton_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String phone = textField_1.getText().trim();
		        String priceStr = (String) comboBox.getSelectedItem();
		        String startDateStr = textField_2.getText().trim();
		        String endDateStr = textField_3.getText().trim();
		        String eventIdStr = textField_4.getText().trim();

		        if (phone.isEmpty() || startDateStr.isEmpty() || endDateStr.isEmpty() || eventIdStr.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
		            return;
		        }

		        Connection conn = null;
		        PreparedStatement stmt = null;
		        ResultSet rs = null;

		        try {
		            conn = MySQLConnection.getConnection();

		            // Check if Event_ID exists
		            String checkEventSql = "SELECT Event_ID FROM EVENT WHERE Event_ID = ?";
		            stmt = conn.prepareStatement(checkEventSql);
		            stmt.setString(1, eventIdStr);
		            rs = stmt.executeQuery();

		            if (!rs.next()) {
		                JOptionPane.showMessageDialog(null, "Event ID '" + eventIdStr + "' does not exist.");
		                return;
		            }
		            rs.close();
		            stmt.close();

		            // Check if the visitor already has a ticket
		            String checkVisitorTicketSql = "SELECT Tick_No FROM VISITOR WHERE Visitor_ID = ?";
		            stmt = conn.prepareStatement(checkVisitorTicketSql);
		            stmt.setString(1, visitorID);
		            rs = stmt.executeQuery();

		            if (rs.next()) {
		                int existingTicket = rs.getInt("Tick_No");
		                if (existingTicket != 0) {
		                    JOptionPane.showMessageDialog(null, "You already have a ticket (Ticket No: " + existingTicket + "). You cannot purchase another.");
		                    rs.close();
		                    stmt.close();
		                    return;
		                }
		            }
		            rs.close();
		            stmt.close();

		            // Validate dates
		            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		            java.sql.Date startDate = new java.sql.Date(sdf.parse(startDateStr).getTime());
		            java.sql.Date endDate = new java.sql.Date(sdf.parse(endDateStr).getTime());

		            if (endDate.before(startDate)) {
		                JOptionPane.showMessageDialog(null, "End date must be after start date.");
		                return;
		            }

		            // Generate new ticket number
		            int newTicketNum = 1;
		            String maxSql = "SELECT MAX(Ticket_Num) AS max_num FROM TICKET";
		            stmt = conn.prepareStatement(maxSql);
		            rs = stmt.executeQuery();
		            if (rs.next()) {
		                newTicketNum = rs.getInt("max_num") + 1;
		            }
		            rs.close();
		            stmt.close();

		            // Insert ticket
		            String insertTicket = "INSERT INTO TICKET (Ticket_Num, Price, Start_Date, End_Date, Tick_Ev_ID) VALUES (?, ?, ?, ?, ?)";
		            stmt = conn.prepareStatement(insertTicket);
		            stmt.setInt(1, newTicketNum);
		            stmt.setInt(2, Integer.parseInt(priceStr));
		            stmt.setDate(3, startDate);
		            stmt.setDate(4, endDate);
		            stmt.setString(5, eventIdStr);
		            stmt.executeUpdate();
		            stmt.close();

		            // Update visitor with ticket
		            String updateVisitor = "UPDATE VISITOR SET VPhone = ?, Tick_No = ? WHERE Visitor_ID = ?";
		            stmt = conn.prepareStatement(updateVisitor);
		            stmt.setString(1, phone);
		            stmt.setInt(2, newTicketNum);
		            stmt.setString(3, visitorID);
		            int rows = stmt.executeUpdate();

		            if (rows > 0) {
		                JOptionPane.showMessageDialog(null, "Ticket purchased successfully! Ticket Number: " + newTicketNum);
		                textField_1.setText("");
		                textField_2.setText("");
		                textField_3.setText("");
		                textField_4.setText("");
		                comboBox.setSelectedIndex(0);
		                btnDisplayAllTickets.doClick(); // Refresh table
		            } else {
		                JOptionPane.showMessageDialog(null, "Failed to update visitor record.");
		            }

		        } catch (ParseException ex) {
		            JOptionPane.showMessageDialog(null, "Invalid date format (use yyyy-MM-dd).");
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
		        } finally {
		            MySQLConnection.closeConnection(rs, stmt, conn);
		        }
		    }
		});



		btnNewButton_1.setBounds(600, 78, 211, 30);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_1_1_1 = new JButton("CANCLE TICKET");
		btnNewButton_1_1_1.setBackground(new Color(0, 0, 64));
		btnNewButton_1_1_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1_1_1.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                Connection conn = null;
	                PreparedStatement stmt = null;
	                ResultSet rs = null;

	                try {
	                    conn = MySQLConnection.getConnection();
	                    // 1. Get ticket number for this visitor
	                    String query = "SELECT Tick_No FROM VISITOR WHERE Visitor_ID = ?";
	                    stmt = conn.prepareStatement(query);
	                    stmt.setString(1, visitorID);
	                    rs = stmt.executeQuery();

	                    if (rs.next()) {
	                        int ticketNum = rs.getInt("Tick_No");
	                        rs.close();
	                        stmt.close();

	                        // 2. Remove ticket reference from visitor
	                        String removeRef = "UPDATE VISITOR SET Tick_No = NULL WHERE Visitor_ID = ?";
	                        stmt = conn.prepareStatement(removeRef);
	                        stmt.setString(1, visitorID);
	                        stmt.executeUpdate();
	                        stmt.close();

	                        // 3. Delete the ticket
	                        String deleteTicket = "DELETE FROM TICKET WHERE Ticket_Num = ?";
	                        stmt = conn.prepareStatement(deleteTicket);
	                        stmt.setInt(1, ticketNum);
	                        stmt.executeUpdate();

	                        JOptionPane.showMessageDialog(null, "Ticket canceled successfully.");
	                        btnDisplayAllTickets.doClick(); // Refresh table
	                    } else {
	                        JOptionPane.showMessageDialog(null, "No ticket found for this visitor.");
	                    }
	                } catch (Exception ex) {
	                    ex.printStackTrace();
	                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
	                } finally {
	                    MySQLConnection.closeConnection(rs, stmt, conn);
	                }
	            }
	        });
		btnNewButton_1_1_1.setBounds(600, 161, 214, 30);
		panel.add(btnNewButton_1_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 409, 766, 279);
		panel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(149, 140, 112, 30);
		panel.add(textField_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Phone");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(45, 146, 49, 13);
		panel.add(lblNewLabel_1_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(149, 195, 112, 30);
		panel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(149, 237, 112, 30);
		panel.add(textField_3);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Start Date");
		lblNewLabel_1_1_1.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1.setBounds(45, 203, 94, 13);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("End Date");
		lblNewLabel_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblNewLabel_1_1_1_1.setBounds(45, 243, 75, 13);
		panel.add(lblNewLabel_1_1_1_1);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(149, 282, 112, 30);
		panel.add(textField_4);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Event ID");
		lblNewLabel_1_1_1_1_1.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblNewLabel_1_1_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1_1_1.setBounds(45, 290, 75, 13);
		panel.add(lblNewLabel_1_1_1_1_1);
		
		JButton btnNewButton_1_1_1_1 = new JButton("UPDATE TICKET");
		btnNewButton_1_1_1_1.setBackground(new Color(0, 0, 64));
		btnNewButton_1_1_1_1.setForeground(new Color(255, 255, 255));
		btnNewButton_1_1_1_1.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		btnNewButton_1_1_1_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String visitorId = visitorID;
		        String newStartDate = textField_2.getText().trim(); // New Start Date (yyyy-MM-dd)
		        String newEndDate = textField_3.getText().trim();   // New End Date (yyyy-MM-dd)

		        if (visitorId == null || visitorId.isEmpty() || newStartDate.isEmpty() || newEndDate.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Please enter both new start and end dates.");
		            return;
		        }

		        Connection conn = null;
		        PreparedStatement stmt = null;
		        ResultSet rs = null;

		        try {
		            conn = MySQLConnection.getConnection();

		            // 1. Get ticket number for the visitor
		            String query = "SELECT Tick_No FROM VISITOR WHERE Visitor_ID = ?";
		            stmt = conn.prepareStatement(query);
		            stmt.setString(1, visitorId);
		            rs = stmt.executeQuery();

		            if (rs.next()) {
		                int ticketNum = rs.getInt("Tick_No");
		                rs.close();
		                stmt.close();

		                // 2. Update the ticket dates
		                String updateTicket = "UPDATE TICKET SET Start_Date = ?, End_Date = ? WHERE Ticket_Num = ?";
		                stmt = conn.prepareStatement(updateTicket);
		                stmt.setDate(1, java.sql.Date.valueOf(newStartDate));
		                stmt.setDate(2, java.sql.Date.valueOf(newEndDate));
		                stmt.setInt(3, ticketNum);

		                int rows = stmt.executeUpdate();
		                if (rows > 0) {
		                    JOptionPane.showMessageDialog(null, "Ticket dates updated successfully.");
		                    textField_2.setText("");
		                    textField_3.setText("");
		                } else {
		                    JOptionPane.showMessageDialog(null, "Failed to update ticket.");
		                }

		            } else {
		                JOptionPane.showMessageDialog(null, "Visitor not found.");
		            }

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
		        } finally {
		            MySQLConnection.closeConnection(rs, stmt, conn);
		        }
		    }
		});
		btnNewButton_1_1_1_1.setBounds(600, 239, 214, 30);
		panel.add(btnNewButton_1_1_1_1);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setForeground(Color.WHITE);
		lblPrice.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
		lblPrice.setBounds(319, 146, 45, 13);
		panel.add(lblPrice);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\mahac\\Downloads\\image (2).jpg"));
		lblNewLabel.setBounds(0, 0, 848, 709);
		panel.add(lblNewLabel);
	}
	
	private String fetchVisitorName(String visitorId) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String name = "";

	    try {
	        conn = MySQLConnection.getConnection();
	        String sql = "SELECT VName FROM VISITOR WHERE Visitor_ID = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, visitorId);
	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            name = rs.getString("VName");
	        } else {
	            JOptionPane.showMessageDialog(null, "Visitor not found in database.");
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    } finally {
	        MySQLConnection.closeConnection(rs, stmt, conn);
	    }
	    return name;
	}
}
