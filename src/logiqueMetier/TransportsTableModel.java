package logiqueMetier;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import objets.Vehicule;

public class TransportsTableModel extends AbstractTableModel {

	ArrayList<Vehicule> vehicules;
	
	public TransportsTableModel(ArrayList<Vehicule> vehicules) {
		this.vehicules = vehicules;
	}
	
	@Override
	public int getColumnCount() {
		// Returns the number of columns in the model : nom, capacite, type transport
		return 3;
	}

	@Override
	public int getRowCount() {
		return vehicules.size();
	}

	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Vehicule v = vehicules.get(rowIndex);
		switch(columnIndex) {
		case 0:
			return v.getVehicule();
		case 1:
			return v.getType();
		case 2: 
			return v.getCapacite();
		default:
			return null;
		}
	}
	
	public Vehicule getVehicule(int rowIndex) {
		return vehicules.get(rowIndex);
	}

	public void addRow(Vehicule v) {
		vehicules.add(v);
		   fireTableDataChanged();
	}

	public void removeRow(int row) {
		vehicules.remove(row);
		   fireTableDataChanged();
	}
}
