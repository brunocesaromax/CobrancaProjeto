package com.cobranca.cobranca.Model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class Titulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Banco de dados tomando conta
    private Long id;

    private String descricao;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataVencimento;

    @NumberFormat(pattern = "#,##0.00")// formatando de . para virgula ,
    private Double valor;

    @Enumerated(EnumType.STRING)
    private StatusTitulo status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public StatusTitulo getStatus() {
        return status;
    }

    public void setStatus(StatusTitulo status) {
        this.status = status;
    }

    public boolean isPendente(){ // Se titulo for pendente usará o label-danger do bootstrap caso contrário será usado o label-success
        return this.status.equals(StatusTitulo.PENDENTE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Titulo titulo = (Titulo) o;
        return Objects.equals(id, titulo.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
