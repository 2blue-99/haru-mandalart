package com.coldblue.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.coldblue.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("DROP TABLE current_group")
            database.execSQL("DROP TABLE todo")
            database.execSQL("DROP TABLE todo_group")
            database.execSQL(
                "CREATE TABLE `manda_todo` (`id` INTEGER NOT NULL, `title` TEXT  NOT NULL," +
                        "`manda_index` INTEGER NOT NULL," +
                        "`is_done` INTEGER NOT NULL," +
                        "`is_alarm` INTEGER NOT NULL," +
                        "`time` INTEGER ," +
                        "`date` TEXT NOT NULL," +
                        "`origin_id` INTEGER NOT NULL," +
                        "`is_sync` INTEGER  NOT NULL," +
                        "`is_del` INTEGER NOT NULL," +
                        "`update_time` TEXT NOT NULL," +
                        " PRIMARY KEY(`id`))"
            )
        }
    }
    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE `manda_todo` ADD COLUMN `repeat_cycle` INTEGER DEFAULT 0 NOT NULL;"
            )
        }
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDataBase =
        Room.databaseBuilder(context, AppDataBase::class.java, "hm-dataBase")
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()


}