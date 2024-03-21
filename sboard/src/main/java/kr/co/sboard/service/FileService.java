package kr.co.sboard.service;

import jakarta.transaction.Transactional;
import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.FileDTO;
import kr.co.sboard.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;

    @Value("${file.upload.path}")
    private  String fileUploadPath;

    public List<FileDTO> fileUpload(ArticleDTO articleDTO){
        String path = new File(fileUploadPath).getAbsolutePath();  //실제 업로드 파일을 구하는 것

        // 파일 정보 리턴을 위한 리스트
        List<FileDTO> files = new ArrayList<>();

        log.info("fileUploadPath..1 : " + path);

        for(MultipartFile mf : articleDTO.getFiles()){
            log.info("oName..2 : ");

            if(!mf.isEmpty()){

                String oName = mf.getOriginalFilename();
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
        }

        return files;
    }

    @Transactional
    public ResponseEntity<?> fileDownload(int fno) {

        //파일 조회
        kr.co.sboard.entity.File file = fileRepository.findById(fno).get();
        log.info("fileDownload...1");

        try {
            Path path = Paths.get(fileUploadPath + file.getSName());
            String contentType = Files.probeContentType(path);

            log.info("fileDownload...2");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(
                    ContentDisposition.builder("attachment")
                            .filename(file.getOName(), StandardCharsets.UTF_8).build());

            headers.add(HttpHeaders.CONTENT_TYPE, contentType);
            Resource resource = new InputStreamResource(Files.newInputStream(path));
            log.info("fileDownload...3");

            // 파일 다운로드 카운트 업데이트
            file.setDownload(file.getDownload() + 1);
            fileRepository.save(file);
            log.info("fileDownloadCount...");

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        }catch (IOException e){
            log.error("fileDownload : " + e.getMessage());
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> fileDownloadCount(int fno){
        // 파일 조회
        kr.co.sboard.entity.File file = fileRepository.findById(fno).get();
        log.info("fileCount...1 : " + file);
        // 다운로드 카운트 Json 생성
        Map<String, Object> resultMap = new HashMap<>();

        log.info("fileCount...2 : " + resultMap);

        resultMap.put("count", file.getDownload());

        log.info("fileCount...3 : " + resultMap);

        return ResponseEntity.ok().body(resultMap);
    }
}
