package com.mo7s.academySystem.resource;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    final private ResourceService resourceService;

    public ResourceController(ResourceService resourceService){
        this.resourceService = resourceService;
    }

    @GetMapping
    public List<ResourceDto> getAllResources(){
        return resourceService.getAllResources();
    }

    @GetMapping("/{id}")
    public ResourceDto getResourceById(@PathVariable Integer id){
        return resourceService.getResourceById(id);
    }

    @PostMapping
    public ResourceDto createResource(@Valid @RequestBody ResourceDto resource){
        return resourceService.createResource(resource);
    }

    @PutMapping("/{id}")
    public ResourceDto updateResourceById(@PathVariable Integer id,@Valid @RequestBody ResourceDto resource){
        return resourceService.updateResourceById(id, resource);
    }

    @DeleteMapping("/{id}")
    public void deleteResourceById(@PathVariable Integer id){
        resourceService.deleteResourceById(id);
    }

}
