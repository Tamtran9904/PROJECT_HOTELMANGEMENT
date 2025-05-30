package mock.project.hotelmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mock.project.hotelmanagement.entity.Company;
import mock.project.hotelmanagement.entity.Room;
import mock.project.hotelmanagement.enums.RoomType;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto extends BaseDto{
    private String status;

    private String bookingUId;

    private List<GuestDto> guests;

    private RoomType bookedType;

    private Room actualRoom;

    private String checkInDate;

    private String checkOutDate;

    private Company company;

    private boolean hasBreakfast;

    private short numberOfChild;

    private Double exchangeRate;

    private String note;

    private List<Room> listSupRooms;

    private List<Room> listDlxRooms;

    private List<Room> listSuiteRooms;

    private List<Room> listSigSuiteRooms;
}
