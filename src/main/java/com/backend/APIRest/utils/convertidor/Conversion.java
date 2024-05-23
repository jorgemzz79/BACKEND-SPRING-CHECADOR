package com.backend.APIRest.utils.convertidor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Conversion {

        // Método estático para redondear un valor double a dos decimales
        public static double redondearDosDecimales(double valor) {
            BigDecimal bd = BigDecimal.valueOf(valor);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }

}
