package project.ticketlink.model.travel.reservation;

import java.math.BigInteger;
import java.util.List;

public class ReservationRequest {
    private Long flightId;
    private Long productId;
    private String memId;
    private int rvCnt1;
    private int rvCnt2;
    private int rvCnt3;
    private int rvCnt4;
    private BigInteger rvTot;
    private String addReq;
    private List<Long> seats;


    public ReservationRequest() {


    }
    public void setMemId(String memId) {
        this.memId = memId;
    }

    public String getMemId(){
        return memId;
    }


    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public List<Long> getSeats() { // JSON의 'seats'와 일치
        return seats;
    }

    public void setSeats(List<Long> seats) {
        this.seats = seats;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getRvCnt1() {
        return rvCnt1;
    }

    public void setRvCnt1(int rvCnt1) {
        this.rvCnt1 = rvCnt1;
    }

    public int getRvCnt2() {
        return rvCnt2;
    }

    public void setRvCnt2(int rvCnt2) {
        this.rvCnt2 = rvCnt2;
    }

    public int getRvCnt3() {
        return rvCnt3;
    }

    public void setRvCnt3(int rvCnt3) {
        this.rvCnt3 = rvCnt3;
    }

    public int getRvCnt4() {
        return rvCnt4;
    }

    public void setRvCnt4(int rvCnt4) {
        this.rvCnt4 = rvCnt4;
    }

    public BigInteger getRvTot() {
        return rvTot;
    }

    public void setRvTot(BigInteger rvTot) {
        this.rvTot = rvTot;
    }

    public String getAddReq() {
        return addReq;
    }

    public void setAddReq(String addReq) {
        this.addReq = addReq;
    }
}
