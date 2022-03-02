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
    person.name is PrimaryKey,
    person.age is Indexed
))

```

this code equals execute SQL:

```sql

CREATE TABLE IF NOT EXISTS `Person`{
    `name` CHAR(128) PRIMARY KEY ,
    `age` INT,
    INDEX(`age`)
};

```

### Query

query all columns:

```scala
    query(*) from persons
```
  
return `List[Person]`
  

with limit:  

```scala
    query("age" === 114514 limit 1) from persons
```

will return `List[Person]` include all columns that age = 114514

### Insert

```scala
val person = Person("野兽前辈",114514)
insert(person) in persons
```
insert new column in `Person` table
this column's name = "野兽前辈"，age=114514

### Update

    Future

### Delete

delete all columns in table `Person`

```scala

delete(*) in persons

```

delete special column

```scala
delete("age"===114514) in persons

```

this operation will delete all columns that age == 114514