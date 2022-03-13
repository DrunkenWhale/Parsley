# Parsley

Delicate Orm，All basic function are .... not be implemented

## CURD

### Create

use `table[T]`

```scala
import com.parsley.orm.DSL

case class Person(name: String, age: Int)
// model must be case class
// for the pure function!

val persons = table[Person]

on(persons)(person => declare(
    person.name is primaryKey,
    person.age is indexed
))

create(persons)

```

this code equals execute SQL:

```sql

CREATE TABLE IF NOT EXISTS `Person`(
    `name` CHAR(128) PRIMARY KEY ,
    `age` INT,
    INDEX(`age`)
);

```

### Query

query all columns:

```scala

persons.query(*)

```
  
return `List[Person]`
  

with limit:  

```scala

persons.query("age" === 114514 limit 1)

```

will return `List[Person]` include all columns that age = 114514

### Insert

```scala
val person = Person("野兽前辈",114514)
persons.insert(person)
```
insert new column in `Person` table
this column's name = "野兽前辈"，age=114514

### Update

```scala

persons.update("age"==>114514)(*)

```

this operation will set all columns' `age == 114514` in table `Person`

### Delete

delete all columns in table `Person`

```scala

persons.delete(*)

```

delete special column

```scala

persons.delete("age"===114514)

```

this operation will delete all columns that `age == 114514`

## OneToMany 

for example,you have two tables like this:
  
```scala

val books = table[Book]
val students = table[Student]
on(students)(student => declare(
    student.name is primaryKey
))
on(books)(book => declare(
    book.name is primaryKey
))

```

now, you can declare relation about them before create

```scala

students <== books

```

you will build a `OneToMany` between table `students` and `books`

```
                +---- book(aaa)  
student(???)--------- book(bbb)  
                +---- book(ccc)  
```

one student can relate many book

### relate two object

```scala

val student = Student("反射魔典", 514)
val book = Book("昏睡红茶",1145141919)
DataBase.students.insertRelation(student)(book)

```

now you will insert a new column in table `Book`,and you can use this student instance to get it
  
### query by relation

```scala

DataBase.students.queryRelation[Book](student)

```

you will get all columns which is related to instance `student`

### ManyToMany

#### Create
  
```scala

students <==> books

```
  
### Insert
  
```scala

students.relatedManyToMany(student)(book)

```
  
create a ManyToMany RelationShip between instance `student` and `book`
  
### query

```scala

students.queryManyToManyRelation[Book](student)

```

get a List[Book] have all instance related to this `student` instance

