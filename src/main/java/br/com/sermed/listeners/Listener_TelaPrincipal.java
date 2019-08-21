package br.com.sermed.listeners;

import br.com.sermed.dal.DaoDerby;
import br.com.sermed.dal.EntityManagerHelper;
import br.com.sermed.frames.TelaPrincipal;
import br.com.sermed.models.Produto;
import br.com.sermed.tables.TableModelProdutos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author joão.furtado
 */
public class Listener_TelaPrincipal implements ActionListener {
    private EntityManagerHelper<Produto> emh;
    private TableModelProdutos model;
    
    
    private DaoDerby dao;
    private List<Produto> listaP;
    private final TelaPrincipal form;

    public Listener_TelaPrincipal(TelaPrincipal form) {
        this.form = form;
        initComponents();
    }
    public void initComponents(){
        emh = new EntityManagerHelper<>();
        attachListener();
        addModel();
        carregaListaV2();
        addSelectionListenerV2();
    }
    public void addModel(){
        model = new TableModelProdutos();
        form.getTbProdutos().setModel(model);
    }
    public void carregaLista() {
        listaP = new ArrayList();
        String sql = "SELECT * FROM CAD_PRODUTOS";
        PreparedStatement ps = conectar(sql);
        ResultSet rst;
        try {
            rst = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) form.getTbProdutos().getModel();
            while (rst.next()) {
                Produto p = new Produto();
                p.setId(rst.getLong(1));
                p.setNome(rst.getString(2));
                p.setValor(rst.getBigDecimal(3));
                listaP.add(p);
            }
            try {
                for (int r = 0; r < listaP.size() - 1; r++) {
                    model.removeRow(0);
                }
                model.fireTableDataChanged();
            } catch (Exception e) {
            }
            for (int r = 0; r < listaP.size(); r++) {
                model.addRow(new Object[3]);
                for (int c = 0; c < 3; c++) {
                    switch (c) {
                        case 0:
                            model.setValueAt(listaP.get(r).getId(), r, c);
                            break;
                        case 1:
                            model.setValueAt(listaP.get(r).getNome(), r, c);
                            break;
                        case 2:
                            model.setValueAt(listaP.get(r).getValor(), r, c);
                            break;
                    }

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void carregaListaV2() {
        model.setList(emh.getListV2("produto.findAll", null));
        
    }

    public void cadastrar() {
        String sql = "INSERT INTO CAD_PRODUTOS (CODIGO,NOME,VALOR) VALUES (NEXT VALUE FOR SEQ_CAD_PRODUTO,?,?)";
        PreparedStatement ps = conectar(sql);
        try {
            ps.setString(1, form.getTxtNome().getText());
            ps.setBigDecimal(2, new BigDecimal(form.getTxtValor().getText()));
            ps.execute();
            carregaListaV2();
        } catch (SQLException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cadastrarV2() {
        if(emh.saveOrUpdate(new Produto("".equals(form.getTxtCodigo().getText()) ? null :Long.parseLong(form.getTxtCodigo().getText()), form.getTxtNome().getText(), new BigDecimal(form.getTxtValor().getText())))){
            JOptionPane.showMessageDialog(form, "Cadastrado!!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
        }
        carregaListaV2();
    }

    public void doConnection() {
        dao = new DaoDerby("jdbc:derby://localhost:1527/estoque", "root", "root");
        Connection con = dao.getConnection();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM CAD_PRODUTOS", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //"INSERT INTO CAD_PRODUTOS (CODIGO, NOME, VALOR) VALUES (?,?,?)"
            /*ps.setObject(1, 4);
            ps.setObject(2, "FONTE 500W");
            ps.setObject(3, new BigDecimal("600"));*/
            ResultSet rst = ps.executeQuery();
            while (rst.next()) {
                Produto p = new Produto();
                p.setId(rst.getLong(1));
                p.setNome(rst.getString(2));
                p.setValor(rst.getBigDecimal(3));
                System.out.println(p);
            }
            //rst.first();
        } catch (SQLException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public PreparedStatement conectar(String sql) {
        if (dao == null) {
            dao = new DaoDerby("jdbc:derby://localhost:1527/estoque", "root", "root");
        }
        Connection connection = dao.getConnection();
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
            return ps;
        } catch (SQLException ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void addSelectionListener() {
        form.getTbProdutos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                Produto p = listaP.get(form.getTbProdutos().getSelectedRow());
                form.getTxtCodigo().setText(p.getId().toString());
                form.getTxtNome().setText(p.getNome());
                form.getTxtValor().setText(p.getValor().toString());

            }

        });
        
    }
    public void addSelectionListenerV2() {
        form.getTbProdutos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()){
                Produto p = model.getObject(form.getTbProdutos().getSelectedRow());
                form.getTxtCodigo().setText(p.getId().toString());
                form.getTxtNome().setText(p.getNome());
                form.getTxtValor().setText(p.getValor().toString());
                }
            }

        });
        
    }
    public void attachListener(){
        form.getBtnCadastrar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "salvar":
                cadastrarV2();
                break;
            case "excluir":
                
                break;
            case "editar":
                break;
        }
    }

}
