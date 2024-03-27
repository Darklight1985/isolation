package ru.kolesnev.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class NominalDiameter {

    private final static Set<Integer> diameters = Set.of(25, 32, 40, 50, 65, 80, 100, 150, 200, 250, 300, 400, 500, 600, 700,
            800, 900, 1000);

    public static boolean checkDiameter(int diameter) {
        return diameters.contains(diameter);
    }
}
