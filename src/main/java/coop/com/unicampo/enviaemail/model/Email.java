/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coop.com.unicampo.enviaemail.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author DBS
 */
@Entity(name = "GEEMAIL")
public class Email implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SQ_EMAIL")
    private Integer id;

    @Column(name = "DS_DE")
    private String from;

    @Column(name = "DS_PARA")
    private String to;

    @Column(name = "DS_CC")
    private String cc;

    @Column(name = "DS_CCO")
    private String cco;

    @Column(name = "DS_ASSUNTO")
    private String subject;

    @Column(name = "DS_CORPO")
    private String body;

    @Column(name = "DS_ANEXO")
    private String anexo;

    @Column(name = "ST_ENVIADO")
    private Integer isEnviado;

    @Column(name = "DS_SENHA")
    private String password;

    @Column(name = "DS_COMPUTADOR")
    private String pc;

    @Column(name = "DS_IP")
    private String ip;

    @Column(name = "SQ_SMTP")
    private Integer codigoConfiguracao;

    @Column(name = "NR_TENTATIVAS")
    private Integer numeroDeTentativas;
    
    @Column(name = "DS_MENSAGEM_ERRO")
    private String mensagemDeErro;

    public String getMensagemDeErro() {
        return mensagemDeErro;
    }

    public void setMensagemDeErro(String mensagemDeErro) {
        this.mensagemDeErro = mensagemDeErro;
    }
    
    

    public Integer getCodigoConfiguracao() {
        return codigoConfiguracao;
    }

    public void setCodigoConfiguracao(Integer codigoConfiguracao) {
        this.codigoConfiguracao = codigoConfiguracao;
    }

    public Integer getNumeroDeTentativas() {
        return numeroDeTentativas;
    }

    public void setNumeroDeTentativas(Integer numeroDeTentativas) {
        this.numeroDeTentativas = numeroDeTentativas;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCco() {
        return cco;
    }

    public void setCco(String cco) {
        this.cco = cco;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAnexo() {
        return anexo;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public Integer getIsEnviado() {
        return isEnviado;
    }

    public void setIsEnviado(Integer isEnviado) {
        this.isEnviado = isEnviado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Email)) {
            return false;
        }
        Email other = (Email) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "coop.com.unicampo.enviaemail.model.Email[ id=" + id + " ]";
    }

}
