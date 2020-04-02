package com.DD.DDBlog.dao;

import com.DD.DDBlog.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleDao {
    int addNewArticle(Article article);

    int updateArticle(Article article);
    List<Article> getArticleByState(@Param("state") Integer state, @Param("start") Integer start, @Param("count") Integer count, @Param("uid") Long uid, @Param("keywords") String keywords);

    int getArticleCountByState(@Param("state") Integer state, @Param("uid") Long uid, @Param("keywords") String keywords);

    int updateArticleState(@Param("aids") Long aids[], @Param("state") Integer state);

    int updateArticleStateById(@Param("articleId") Integer articleId, @Param("state") Integer state);

    int deleteArticleById(@Param("aids") Long[] aids);

    Article getArticleById(Long aid);

    void pvIncrement(Long aid);
}
