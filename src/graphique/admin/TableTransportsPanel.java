package graphique.admin;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;

import logiqueMetier.Serveur;
import objets.TypeVehicule;
import objets.Vehicule;

public class TableTransportsPanel extends JPanel {
	private TransportsTableModel transportModel;
	private JTable transportTable;
	private JScrollPane scrollPane;

	ArrayList<Vehicule> vehicules;

	private Serveur serveur;

	public TableTransportsPanel(Serveur s) {
		super();
		serveur = s;
		vehicules = serveur.getVehicules();
		build();
	}

	private void build() {
		setBorder(BorderFactory.createTitledBorder("Gestion des transports"));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		buildTrajetsTable();
		buildButtons();
	}

	private void buildTrajetsTable() {
		String[] columnNames = { "Nom du véhicule", "Type de véhicule", "Capacité d'accueil"};

		transportModel = new TransportsTableModel(vehicules);
		transportModel.setColumnNames(columnNames);
		transportTable = new JTable();
		transportTable.setModel(transportModel);
		transportTable.setFillsViewportHeight(true); // Fill all the container
		
		transportTable.getModel().addTableModelListener(new CellListener()); 

		JComboBox combo = buildTypeCombo();
		addComboToTable(combo, 1);
		scrollPane = new JScrollPane(transportTable);
		add(scrollPane);
	}

	private void buildButtons() {
		JPanel panel = new JPanel();
		panel.setLayout( new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(new JButton(new AddAction("Ajouter")), BorderLayout.CENTER);
		panel.add(new JButton(new DeleteAction("Supprimer")), BorderLayout.CENTER);
		add(panel);
	}

	public void setEditable(boolean isEditable) {
			transportModel.setEditable(isEditable);
	}

	public class AddAction extends AbstractAction {
		public AddAction(String texte) {
			super(texte);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("Ajout !");
			TransportsTableModel model = (TransportsTableModel) transportTable.getModel();
			model.addRow(new Vehicule(serveur.getVehiculeNewIdentifiant()));	
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
				try {
					int row = selectedIndexes[i];
					serveur.removeVehicule(transportModel.getVehicule(row));
					transportModel.removeRow(row);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
			if(column == 5) {
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
						TransportSelectorDialog p = new TransportSelectorDialog(serveur);
						p.setVisible(true);
					}
				});
			
			}

			
			switch(e.getType()) {
			case TableModelEvent.INSERT:
				System.out.println("Insertion");
//				transportModel.setValueAt(serveur.getVehiculeNewIdentifiant(), row, 0);
				
				break;
			case TableModelEvent.UPDATE:
				System.out.println("Updated");
				for(Vehicule v:vehicules) {
					Vehicule tv = transportModel.getVehicule(row);
					if(v.getIdentifiant() == tv.getIdentifiant()) {
						v.setVehicule((String) transportModel.getValueAt(row, 0));
						v.setType((TypeVehicule)transportModel.getValueAt(row, 1));
						v.setCapacite((Integer) transportModel.getValueAt(row, 2));
						try {
							serveur.modifierVehicule(tv, v);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				break;
			}	
		}
	}

	private JComboBox buildTypeCombo() {
		JComboBox combo = new JComboBox();
		for(TypeVehicule v : TypeVehicule.values()) {
			combo.addItem(v);
		}
		return combo;
	}
	private void addComboToTable(JComboBox combo, int column) {
		TableColumn gradeColumn = transportTable.getColumnModel().getColumn(column);
		gradeColumn.setCellEditor(new DefaultCellEditor(combo));
	}

}
