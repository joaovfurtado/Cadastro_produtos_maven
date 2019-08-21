/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sermed.tables;

import br.com.sermed.models.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author joão.furtado
 */
public class TableModelProdutos extends AbstractTableModel {

    private final List<Produto> lista;
    private final String[] columnNames = {"Codigo", "Nome", "Valor"};

    public TableModelProdutos() {
        this.lista = new ArrayList();
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Produto p = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getId();
            case 1:
                return p.getNome();
            case 2:
                return p.getValor();
            default:
                return null;
        }
    }
    public void setList(List<Produto> l){
        lista.addAll(l);
        fireTableDataChanged();
    }
    public Produto getObject(int index){
        return lista.get(index);
    }
    
}
