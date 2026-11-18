package br.fiap.vinicius562003.controller;

import lombok.AllArgsConstructor;
import br.fiap.vinicius562003.dto.BairroDTO;
import br.fiap.vinicius562003.dto.PainelDTO;
import br.fiap.vinicius562003.service.BairroService;
import br.fiap.vinicius562003.service.PainelService;
import br.fiap.vinicius562003.service.RegraNegocioException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/bairros")
public class BairroController {

    private static final List<String> ZONAS = List.of("Centro", "Norte", "Sul", "Leste", "Oeste");

    private final BairroService bairroService;
    private final PainelService painelService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("bairros", bairroService.listar());
        return "bairros/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        return abrirFormulario(model, new BairroDTO());
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            return abrirFormulario(model, bairroService.buscarPorId(id));
        } catch (RegraNegocioException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
            return "redirect:/bairros";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("bairro") BairroDTO bairro, BindingResult result,
                         Model model, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return abrirFormulario(model, bairro);
        }
        try {
            bairroService.salvar(bairro);
            redirect.addFlashAttribute("sucesso", "Bairro salvo com sucesso.");
            return "redirect:/bairros";
        } catch (RegraNegocioException e) {
            model.addAttribute("erro", e.getMessage());
            return abrirFormulario(model, bairro);
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            bairroService.excluir(id);
            redirect.addFlashAttribute("sucesso", "Bairro excluído com sucesso.");
        } catch (RegraNegocioException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/bairros";
    }

    @GetMapping("/{id}/painel")
    public String painel(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            PainelDTO painel = painelService.montarPainel(id);
            model.addAttribute("bairroNome", painel.bairroNome());
            model.addAttribute("qtdNormal", painel.qtdNormal());
            model.addAttribute("qtdAtencao", painel.qtdAtencao());
            model.addAttribute("qtdAlerta", painel.qtdAlerta());
            model.addAttribute("itens", painel.itens());
            return "bairros/painel";
        } catch (RegraNegocioException e) {
            redirect.addFlashAttribute("erro", e.getMessage());
            return "redirect:/bairros";
        }
    }

    private String abrirFormulario(Model model, BairroDTO bairro) {
        model.addAttribute("bairro", bairro);
        model.addAttribute("zonas", ZONAS);
        return "bairros/form";
    }
}
