package com.parsley.v2

import com.parsley.v2.`type`.Text

import java.lang.reflect.Constructor
import java.sql.{PreparedStatement, ResultSet}

object TypeMapping {
    def getCaseClassWithInstance(obj: Product, parameter: Seq[Any]): Any = {
        val constructor: Constructor[_] = obj.getClass.getConstructors.head
        parameter.size match {
            case 0 => constructor.newInstance()
            
            case 1 => constructor.newInstance(parameter(0))

            case 2 => constructor.newInstance(parameter(0), parameter(1))

            case 3 => constructor.newInstance(parameter(0), parameter(1), parameter(2))

            case 4 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3))

            case 5 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4))

            case 6 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5))

            case 7 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6))

            case 8 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7))

            case 9 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8))

            case 10 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9))

            case 11 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10))

            case 12 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11))

            case 13 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12))

            case 14 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13))

            case 15 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14))

            case 16 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14), parameter(15))

            case 17 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14), parameter(15), parameter(16))

            case 18 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14), parameter(15), parameter(16), parameter(17))

            case 19 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14), parameter(15), parameter(16), parameter(17), parameter(18))

            case 20 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14), parameter(15), parameter(16), parameter(17), parameter(18), parameter(19))

            case 21 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14), parameter(15), parameter(16), parameter(17), parameter(18), parameter(19), parameter(20))

            case 22 => constructor.newInstance(parameter(0), parameter(1), parameter(2), parameter(3), parameter(4), parameter(5), parameter(6), parameter(7), parameter(8), parameter(9), parameter(10), parameter(11), parameter(12), parameter(13), parameter(14), parameter(15), parameter(16), parameter(17), parameter(18), parameter(19), parameter(20), parameter(21))

            case _ => throw Exception("Illeagl Arguments")
        }
    }

    val convertClassToSchema: String => String = (dataType: String) => dataType match {
        case "Integer" => "INT"
        case "Long" => "BIGINT"
        case "Float" => "FLOAT"
        case "Double" => "DOUBLE"
        case "Boolean" => "INT"
        case "String" => "CHAR(255)"
        case "Character" => "CHAR(1)"
        case "Text" => "TEXT"
        case x => throw Exception(s" type: $x not be implement ")
    }

    val getColumnFromResultSet: (String, ResultSet, Int) => Any = (dataType: String, resultSet: ResultSet, index: Int) => dataType match {
        case "Integer" => resultSet.getInt(index)
        case "Long" => resultSet.getLong(index)
        case "Float" => resultSet.getFloat(index)
        case "Double" => resultSet.getDouble(index)
        case "Boolean" =>resultSet.getBoolean(index)// if (resultSet.getInt(index) == 0) false else true
        case "String" => resultSet.getString(index)
        case "Character" => resultSet.getString(index).indexOf(0)
        case "Text" => Text(resultSet.getString(index))
        case x => throw Exception(s" type: $x not be implement ")
    }

    val setColumnFromCaseClass: (Any, PreparedStatement, Int) => Unit = (data: Any, preparedStatement:PreparedStatement, index: Int) => data.getClass.getSimpleName match {
        case "Integer" => preparedStatement.setInt(index,data.asInstanceOf[Int])
        case "Long" => preparedStatement.setLong(index,data.asInstanceOf[Long])
        case "Float" => preparedStatement.setFloat(index,data.asInstanceOf[Float])
        case "Double" => preparedStatement.setDouble(index,data.asInstanceOf[Double])
        case "Boolean" => preparedStatement.setBoolean(index,data.asInstanceOf[Boolean])
        case "String" => preparedStatement.setString(index,data.asInstanceOf[String])
        case "Character" => preparedStatement.setString(index,data.asInstanceOf[Character].toString)
        case "Text" => preparedStatement.setString(index,data.asInstanceOf[Text].toString())
        case x => throw Exception(s" type: $x not be implement ")
    }

}
