package kr.co.sboard.service;

import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.entity.Article;
import kr.co.sboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    // RootConfig Bean 생성/등록
    private final ModelMapper modelMapper;

    public void insertArticle(ArticleDTO articleDTO){

        fileUpload(articleDTO);
        Article article = modelMapper.map(articleDTO, Article.class);
        log.info(article.toString());

        articleRepository.save(article);
    }

    @Value("${file.upload.path}")
    private  String fileUploadPath;

    public  void fileUpload(ArticleDTO articleDTO){
        String path = new File(fileUploadPath).getAbsolutePath();  //실제 업로드 파일을 구하는 것

        for(MultipartFile mf : articleDTO.getFiles()){
            String oName = mf.getOriginalFilename();
            String ext = oName.substring(oName.lastIndexOf(".")); //확장자
            String sName = UUID.randomUUID().toString()+ ext;

            log.info("oName : "+oName);

            try {
                mf.transferTo(new File(path, sName));
            }catch (IOException e){
                log.error("fileUpload : " + e.getMessage());
            }

        }
    }
}
