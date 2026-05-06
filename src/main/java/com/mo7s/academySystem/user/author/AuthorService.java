package com.mo7s.academySystem.user.author;


import com.mo7s.academySystem.user.auth.RegisterRequest;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AuthorService {

    final private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public List<AuthorDto> getAllAuthors(){
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(AuthorDto::new).toList();
    }

    public AuthorDto getAuthorById(Integer id){
        Author author = authorRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return new AuthorDto(author);
    }

    public AuthorDto getAuthorByUserName(String userName){
        Author author = authorRepository.findByUserName(userName).orElseThrow(NoSuchElementException::new);
        return new AuthorDto(author);
    }


    public AuthorDto saveAuthor(AuthorDto authorDto){
        Author saved = authorRepository.save(AuthorDto.toEntity(authorDto));
        return new AuthorDto(saved);
    }

    public AuthorDto updateAuthorById(Integer id , AuthorDto newAuthorDto){
        Author author = authorRepository.findById(id).orElseThrow(NoSuchElementException::new);
        AuthorDto oldAuthorDto = new AuthorDto(author);
        oldAuthorDto = updateAuthor(oldAuthorDto, newAuthorDto);
        Author updated = authorRepository.save(AuthorDto.toEntity(oldAuthorDto));
        return new AuthorDto(updated);
    }

    public void deleteAuthorById(Integer id){
        authorRepository.deleteById(id);
    }


    private AuthorDto updateAuthor(AuthorDto oldAuthor , AuthorDto newAuthor){
        Class<?> clazz = oldAuthor.getClass();
        while(clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    if (field.get(newAuthor) != null) {
                        field.set(oldAuthor, field.get(newAuthor));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            clazz = clazz.getSuperclass();
        }
        return oldAuthor;
    }

    public AuthorDto register(RegisterRequest request) {
        if(authorRepository.findByUserName("author_" + request.getUserName()).isPresent()){
            throw new IllegalArgumentException("Username already exists");
        }

        if(authorRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }

        Author author = Author.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userName("author_" + request.getUserName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
        Author saved = authorRepository.save(author);
        return new AuthorDto(saved);
    }
}
