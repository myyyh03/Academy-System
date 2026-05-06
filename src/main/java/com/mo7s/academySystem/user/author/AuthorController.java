package com.mo7s.academySystem.user.author;


import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {


    final private AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @GetMapping
    public List<AuthorDto> getAllAuthors(){
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable Integer id){
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public AuthorDto createAuthor(@Valid @RequestBody AuthorDto author){
        return authorService.saveAuthor(author);
    }

    @PutMapping("/{id}")
    public AuthorDto updateAuthor(@PathVariable Integer id, @Valid @RequestBody AuthorDto newAuthor){
       return authorService.updateAuthorById(id , newAuthor);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Integer id){
        authorService.deleteAuthorById(id);
    }
}
