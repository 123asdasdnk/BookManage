package book.manage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
    int bid;
    String title;
    String desc;
    double price;
}
