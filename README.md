# 在不指定关联表的情况下关联关系是否会生成中间表

- 单向一对多，会生成
- 单向多对一，不会生成
- 单向一对一，不会生成
- 双向关系中只要加入mappedBy就可以正确展示

结论：

- 在关联关系中，要么指定关联字段，要么指定关联表，不要让程序运行自动生成。
- 对于单向和双向关系，双向关系可以方便从双向查询，但是单向只能单向查询
- 单向程序复杂度低，双向程序复杂度高，会有更多的内存消耗等等。

示例代码

1.单向一对一

```java
@Entity
public class Employee {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "parking_spot_id")
    private ParkingSpot parkingSpot;

}

@Entity
public class ParkingSpot {

    @Id
    private Long id;

}
```

2.单向一对多

```java
@Entity
public class Department {

    @Id
    private Long id;

    @OneToMany
    @JoinColumn(name = "department_id")
    private List<Employee> employees;
}

@Entity
public class Employee {

    @Id
    private Long id;
}
```

3.单向多对一

```java
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;
}

@Entity
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
```

4.单向多对多

```java
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

}

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
```
5.双向一对一
```java
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    //... 

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    // ... getters and setters
}

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    //...

    @OneToOne(mappedBy = "address")
    private User user;

    //... getters and setters
}
```
6.对于非强制的一对一关系，最好使用中间表，来避免出现空置的情况，下面的例子是雇员和工作电脑，雇员不一定有工作电脑，工作电脑也有可能是闲置，不属于某个雇员。同样的道理也可以推到一对多关系，也可以使用中间表。
```java
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    //...

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "emp_workstation", 
      joinColumns = 
        { @JoinColumn(name = "employee_id", referencedColumnName = "id") },
      inverseJoinColumns = 
        { @JoinColumn(name = "workstation_id", referencedColumnName = "id") })
    private WorkStation workStation;

    //... getters and setters
}

@Entity
@Table(name = "workstation")
public class WorkStation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    //...

    @OneToOne(mappedBy = "workStation")
    private Employee employee;

    //... getters and setters
}
```
7.双向一对多关系
```
@Entity
public class Department {
 
    @OneToMany(mappedBy = "department")
    private List<Employee> employees;
}
 
@Entity
public class Employee {
 
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}

```
8.双向多对多
```
@Entity
public class Student {
 
    @ManyToMany(mappedBy = "students")
    private List<Course> courses;
}
 
@Entity
public class Course {
 
    @ManyToMany
    @JoinTable(name = "course_student",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id"))
    private List<Student> students;
}
```
# mappedBy的具体使用及其含义

在双向关系中必须进行设置，避免建立多个字段来对应关联关系。