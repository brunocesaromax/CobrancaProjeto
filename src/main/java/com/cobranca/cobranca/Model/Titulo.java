package com.cobranca.cobranca.Model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
public class Titulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Banco de dados tomando conta
    private Long id;

    @NotEmpty(message = "Descrição é obrigatória")
    @Size(max = 60 , message = "A descrição não pode conter mais de 60 caracteres")
    private String descricao;

    @NotNull(message = "Data de vencimento é obrigatória")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataVencimento;

    @DecimalMin(value = "0.01" , message = "Valor não pode ser menor que R$0,01")
    @DecimalMax(value = "999999999.99" , message = "Valor não pode ser maior que R$999.9999.999,99")
    @NotNull(message = "Valor é obrigatório")
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
