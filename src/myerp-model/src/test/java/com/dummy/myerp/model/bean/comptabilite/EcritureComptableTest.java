package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;


import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EcritureComptableTest {

    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                                     .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        LigneEcritureComptable vRetour = new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                                                                    vLibelle,
                                                                    vDebit, vCredit);
        return vRetour;
    }

    @Test
    void isEquilibree() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();

        vEcriture.setLibelle("Equilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "200.50", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "100.50", "33"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "301"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "40", "7"));
        assertTrue(vEcriture.isEquilibree(), vEcriture.toString());
        System.out.println("Test IsEquilibree : True "+vEcriture.isEquilibree());
        System.out.println(vEcriture.toString());

        vEcriture.getListLigneEcriture().clear();
        vEcriture.setLibelle("Non équilibrée");
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "10", null));
        vEcriture.getListLigneEcriture().add(this.createLigne(1, "20", "1"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, null, "30"));
        vEcriture.getListLigneEcriture().add(this.createLigne(2, "1", "2"));
        assertFalse(vEcriture.isEquilibree(), vEcriture.toString());
        System.out.println("Test IsEquilibree : False "+vEcriture.isEquilibree());
        System.out.println(vEcriture.toString());
    }

    @Test
    void getReference() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.setReference("CL-2018/01234");
        assertTrue(vEcriture.getReference().matches("[A-Z]{2}-\\d{4}/\\d{5}"), "1-The reference doesn't matches with the pattern \"XX-AAAA/#####\"");
        System.out.println("Test getRef :"+vEcriture.getReference()+" matches with the pattern [A-Z]{2}-\\d{4}/\\d{5}");
    }

    @Test
    void referenceCodeEqualJournalCode() {
        EcritureComptable vEcriture;
        vEcriture = new EcritureComptable();
        vEcriture.setJournal(new JournalComptable("CL", "Client"));
        vEcriture.setReference("CL-2018/01234");
        assertTrue(
                vEcriture.getReference().substring(0, 2).equals(vEcriture.getJournal().getCode()),
                vEcriture.toString());
    }


}
