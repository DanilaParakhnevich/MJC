package com.epam.esm.controller;

import com.epam.esm.CertificateService;
import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.entity.CertificateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;





import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Certificate controller.
 */
@RestController
@RequestMapping("/certificates")
public class CertificateController {
    /**
     * The Certificate service.
     */
    CertificateService certificateService;

    /**
     * Add certificate certificate client model.
     *
     * @param certificate the certificate
     * @return the certificate client model
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public CertificateClientModel addCertificate(@RequestBody CertificateEntity certificate) {
        return certificateService.add(certificate);
    }

    /**
     * Find all list.
     *
     * @param parameters the parameters
     * @return the list
     */
    @GetMapping
    @ResponseStatus(OK)
    public List<CertificateClientModel> findAll
            (@RequestParam(required = false) Map<String, String> parameters){
        return certificateService.findAll(parameters);
    }

    /**
     * Find by id certificate client model.
     *
     * @param id the id
     * @return the certificate client model
     */
    @GetMapping("/id/{id}")
    @ResponseStatus(OK)
    public CertificateClientModel findById(@PathVariable long id) {
        return certificateService.findCertificateById(id);
    }

    /**
     * Find by tag name list.
     *
     * @param name       the name
     * @param parameters the parameters
     * @return the list
     */
    @GetMapping("/tag/{name}")
    @ResponseStatus(OK)
    public List<CertificateClientModel> findByTagName(
            @PathVariable String name,
            @RequestParam(required = false) Map<String, String> parameters) {
        return certificateService.findByTagName(name, parameters);
    }

    /**
     * Find by name list.
     *
     * @param name       the name
     * @param parameters the parameters
     * @return the list
     */
    @GetMapping("/name/{name}")
    @ResponseStatus(OK)
    public List<CertificateClientModel> findByName(
            @PathVariable String name,
            @RequestParam(required = false) Map<String, String> parameters) {
        return certificateService.findByName(name, parameters);
    }

    /**
     * Update certificate client model.
     *
     * @param certificate the certificate
     * @return the certificate client model
     */
    @PutMapping
    @ResponseStatus(OK)
    public CertificateClientModel update(@RequestBody CertificateEntity certificate) {
        return certificateService.update(certificate);
    }

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    @DeleteMapping("/id/{id}")
    @ResponseStatus(NO_CONTENT)
    public boolean delete(@PathVariable long id) {
        return certificateService.deleteById(id);
    }

    /**
     * Sets certificate service.
     *
     * @param certificateService the certificate service
     */
    @Autowired
    public void setCertificateService(CertificateService certificateService) {
        this.certificateService = certificateService;
    }
}
