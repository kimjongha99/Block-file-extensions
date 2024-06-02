package com.flow.blockfileextensions.presentation;

import com.flow.blockfileextensions.application.FixedExtensionService;
import com.flow.blockfileextensions.domain.FixedExtension;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/fixed-extensions")
@RequiredArgsConstructor
public class FixedExtensionController {

    private final FixedExtensionService fixedExtensionService;

    @GetMapping
    public String listFixedExtensions(Model model) {
        List<FixedExtension> extensions = fixedExtensionService.findAll();
        model.addAttribute("extensions", extensions);
        return "fixed-extensions/list";
    }

    @PostMapping
    public String addFixedExtension(@ModelAttribute FixedExtension extension) {
        fixedExtensionService.save(extension);
        return "redirect:/fixed-extensions";
    }

    @PostMapping("/delete")
    public String deleteFixedExtension(@RequestParam Long id) {
        fixedExtensionService.delete(id);
        return "redirect:/fixed-extensions";
    }

    @GetMapping("/{id}")
    public String getFixedExtension(@PathVariable Long id, Model model) {
        FixedExtension extension = fixedExtensionService.findById(id).orElse(null);
        model.addAttribute("extension", extension);
        return "fixed-extensions/detail";
    }

}

