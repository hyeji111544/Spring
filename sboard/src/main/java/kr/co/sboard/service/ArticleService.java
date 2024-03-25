package kr.co.sboard.service;

import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.FileDTO;
import kr.co.sboard.dto.PageRequestDTO;
import kr.co.sboard.dto.PageResponseDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.entity.File;
import kr.co.sboard.repository.ArticleRepository;
import kr.co.sboard.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;

    // RootConfig Bean 생성/등록
    private final ModelMapper modelMapper;

    public PageResponseDTO findByParentAndCate(PageRequestDTO pageRequestDTO){

        Pageable pageable = pageRequestDTO.getPageable("no");

        Page<Article> pageArticle = articleRepository.findByParentAndCate(0, pageRequestDTO.getCate(), pageable);


        List<ArticleDTO> dtoList = pageArticle.getContent().stream()
                .map(entity -> modelMapper.map(entity, ArticleDTO.class))
                .toList();

        int total = (int) pageArticle.getTotalElements();

        return PageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total(total)
                .build();
    }

    public ArticleDTO findById(int no){
        Optional<Article> optArticle = articleRepository.findById(no);
        log.info("findById ...1 " + optArticle);

        ArticleDTO articleDTO = null;

        if(optArticle.isPresent()){
            log.info("findById ...2 ");
            Article article = optArticle.get();
            log.info("findById...3 : " + article.toString());
            articleDTO = modelMapper.map(article, ArticleDTO.class);
            log.info("findById ...4 ");
        }
        log.info("findById ...5 : " + articleDTO);
        return articleDTO;
    }

    public Article insertArticle(ArticleDTO articleDTO){

        // 파일 첨부 처리
        List<FileDTO> files = fileService.fileUpload(articleDTO);

        // 파일 전부 갯수 초기화
        articleDTO.setFile(files.size());

        // articleDTO 를 articleEntity로 변환
        Article article = modelMapper.map(articleDTO, Article.class);
        log.info("insert : " + article.toString());

        // 저장 후 저장한 Entity 객체 반환
        Article savedArticle = articleRepository.save(article);
        log.info("insertArticle : " + savedArticle.toString());

        // 파일 insert
        for (FileDTO fileDTO : files){
            fileDTO.setAno(savedArticle.getNo());

            File file = modelMapper.map(fileDTO, File.class);

            fileRepository.save(file);
        }

        return savedArticle;
    }

    public ResponseEntity<?> deleteArticle(int no){
        log.info("no : "+ no);

        Optional<Article> optArticle = articleRepository.findById(no);

        log.info("optArticle : " + optArticle);

        if(optArticle.isPresent()){
            log.info("deleteArticle.....1");

            articleRepository.deleteById(no);

            return ResponseEntity
                    .ok()
                    .body(optArticle.get());
        }else {
            log.info("deleteArticle.....2");

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("not found");
        }
    }

    public ResponseEntity<?> updateArticle(ArticleDTO articleDTO){

        // 수정 전 존재여부 확인
        Optional<Article> optArticle = articleRepository.findById(articleDTO.getNo());

        if(optArticle.isPresent()){
            Article article = optArticle.get();

            article.setContent(articleDTO.getContent());
            article.setTitle(articleDTO.getTitle());


            log.info("updateArticle....1" + article);

            Article modifiedArticle = articleRepository.save(article);

            // 수정 후 수정 데이터 반환
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(modifiedArticle);
        }else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("not found");
        }
    }

    // insertComment 메서드 -> CommentService 클래스로 이동
    // fileUpload 메서드 -> FileService 클래스로 이동


}
