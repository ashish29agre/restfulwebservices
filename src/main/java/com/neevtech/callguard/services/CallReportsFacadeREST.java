/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neevtech.callguard.services;

import com.neevtech.callguard.CallReports;
import com.neevtech.callguard.Categories;
import com.neevtech.callguard.ReportedNumbers;
import com.neevtech.callguard.Users;
import com.neevtech.callguard.request.CallReportRequest;
import com.neevtech.callguard.request.Paging;
import com.neevtech.callguard.response.CallReportResponse;
import com.neevtech.callguard.response.ResponseDTO;
import com.neevtech.callguard.utils.Constants;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author ashish
 */
@Stateless
@Path("callreports")
public class CallReportsFacadeREST extends AbstractFacade<CallReports> {
    
    @Inject
    ResponseDTO dTO;
    @Inject
    CallReports callReport;
    @Inject
    ReportedNumbers reportedNumber;
    @Inject
    Categories c;
    @Inject
    CallReportResponse callReportResponse;
//    @Inject
//    ReportedNumbersFacadeREST rnfrest;
    @PersistenceContext(unitName = "com.neevtech.callguard_CallGuard_war_1.0SNAPSHOTPU")
    private EntityManager em;
    
    public CallReportsFacadeREST() {
        super(CallReports.class);
    }
    
    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(CallReports entity) {
        super.create(entity);
    }
    
    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(CallReports entity) {
        super.edit(entity);
    }
    
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }
    
    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public CallReports find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<CallReports> findAll() {
        return super.findAll();
    }

//    @POST
//    @Path("getCallReports")
//    @Consumes("application/json")
//    @Produces("application/json")
//    public List<CallReports> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }
    public List<CallReports> findRange(Paging p) {
        int from = Integer.parseInt(p.getFrom());
        int to = Integer.parseInt(p.getTo());
        List<CallReports> responseList = super.findRange(new int[]{from, to});
        Users u = getUserObject(p.getToken());
        if (u != null) {
            return responseList;
        } else {
            return null;
        }
        
    }
    
    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @POST
    @Path("createReport")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createReport(CallReportRequest request) {
//        CallReports entity = new CallReports();
        callReport.setComment(request.getComment());
        callReport.setReportedNumber(request.getNumber());
        Users u = getUserObject(request.getToken());
        if (u == null) {
//            System.err.print("User search" + u.getToken());
            dTO.setStatus(Constants.STATUS_FAILURE);
            dTO.setMessage(Constants.TOKEN_EXPIRED);
            return Response.status(200).entity(dTO).build();
        }
        callReport.setUserId(u);
        System.err.println("Callreport " + callReport.toString());
        if (!isAlreadyReported(callReport)) {
            try {
                getEntityManager().persist(callReport);
                dTO.setStatus(Constants.STATUS_SUCCESS);
                dTO.setMessage(Constants.REPORTED_SUCCESS_MSG);
                doBanTask(callReport.getReportedNumber());
                return Response.status(200).entity(dTO).build();
            } catch (Exception e) {
                e.printStackTrace();
                dTO.setStatus(Constants.STATUS_FAILURE);
                dTO.setMessage(Constants.REPORTED_FAILURE_MSG);
                return Response.status(503).entity(dTO).build();
            }
        } else {
            dTO.setStatus(Constants.STATUS_FAILURE);
            dTO.setMessage(Constants.REPORTED_ALREADY_MSG);
            return Response.status(200).entity(dTO).build();
        }
    }
    
    public boolean isAlreadyReported(CallReports report) {
        Query q = getEntityManager().createNamedQuery("CallReports.findByUserIdAndReportedNumber", Users.class);
        q.setParameter("reportedNumber", report.getReportedNumber());
        q.setParameter("userId", report.getUserId());
        if (q.getResultList().size() > 0) {
            System.err.print("u Already repoted this number>0");
            return true;
        } else {
            System.err.print("no reported yet report now0");
            return false;
        }
    }
    
    private Users getUserObject(String token) {
        Query q = getEntityManager().createNamedQuery("Users.findByToken", Users.class);
        q.setParameter("token", token);
        if (q.getResultList().size() == 1) {
            Users u = (Users) q.getResultList().get(0);
            System.err.println("Found User Object is" + u.getEmailId());
            return u;
        } else {
            System.err.println("Found User Object null");
            return null;
        }
    }
    
    private boolean doBanTask(String number) {
        Query q = em.createNamedQuery("CallReports.findByReportedNumber", CallReports.class);
        q.setParameter("reportedNumber", number);
        if (q.getResultList().size() > 1) {
            System.err.println("This number will be banned now" + number);
            banNumber(number);
            return true;
        } else {
            System.out.println("number not eligible to be banned yet");
            return false;
        }
    }
    
    private void banNumber(String number) {
        reportedNumber.setDateCreated(new Date());
        reportedNumber.setLastUpdated(new Date());
        reportedNumber.setNumber(number);
        reportedNumber.setBanned(Boolean.TRUE);
        Query q = getEntityManager().createNamedQuery("ReportedNumbers.findByNumber", ReportedNumbers.class);
        q.setParameter("number", number);
        if (q.getFirstResult() == 0) {
            System.out.println("No present in the ban list");
            c.setDateCreated(new Date());
            c.setLastUpdated(new Date());
            c.setName(Constants.DEFAULT_CATEGORY);
            getEntityManager().persist(reportedNumber);
        } else {
            System.out.println("Present in the ban list");
        }
    }

    /**
     *
     * @param p
     * @return
     */
//    @POST
//    @Path("getCallReports")
//    @Consumes("application/json")
//    @Produces("application/json")
////    public List<CallReports> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
////        return super.findRange(new int[]{from, to});
////    }
//    public List<CallReports> getCallReports(Paging p) {
//        int from = Integer.parseInt(p.getFrom());
//        int to = Integer.parseInt(p.getTo());
//        List<CallReports> responseList = super.findRange(new int[]{from, to});
//        Users u = getUserObject(p.getToken());
//        if (u != null) {
//            return responseList;
//        } else {
//            return null;
//        }
//    }
//    
//    private List<CallReportResponse> processCallResponse(List<CallReports> callReports) {
//        List<CallReportResponse> reportResponses = new ArrayList<CallReportResponse>();
//        for (CallReports cr : callReports) {
//            callReportResponse.setIsBanned(true);
//        }
//        return reportResponses;
//    }
}
