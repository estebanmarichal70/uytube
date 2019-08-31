/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;

import java.awt.Font;

/**
 *
 * @author esteban
 */
public class VentanaEmergente extends javax.swing.JDialog {

    Font manjari;
    /**
     * Creates new form UsuarioIncorrecto
     * @param parent
     * @param modal
     * @param manjari
     */
    public VentanaEmergente(java.awt.Frame parent, boolean modal, Font manjari) {
        this.manjari = manjari;
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BarraSuperior = new javax.swing.JPanel();
        BotonCerrar = new javax.swing.JButton();
        Panel = new javax.swing.JPanel();
        Texto = new javax.swing.JLabel();
        BotonAceptar = new javax.swing.JButton();
        DragPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(353, 144));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(null);

        BarraSuperior.setBackground(new java.awt.Color(73, 73, 73));

        BotonCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/IconCerrar.png"))); // NOI18N
        BotonCerrar.setBorder(null);
        BotonCerrar.setBorderPainted(false);
        BotonCerrar.setContentAreaFilled(false);
        BotonCerrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/IconCerrar2.png"))); // NOI18N
        BotonCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BarraSuperiorLayout = new javax.swing.GroupLayout(BarraSuperior);
        BarraSuperior.setLayout(BarraSuperiorLayout);
        BarraSuperiorLayout.setHorizontalGroup(
            BarraSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BarraSuperiorLayout.createSequentialGroup()
                .addContainerGap(317, Short.MAX_VALUE)
                .addComponent(BotonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        BarraSuperiorLayout.setVerticalGroup(
            BarraSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BarraSuperiorLayout.createSequentialGroup()
                .addComponent(BotonCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(BarraSuperior);
        BarraSuperior.setBounds(0, 0, 360, 33);

        Panel.setLayout(null);

        Texto.setFont(manjari);
        Panel.add(Texto);
        Texto.setBounds(34, 43, 305, 40);

        BotonAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_aceptar_2.png"))); // NOI18N
        BotonAceptar.setBorder(null);
        BotonAceptar.setBorderPainted(false);
        BotonAceptar.setContentAreaFilled(false);
        BotonAceptar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/images/btn_aceptar_1.png"))); // NOI18N
        BotonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAceptarActionPerformed(evt);
            }
        });
        Panel.add(BotonAceptar);
        BotonAceptar.setBounds(133, 94, 85, 39);

        getContentPane().add(Panel);
        Panel.setBounds(0, 0, 360, 150);

        DragPanel.setBackground(new java.awt.Color(12, 129, 129));
        DragPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                DragPanelMouseDragged(evt);
            }
        });
        DragPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DragPanelMousePressed(evt);
            }
        });

        javax.swing.GroupLayout DragPanelLayout = new javax.swing.GroupLayout(DragPanel);
        DragPanel.setLayout(DragPanelLayout);
        DragPanelLayout.setHorizontalGroup(
            DragPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        DragPanelLayout.setVerticalGroup(
            DragPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        getContentPane().add(DragPanel);
        DragPanel.setBounds(0, 0, 360, 150);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAceptarActionPerformed
        dispose();
    }//GEN-LAST:event_BotonAceptarActionPerformed
int xx, xy;
    private void DragPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DragPanelMouseDragged
        // TODO add your handling code here:

        //source to drag
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x-xx,y-xy);
    }//GEN-LAST:event_DragPanelMouseDragged

    private void DragPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DragPanelMousePressed
        // TODO add your handling code here:
        //drag this pane
        xx = evt.getX();
        xy = evt.getY();
    }//GEN-LAST:event_DragPanelMousePressed

    private void BotonCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonCerrarActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_BotonCerrarActionPerformed

public void CambioTexto(String texto){
    Texto.setText(texto);
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BarraSuperior;
    private javax.swing.JButton BotonAceptar;
    private javax.swing.JButton BotonCerrar;
    private javax.swing.JPanel DragPanel;
    private javax.swing.JPanel Panel;
    private javax.swing.JLabel Texto;
    // End of variables declaration//GEN-END:variables
}
