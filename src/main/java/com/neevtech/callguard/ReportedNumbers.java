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
@Table(name = "reported_numbers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReportedNumbers.findAll", query = "SELECT r FROM ReportedNumbers r"),
    @NamedQuery(name = "ReportedNumbers.findById", query = "SELECT r FROM ReportedNumbers r WHERE r.id = :id"),
    @NamedQuery(name = "ReportedNumbers.findByNumber", query = "SELECT r FROM ReportedNumbers r WHERE r.number = :number"),
    @NamedQuery(name = "ReportedNumbers.findByDateCreated", query = "SELECT r FROM ReportedNumbers r WHERE r.dateCreated = :dateCreated"),
    @NamedQuery(name = "ReportedNumbers.findByLastUpdated", query = "SELECT r FROM ReportedNumbers r WHERE r.lastUpdated = :lastUpdated"),
    @NamedQuery(name = "ReportedNumbers.findByBanned", query = "SELECT r FROM ReportedNumbers r WHERE r.banned = :banned")})
public class ReportedNumbers implements Serializable {
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    @Column(name = "banned")
    private Boolean banned;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne
    private Categories categoryId;

    public ReportedNumbers() {
    }

    public ReportedNumbers(Integer id) {
        this.id = id;
    }

    public ReportedNumbers(Integer id, String number, Date dateCreated, Date lastUpdated) {
        this.id = id;
        this.number = number;
        this.dateCreated = dateCreated;
        this.lastUpdated = lastUpdated;
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

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Categories getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Categories categoryId) {
        this.categoryId = categoryId;
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
        if (!(object instanceof ReportedNumbers)) {
            return false;
        }
        ReportedNumbers other = (ReportedNumbers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.neevtech.callguard.ReportedNumbers[ id=" + id + " ]";
    }
    
}
