package com.pinkylam.blog.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinkylam.blog.dao.ArticleCateLabelDao;
import com.pinkylam.blog.dao.ArticleDao;
import com.pinkylam.blog.dao.CateLabelDao;
import com.pinkylam.blog.entity.Article;
import com.pinkylam.blog.entity.ArticleCateLabel;
import com.pinkylam.blog.entity.CateLabel;

/**
 * @author Pinky Lam 908716835@qq.com
 * @date 2017年7月12日 下午5:30:05
 */

@Service
public class ArticleService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ArticleDao articleDao;
	@Autowired
	ArticleCateLabelDao articleCateLabelDao;
	@Autowired
	CateLabelDao cateLabelDao;

	@Transactional
	public void saveArticle(Article article) {
		if (article.getId() != null) {
			Article art = articleDao.getOne(article.getId());
			art.setContent(article.getContent());
			art.setSubtitle(article.getSubtitle());
			art.setTitle(article.getTitle());
			art.setUpdateTime(new Date());
			art.setWritingTime(art.getWritingTime() + article.getWritingTime());

			String[] tags = article.getTag().split(",");

			List<CateLabel> cateLabels = cateLabelDao.findCateLabelByArticleId(article.getId());
			articleCateLabelDao.deleteArticleCateLabel(article.getId());// 删除文章标签关系

			for (CateLabel cateLabel : cateLabels) {
				if (cateLabel.getType() == CateLabel.LABEL_TYPE) {
					for (String tag : tags) {
						CateLabel label = cateLabelDao.findCateLabelByNameAndType(tag, CateLabel.LABEL_TYPE);
						if (label != null) {
							saveArticleCateLabel(article, label);
						} else {
							label = new CateLabel(tag, CateLabel.LABEL_TYPE);
							label = cateLabelDao.save(label);
							saveArticleCateLabel(article, label);
						}
					}
				}
			}
			article = articleDao.save(art);
		} else {
			article.setCreateTime(new Date());
			article.setStatus(Article.ARTICLE_DRAFT);
			article.setHits(0);
			article = articleDao.save(article);

			ArticleCateLabel articleCateLabel = new ArticleCateLabel(article.getId(), article.getCateId());
			articleCateLabelDao.save(articleCateLabel);

			String[] tags = article.getTag().split(",");

			for (String tag : tags) {
				// 验证是否存在
				CateLabel label = cateLabelDao.findCateLabelByNameAndType(tag, CateLabel.LABEL_TYPE);
				if (label != null) {
					articleCateLabel = saveArticleCateLabel(article, label);
					continue;
				}

				label = new CateLabel(tag, CateLabel.LABEL_TYPE);
				label = cateLabelDao.save(label);

				articleCateLabel = saveArticleCateLabel(article, label);

			}
		}

	}

	/**
	 * @Title: saveArticleCateLabel
	 * @Description: 保存标签与文章之间的关系
	 * @param article
	 * @param label
	 * @return ArticleCateLabel
	 */
	private ArticleCateLabel saveArticleCateLabel(Article article, CateLabel label) {
		ArticleCateLabel articleCateLabel = new ArticleCateLabel(article.getId(), label.getId());
		articleCateLabelDao.save(articleCateLabel);
		return articleCateLabel;
	}

}
