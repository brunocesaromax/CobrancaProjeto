package com.cobranca.cobranca.Controller;

import com.cobranca.cobranca.Dao.ITitulo;
import com.cobranca.cobranca.Model.Titulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/titulos")
public class TituloController {

    @Autowired
    private ITitulo iTitulo;

    @RequestMapping("/novo")
    public String novo() {
        return "CadastroTitulo"; // sem ".html" , para mudancas futuras , ex: JSP
    }

    @RequestMapping(method = RequestMethod.POST)
    public String salvar(Titulo titulo) { //Spring transforma o corpo que vem para o servidor no objeto esperado , gracas ao mesmo nome de atributos na view

        iTitulo.save(titulo);
        return "CadastroTitulo";
    }

}

