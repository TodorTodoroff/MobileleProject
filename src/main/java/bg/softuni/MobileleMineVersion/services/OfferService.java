package bg.softuni.MobileleMineVersion.services;


import bg.softuni.MobileleMineVersion.model.dto.AddOfferDTO;
import bg.softuni.MobileleMineVersion.model.entities.ModelEntity;
import bg.softuni.MobileleMineVersion.model.entities.OfferEntity;
import bg.softuni.MobileleMineVersion.model.entities.UserEntity;
import bg.softuni.MobileleMineVersion.model.mapper.OfferMapper;
import bg.softuni.MobileleMineVersion.repositories.ModelRepository;
import bg.softuni.MobileleMineVersion.repositories.OfferRepository;
import bg.softuni.MobileleMineVersion.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    private OfferRepository offerRepository;
    private OfferMapper offerMapper;
    private UserRepository userRepository;
    private ModelRepository modelRepository;


    public OfferService(OfferRepository offerRepository,
                        OfferMapper offerMapper,
                        UserRepository userRepository,
                       ModelRepository modelRepository) {
        this.offerRepository = offerRepository;
        this.offerMapper = offerMapper;
        this.userRepository = userRepository;
        this.modelRepository = modelRepository;
    }


    public void addOffer(AddOfferDTO offerAddModel) {

        OfferEntity offer = offerMapper.addOfferDtoToOfferEntity(offerAddModel);

     //   UserEntity userEntity = userRepository.findByEmail(currentUser.getEmail()).orElseThrow();

        ModelEntity modelEntity = this.modelRepository.findById(offerAddModel.getModelId()).orElseThrow();

        offer.setModel(modelEntity);
    //    offer.setSeller(userEntity);

        this.offerRepository.save(offer);
    }

    public List<OfferEntity> getAllOffers() {

        return this.offerRepository.findAll();
    }
}
