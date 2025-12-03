/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Forms;
import database.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author leandra
 */
public class ReturnConfirmation extends javax.swing.JFrame {
    
        private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(ReturnConfirmation.class.getName());

    private int reservationId;
    private int movieId;
    private int userId;
    private String format;
    private String movieTitle;
    private final int currentUserId;

private void returnMovie(int reservationId, int movieId, int userId, String format) {

    Connection cn = null;
    try {
        cn = DBConnection.getConnection();
        cn.setAutoCommit(false);

        // 1) Make sure this rental belongs to this user
        String sqlUpdateRes =
            "UPDATE reservations SET status='RETURNED', return_actual_at=NOW() " +
            "WHERE reservation_id=? AND user_id=?";
        try (PreparedStatement ps1 = cn.prepareStatement(sqlUpdateRes)) {
            ps1.setInt(1, reservationId);
            ps1.setInt(2, userId);

            int updated = ps1.executeUpdate();
            if (updated == 0) {
                cn.rollback();
                JOptionPane.showMessageDialog(this,
                    "You cannot return this rental. It does not belong to your account.");
                return;
            }
        }

        // 2) Free the correct physical format
        String column = format.equalsIgnoreCase("DVD")
                        ? "available_dvd"
                        : "available_bluray";

        String sqlUpdateMovie = 
            "UPDATE movies SET " + column + " = 1 WHERE movie_id=?";
        try (PreparedStatement ps2 = cn.prepareStatement(sqlUpdateMovie)) {
            ps2.setInt(1, movieId);
            ps2.executeUpdate();
        }

        cn.commit();
        JOptionPane.showMessageDialog(this, "Movie returned successfully!");

    } catch (Exception e) {
        try { if (cn != null) cn.rollback(); } catch (Exception ignore) {}
        JOptionPane.showMessageDialog(this, "Error completing return: " + e.getMessage());
    } finally {
        try { if (cn != null) cn.setAutoCommit(true); } catch (Exception ignore) {}
    }
}
    /**
     * Creates new form Return_Confirmation
     */
    public ReturnConfirmation() {
        this(-1, -1, -1, "", "");
        initComponents();
    }
    
public ReturnConfirmation(int reservationId, int movieId,
                          int currentUserId, String format, String movieTitle) {
    this.reservationId = reservationId;
    this.movieId = movieId;
    this.currentUserId = currentUserId;
    this.format = format;
    this.movieTitle = movieTitle;

    initComponents();
    
    loadReturnDetails(); 

    // Change lblInfo to your real label name
    //jLabel1.setText("Return " + movieTitle + " (" + format + ")?");
}


    private void loadReturnDetails() {
    // These labels are guesses based on your form:
    // jLabel8, 10, 11, 12, 14, 13, 15 are on the right side.
    // Adjust if they represent something different in your design.

    LabPreMovie.setText(movieTitle);               // Movie:
    LabPreCustomer.setText("User ID: " + currentUserId);    // Customer:
    LabPreRented.setText(format);                  // Format:
    LabPreReturned.setText("loading...");
    LabPreCost.setText("loading...");
    LabPreDays.setText("loading...");
    LabPreCostDays.setText("loading...");
    
     String sql = 
        "SELECT r.reservation_date, r.return_due_at, " +
        "       DATEDIFF(IFNULL(NOW(), r.reservation_date), r.reservation_date) AS days_rented, " +
        "       m.costo " +
        "FROM reservations r " +
        "JOIN movies m ON r.movie_id = m.movie_id " +
        "WHERE r.reservation_id = ?";
     
    try (Connection cn = DBConnection.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setInt(1, reservationId);
        
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                 java.sql.Timestamp reservationDate = rs.getTimestamp("reservation_date");
                //java.sql.Timestamp dueDate = rs.getTimestamp("return_due_at");
                long daysRented = rs.getLong("days_rented");
                double costPerDay = rs.getDouble("costo");

                // Formatear las fechas para que se vean bonitas
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");

                LabPreRented.setText(sdf.format(reservationDate));  // Rented On:
                
                // Solo para mostrar: asumimos que se devuelve hoy
                java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
                LabPreReturned.setText(sdf.format(now));            // Returned On:

                LabPreCost.setText(String.format("$%.2f per day", costPerDay)); // Cost:
                LabPreDays.setText(daysRented + " days");                       // Total Days:

                double totalCost = costPerDay * daysRented;
                LabPreCostDays.setText(String.format("$%.2f", totalCost));      // Total Cost:
            }
        }
    } catch (Exception e) {
        logger.log(java.util.logging.Level.SEVERE, "Error loading return details", e);
        JOptionPane.showMessageDialog(this, "Error loading receipt details: " + e.getMessage());
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CompleteReturnButton = new javax.swing.JButton();
        CancelReturnButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        LabPreMovie = new javax.swing.JLabel();
        LabPreCustomer = new javax.swing.JLabel();
        LabPreRented = new javax.swing.JLabel();
        LabPreReturned = new javax.swing.JLabel();
        LabPreDays = new javax.swing.JLabel();
        LabPreCost = new javax.swing.JLabel();
        LabPreCostDays = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        CompleteReturnButton.setText("Complete Return");
        CompleteReturnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CompleteReturnButtonActionPerformed(evt);
            }
        });

        CancelReturnButton.setText("Cancel Return");
        CancelReturnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelReturnButtonActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Movie:");

        jLabel2.setText("Customer:");

        jLabel3.setText("Rented On:");

        jLabel4.setText("Returned On:");

        jLabel5.setText("Cost:");

        jLabel6.setText("Total Days:");

        jLabel7.setText("Total Cost:");

        LabPreMovie.setText("import db");

        LabPreCustomer.setText("import db");

        LabPreRented.setText("import db");

        LabPreReturned.setText("import db");

        LabPreDays.setText("import db");

        LabPreCost.setText("import db");

        LabPreCostDays.setText("import cost mult by days");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(CompleteReturnButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CancelReturnButton)
                .addGap(48, 48, 48))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(LabPreMovie, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabPreCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabPreRented, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabPreReturned, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabPreCost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabPreDays, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LabPreCostDays)
                        .addGap(0, 72, Short.MAX_VALUE)))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(LabPreMovie))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabPreCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(LabPreRented))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(LabPreReturned))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(LabPreCost))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(LabPreDays))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(LabPreCostDays))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CompleteReturnButton)
                    .addComponent(CancelReturnButton))
                .addGap(17, 17, 17))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CompleteReturnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CompleteReturnButtonActionPerformed
        // TODO add your handling code here:
        /* removes entire row from user rental tab, research database deletion via java code*/
        returnMovie(reservationId, movieId, currentUserId, format);

    // After success → return to movie browser
    MovieBrowser mb = new MovieBrowser(currentUserId);
    mb.setLocationRelativeTo(this);
    mb.setVisible(true);

    this.dispose();
    }//GEN-LAST:event_CompleteReturnButtonActionPerformed

    private void CancelReturnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelReturnButtonActionPerformed
        // TODO add your handling code here:
        new MovieBrowser().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_CancelReturnButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ReturnConfirmation().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelReturnButton;
    private javax.swing.JButton CompleteReturnButton;
    private javax.swing.JLabel LabPreCost;
    private javax.swing.JLabel LabPreCostDays;
    private javax.swing.JLabel LabPreCustomer;
    private javax.swing.JLabel LabPreDays;
    private javax.swing.JLabel LabPreMovie;
    private javax.swing.JLabel LabPreRented;
    private javax.swing.JLabel LabPreReturned;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables

    
}
