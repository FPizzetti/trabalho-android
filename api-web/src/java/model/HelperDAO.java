
package model;

import java.util.Calendar;


class HelperDAO {
    
    public HelperDAO() {
        
    }
    
    public String generateId(String cpf) {
        
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String codigo = "GRR" + year;
        
        codigo = codigo + cpf.substring(7);
        
        return codigo;
    }
}
