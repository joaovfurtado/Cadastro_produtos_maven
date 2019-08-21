/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sermed.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author joão.furtado
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "CAD_PRODUTOS")
@NamedQueries({
    @NamedQuery(name = "produto.findAll",query = "FROM Produto AS p"),
    @NamedQuery(name = "produto.findById", query = "FROM Produto AS p WHERE p.id = :paramId")
})
@SequenceGenerator(name = "SEQ_CAD_PRODUTO",initialValue = 1,allocationSize = 1)
public class Produto implements Serializable{

    private static final long serialVersionUID = 2438265499648062673L;

    @Id
    @GeneratedValue(generator = "SEQ_CAD_PRODUTO",strategy = GenerationType.SEQUENCE)
    @Column(name = "CODIGO", length = 19)
    private Long id;
    @Column(name = "NOME", length = 255)
    private String nome;
    @Column(name = "VALOR",scale = 3, precision = 12)
    private BigDecimal valor;

    public Produto() {
    }

    public Produto(Long id, String nome, BigDecimal valor) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
    }

    public Produto(String nome, BigDecimal valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.nome);
        hash = 59 * hash + Objects.hashCode(this.valor);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.valor, other.valor)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Produto{" + "id=" + id + ", nome=" + nome + ", valor=" + valor + '}';
    }

}
