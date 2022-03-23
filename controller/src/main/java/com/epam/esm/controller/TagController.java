package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagClientModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Tag controller.
 */
@RestController
@RequestMapping("/tags")
public class TagController {
    private TagService tagService;

    /**
     * Add tag client model.
     *
     * @param tag the tag
     * @return the tag client model
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public TagClientModel add(@RequestBody TagClientModel tag) {
        return tagService.add(tag);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @GetMapping
    @ResponseStatus(OK)
    public List<TagClientModel> findAll(@RequestParam Map<String, String> parameters) {
        return tagService.readAll(parameters);
    }

    /**
     * Find by id tag client model.
     *
     * @param id the id
     * @return the tag client model
     */
    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public TagClientModel findById(@PathVariable long id) {
        return tagService.readById(id);
    }

    /**
     * Delete tag boolean.
     *
     * @param id the id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteTag(@PathVariable long id) {
        tagService.deleteById(id);
    }

    /**
     * Sets tag service.
     *
     * @param tagService the tag service
     */
    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
}
