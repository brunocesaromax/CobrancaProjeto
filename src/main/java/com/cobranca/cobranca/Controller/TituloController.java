package com.cobranca.cobranca.Controller;

import com.cobranca.cobranca.Dao.ITitulo;
import com.cobranca.cobranca.Model.StatusTitulo;
import com.cobranca.cobranca.Model.Titulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/titulos")
public class TituloController {

    @Autowired
    private ITitulo iTitulo;

    @RequestMapping("/novo")
    public ModelAndView novo() {

        ModelAndView mv = new ModelAndView("CadastroTitulo");// sem ".html" , para mudancas futuras , ex: JSP
        mv.addObject("todosStatusTitulo", StatusTitulo.values()); //Retornar todos os status para iterar dinamicamente na view
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView salvar(Titulo titulo) { //Spring transforma o corpo que vem para o servidor no objeto esperado , gracas ao mesmo nome de atributos na view

        iTitulo.save(titulo);

        ModelAndView modelAndView= new ModelAndView("CadastroTitulo");
        modelAndView.addObject("mensagem" , "Título salvo com sucesso!");

        return modelAndView;
    }

    @RequestMapping
    public ModelAndView pesquisar(){

        List<Titulo> titulos = iTitulo.findAll(); //todo: trocar posteriormente
        ModelAndView mv = new ModelAndView("PesquisaTitulos");
        mv.addObject("titulos",titulos);
        return mv;
    }


    @ModelAttribute("todosStatusTitulo") // Atributos vai estar disponível em todas as views sem precisar replicar código
    public List<StatusTitulo> todosStatusTitulo(){
        return Arrays.asList(StatusTitulo.values());
    }

}

