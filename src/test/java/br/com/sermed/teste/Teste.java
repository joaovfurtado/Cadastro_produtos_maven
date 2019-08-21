/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sermed.teste;

import br.com.sermed.dal.EntityManagerHelper;
import br.com.sermed.models.Produto;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 *
 * @author joão.furtado
 */
public class Teste {
    //@Test
    public void testando(){
        EntityManagerHelper<Produto> emh = new EntityManagerHelper();
        emh.saveOrUpdate(new Produto("Gabinete",new BigDecimal(100)));
        emh.saveOrUpdate(new Produto("Monitor",new BigDecimal(200)));
        emh.saveOrUpdate(new Produto("Teclado",new BigDecimal(300f)));
        Map<String, Object> params = new HashMap();
        params.put("paramId", 1);
        emh.getListV2("produto.findById",params).forEach(str -> System.out.println(str));
        emh.getListV2("produto.findById",params).forEach(System.out::println);
        emh.close();
        
    }
    
    
}
