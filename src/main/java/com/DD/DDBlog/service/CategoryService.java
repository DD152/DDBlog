package com.DD.DDBlog.service;

import com.DD.DDBlog.dao.CategoryDao;
import com.DD.DDBlog.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;


@Service
@Transactional
public class CategoryService {
    @Lazy
    @Autowired
    CategoryDao categoryDao;

    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    public boolean deleteCategoryByIds(String ids) {
        String[] split = ids.split(",");
        int result = categoryDao.deleteCategoryByIds(split);
        return result == split.length;
    }

    public int updateCategoryById(Category category) {
        return categoryDao.updateCategoryById(category);
    }

    public int addCategory(Category category) {
        category.setDate(new Timestamp(System.currentTimeMillis()));
        return categoryDao.addCategory(category);
    }
}
