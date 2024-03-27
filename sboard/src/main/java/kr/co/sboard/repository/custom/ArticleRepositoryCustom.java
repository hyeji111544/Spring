package kr.co.sboard.repository.custom;

import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryCustom {


    public Page<Article> searchArticles(PageRequestDTO pageRequestDTO, Pageable pageable);

}
