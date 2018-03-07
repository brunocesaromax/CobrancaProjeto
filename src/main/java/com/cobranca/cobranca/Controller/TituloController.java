package com.cobranca.cobranca.Controller;

import com.cobranca.cobranca.Dao.ITitulo;
import com.cobranca.cobranca.Model.StatusTitulo;
import com.cobranca.cobranca.Model.Titulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/titulos")
public class TituloController {

    public static final String CADASTRO_VIEW = "CadastroTitulo";

    @Autowired
    private ITitulo iTitulo;

    @RequestMapping("/novo")
    public ModelAndView novo() {

        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);// sem ".html" , para mudancas futuras , ex: JSP
        mv.addObject(new Titulo());
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String salvar(@Validated Titulo titulo, Errors errors , RedirectAttributes attributes) { //Spring transforma o corpo que vem para o servidor no objeto esperado , gracas ao mesmo nome de atributos na view

        if(errors.hasErrors()){ // Validando o objeto titulo se apresentar erro manda para a página de cadastro
            return CADASTRO_VIEW;
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

    // Por causa da aplicação estar usando JPA Repository,
    // o próprio spring consegue relacionar o id a um objeto titulo específico, não necessitando de buscar esse objeto no banco
    @RequestMapping("{id}")
    public ModelAndView edicao(@PathVariable("id") Titulo titulo){

        //Titulo tituloBD = iTitulo.findOne(id);
        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
        mv.addObject(titulo);

        return mv;
    }

    @RequestMapping(value = "{id}",method = RequestMethod.DELETE)
    public String excluir(@PathVariable Long id , RedirectAttributes attributes){

        iTitulo.delete(id);
        attributes.addFlashAttribute("mensagem","Título excluído com sucesso!");

        return "redirect:/titulos";

    }

    @ModelAttribute("todosStatusTitulo") // Atributos vai estar disponível em todas as views sem precisar replicar código
    public List<StatusTitulo> todosStatusTitulo(){
        return Arrays.asList(StatusTitulo.values());
    }

}

