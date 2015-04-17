/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coop.com.unicampo.enviaemail.Controller;

import coop.com.unicampo.enviaemail.Main;
import coop.com.unicampo.enviaemail.model.Configuracoes;
import javax.persistence.Query;
import org.apache.log4j.Logger;

/**
 *
 * @author DBS
 */
public class ConfiguracoesController {
   
    public static Configuracoes getConfiguracaoAtiva() {

        Configuracoes config = new Configuracoes();
        Query query = Main.em.createQuery("select c "
                + "   from GEMASMTP c "
                + "  where c.isAtivo = 1");
        for (Object o : query.getResultList()) {
            config = (Configuracoes) o;
        }

        return config;

    }
    
    public static Configuracoes getConfiguracaoPerID(Integer id) {

        Configuracoes config = new Configuracoes();
        Query query = Main.em.createQuery("select c "
                + "   from GEMASMTP c "
                + "  where c.id = "+ id);
        for (Object o : query.getResultList()) {
            config = (Configuracoes) o;
        }

        return config;

    }

}
