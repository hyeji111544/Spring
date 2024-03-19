package kr.co.sboard.service;

import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    // RootConfig Bean 생성/등록
    private final ModelMapper modelMapper;

    public void insertArticle(ArticleDTO articleDTO){
        Article article = modelMapper.map(articleDTO, Article.class);
        log.info(article.toString());

        articleRepository.save(article);
    }
}
