package com.example.photosappusersservice.data;

import com.example.photosappusersservice.ui.model.AlbumResponseModel;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "albums-service", fallbackFactory = AlbumsFallbackFactory.class)
public interface AlbumsServiceClient {

    @GetMapping("/users/{id}/albums")
    List<AlbumResponseModel> getAlbums(@PathVariable String id);

}

@Component
class AlbumsFallbackFactory implements FallbackFactory<AlbumsServiceClient> {

    @Override
    public AlbumsServiceClient create(Throwable throwable) {
        return new AlbumsFallback(throwable);
    }
}

@Slf4j
class AlbumsFallback implements AlbumsServiceClient{

    private Throwable throwable;

    public AlbumsFallback(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public List<AlbumResponseModel> getAlbums(String id) {
        if(throwable instanceof FeignException && ((FeignException) throwable).status() == 404) {
            log.error("404 error while calling getAlbums with id: " + id + "error: " + throwable.getLocalizedMessage());
        }
        else {
            log.error(throwable.getLocalizedMessage());
        }
        return new ArrayList<>();
    }
}

