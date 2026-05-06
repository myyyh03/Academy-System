package com.mo7s.academySystem.resource;

import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ResourceService {

    final private ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository){
        this.resourceRepository = resourceRepository;
    }

    public List<ResourceDto> getAllResources(){
        List<Resource> resources = resourceRepository.findAll();
        return resources.stream().map(ResourceDto::fromEntity).toList();
    }

    public ResourceDto getResourceById(Integer id){
        Resource resource = resourceRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return ResourceDto.fromEntity(resource);
    }

    public ResourceDto createResource(ResourceDto resourceDto){
        Resource saved = resourceRepository.save(ResourceDto.toEntity(resourceDto));
        return ResourceDto.fromEntity(saved);
    }

    public ResourceDto updateResourceById(Integer id, ResourceDto newResourceDto){
        Resource resource = resourceRepository.findById(id).orElseThrow(NoSuchElementException::new);
        ResourceDto oldResourceDto = ResourceDto.fromEntity(resource);
        oldResourceDto = updateResource(oldResourceDto, newResourceDto);
        Resource updated = resourceRepository.save(ResourceDto.toEntity(oldResourceDto));
        return ResourceDto.fromEntity(updated);
    }

    public void deleteResourceById(Integer id){
        resourceRepository.deleteById(id);
    }


    private ResourceDto updateResource(ResourceDto oldResource , ResourceDto newResource){
        for(Field field : newResource.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                Object newValue = field.get(newResource);
                if(newValue != null){
                    field.set(oldResource, newValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return oldResource;
    }

}
