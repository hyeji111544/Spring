package kr.co.ch07.repository.board;

import jakarta.transaction.Transactional;
import kr.co.ch07.entity.board.Article;
import kr.co.ch07.entity.board.Comment;
import kr.co.ch07.entity.board.File;
import kr.co.ch07.entity.board.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class BoardRepositoryTest {

    @Autowired private ArticleRepository articleRepository;
    @Autowired private CommentRepository commentRepository;
    @Autowired private FileRepository fileRepository;
    @Autowired private UserRepository userRepository;

    public void insertUser(){
        // 작업 1 - 사용자 등록
        User user = User.builder()
                .uid("a102")
                .name("김춘추")
                .hp("010-1234-1002")
                .build();

        userRepository.save(user);
    }


    public void insertArticle(){
        // 작업2 - 글 등록
        User user = User.builder()
                .uid("a101")
                .build();

        Article article = Article.builder()
                .title("제목 1입니다")
                .content("내용 입니다.")
                .user(user)
                .build();

        articleRepository.save(article);
    }


    public void insertComment(){
        // 작업3 - 댓글 등록
        User user = User.builder()
                    .uid("a101")
                    .build();

        Article article = Article.builder()
                            .no(2)
                            .build();

        Comment comment = Comment.builder()
                .content("댓글 2-3입니다.")
                .user(user)
                .article(article)
                .build();

        commentRepository.save(comment);
    }

    public void insertFile(){
        // 작업4 - 파일 등록
        Article article = Article.builder()
                .no(2)
                .build();

        File file = File.builder()
                .oName("원래파일명.txt")
                .sName("ADVVD121.txt")
                .article(article)
                .build();

        fileRepository.save(file);
    }

    /*
        연관곽녜로 설정된 엔티티를 조회활 때 하나 이상의 S
     */
    @Test
    @Transactional
    public void selectArticles(){
        List<Article> articles = articleRepository.findAll();
        for(Article article : articles){

            log.info(article.toString());
        }

    }



}