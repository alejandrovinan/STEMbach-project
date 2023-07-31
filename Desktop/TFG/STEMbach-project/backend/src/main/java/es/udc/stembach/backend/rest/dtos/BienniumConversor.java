package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.Biennium;

import java.util.ArrayList;
import java.util.List;

public class BienniumConversor {

    public final static BienniumDto toBienniumDto(Biennium biennium){
        return new BienniumDto(biennium.getId(), biennium.getDateRange());
    }

    public final static List<BienniumDto> toBienniumDtos(List<Biennium> bienniums){
        List<BienniumDto> bienniumDtos = new ArrayList<>();

        bienniums.forEach(b -> bienniumDtos.add(toBienniumDto(b)));
        return bienniumDtos;
    }
}
