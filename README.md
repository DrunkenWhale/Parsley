Delicate Orm，All basic function are .... not be implemented

# Parsley

## init

you must create connection before use `ORM`

```scala


DataBaseManager.register(
    MysqlConnection(database = "database_name", password = "password")
)

```

ps: you can define method in class, but can't define any fields out of constructor  
:p, scala reflect so difficult (✪ω✪)

## create

you can create an easy model from class / case class in a easy way like this

```scala
case class Student(name: String, gender: Boolean, age: Int)

object DataBase {
    val students = table[Student]
    on(students)(student => declare(
        student.age is primaryKey,
        student.name is unique,
        student.gender is indexed,
    ))
    students.create()
}

```

## query

```scala

students.query()

```

you can get an instance seq


## insert

```scala

students.insert(Studnet("1145114", true, 1919810))

```