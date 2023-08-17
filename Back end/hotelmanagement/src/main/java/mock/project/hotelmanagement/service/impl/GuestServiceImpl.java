package mock.project.hotelmanagement.service.impl;

import mock.project.hotelmanagement.dto.GuestDto;
import mock.project.hotelmanagement.entity.Guest;
import mock.project.hotelmanagement.exception.CommonFunc;
import mock.project.hotelmanagement.exception.NotFoundException;
import mock.project.hotelmanagement.exception.PresentOrFuture;
import mock.project.hotelmanagement.mapper.service.impl.ConverterServiceImpl;
import mock.project.hotelmanagement.repository.GuestRepository;
import mock.project.hotelmanagement.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@EnableCaching
public class GuestServiceImpl implements GuestService {

    @Autowired
    private ConverterServiceImpl converterService;

    @Autowired
    private GuestRepository guestRepository;

    private CommonFunc commonFunc = new CommonFunc();


    @Override
    public GuestDto createGuest(GuestDto guestDto) {
        Guest guest = converterService.convertToEntity(guestDto, Guest.class);
        isValidStayPeriod(guest.getArrivalDate(), guest.getDepartureDate());

        LocalDateTime time = guest.getCreatedDate();
        guest.setCreatedDate(commonFunc.crtDate(time));

        Guest saveGuest = guestRepository.save(guest);
        GuestDto createGuestDto = converterService.convertToDto(saveGuest, guestDto.getClass());
        return createGuestDto;
    }

    @Override
    public GuestDto updateGuest(GuestDto guestDto) {
        Guest guest = converterService.convertToEntity(guestDto, Guest.class);
        isValidStayPeriod(guest.getArrivalDate(), guest.getDepartureDate());

        LocalDateTime time = guest.getCreatedDate();
        guest.setCreatedDate(commonFunc.crtDate(time));

        Guest saveGuest = guestRepository.save(guest);
        GuestDto updateGuestDto = converterService.convertToDto(saveGuest, guestDto.getClass());
        return updateGuestDto;
    }

    @Override
    public Page<GuestDto> getAllGuest(Integer number, Integer size) {
        Pageable pageable = PageRequest.of(number, size);
        return guestRepository.findAll(pageable).map(guest -> converterService.convertToDto(guest, GuestDto.class));
    }

    @Override
    public GuestDto findGuestById(Long guestId) {
        Optional<Guest> optionalGuest = guestRepository.findById(guestId);
        if (!optionalGuest.isPresent()) {
            throw new NotFoundException("Guest id: " + guestId + " : not found !");
        }
        GuestDto guestDto = converterService.convertToDto(optionalGuest.get(), GuestDto.class);
        return guestDto;
    }

    @Override
    public Boolean deleteGuestById(GuestDto guestDto) {
        Optional<Guest> optionalGuest = guestRepository.findById(guestDto.getId());
        if (!optionalGuest.isPresent()) {
            throw new NotFoundException("Guest id: " + guestDto.getId() + " : not found by deleteGuest");
        }
        Guest guestEntity = converterService.convertToEntity(guestDto, Guest.class);
        guestEntity.setDeleted(true);
        guestRepository.save(guestEntity);
        return true;
    }

    public void isValidStayPeriod(LocalDateTime arrivalDate, LocalDateTime departureDate) {
        LocalDateTime today = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(), LocalDateTime.now().getDayOfMonth(), 0, 0);
        if (arrivalDate.isBefore(today) || departureDate.isBefore(today)) {
            throw new PresentOrFuture("arrival date and departure date can not be earlier than today");
        }
        if (departureDate.isBefore(arrivalDate)) {
            throw new PresentOrFuture("Departure date can not be earlier than arrival date");
        }
    }
}
