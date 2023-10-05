package book.manage.mapper;

import book.manage.entity.Book;
import book.manage.entity.Borrow;
import book.manage.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BookMapper {

    @Insert("insert into student(sid, name, sex, grade) values(#{sid}, #{name}, #{sex}, #{grade})")
    int addStudent(Student student);
    @Insert("insert into book(bid, title, `desc`, price) values(#{bid}, #{title}, #{desc}, #{price})")
    int addBook(Book book);//关键字加`` (ESC下面那个键)
    @Insert("insert into borrow(id, sid, bid) values(#{id}, #{sid}, #{bid})")
    int addBorrow(@Param("id") int id,@Param("sid") int sid,@Param("bid") int bid);

    @Results({
            @Result(column = "id", property = "id", id = true),
            @Result(column = "sid",property = "student", one = @One(select = "getStudentBySid")),
            @Result(column = "bid",property = "book", one = @One(select = "getBookByBid")),

    })
    @Select("select * from borrow")
    List<Borrow> getBorrowList();
    @Select("select * from student")
    List<Student> getStudentList();

    @Select("select * from book")
    List<Book> getBookList();

    @Select("select * from student where sid = #{sid}")
    Student getStudentBySid(int sid);
    @Select("select * from book where bid = #{bid}")
    Book getBookByBid(int bid);

}
