package kr.co.sboard.repository;

import kr.co.sboard.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    //select * from article where cate=? and parent=0
    /*
        JPA 페이지네이션 처리를 위한 Page 타입으로 변환
     */
    public Page<Article> findByParentAndCate(int parent, String cate, Pageable pageable);

    public List<Article> findByParent(int parent);
}