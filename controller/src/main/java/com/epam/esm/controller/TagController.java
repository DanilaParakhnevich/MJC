package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController()
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping
    @ResponseStatus(CREATED)
    public TagClientModel addCertificate(@RequestBody TagEntity tag) {
        return tagService.add(tag);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<TagClientModel> findAll() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public TagClientModel findById(@PathVariable long id) {
        return tagService.findTagById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public boolean deleteTag(@PathVariable long id) {
        return tagService.deleteById(id);
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
}
