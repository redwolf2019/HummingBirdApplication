package com.wqh.hummingbird.api.common.device;

import com.wqh.hummingbird.api.common.IdModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum DataPointAbilityEnum {

    R(0),
    W(1),
    RW(2),
    X(3),
    UNKNOWN(4),

    ;

    private int ability;

    public static DataPointAbilityEnum getAbility(int ability){
        return Stream.of(DataPointAbilityEnum.values())
                .filter(p->p.ability == ability)
                .findAny()
                .orElse(DataPointAbilityEnum.UNKNOWN);
    }

    public static String getAbilityName(int ability){
        return Stream.of(DataPointAbilityEnum.values())
                .filter(p->p.ability == ability)
                .findAny()
                .orElse(DataPointAbilityEnum.UNKNOWN).name();
    }

    public static List<IdModel> getList(){
        return Stream.of(DataPointAbilityEnum.values())
                .filter(p->p !=DataPointAbilityEnum.UNKNOWN)
                .filter(p->p !=DataPointAbilityEnum.X)
                .map(p-> new IdModel((long)p.ability,p.name()))
                .collect(Collectors.toList());
    }


}
