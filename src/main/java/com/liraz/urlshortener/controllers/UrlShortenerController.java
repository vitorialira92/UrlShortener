package com.liraz.urlshortener.controllers;

import com.liraz.urlshortener.domain.Url;
import com.liraz.urlshortener.dtos.UrlDTO;
import com.liraz.urlshortener.dtos.UrlErrorDTO;
import com.liraz.urlshortener.dtos.UrlResponseDTO;
import com.liraz.urlshortener.services.UrlShortenerService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/s")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService service;


    @PostMapping("/generate")
    public ResponseEntity<?> generateShortUrl(@RequestBody UrlDTO urlDTO){

        Url url = service.generateShortUrl(urlDTO);

        if(url != null){
            UrlResponseDTO response = new UrlResponseDTO();
            response.setShortUrl(url.getShortUrl());
            response.setOriginalUrl(url.getOriginalUrl());
            response.setExpiresOn(url.getExpiresOn().toString());
            return new ResponseEntity<UrlResponseDTO>(response, HttpStatus.OK);
        }

        UrlErrorDTO errorDTO = new UrlErrorDTO();
        errorDTO.setMessage("A mistake occurred. Please try again.");
        errorDTO.setStatus("404");

        return new ResponseEntity<UrlErrorDTO>(errorDTO, HttpStatus.OK);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<?> redirectToOriginalUrl
            (@PathVariable String shortUrl,
                HttpServletResponse response) throws IOException {

        if(StringUtils.isEmpty(shortUrl)){
            UrlErrorDTO errorDTO = new UrlErrorDTO();
            errorDTO.setStatus("400");
            errorDTO.setMessage("Invalid url.");
            return new ResponseEntity<UrlErrorDTO>(errorDTO, HttpStatus.OK);
        }

        Url url = service.getEncodedUrl(shortUrl);

        if(url == null){
            UrlErrorDTO errorDTO = new UrlErrorDTO();
            errorDTO.setStatus("400");
            errorDTO.setMessage("Encoded url not valid.");
            return new ResponseEntity<UrlErrorDTO>(errorDTO, HttpStatus.OK);
        }

        if(url.getExpiresOn().isBefore(LocalDateTime.now())){ //expired
            UrlErrorDTO errorDTO = new UrlErrorDTO();
            errorDTO.setStatus("400");
            errorDTO.setMessage("Encoded url is expired. Please generate a new one.");
            return new ResponseEntity<UrlErrorDTO>(errorDTO, HttpStatus.OK);
        }

        response.sendRedirect(url.getOriginalUrl());
        return null;
    }

}
