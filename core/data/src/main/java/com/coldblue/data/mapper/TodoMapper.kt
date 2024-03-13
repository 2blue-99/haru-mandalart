package com.coldblue.data.mapper

import com.coldblue.data.util.getUpdateTime
import com.coldblue.database.entity.TodoEntity
import com.coldblue.database.entity.TodoWithGroupName
import com.coldblue.model.Todo
import com.coldblue.network.model.NetworkTodo

object TodoEntityMapper {
    fun asEntity(domain: Todo): TodoEntity {
        return TodoEntity(
            title = domain.title,
            content = domain.content,
            isDone = domain.isDone,
            time = domain.time,
            date = domain.date,
            todoGroupId = domain.todoGroupId,
            originId = domain.originId,
            isSync = false,
            isDel = domain.isDel,
            updateTime = getUpdateTime(),
            id = domain.id,
        )
    }

    fun asEntity(domain: List<Todo>): List<TodoEntity> {
        return domain.map { todo ->
            todo.asEntity()
        }
    }

    fun asDomain(entity: TodoWithGroupName): Todo {
        return Todo(
            title = entity.todo.title,
            content = entity.todo.content,
            isDone = entity.todo.isDone,
            time = entity.todo.time,
            date = entity.todo.date,
            todoGroupId = entity.todo.todoGroupId,
            isDel = entity.todo.isDel,
            originId = entity.todo.originId,
            groupName = entity.groupName,
            id = entity.todo.id,
        )
    }

    fun asDomain(entity: List<TodoWithGroupName>): List<Todo> {
        return entity.map { todoEntity ->
            todoEntity.asDomain()
        }
    }

    fun asEntity(network: List<NetworkTodo>, todoIds: List<Int?>): List<TodoEntity> {
        return network.mapIndexed { index, networkTodo ->
            networkTodo.asEntity(todoIds[index])
        }
    }

    fun asEntity(network: NetworkTodo, todoId: Int?): TodoEntity {
        return TodoEntity(
            title = network.title,
            content = network.content,
            isDone = network.is_done,
            time = network.time,
            date = network.date,
            todoGroupId = network.todo_group_id,
            originId = network.id,
            isSync = true,
            isDel = network.is_del,
            updateTime = network.update_time,
            id = todoId ?: 0,
        )
    }
}

fun List<Todo>.asEntity(): List<TodoEntity> {
    return TodoEntityMapper.asEntity(this)
}

fun List<NetworkTodo>.asEntity(todoIds: List<Int?>): List<TodoEntity> {
    return TodoEntityMapper.asEntity(this, todoIds)
}

fun Todo.asEntity(): TodoEntity {
    return TodoEntityMapper.asEntity(this)
}


fun NetworkTodo.asEntity(todoId: Int?): TodoEntity {
    return TodoEntityMapper.asEntity(this, todoId)
}

fun List<TodoWithGroupName>.asDomain(): List<Todo> {
    return TodoEntityMapper.asDomain(this)
}

fun TodoWithGroupName.asDomain(): Todo {
    return TodoEntityMapper.asDomain(this)
}
