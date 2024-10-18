package com.Thai.CRUD.repository.httpClient;

import com.Thai.CRUD.dto.request.ProfileRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "profile-service", url = "http://localhost:8083/profile")
public interface ProfileClient {

    @PostMapping(value = "/create",produces = MediaType.APPLICATION_JSON_VALUE)
    void createProfile(@RequestBody ProfileRequest request);
}
