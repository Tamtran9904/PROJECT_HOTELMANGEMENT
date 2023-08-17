package mock.project.hotelmanagement.service;

import mock.project.hotelmanagement.dto.DataResponse;
import mock.project.hotelmanagement.dto.GuestDto;
import mock.project.hotelmanagement.entity.Guest;
import mock.project.hotelmanagement.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface GuestService {
    public GuestDto createGuest(GuestDto guestDto);

    public GuestDto updateGuest(GuestDto guestDto);

    Page<GuestDto> getAllGuest(Integer number, Integer size);

    public GuestDto findGuestById(Long guestId);

    public Boolean deleteGuestById(GuestDto guestDto);


}
