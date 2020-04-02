package com.DD.DDBlog.dao;

import com.DD.DDBlog.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface CategoryDao {
    List<Category> getAllCategories();

    int deleteCategoryByIds(@Param("ids") String[] ids);

    int updateCategoryById(Category category);

    int addCategory(Category category);
}
