package com.DD.DDBlog.controller.admin;


import com.DD.DDBlog.entity.Category;
import com.DD.DDBlog.entity.RespBean;
import com.DD.DDBlog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * 超级管理员专属Controller
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/all")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @DeleteMapping( "/{ids}")
    public RespBean deleteById(@PathVariable String ids) {
        boolean result = categoryService.deleteCategoryByIds(ids);
        if (result) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }

    @PostMapping("/")
    public RespBean addNewCate(Category category) {

        if ("".equals(category.getCateName()) || category.getCateName() == null) {
            return new RespBean("error", "请输入栏目名称!");
        }

        int result = categoryService.addCategory(category);

        if (result == 1) {
            return new RespBean("success", "添加成功!");
        }
        return new RespBean("error", "添加失败!");
    }

    @PutMapping( "/")
    public RespBean updateCate(Category category) {
        int i = categoryService.updateCategoryById(category);
        if (i == 1) {
            return new RespBean("success", "修改成功!");
        }
        return new RespBean("error", "修改失败!");
    }
}
