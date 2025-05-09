package com.pokegame.app.util;

import com.pokegame.app.modelo.Ataque;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/** AtaqueTableModel. */
public class AtaqueTableModel extends AbstractTableModel {
  private final String[] columnas = {"id", "nombre", "damage"};
  private List<Ataque> ataques;

  public AtaqueTableModel(List<Ataque> ataques) {
    this.ataques = ataques;
  }

  @Override
  public int getRowCount() {
    return ataques.size();
  }

  @Override
  public int getColumnCount() {
    return columnas.length;
  }

  @Override
  public String getColumnName(int column) {
    return columnas[column];
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Ataque ataque = ataques.get(rowIndex);
    switch (columnIndex) {
      case 0:
        return ataque.getId();
      case 1:
        return ataque.getNombre();
      case 2:
        return ataque.getDamage();
      default:
        return null;
    }
  }
}
