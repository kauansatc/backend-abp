package backend.medapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.medapi.dtos.ProjectInfo;

@RestController
public class InfoController {
    @GetMapping("/ajuda")
    public ProjectInfo ajuda() {
        return new ProjectInfo(new String[] { "Kauan Fontanela", "Lucas Adriano" }, "MedAPI", "Saúde e Bem-estar");
    }
}
