package com.canada.edu.stocktrading.utils;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class MapperUtils{
    public static <S,T> List<T> mapperList(List<S> source, Class<T> targetClass){
        ModelMapper modelMapper = new ModelMapper();
        return source
                .stream()
                .map(element -> modelMapper.map(element,targetClass))
                .collect(Collectors.toList());
    }

    public static <S,T> T mapperObject(S source, Class<T> targetObject){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(source, targetObject);
    }


//    public static DL mapperInheritance(){
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        mapper.typeMap(Daily.class, DailyDetailsDto.class)
//                .addMappings(m -> m.map(src -> src., TagVolumeDTO::setIdTag))
//    }

}
