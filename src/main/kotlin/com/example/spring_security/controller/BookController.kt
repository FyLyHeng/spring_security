package com.example.spring_security.controller

import com.example.spring_security.Repository.BookRepo
import com.example.spring_security.model.Book
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/book")
class BookController {

    @Autowired
    lateinit var bookRepo: BookRepo


    @GetMapping
    fun home(): MutableList<Book> {
        return bookRepo.findAll()
    }


    @PostMapping
    fun add (@RequestBody book : Book) : Book {
        return bookRepo.save(book)
    }
}
