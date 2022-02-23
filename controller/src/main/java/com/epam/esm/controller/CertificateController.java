package com.epam.esm.controller;

import com.epam.esm.CertificateService;
import com.epam.esm.dto.CertificateClientModel;
import com.epam.esm.handler.exception.BadParameterException;
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


import java.util.Collections;
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
     * Add certificate client model.
     *
     * @param certificate the certificate
     * @return the certificate client model
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public CertificateClientModel addCertificate(@RequestBody CertificateClientModel certificate) {
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
    (@RequestParam(required = false) Map<String, String> parameters) {
        return certificateService.findAll(parameters);
    }

    /**
     * Find by name list.
     *
     * @param parameters the parameters
     * @return the list
     */
    @GetMapping("/search")
    @ResponseStatus(OK)
    public List<CertificateClientModel> search(@RequestParam(required = false) Map<String, String> parameters) {
        for (Map.Entry<String, String> parameter : parameters.entrySet()) {
            if (isRequiredParameter(parameter.getKey())) {
                parameters.remove(parameter.getKey());
                switch (parameter.getKey()) {
                    case "name":
                        return certificateService
                                .findByName(parameter.getValue(), parameters);
                    case "id":
                        return Collections.singletonList(certificateService
                                .findById(Integer.parseInt(parameter.getValue())));
                    default:
                        return certificateService.findByTagName(parameter.getValue(),
                                parameters);
                }
            }
        }
        throw new BadParameterException("bad.param");
    }

    /**
     * Update certificate client model.
     *
     * @param certificate the certificate
     * @return the certificate client model
     */
    @PutMapping
    @ResponseStatus(OK)
    public CertificateClientModel update(@RequestBody CertificateClientModel certificate) {
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

    private boolean isRequiredParameter(String parameter) {
        return parameter.equals("name") ||
                parameter.equals("tag") ||
                parameter.equals("id");
    }
}
