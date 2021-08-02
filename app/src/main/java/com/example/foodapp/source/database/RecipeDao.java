package com.example.foodapp.source.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodapp.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Recipe recipe);

    @Query("delete from recipesTable")
    void deleteAll();

    @Query("select * from recipesTable order by title asc")
    LiveData<List<Recipe>> getAllRecipes();

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT EXISTS(SELECT * FROM recipesTable WHERE id = :id)")
    LiveData<Boolean> rowExists(int id);
}
