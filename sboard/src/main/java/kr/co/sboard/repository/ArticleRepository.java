package kr.co.sboard.repository;

import kr.co.sboard.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    //select * from article where cate=? and parent=0
    public List<Article> findByParentAndCate(int parent, String cate);
}