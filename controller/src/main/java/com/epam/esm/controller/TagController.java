package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * The type Tag controller.
 */
@RestController()
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * Add tag client model.
     *
     * @param tag the tag
     * @return the tag client model
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public TagClientModel add(@RequestBody TagEntity tag) {
        return tagService.add(tag);
    }

    /**
     * Find all list.
     *
     * @return the list
     */
    @GetMapping
    @ResponseStatus(OK)
    public List<TagClientModel> findAll() {
        return tagService.findAll();
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
        return tagService.findTagById(id);
    }

    /**
     * Delete tag boolean.
     *
     * @param id the id
     * @return the boolean
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public boolean deleteTag(@PathVariable long id) {
        return tagService.deleteById(id);
    }

    /**
     * Sets tag service.
     *
     * @param tagService the tag service
     */
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
}
