package ru.kolesnev.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class NominalDiameter {

    private final static Set<Integer> diameters = Set.of(15, 25, 40, 50, 65, 80, 100, 125, 150, 200, 250, 300, 350, 400, 450,
            500, 550, 600, 700, 800, 900, 1000, 1400);

    public static boolean checkDiameter(int diameter) {
        return diameters.contains(diameter);
    }
}
