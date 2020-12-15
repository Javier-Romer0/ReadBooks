package cat.itb.readbooks;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookViewModel extends ViewModel {
    private BookAdapter adapter;
    static List<Book> bookList = new ArrayList<>();

    public BookViewModel() {
        Book book;
        String[] titles = {"Las legiones malditas", "Los pilares de la tierra", "Un mundo sin fin", "La escriba", "El médico", "La buena cocina",
                "Wigetta - Un viaje mágico", "Yo, Gamer", "Tantos lobos", "Africanus"};
        String[] authors = {"Santiago posteguillo", "Ken Follett", "Ken Follett", "Antonio Garrido", "Noah Gordon", "Karlos Arguiñano",
                "Vegetta777 y Willyrex", "Werlyb", "Lorenzo Silva", "Santiago Posteguillo"};
        int[] status = {1, 2, 3};

        for (int i=1; i<10; i++){
            int randomStatus = (int)(Math.random()*3);
            double randomRate = (Math.random()*5);
            book = new Book();
            book.setTitle(titles[i]);
            book.setAuthor(authors[i]);
            book.setStatus(status[randomStatus]);
            if (status[randomStatus] == 3){
                book.setRate(randomRate);
            }
            bookList.add(book);
        }
        adapter  = new BookAdapter(bookList);
    }
}
