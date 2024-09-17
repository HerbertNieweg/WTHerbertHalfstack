package wtSeptember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SpringBootApplication
@RestController
public class Main {

    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @GetMapping("/greet")
    public String greet(){
        return "Hello world";
    }



    @GetMapping("/getBooks")
    public List<Book> getBooks(){
        return (List<Book>) bookRepository.findAll();
    }

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewBook (@RequestParam String titel, @RequestParam String auteur, @RequestParam int aantalExemplaren, @RequestParam String afbeeldingURL) {
        Book b = new Book();
        b.setTitel(titel);
        b.setAuteur(auteur);
        b.setAantalExemplaren(aantalExemplaren);
        b.setAfbeeldingURL(afbeeldingURL);
        bookRepository.save(b);
        return aantalExemplaren + " copy(s) of "+ titel + " written by " + auteur + " is(are) saved";
    }

    @PostMapping("/multiAdd")
    public @ResponseBody String addBookMulti (@RequestBody List<Book> books){
        bookRepository.saveAll(books);
        return books.size() + " added to repository";
    }

    @PutMapping("/update/{id}")
    public String updateUser (@PathVariable int id, @RequestBody Book book) {
        Book updatedBook = bookRepository.findById(id).get();
        updatedBook.setTitel(book.getTitel());
        updatedBook.setAuteur(book.getAuteur());
        updatedBook.setAantalExemplaren(book.getAantalExemplaren());
        updatedBook.setAfbeeldingURL(book.getAfbeeldingURL());
        bookRepository.save(updatedBook);
        return "Book id: " + id + " updated";
    }

    @DeleteMapping("/deleteBook/{id}")
        public String deleteBook(@PathVariable int id){
        Book deleteBook = bookRepository.findById(id).get();
        bookRepository.delete(deleteBook);
        return "Book with id: " + id + " deleted.";
    }


}



