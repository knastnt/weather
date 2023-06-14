package ru.knastnt.weather.doc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class WUnitDateList {
    private List<WUnit> wunits;
    private String date;
}
