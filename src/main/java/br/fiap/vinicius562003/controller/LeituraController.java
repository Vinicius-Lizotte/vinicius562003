package br.fiap.vinicius562003.controller;

import lombok.AllArgsConstructor;
import br.fiap.vinicius562003.dto.LeituraDTO;
import br.fiap.vinicius562003.service.LeituraService;
import br.fiap.vinicius562003.service.RegraNegocioException;
import br.fiap.vinicius562003.service.TrechoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
@RequestMapping("/leituras")
public class LeituraController {

    private final LeituraService leituraService;
    private final TrechoService trechoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("leituras", leituraService.listar());
        return "leituras/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        return abrirFormulario(model, new LeituraDTO());
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            return abrirFormulario(model, leituraService.buscarPorId(id));
        } catch (RegraNegocioException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
            return "redirect:/leituras";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("leitura") LeituraDTO leitura, BindingResult result,
                         Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return abrirFormulario(model, leitura);
        }
        try {
            LeituraDTO salva = leituraService.salvar(leitura);
            redirect.addFlashAttribute("sucesso", "Leitura salva com status " + salva.getStatus() + ".");
            return "redirect:/leituras";
        } catch (RegraNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            return abrirFormulario(model, leitura);
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            leituraService.excluir(id);
            redirect.addFlashAttribute("sucesso", "Leitura excluída com sucesso.");
        } catch (RegraNegocioException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/leituras";
    }

    private String abrirFormulario(Model model, LeituraDTO leitura) {
        model.addAttribute("leitura", leitura);
        model.addAttribute("trechos", trechoService.listar());
        return "leituras/form";
    }
}
