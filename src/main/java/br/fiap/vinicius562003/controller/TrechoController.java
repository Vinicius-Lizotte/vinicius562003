package br.fiap.vinicius562003.controller;

import lombok.AllArgsConstructor;
import br.fiap.vinicius562003.dto.TrechoDTO;
import br.fiap.vinicius562003.service.BairroService;
import br.fiap.vinicius562003.service.RegraNegocioException;
import br.fiap.vinicius562003.service.TrechoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/trechos")
public class TrechoController {

    private static final List<String> TIPOS_CALCADA =
            List.of("Regular", "Irregular", "Escadaria", "Sem iluminação");

    private final TrechoService trechoService;
    private final BairroService bairroService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("trechos", trechoService.listar());
        return "trechos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        return abrirFormulario(model, new TrechoDTO());
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            return abrirFormulario(model, trechoService.buscarPorId(id));
        } catch (RegraNegocioException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
            return "redirect:/trechos";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("trecho") TrechoDTO trecho, BindingResult result,
                         Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return abrirFormulario(model, trecho);
        }
        try {
            trechoService.salvar(trecho);
            redirect.addFlashAttribute("sucesso", "Trecho salvo com sucesso.");
            return "redirect:/trechos";
        } catch (RegraNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            return abrirFormulario(model, trecho);
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            trechoService.excluir(id);
            redirect.addFlashAttribute("sucesso", "Trecho e suas leituras excluídos com sucesso.");
        } catch (RegraNegocioException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/trechos";
    }

    private String abrirFormulario(Model model, TrechoDTO trecho) {
        model.addAttribute("trecho", trecho);
        model.addAttribute("bairros", bairroService.listar());
        model.addAttribute("tiposCalcada", TIPOS_CALCADA);
        return "trechos/form";
    }
}
