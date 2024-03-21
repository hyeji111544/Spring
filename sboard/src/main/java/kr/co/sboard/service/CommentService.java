package kr.co.sboard.service;

import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final ArticleRepository articleRepository;
    public final ModelMapper modelMapper;

    public ResponseEntity<Article> insertComment(ArticleDTO articleDTO){
       Article article = modelMapper.map(articleDTO, Article.class);
        log.info("insertComment : " + article.toString());

        Article savedArticle = articleRepository.save(article);

        return ResponseEntity.ok().body(savedArticle);

    }


}
