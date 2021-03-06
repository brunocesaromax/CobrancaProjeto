package com.cobranca.cobranca.Controller;

import com.cobranca.cobranca.Dao.TituloFilter;
import com.cobranca.cobranca.Model.StatusTitulo;
import com.cobranca.cobranca.Model.Titulo;
import com.cobranca.cobranca.Service.CadatroTituloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/titulos")
public class TituloController {

    public static final String CADASTRO_VIEW = "CadastroTitulo";

    @Autowired
    private CadatroTituloService cadatroTituloService;

    @RequestMapping("/novo")
    public ModelAndView novo() {

        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);// sem ".html" , para mudancas futuras , ex: JSP
        mv.addObject(new Titulo());
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) { //Spring transforma o corpo que vem para o servidor no objeto esperado , gracas ao mesmo nome de atributos na view

        if (errors.hasErrors()) { // Validando o objeto titulo se apresentar erro manda para a página de cadastro
            return CADASTRO_VIEW;
        }

        try {
            cadatroTituloService.salvar(titulo);
            attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");
            return "redirect:/titulos/novo"; // redirect é uma nova requisição

        } catch (IllegalArgumentException e) { // Capturar erro quando data informada mesmo com o calendário for inválida, e retornar erro para o usuário
            errors.rejectValue("dataVencimento", null, e.getMessage());
            return CADASTRO_VIEW;
        }
    }

    //public ModelAndView pesquisar(@RequestParam(defaultValue = "%") String descricao) [Poderia ser feito desta maneira]
    @RequestMapping
    public ModelAndView pesquisar(@ModelAttribute("filtro") TituloFilter filtro) { //Criando objeto em tempo de execução pelo spring para evitar de passar para a view um objeto null

        List<Titulo> titulosFiltrados = cadatroTituloService.filtrar(filtro);
        ModelAndView mv = new ModelAndView("PesquisaTitulos");
        mv.addObject("titulos", titulosFiltrados);
        return mv;
    }

    @RequestMapping("{id}")
    public ModelAndView edicao(@PathVariable("id") Titulo titulo) {

        //Titulo tituloBD = iTitulo.findOne(id);
        ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
        mv.addObject(titulo);

        return mv;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public String excluir(@PathVariable Long id, RedirectAttributes attributes) {

        cadatroTituloService.excluir(id);
        //iTitulo.delete(id); passado para a camada de serviço
        attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
        return "redirect:/titulos";
    }

    @RequestMapping(value = "{id}/receber", method = RequestMethod.PUT)
    public @ResponseBody
    String receber(@PathVariable Long id) { // ResponseBody , quer retornar apenas a string como resposta não uma view

        return cadatroTituloService.receber(id);// Retornando Status RECEBIDO
    }

    @ModelAttribute("todosStatusTitulo")
    // Atributos vai estar disponível em todas as views sem precisar replicar código
    public List<StatusTitulo> todosStatusTitulo() {
        return Arrays.asList(StatusTitulo.values());
    }

}

