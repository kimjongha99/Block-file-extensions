package com.flow.blockfileextensions.presentation;

import com.flow.blockfileextensions.application.CustomExtensionService;
import com.flow.blockfileextensions.domain.CustomExtension;
import com.flow.blockfileextensions.presentation.dto.CustomExtensionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/custom-extensions")
@RequiredArgsConstructor
public class CustomExtensionController {

    private final CustomExtensionService customExtensionService;

    @GetMapping
    public String listCustomExtensions(Model model) {
        List<CustomExtension> extensions = customExtensionService.findAll();
        model.addAttribute("extensions", extensions);
        return "custom-extensions/list";
    }

    @PostMapping
    public String addCustomExtension(@ModelAttribute CustomExtensionDTO extensionDTO) {
        customExtensionService.save(extensionDTO.toEntity());
        return "redirect:/custom-extensions";
    }

    @PostMapping("/delete")
    public String deleteCustomExtension(@RequestParam Long id) {
        customExtensionService.delete(id);
        return "redirect:/custom-extensions";
    }

    @GetMapping("/{id}")
    public String getCustomExtension(@PathVariable Long id, Model model) {
        CustomExtension extension = customExtensionService.findById(id).orElse(null);
        model.addAttribute("extension", extension);
        return "custom-extensions/detail";
    }
    }