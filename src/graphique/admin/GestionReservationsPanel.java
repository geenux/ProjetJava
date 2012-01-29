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

/**
 * @author Fauvel-jaeger Olivier, Tanguy Arnaud, Ceschel Marvin, Kruck Nathan
 * @version 2012.01.29
 */

public class GestionReservationsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;

    private Serveur serveur;

    public GestionReservationsPanel(Serveur s) {
        super();
        serveur = s;
        build();
    }

    private void build() {
        setBorder(BorderFactory.createTitledBorder("Gestion des réservations"));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        buildReservationsTable();
        buildButtons();
    }

    private void buildReservationsTable() {
        String[] columnNames = { "Id", "Last Name", "Sport", "# of Years",
                "Vegetarian" };
        Object[][] data = {
                { "Kathy", "Smith", "Snowboarding", new Integer(5),
                        new Boolean(false) },
                { "John", "Doe", "Rowing", new Integer(3), new Boolean(true) },
                { "Sue", "Black", "Knitting", new Integer(2),
                        new Boolean(false) },
                { "Jane", "White", "Speed reading", new Integer(20),
                        new Boolean(true) },
                { "Joe", "Brown", "Pool", new Integer(10), new Boolean(false) } };
        model = new DefaultTableModel(data, columnNames);
        table = new JTable(data, columnNames);
        table.setModel(model);
        table.setFillsViewportHeight(true); // Fill all the
        // container
        table.getSelectionModel().addListSelectionListener(
                new ReservationListener(table));
        /*
         * table.getColumnModel().getSelectionModel() .addListSelectionListener(
         * new ReservationListener(table));
         */

        scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

    private void buildButtons() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(new JButton(new DeleteAction("Supprimer")),
                BorderLayout.CENTER);
        add(panel);
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
            for (int i = selectedIndexes.length - 1; i >= 0; i--) {
                int row = selectedIndexes[i];
                System.out.println(model.getValueAt(row, 0));
                model.removeRow(row);
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
            // If cell selection is enabled, both row and column change events
            // are fired
            if (e.getSource() == table.getSelectionModel()
                    && table.getRowSelectionAllowed()) {
                // Column selection changed
                // int first = e.getFirstIndex();
                // int last = e.getLastIndex();
                System.out.println("Selection changed");
            }
        }
    }

}
