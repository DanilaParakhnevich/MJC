package com.epam.esm.controller;

import com.epam.esm.CertificateService;
import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.entity.CertificateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
    public List<CertificateClientModel> findAll(){
        return certificateService.findAll();
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(OK)
    public CertificateClientModel findById(@PathVariable long id) {
        return certificateService.findCertificateById(id);
    }

    @GetMapping("/tag/{name}")
    @ResponseStatus(OK)
    public List<CertificateClientModel> findByTagName(@PathVariable String name) {
        return certificateService.findByTagName(name);
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(OK)
    public List<CertificateClientModel> findByName(@PathVariable String name) {
        return certificateService.findByName(name);
    }

    @PutMapping
    @ResponseStatus(OK)
    public CertificateClientModel update(@RequestBody CertificateEntity certificate) {
        return certificateService.update(certificate);
    }// TODO: 2/1/2022 date

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable long id) {
        return certificateService.deleteById(id);
    }

    public void setCertificateService(CertificateService certificateService) {
        this.certificateService = certificateService;
    }
}
