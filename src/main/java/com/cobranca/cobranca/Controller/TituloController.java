package com.cobranca.cobranca.Controller;

import com.cobranca.cobranca.Dao.ITitulo;
import com.cobranca.cobranca.Model.StatusTitulo;
import com.cobranca.cobranca.Model.Titulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        mv.addObject(new Titulo());
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String salvar(@Validated Titulo titulo, Errors errors , RedirectAttributes attributes) { //Spring transforma o corpo que vem para o servidor no objeto esperado , gracas ao mesmo nome de atributos na view

        if(errors.hasErrors()){ // Validando o objeto titulo se apresentar erro manda para a página de cadastro
            return "CadastroTitulo";
        }

        iTitulo.save(titulo);
        attributes.addFlashAttribute("mensagem" , "Título salvo com sucesso!");

        return "redirect:/titulos/novo"; // redirect é uma nova requisição
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

