package com.cobranca.cobranca.Dao;

import com.cobranca.cobranca.Model.Titulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITitulo extends JpaRepository<Titulo,Long> {

    //Buscar titulos que contenham na descricao o parametro que foi passado como parametro
    List<Titulo> findByDescricaoContaining(String descricao);

}
