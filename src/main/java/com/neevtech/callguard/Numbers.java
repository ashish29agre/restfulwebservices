/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neevtech.callguard;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ashish
 */
@Entity
@Table(name = "numbers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Numbers.findAll", query = "SELECT n FROM Numbers n"),
    @NamedQuery(name = "Numbers.findById", query = "SELECT n FROM Numbers n WHERE n.id = :id"),
    @NamedQuery(name = "Numbers.findByNumber", query = "SELECT n FROM Numbers n WHERE n.number = :number"),
    @NamedQuery(name = "Numbers.findByName", query = "SELECT n FROM Numbers n WHERE n.name = :name"),
    @NamedQuery(name = "Numbers.findByDateCreated", query = "SELECT n FROM Numbers n WHERE n.dateCreated = :dateCreated"),
    @NamedQuery(name = "Numbers.findByLastUpdated", query = "SELECT n FROM Numbers n WHERE n.lastUpdated = :lastUpdated")})
public class Numbers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "number")
    private String number;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Groups groupId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users userId;

    public Numbers() {
    }

    public Numbers(Integer id) {
        this.id = id;
    }

    public Numbers(Integer id, String number) {
        this.id = id;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Groups getGroupId() {
        return groupId;
    }

    public void setGroupId(Groups groupId) {
        this.groupId = groupId;
    }

    public Users getUserId() {
        return userId;
    }

    public void setUserId(Users userId) {
        this.userId = userId;
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
        if (!(object instanceof Numbers)) {
            return false;
        }
        Numbers other = (Numbers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.neevtech.callguard.Numbers[ id=" + id + " ]";
    }
    
}
