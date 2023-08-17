package mock.project.hotelmanagement.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mock.project.hotelmanagement.dto.DataResponse;
import mock.project.hotelmanagement.dto.GuestDto;
import mock.project.hotelmanagement.service.impl.GuestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/guest")
@Slf4j
public class GuestController {
    @Autowired
    private GuestServiceImpl guestServiceImpl;

    @GetMapping("/find-all")
    @Caching(
            put = {
                    @CachePut(value = "tutorial", key = "{#number, #size}"),
            },
            cacheable = {
                    @Cacheable(value = "tutorial", key = "{#number, #size}")
            }
    )
    public DataResponse getAllGuest(@RequestParam(defaultValue = "0") int number,
                                    @RequestParam(defaultValue = "10") int size) {
        return new DataResponse(null, "Successfully find all Guest !", guestServiceImpl.getAllGuest(number, size), 202);
    }

    @Cacheable(value = "tutorial", key = "#guestId")
    @GetMapping("/find-by-id/{guestId}")
    public DataResponse getGuestById(@PathVariable Long guestId) {
        return new DataResponse(null, "Successfully find-by-id Guest !", guestServiceImpl.findGuestById(guestId), 202);
    }

    @PostMapping("/create")
    public DataResponse createGuest(@Valid @RequestBody GuestDto guestDto) throws ParseException {
        return new DataResponse(null, "Successfully create Guest !", guestServiceImpl.createGuest(guestDto), 202);
    }

    @CachePut(value = "tutorial", key = "#guestDto.id")
    @PostMapping("/update")
    public DataResponse updateGuest(@Valid @RequestBody GuestDto guestDto) throws ParseException {
        return new DataResponse(null, "Successfully update Guest !", guestServiceImpl.updateGuest(guestDto), 202);
    }

    @CacheEvict(value = "tutorial", key = "#guestDto.id")
    @PostMapping("/delete")
    public DataResponse deleteGuestById(@RequestBody GuestDto guestDto) {
        return new DataResponse(null, "Successfully delete Guest !", guestServiceImpl.deleteGuestById(guestDto), 202);
    }
}

