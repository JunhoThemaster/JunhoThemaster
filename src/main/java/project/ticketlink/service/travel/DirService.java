package project.ticketlink.service.travel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.ticketlink.model.travel.director.Director;
import project.ticketlink.model.travel.repository.DirecRepositoy;

import java.util.List;

@Service
public class DirService {




    private final DirecRepositoy direcRepositoy;


    @Autowired
    public DirService(DirecRepositoy direcRepositoy) {
        this.direcRepositoy = direcRepositoy;
    }


    public List<Director> getDirectors() {
        return  direcRepositoy.findAll();
    }

    public Director getDirbyId(Long no){
        return direcRepositoy.findByDirNo(no);
    }
}
