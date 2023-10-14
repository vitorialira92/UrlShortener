package com.liraz.urlshortener.services;

import com.google.common.hash.Hashing;
import com.liraz.urlshortener.domain.Url;
import com.liraz.urlshortener.dtos.UrlDTO;
import com.liraz.urlshortener.repositories.UrlRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlRepository repository;

    public Url generateShortUrl(UrlDTO urlDTO) {
        if(StringUtils.isNotEmpty(urlDTO.getUrl())){
            String shortUrl = hashShortUrl(urlDTO.getUrl());
            Url url = new Url();
            url.setOriginalUrl(urlDTO.getUrl());
            url.setShortUrl(shortUrl);
            url.setExpiresOn(LocalDateTime.now().plusDays(2));
            saveUrl(url);
            return url;
        }
        return null;
    }

    public Url getEncodedUrl(String shortUrl){
        Url url = repository.findByShortUrl(shortUrl);
        return url;
    }

    private String hashShortUrl(String url){
        String shortUrl = "";
        LocalDateTime time = LocalDateTime.now();
        shortUrl = Hashing.adler32()
                .hashString(url.concat(time.toString()),
                        StandardCharsets.UTF_8).toString();
        return shortUrl;
    }

    private void saveUrl(Url url){
        repository.save(url);
    }

}
