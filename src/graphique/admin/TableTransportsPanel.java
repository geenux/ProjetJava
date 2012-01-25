package graphique.admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

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

public class TableTransportsPanel extends JPanel {
	private DefaultTableModel reservationsModel;
	private JTable reservationsTable;
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
		
		reservationsModel = new DefaultTableModel(null, columnNames);
		reservationsTable = new JTable(null, columnNames);
		reservationsTable.setModel(reservationsModel);
		reservationsTable.setFillsViewportHeight(true); // Fill all the container
		reservationsTable.getSelectionModel().addListSelectionListener(
				new ReservationListener(reservationsTable));
	

		scrollPane = new JScrollPane(reservationsTable);
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
			int[] selectedIndexes = reservationsTable.getSelectedRows();
			for (int i=selectedIndexes.length-1;i>=0;i--) {
				int row = selectedIndexes[i];
				System.out.println(reservationsModel.getValueAt(row, 0));
				reservationsModel.removeRow(row);
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
