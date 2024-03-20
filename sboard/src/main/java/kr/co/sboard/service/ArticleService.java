package kr.co.sboard.service;

import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.FileDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.entity.File;
import kr.co.sboard.repository.ArticleRepository;
import kr.co.sboard.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<ArticleDTO> findByParentAndCate(int parent, String cate){
        List<Article> articles = articleRepository.findByParentAndCate(parent, cate);

        return articles.stream()
                .map((entity)->modelMapper.map(entity, ArticleDTO.class))
                .toList();
    }

    public void insertArticle(ArticleDTO articleDTO){

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

    }

    // fileUpload 메서드 -> FileService 클래스로 이동

}
