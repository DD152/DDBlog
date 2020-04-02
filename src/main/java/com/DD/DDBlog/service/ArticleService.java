package com.DD.DDBlog.service;

import com.DD.DDBlog.dao.ArticleDao;
import com.DD.DDBlog.dao.TagsDao;
import com.DD.DDBlog.entity.Article;
import com.DD.DDBlog.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by sang on 2017/12/20.
 */
@Service
@Transactional
public class ArticleService {

    @Lazy
    @Autowired
    ArticleDao articleDao;
    @Lazy
    @Autowired
    TagsDao tagsDao;

    public int addNewArticle(Article article) {
        //处理文章摘要
        if (article.getSummary() == null || "".equals(article.getSummary())) {
            //直接截取
            String stripHtml = stripHtml(article.getHtmlContent());
            article.setSummary(stripHtml.substring(0, stripHtml.length() > 50 ? 50 : stripHtml.length()));
        }
        if (article.getId() == -1) {
            //添加操作
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (article.getState() == 1) {
                //设置发表日期
                article.setPublishDate(timestamp);
            }
            article.setEditTime(timestamp);
            //设置当前用户
            article.setUid(Util.getCurrentUser().getId());
            int i = articleDao.addNewArticle(article);
            //打标签
            String[] dynamicTags = article.getDynamicTags();
            if (dynamicTags != null && dynamicTags.length > 0) {
                int tags = addTagsToArticle(dynamicTags, article.getId());
                if (tags == -1) {
                    return tags;
                }
            }
            return i;
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (article.getState() == 1) {
                //设置发表日期
                article.setPublishDate(timestamp);
            }
            //更新
            article.setEditTime(new Timestamp(System.currentTimeMillis()));
            int i = articleDao.updateArticle(article);
            //修改标签
            String[] dynamicTags = article.getDynamicTags();
            if (dynamicTags != null && dynamicTags.length > 0) {
                int tags = addTagsToArticle(dynamicTags, article.getId());
                if (tags == -1) {
                    return tags;
                }
            }
            return i;
        }
    }

    private int addTagsToArticle(String[] dynamicTags, Long aid) {
        //1.删除该文章目前所有的标签
        tagsDao.deleteTagsByAid(aid);
        //2.将上传上来的标签全部存入数据库
        tagsDao.saveTags(dynamicTags);
        //3.查询这些标签的id
        List<Long> tIds = tagsDao.getTagsIdByTagName(dynamicTags);
        //4.重新给文章设置标签
        int i = tagsDao.saveTags2ArticleTags(tIds, aid);
        return i == dynamicTags.length ? i : -1;
    }

    public String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }

    public List<Article> getArticleByState(Integer state, Integer page, Integer count, String keywords) {
        int start = (page - 1) * count;
        Long uid = Util.getCurrentUser().getId();
        return articleDao.getArticleByState(state, start, count, uid, keywords);
    }


    public int getArticleCountByState(Integer state, Long uid, String keywords) {
        return articleDao.getArticleCountByState(state, uid, keywords);
    }

    public int updateArticleState(Long[] aids, Integer state) {
        if (state == 2) {
            return articleDao.deleteArticleById(aids);
        } else {
            return articleDao.updateArticleState(aids, 2);//放入到回收站中
        }
    }

    public int restoreArticle(Integer articleId) {
        return articleDao.updateArticleStateById(articleId, 1); // 从回收站还原在原处
    }

    public Article getArticleById(Long aid) {
        Article article = articleDao.getArticleById(aid);
        articleDao.pvIncrement(aid);
        return article;
    }

}
