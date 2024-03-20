package kr.co.sboard.service;

import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.FileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    @Value("${file.upload.path}")
    private  String fileUploadPath;

    public List<FileDTO> fileUpload(ArticleDTO articleDTO){
        String path = new File(fileUploadPath).getAbsolutePath();  //실제 업로드 파일을 구하는 것

        // 파일 정보 리턴을 위한 리스트
        List<FileDTO> files = new ArrayList<>();

        log.info("fileUploadPath..1 : " + path);

        for(MultipartFile mf : articleDTO.getFiles()){
            String oName = mf.getOriginalFilename();
            log.info("oName..2 : "+oName);

            String ext = oName.substring(oName.lastIndexOf(".")); //확장자
            String sName = UUID.randomUUID().toString()+ ext;

            log.info("sName..3 : "+sName);

            try {
                mf.transferTo(new File(path, sName));

                //파일 정보 생성
                FileDTO fileDTO = FileDTO.builder()
                        .oName(oName)
                        .sName(sName)
                        .build();

                log.info("uploadFile..4 : " + fileDTO);
                //리스트 저장
                files.add(fileDTO);

            }catch (IOException e){
                log.error("fileUpload : " + e.getMessage());
            }

        }

        return files;
    }

}
