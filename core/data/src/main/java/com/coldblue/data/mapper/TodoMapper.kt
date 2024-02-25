package com.coldblue.data.mapper

import com.coldblue.database.entity.TodoEntity
import com.coldblue.model.Todo

object TodoEntityMapper : EntityMapper<Todo, TodoEntity> {

    override fun asEntity(domain: Todo): TodoEntity {
        return TodoEntity(
            title = domain.title,
            content = domain.content,
            isDone = domain.isDone,
            time = domain.time,
            date = domain.date,
            todoGroupId = domain.todoGroupId,
            originId = domain.originId,
            isSync = domain.isSync,
            isDel = domain.isDel,
            updateTime = domain.updateTime,
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
            title = entity.title,
            content = entity.content,
            isDone = entity.isDone,
            time = entity.time,
            date = entity.date,
            todoGroupId = entity.todoGroupId,
            originId = entity.originId,
            isSync = entity.isSync,
            isDel = entity.isDel,
            updateTime = entity.updateTime,
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
