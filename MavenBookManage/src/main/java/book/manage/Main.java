package book.manage;

import book.manage.entity.Book;
import book.manage.entity.Student;
import book.manage.sql.SqlUtil;
import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.LogManager;

@Log
public class Main {
    public static void main(String[] args) throws InterruptedException {
        try(Scanner scanner = new Scanner(System.in)){
            LogManager manager = LogManager.getLogManager();
            manager.readConfiguration(Resources.getResourceAsStream("logging.properties"));
            while(true) {
                System.out.println("===========================");
                System.out.println("1.录入学生信息");
                System.out.println("2.录入书籍信息");
                System.out.println("3.学生添加借阅信息");
                System.out.println("4.查询借阅信息");
                System.out.println("5.查询学生信息");
                System.out.println("6.查询书籍信息");
                System.out.print("输入你想要执行的操作(输入其他任意数字退出):");
                int input;
                try{
                   input = scanner.nextInt();
                }catch(Exception e){
                    return;
                }
                scanner.nextLine();
                switch(input){
                   case 1: {
                       addStudent(scanner);
                       break;
                   }
                   case 2: {
                       addBook(scanner);
                       break;
                   }
                   case 3: {
                        addBorrow(scanner);
                        break;
                   }
                   case 4:{
                       showBorrow();
                        break;
                   }
                   case 5:{
                        showStudent();
                        break;
                    }
                    case 6:{
                        showBook();
                        break;
                    }
                   default: {
                       System.out.println("正在退出...");
                       Thread.sleep(1000);
                       return;
                   }
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void showBorrow(){
        SqlUtil.doSqlWork(mapper->{
            mapper.getBorrowList().forEach(borrow -> {
                System.out.println(borrow.getStudent().getName()+" -> "+borrow.getBook().getTitle());
            });
        });
    }
    private static void showStudent(){
        SqlUtil.doSqlWork(mapper->{
            mapper.getStudentList().forEach(student -> {
                System.out.println(student.getSid()+" "+""+student.getName()+" "+student.getSex()+" "+student.getGrade()+"级");
            });
        });
    }
    private static void showBook(){
        SqlUtil.doSqlWork(mapper->{
            mapper.getBookList().forEach(book -> {
                System.out.println(book.getBid()+"."+book.getTitle()+"("+book.getDesc()+")"+" price:"+book.getPrice()+"元");
            });
        });
    }
    private static void addBorrow(Scanner scanner){
        System.out.print("请输入借阅号:");
        String id = scanner.nextLine();
        int i = Integer.parseInt(id);
        System.out.print("请输入学号: ");
        String sid = scanner.nextLine();
        int s = Integer.parseInt(sid);
        System.out.print("请输入书籍号:");
        String bid = scanner.nextLine();
        int b = Integer.parseInt(bid);
        SqlUtil.doSqlWork(mapper -> {
           int flag = mapper.addBorrow(i, s, b);
            if(flag > 0){
                System.out.println("借阅信息录入成功");
                log.info("新添加了一条借阅信息:"+i+" "+s+" "+b+" ");
            }
            else System.out.println("借阅信息录入失败，请重试!");
        });

    }
    private static void addBook(Scanner scanner){
        System.out.print("请输入书籍编号:");
        String bid = scanner.nextLine();
        int b = Integer.parseInt(bid);
        System.out.print("请输入书籍标题:");
        String title = scanner.nextLine();
        System.out.print("请输入书籍介绍:");
        String desc = scanner.nextLine();
        System.out.print("请输入书籍价格:");
        String price = scanner.nextLine();
        double p = Double.parseDouble(price);
        Book book = new Book(b,title, desc, p);
        SqlUtil.doSqlWork(mapper -> {
            int flag = mapper.addBook(book);
            if(flag > 0){
                System.out.println("书籍信息录入成功");
                log.info("新添加了一条书籍信息:"+book);
            }
            else System.out.println("书籍信息录入失败，请重试!");
        });
    }
    private static void addStudent(Scanner scanner){
        System.out.print("请输入学生学号:");
        String sid = scanner.nextLine();
        int s = Integer.parseInt(sid);
        System.out.print("请输入学生名字:");
        String name = scanner.nextLine();
        System.out.print("请输入学生性别(男/女):");
        String sex = scanner.nextLine();
        System.out.print("请输入学生年级:");
        String grade = scanner.nextLine();
        int g = Integer.parseInt(grade);
        Student student = new Student(s,name, sex, g);
        SqlUtil.doSqlWork(mapper -> {
           int flag = mapper.addStudent(student);
           if(flag > 0){
               System.out.println("学生信息录入成功");
               log.info("新添加了一条学生信息:"+student);
           }
           else System.out.println("学生信息录入失败，请重试!");
        });
    }
}
