package graphique.admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import logiqueMetier.Serveur;
import objets.Vehicule;

public class TableTransportsPanel extends JPanel {
	private DefaultTableModel transportModel;
	private JTable transportTable;
	private JScrollPane scrollPane;

	private Serveur serveur;
	
	public TableTransportsPanel(Serveur s) {
		super();
		serveur = s;
		build();
	}

	private void build() {
		setBorder(BorderFactory.createTitledBorder("Gestion des trajets"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		buildTrajetsTable();
		buildButtons();
	}

	private void buildTrajetsTable() {
		   /* protected String vehicule;
		    protected TypeVehicule type;
		    protected int capacite;
		    protected int identifiant;
		    protected List<ClassesRepas> classes;
		    protected List<ClassesRepas> repas; */
		String[] columnNames = { "Id", "Nom du véhicule", "Type de véhicule", "Capacité d'accueil"};
		
		transportModel = new DefaultTableModel(null, columnNames);
		transportTable = new JTable(null, columnNames);
		transportTable.setModel(transportModel);
		transportTable.setFillsViewportHeight(true); // Fill all the container
		transportTable.getSelectionModel().addListSelectionListener(
				new ReservationListener(transportTable)); 
		
		ArrayList<Vehicule> vehicules = serveur.getVehicules();
		Vector<Object> l = null;
		for(Vehicule v : vehicules) {
			l = new Vector<Object>();
			l.add(v.getIdentifiant());
			l.add(v.getVehicule());
			l.add(v.getType());
			l.add(v.getCapacite());
			transportModel.addRow(l);
		}
		

		scrollPane = new JScrollPane(transportTable);
		add(scrollPane);
	}

	private void buildButtons() {

		JPanel panel = new JPanel();
		panel.setLayout( new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(new JButton(new DeleteAction("Supprimer")), BorderLayout.CENTER);
		panel.add(new JButton(new SaveAction("Enregistrer")), BorderLayout.CENTER);
		add(panel);
	}


	public class SaveAction extends AbstractAction {
		public SaveAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Enregistré !");
		}
	}
	public class DeleteAction extends AbstractAction {
		public DeleteAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Supprimé !");
			int[] selectedIndexes = transportTable.getSelectedRows();
			for (int i=selectedIndexes.length-1;i>=0;i--) {
				int row = selectedIndexes[i];
				System.out.println(transportModel.getValueAt(row, 0));
				transportModel.removeRow(row);
				// XXX: Call the delete method
			}	  
		}
	}

	private class ReservationListener implements ListSelectionListener {
		JTable table;

		// It is necessary to keep the table since it is not possible
		// to determine the table from the event's source
		ReservationListener(JTable table) {
			this.table = table;
		}

		public void valueChanged(ListSelectionEvent e) {
			// If cell selection is enabled, both row and column change events are fired
			if (e.getSource() == table.getSelectionModel() && table.getRowSelectionAllowed()) {
				// Column selection changed
				int first = e.getFirstIndex();
				int last = e.getLastIndex();
				System.out.println("Selection changed");
			} 
		}
	}

}
