package graphique.admin;

import graphique.models.ReservationsTableModel;
import graphique.widgets.AbstractTablePanel;
import graphique.widgets.TableSpinnerEditor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import logiqueMetier.Serveur;
import objets.Trajet;
import objets.Vehicule;
import objets.Ville;

public class TableReservationsPanel extends AbstractTablePanel {
	private TableSpinnerEditor dateDepartSpinner;
	private TableSpinnerEditor dateArriveeSpinner;

	private ArrayList<Trajet> trajets;
	private ArrayList<Ville> villes;

	private Serveur serveur;

	public TableReservationsPanel(Serveur s) {
		super(s);
	//	trajets = serveur.getR();
		villes = serveur.getVilles();
		build();
	}

	private void build() {
		setBorder(BorderFactory.createTitledBorder("Gestion des trajets"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		buildReservationsTable();
		buildButtons();
	}

	private void buildReservationsTable() {
		/*
		    private Passager passager;
    		private Trajet trajet;
    		private boolean modifiable;
    		private int identifiant;
    		private int placesVoulues;
    		private boolean prendCouchette;
    		private Map<String, Boolean> prendRepas;
		 */
		String[] columnNames = { "Départ", "Arrivée", "Date départ",
				"Date arrivée", "Transport" };

		// Create a SpinnerDateModel with current date as the initial value.
		dateDepartSpinner = new TableSpinnerEditor(new SpinnerDateModel());
		dateArriveeSpinner = new TableSpinnerEditor(new SpinnerDateModel());

		model = new ReservationsTableModel<Trajet>(trajets);
		model.setColumnNames(columnNames);
		table = new JTable();
		table.setModel(model);
		table.setFillsViewportHeight(true); // Fill all the
		// container
		table.getModel().addTableModelListener(new CellListener()); 

		JComboBox combo = buildDepartCombo();
		addComboToTable(combo, 0);
		addComboToTable(combo, 1);
		addSpinnerToTable(dateDepartSpinner, 2);
		addSpinnerToTable(dateArriveeSpinner, 3);

		scrollPane = new JScrollPane(table);
		add(scrollPane);
	}


	private JComboBox buildDepartCombo() {
		JComboBox combo = new JComboBox();
		for(Ville v : villes) {
			combo.addItem(v);
		}
		return combo;
	}

	private void buildButtons() {
		buttonsPanel.add(new JButton(new AddAction("Ajouter")), BorderLayout.CENTER);
		buttonsPanel.add(new JButton(new DeleteAction("Supprimer")), BorderLayout.CENTER);
		buttonsPanel.add(new JButton(new LinkAction("Lier à un tranport", this)));
		add(buttonsPanel);
	}

	public class LinkAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private TableReservationsPanel parent;

		public LinkAction(String texte, TableReservationsPanel parent) {
			super(texte);
			this.parent = parent;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Link !");
			int [] selected = table.getSelectedRows();
			for(final int i : selected) {
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						// TODO
						//TrajetSelectorDialog d = new TrajetSelectorDialog(serveur, parent, i);
						//d.setVisible(true);
					}
				});

			}
		}
	}
	public class AddAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public AddAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Ajout !");
			model.addRow(new Trajet(serveur.getTrajetNewIdentifiant()));
		}
	}

	public class DeleteAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public DeleteAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Supprimé !");
			int[] selectedIndexes = table.getSelectedRows();
			for (int i=selectedIndexes.length-1;i>=0;i--) {
				int row = selectedIndexes[i];
				System.out.println(model.getValueAt(row, 0));
				serveur.removeTrajet((Trajet) model.get(row));
				model.removeRow(row);
			}	  
		}
	}

	private class CellListener implements TableModelListener {
		public CellListener() {
		}

		public void tableChanged(TableModelEvent e) {
			int row    = e.getFirstRow();
			int column = e.getColumn();
			System.out.println("Row " + row);
			System.out.println("Column " + column);

			switch(e.getType()) {				
			case TableModelEvent.UPDATE:
				System.out.println("Updated");
				for(Trajet t:trajets) {
					Trajet tt = (Trajet) model.get(row);
					if(t.getIdentifiant() == tt.getIdentifiant()) {
						try {
							serveur.modifierTrajet(t, tt);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				break;
			}	
		}
	}

	public void linkTranjet(int parentSelectedRow, Trajet selectedTrajet) {
		// FIXME
		//model.setValueAt(selectedTransport, parentSelectedRow, 4);
		System.out.println("Lié à "+selectedTrajet.toString());
	}
}
