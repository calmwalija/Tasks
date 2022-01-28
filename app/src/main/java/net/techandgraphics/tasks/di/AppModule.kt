package net.techandgraphics.tasks.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.techandgraphics.tasks.data.Repo
import net.techandgraphics.tasks.data.Repository
import net.techandgraphics.tasks.db.Database
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ): Database = Room.databaseBuilder(
        context, Database::class.java, Database.DB_NAME
    )
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providesRepo(db: Database): Repo = Repository(db)

}
