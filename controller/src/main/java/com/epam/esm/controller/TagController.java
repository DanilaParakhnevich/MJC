package com.epam.esm.controller;

import com.epam.esm.TagService;
import com.epam.esm.dto.TagClientModel;
import com.epam.esm.entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping
    public boolean createTag(@RequestBody TagEntity tag) {
        return tagService.addTag(tag) != null;
    }

    @GetMapping
    public List<TagClientModel> findAll() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public TagClientModel findById(@PathVariable long id) {
        return tagService.findTagById(id);
    }

    @DeleteMapping
    public boolean deleteTag(@RequestBody TagEntity tag) {
        return tagService.removeTag(tag);
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }
}
