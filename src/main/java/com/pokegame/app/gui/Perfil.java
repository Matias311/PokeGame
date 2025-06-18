package com.pokegame.app.gui;

import com.pokegame.app.modelo.Cliente;
import com.pokegame.app.repository.ClienteRepository;
import com.pokegame.app.repository.implementacion.ClienteRepositoryImpl;
import com.pokegame.app.util.VerificarSesion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Perfil extends JPanel {

    private final Cliente cliente = VerificarSesion.getCliente();
    private final ClienteRepository<Cliente> repo = new ClienteRepositoryImpl();
    private InicioGui padre;
    private JLabel nombreUsuario;

    public Perfil(InicioGui gui) {
        this.padre = gui;

        setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

        panelCentral.add(Box.createVerticalGlue());

        nombreUsuario = new JLabel(cliente.getNombreUsuario(), SwingConstants.CENTER);
        nombreUsuario.setFont(new Font("Arial", Font.BOLD, 24));
        nombreUsuario.setAlignmentX(CENTER_ALIGNMENT);
        panelCentral.add(nombreUsuario);

        panelCentral.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnCambiarNombre = new JButton("Cambiar Nombre");
        btnCambiarNombre.setFont(new Font("Arial", Font.PLAIN, 16));
        btnCambiarNombre.setAlignmentX(CENTER_ALIGNMENT);
        btnCambiarNombre.setMaximumSize(new Dimension(200, 40));
        btnCambiarNombre.addActionListener(e -> cambiarNombreUsuario());
        panelCentral.add(btnCambiarNombre);

        panelCentral.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton logout = new JButton("Cerrar sesión");
        logout.setFont(new Font("Arial", Font.PLAIN, 18));
        logout.setAlignmentX(CENTER_ALIGNMENT);
        logout.setMaximumSize(new Dimension(200, 40));
        logout.addActionListener(e -> {
            VerificarSesion.logout();
            padre.actualizarInterfaz();
        });
        panelCentral.add(logout);

        panelCentral.add(Box.createVerticalGlue());

        add(panelCentral, BorderLayout.CENTER);
    }

    private void cambiarNombreUsuario() {
        String nuevo = JOptionPane.showInputDialog(
                this, "Ingresa el nuevo nombre de usuario:", "Cambiar Nombre", JOptionPane.PLAIN_MESSAGE);

        if (nuevo != null && !nuevo.trim().isEmpty()) {
            boolean actualizado = repo.cambiarNombreUsuario(cliente.getId(), nuevo.trim());
            if (actualizado) {
                cliente.setNombreUsuario(nuevo.trim());
                nombreUsuario.setText(nuevo.trim());
                JOptionPane.showMessageDialog(this, "Nombre actualizado.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
