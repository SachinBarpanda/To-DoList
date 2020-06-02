package great.sachin.to_dolist

const val DB_NAME = "ToDoList"
const val DB_VERSION = 1
const val TABLE_TO_DO = "ToDoTable"


const val COL_ID = "id"
const val COL_NAME = "name"
const val COL_CREATED_ID = "created_at"
const val COL_REMAINDER = "remainder"
const val COL_IS_COMPLETED = "isCompleted"


/***
CREATE TABLE Untitled (
id integer PRIMARY KEY AUTOINCREMENT,
name varchar,
created_at datetime,
remainder datetime,
isCompleted boolean);
***/