package graphique.admin;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import logiqueMetier.Admin;
import logiqueMetier.ServeurV2;

public class FenetreClientAdmin extends JFrame {
	Admin admin;
	
	public FenetreClientAdmin() {
		super();
		admin = new Admin(new ServeurV2());
		build();
	}

	private void build() {
		setTitle("Administration"); 
		setSize(800,600); 
		setLocationRelativeTo(null); 
		setResizable(true); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setContentPane(buildContentPane());
	}

	private JPanel buildContentPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		panel.add(new AjoutTransportPanel(admin));		
		panel.add(new AjoutTrajetPanel(admin));
		panel.add(new AjoutVillePanel(admin));
		panel.add(new GestionReservationsPanel(admin), BorderLayout.CENTER);
		
		JButton quit = new JButton(new QuitAction("Quitter"));
		panel.add(quit);
		
		return panel;
	}

	private class AjoutClientAction extends AbstractAction {
		public AjoutClientAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					AjoutTrajetPanel fenetre = new AjoutTrajetPanel(admin);
					fenetre.setVisible(true);
				}
			});
		}
	}

	private class AjoutTransportAction extends AbstractAction {
		public AjoutTransportAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					AjoutTransportPanel fenetre = new AjoutTransportPanel(admin);
					fenetre.setVisible(true);
				}
			});
		}
	}
	private class AjoutVilleAction extends AbstractAction {
		public AjoutVilleAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					AjoutVillePanel fenetre = new AjoutVillePanel(admin);
					fenetre.setVisible(true);
				}
			});
		}
	}
	private class QuitAction extends AbstractAction {
		public QuitAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}
}
