package com.coldblue.data.mapper

import com.coldblue.database.entity.TodoEntity
import com.coldblue.model.Todo

object TodoEntityMapper : EntityMapper<Todo, TodoEntity> {

    override fun asEntity(domain: Todo): TodoEntity {
        return TodoEntity(
            time = domain.time,
            todoGroupId = domain.todoGroupId,
            date = domain.date,
            content = domain.content,
            title = domain.title,
            id = domain.id,
        )
    }

    fun asEntity(domain: List<Todo>): List<TodoEntity> {
        return domain.map { todo ->
            todo.asEntity()
        }
    }

    override fun asDomain(entity: TodoEntity): Todo {
        return Todo(
            time = entity.time,
            todoGroupId = entity.todoGroupId,
            date = entity.date,
            content = entity.content,
            title = entity.title,
            id = entity.id,
        )
    }

    fun asDomain(entity: List<TodoEntity>): List<Todo> {
        return entity.map { todoEntity ->
            todoEntity.asDomain()
        }
    }
}

fun List<Todo>.asEntity(): List<TodoEntity> {
    return TodoEntityMapper.asEntity(this)
}

fun Todo.asEntity(): TodoEntity {
    return TodoEntityMapper.asEntity(this)
}

fun List<TodoEntity>.asDomain(): List<Todo> {
    return TodoEntityMapper.asDomain(this)
}

fun TodoEntity.asDomain(): Todo {
    return TodoEntityMapper.asDomain(this)
}
