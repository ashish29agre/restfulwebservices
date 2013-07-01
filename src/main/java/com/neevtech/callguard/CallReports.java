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
@Table(name = "call_reports")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CallReports.findAll", query = "SELECT c FROM CallReports c"),
    @NamedQuery(name = "CallReports.findById", query = "SELECT c FROM CallReports c WHERE c.id = :id"),
    @NamedQuery(name = "CallReports.findByReportedNumber", query = "SELECT c FROM CallReports c WHERE c.reportedNumber = :reportedNumber"),
    @NamedQuery(name = "CallReports.findByComment", query = "SELECT c FROM CallReports c WHERE c.comment = :comment"),
    @NamedQuery(name = "CallReports.findByDateCreated", query = "SELECT c FROM CallReports c WHERE c.dateCreated = :dateCreated"),
    @NamedQuery(name = "CallReports.findByLastUpdated", query = "SELECT c FROM CallReports c WHERE c.lastUpdated = :lastUpdated"),
    @NamedQuery(name = "CallReports.findByUserIdAndReportedNumber", query = "SELECT c FROM CallReports c WHERE c.reportedNumber = :reportedNumber AND c.userId = :userId")})
public class CallReports implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "reported_number")
    private String reportedNumber;
    @Size(max = 45)
    @Column(name = "comment")
    private String comment;
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Users userId;

    public CallReports() {
    }

    public CallReports(Integer id) {
        this.id = id;
    }

    public CallReports(Integer id, String reportedNumber) {
        this.id = id;
        this.reportedNumber = reportedNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReportedNumber() {
        return reportedNumber;
    }

    public void setReportedNumber(String reportedNumber) {
        this.reportedNumber = reportedNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        if (!(object instanceof CallReports)) {
            return false;
        }
        CallReports other = (CallReports) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

//    @Override
//    public String toString() {
//        return "com.neevtech.callguard.CallReports[ id=" + id + " ]";
//    }
}
