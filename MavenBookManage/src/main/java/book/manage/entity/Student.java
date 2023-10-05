package book.manage.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    int sid;
    String name;
    String sex;
    int grade;
}
