package es.udc.stembach.backend.rest.dtos;

import es.udc.stembach.backend.model.entities.Defense;

public class DefenseConversor {

    public final static DefenseDto toDefenseDto(Defense defense){
        return new DefenseDto(defense.getId(), defense.getPlace(), defense.getDate(), defense.getMark(), defense.getObservations());
    }
}
