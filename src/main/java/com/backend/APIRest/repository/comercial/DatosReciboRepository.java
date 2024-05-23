package com.backend.APIRest.repository.comercial;

import com.backend.APIRest.model.entidades.comercial.DatosReciboView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface
DatosReciboRepository extends JpaRepository<DatosReciboView, Integer>
{

    String query = "Select  @rownum\\:=@rownum+1 as id, clav_us as cuenta, nomb_us as nombre, dire_us as direccion,colo_us as colonia,tari_us as tarifa,nombre_tarifa,tipo_tarifa,giro, falt_us as fecha_alta,tele_us as telefono,email_us as email,fulp_us as fecha_ultimo_pago,mednume_us as no_medidor,marca_medidor,diametro_medidor, tipo_descuento,caducidad_descuento,ulpf_us as ultimo_periodo_facturado,lectura_anterior,lectura_actual,consumo_facturado,facturacion_del_mes,vencimiento,anticipo_ejercido,obsr_us as observacion,peri_ant1,consumo_ant1,peri_ant2,consumo_ant2,peri_ant3,consumo_ant3,id_convenio,fecha_convenio,deuda_convenio,conc_ad as concepto,\n" +
            "(select agde_cc from conceptos where clav_cc=conc_Ad) as concepto_tipo,(select desc_cc from conceptos where clav_cc=conc_Ad) as concepto_nombre ,coalesce(mont_ad,0) as adeudo,\n" +
            "coalesce((select if(sciva_cc=1,tasa_cc,if(ssiva_cc=1,0,(select iva_ta from tarifas where clav_ta=tari_us))) from conceptos where clav_cc=conc_Ad)/100,0) as tasa_iva,COALESCE(\n" +
            "round(LEAST((SELECT porcentaje FROM reglasinsen WHERE id=clave_descuento and reglasinsen.concepto=conc_ad and periodo=ulpf_us)*mont_ad/100 , (SELECT tope FROM reglasinsen WHERE id=clave_descuento and reglasinsen.concepto=conc_ad and periodo=ulpf_us)),2),0) as descuento \n" +
            "\n" +
            "from (SELECT @rownum\\:=0) r,(select clav_us,nomb_us,dire_us,colo_us,tari_us,date(falt_us) as falt_us,mednume_us, REPLACE(REPLACE(tele_us,' ',''),'-','') as tele_us,email_us,date(fulp_us) as fulp_us,ulpf_us,COALESCE(obsr_us,'') as obsr_us ,\n" +
            "(select tarifas.desc_ta from tarifas where clav_ta=tari_us) as nombre_tarifa,\n" +
            "(select if(tarifas.SM='NO','FIJA','SM') from tarifas where clav_ta=tari_us) as tipo_tarifa,\n" +
            "(select UPPER(med_marcas.desc_mm) from med_marcas where clav_mm=medmarc_us) as marca_medidor, \n" +
            "(select UPPER(med_diam.desc_dm) from med_diam where clav_dm=meddiam_us) as diametro_medidor,\n" +
            "(select desc_gi from giros where clav_gi=giro_us) as giro, \n" +
            "(select tipo_in from insen where usua_in=clav_us and fcad_in>now()) as clave_descuento,\n" +
            "(select (select desc_ti from tipoins where clav_ti=tipo_in) from insen where usua_in=clav_us and fcad_in>now()) as tipo_descuento,\n" +
            "(select fcad_in from insen where usua_in=clav_us and fcad_in>now()) as caducidad_descuento  ,\n" +
            "(select m3fa_fa from facturacion where usua_fa=clav_us and peri_fa=ulpf_us and conc_fa=1) as consumo_facturado,\n" +
            "(select sum(mont_fa) from facturacion where usua_fa=clav_us and peri_fa=ulpf_us) as facturacion_del_mes,\n" +
            "convert(IF(right(ulpf_us,2)=1,CONCAT(LEFT(ulpf_us,4)-1,'12'),ulpf_us-1) USING utf8) as peri_ant1 ,\n" +
            "COALESCE((select m3fa_fa from facturacion where usua_fa=clav_us and peri_fa=peri_ant1 and conc_fa=1),0) as consumo_ant1,\n" +
            "convert(IF(right(ulpf_us,2)=1,CONCAT(LEFT(ulpf_us,4)-1,'11'),IF(right(ulpf_us,2)=2,CONCAT(LEFT(ulpf_us,4)-1,'12'),ulpf_us-2))USING utf8) as peri_ant2 ,\n" +
            "COALESCE((select m3fa_fa from facturacion where usua_fa=clav_us and peri_fa=peri_ant2 and conc_fa=1),0) as consumo_ant2, \n" +
            "convert(IF(right(ulpf_us,2)=1,CONCAT(LEFT(ulpf_us,4)-1,'10'),IF(right(ulpf_us,2)=2,CONCAT(LEFT(ulpf_us,4)-1,'11'),IF(right(ulpf_us,2)=3,CONCAT(LEFT(ulpf_us,4)-1,'12'),ulpf_us-3)))USING utf8) as peri_ant3 ,\n" +
            "COALESCE((select m3fa_fa from facturacion where usua_fa=clav_us and peri_fa=peri_ant3 and conc_fa=1),0) as consumo_ant3,\n" +
            "COALESCE((select lant_le from lecturas where usua_le=clav_us and peri_le=ulpf_us),0) as lectura_Anterior, \n" +
            "COALESCE((select lact_le from lecturas where usua_le=clav_us and peri_le=ulpf_us),0) as lectura_Actual,\n" +
            "STR_TO_DATE(IF(rutl_us=0,(select MAX(FechaVencimiento) from ciclocomercial.cicloscomerciales where ciclocomercial.cicloscomerciales.sector=LEFT(CLAV_US,3) and periodo=ulpf_us),(select MAX(FechaVencimiento) from ciclocomercial.cicloscomerciales where ciclocomercial.cicloscomerciales.sector=rutl_us and periodo=ulpf_us)), '%d/%m/%Y') AS vencimiento, \n" +
            "COALESCE((select sum(mont_an) from anticipos where usua_an=clav_us and ppan_an=ulpf_us and concepto<>0 and mont_an<0),0) as anticipo_ejercido,\n" +
            "(select id_co from convenios2 where usua_co=clav_us and status_co='ACTIVO') as id_convenio,\n" +
            "(select date(fech_co) from convenios2 where usua_co=clav_us and status_co='ACTIVO') as fecha_convenio,\n" +
            "coalesce((select round( (select sum(mont_cd-inicial_cd) from convenios_detalle where id_cd=id_co)-(select SUM(MONT_MO) from movimientos where TIPO_MO='CM' AND idmovimientos in (SELECT convenios_movimientos.id_mo from convenios_movimientos WHERE convenios_movimientos.id_co=convenios2.id_co)),2) from convenios2 where usua_co=clav_us and status_co='ACTIVO'),0) as deuda_convenio \n" +
            "from usuarios   where clav_us = :cuenta )DATOSDEUSUARIO\n" +
            "left join adeudos on usua_ad=clav_us and conc_ad<>0 and mont_Ad<>0";

    @Query(nativeQuery = true,value=query)
    List<DatosReciboView> datosRecibo (@Param("cuenta") String cuenta);



    String queryVenc = "select STR_TO_DATE(IF(rutl_us=0,(select MAX(FechaVencimiento) from ciclocomercial.cicloscomerciales where " +
            "ciclocomercial.cicloscomerciales.sector=LEFT(CLAV_US,3) and periodo=ulpf_us),(select MAX(FechaVencimiento) " +
            "from ciclocomercial.cicloscomerciales where ciclocomercial.cicloscomerciales.sector=rutl_us and periodo=ulpf_us)), '%d/%m/%Y') AS vencimiento" +
            " from usuarios   where clav_us = :cuenta \n";
    @Query(nativeQuery = true,value=queryVenc)
    LocalDate fechaVencimiento(@Param("cuenta") String cuenta);

}
