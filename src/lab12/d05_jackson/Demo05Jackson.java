package lab12.d05_jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import java.io.File;
import java.util.List;

/**
 * Slide 5 — Jackson: obiect ↔ JSON.
 */
public class Demo05Jackson {

    // POJO clasic: constructor fără parametri + getteri/setteri
    public static class Book {
        private String title;
        private int pages;
        @JsonIgnore
        private String internalNotes = "nu apare în JSON";

        public Book() { }
        public Book(String title, int pages) { this.title = title; this.pages = pages; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        @JsonProperty("page_count")                 // nume diferit în JSON
        public int getPages() { return pages; }
        public void setPages(int pages) { this.pages = pages; }

        public String getInternalNotes() { return internalNotes; }

        @Override
        public String toString() { return "Book{" + title + ", " + pages + " pagini}"; }
    }

    // Exemplul de pe pagină: @JsonProperty("years") pus DOAR pe setter
    public static class User {
        private String name;
        private int age;
        public User() { }
        public User(String name, int age) { this.name = name; this.age = age; }
        public String getName() { return name; }
        public int getAge() { return age; }
        @JsonProperty("years")
        public void setAge(int age) { this.age = age; }
    }

    // Record: Jackson 2.12+ îl folosește direct, fără setteri și fără constructor gol
    public record Review(String author, int stars) { }

    // Fără constructor fără parametri și fără @JsonCreator
    public static class Immutable {
        private final String id;
        public Immutable(String id) { this.id = id; }
        public String getId() { return id; }
    }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // Obiect → JSON
        Book book = new Book("Enigma Otiliei", 384);
        String json = mapper.writeValueAsString(book);
        System.out.println("serializat:    " + json);

        // JSON → obiect
        Book back = mapper.readValue("{\"title\":\"Ion\",\"page_count\":472}", Book.class);
        System.out.println("deserializat:  " + back);

        // Ce face @JsonProperty pus doar pe setter (cazul de pe pagină)
        System.out.println("User serializat: " + mapper.writeValueAsString(new User("Ana", 30))
                + "  ← numele se aplică și la scriere");

        // Proprietate necunoscută: implicit EROARE
        String withExtra = "{\"title\":\"Ion\",\"page_count\":472,\"isbn\":\"978-973\"}";
        try {
            mapper.readValue(withExtra, Book.class);
        } catch (UnrecognizedPropertyException e) {
            System.out.println("câmp necunoscut → UnrecognizedPropertyException: \"" + e.getPropertyName() + "\"");
        }
        ObjectMapper lenient = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        System.out.println("cu FAIL_ON_UNKNOWN_PROPERTIES=false: " + lenient.readValue(withExtra, Book.class));

        // Record + listă generică (TypeReference ocolește type erasure — Lab 10!)
        List<Review> reviews = List.of(new Review("ana", 5), new Review("ion", 3));
        String reviewsJson = mapper.writeValueAsString(reviews);
        List<Review> reviewsBack = mapper.readValue(reviewsJson, new TypeReference<List<Review>>() { });
        System.out.println("records:       " + reviewsJson + " → " + reviewsBack.get(0));

        // Pretty print + fișier
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File("book.json"), book);
        System.out.println("book.json:\n" + mapper.writeValueAsString(mapper.readValue(new File("book.json"), Book.class)));

        // Clasă fără constructor gol
        try {
            mapper.readValue("{\"id\":\"x1\"}", Immutable.class);
        } catch (DatabindException e) {
            System.out.println("fără constructor gol → " + e.getClass().getSimpleName() + " (soluții: @JsonCreator sau record)");
        }

        // DEMO EROARE: decomentează → UnrecognizedPropertyException la runtime: "isbn" nu există în Book
        // mapper.readValue(withExtra, Book.class);
    }
}
