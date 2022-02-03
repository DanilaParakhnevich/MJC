package com.epam.esm.controller;

import com.epam.esm.CertificateService;
import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.entity.CertificateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/certificates")
public class CertificateController {
    @Autowired
    CertificateService certificateService;

    @PostMapping
    @ResponseStatus(CREATED)
    public CertificateClientModel addCertificate(@RequestBody CertificateEntity certificate) {
        return certificateService.add(certificate);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<CertificateClientModel> findAll
            (@RequestParam(required = false) Map<String, String> parameters){
        return certificateService.findAll(parameters);
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(OK)
    public CertificateClientModel findById(@PathVariable long id) {
        return certificateService.findCertificateById(id);
    }

    @GetMapping("/tag/{name}")
    @ResponseStatus(OK)
    public List<CertificateClientModel> findByTagName(
            @PathVariable String name,
            @RequestParam(required = false) Map<String, String> parameters) {
        return certificateService.findByTagName(name, parameters);
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(OK)
    public List<CertificateClientModel> findByName(
            @PathVariable String name,
            @RequestParam(required = false) Map<String, String> parameters) {
        return certificateService.findByName(name, parameters);
    }

    @PutMapping
    @ResponseStatus(OK)
    public CertificateClientModel update(@RequestBody CertificateEntity certificate) {
        return certificateService.update(certificate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public boolean delete(@PathVariable long id) {
        return certificateService.deleteById(id);
    }

    public void setCertificateService(CertificateService certificateService) {
        this.certificateService = certificateService;
    }
}
