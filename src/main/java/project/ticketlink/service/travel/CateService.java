package project.ticketlink.service.travel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.ticketlink.model.travel.category.MainCategory;

import project.ticketlink.model.travel.category.MiddleCategory;
import project.ticketlink.model.travel.category.SubCategory;
import project.ticketlink.model.travel.repository.CateRepository;
import project.ticketlink.model.travel.repository.MainCateRepository;
import project.ticketlink.model.travel.repository.SubCateRepository;

import java.util.List;


@Service
public class CateService {

    private final CateRepository cateRepository;
    private final MainCateRepository mainCateRepository;
    private final SubCateRepository subCateRepository;

    @Autowired
    public CateService(CateRepository cateRepository, MainCateRepository mainCateRepository,SubCateRepository subCateRepository) {
        this.subCateRepository = subCateRepository;
        this.cateRepository = cateRepository;
        this.mainCateRepository = mainCateRepository;
    }

    public List<MainCategory> findAllMainCategories() {
        return mainCateRepository.findAll();
    }

    public List<MiddleCategory> getMiddleCategoriesByCategory(Long no) {

        MainCategory mainCategory = new MainCategory();
        mainCategory.setMacNo(no);

        return cateRepository.findByMainCategory(mainCategory);

    }

    public List<SubCategory> findAllSubCategoriesByCategory(Long micPk) {
        return  cateRepository.findSubCategoriesByMicNo(micPk);
    }

    public MainCategory findMainCate(Long no) {
        return  mainCateRepository.findByMacNo(no);
    }

    public MiddleCategory findMiddleCate(Long no) {
        return cateRepository.findByMicNO(no);
    }

    public SubCategory findSubCate(Long no) {
        return subCateRepository.findBySucNo(no);
    }


}
