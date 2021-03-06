package com.cobranca.cobranca.Service;


import com.cobranca.cobranca.Dao.ITitulo;
import com.cobranca.cobranca.Dao.TituloFilter;
import com.cobranca.cobranca.Model.StatusTitulo;
import com.cobranca.cobranca.Model.Titulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

//@Component , deve ser no mínimo @Component para pode ser injetada em outras partes do projeto
@Service // Transformando essa classe em um componente para o spring
public class CadatroTituloService {

    @Autowired
    private ITitulo iTitulo;

    public void salvar(Titulo titulo) {

        try {
            iTitulo.save(titulo);

        } catch (DataIntegrityViolationException e) { // Capturar erro quando data informada mesmo com o calendário for inválida, e retornar erro para o usuário
            throw new IllegalArgumentException("Formato de data inválido");
        }

    }

    public void excluir(Long id) {
        iTitulo.delete(id);
    }

    public String receber(Long id) { // atualizando status do titulo encontrado

        Titulo tituloBD= iTitulo.findOne(id);
        tituloBD.setStatus(StatusTitulo.RECEBIDO);
        iTitulo.save(tituloBD);

        return tituloBD.getStatus().getDescricao();
    }

    public List<Titulo> filtrar(TituloFilter filtro){

        String descricao = filtro.getDescricao() == null ? "%": filtro.getDescricao();
        return iTitulo.findByDescricaoContaining(descricao);
    }
}
