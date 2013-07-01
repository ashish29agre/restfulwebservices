/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neevtech.callguard.services;

import com.neevtech.callguard.ReportedNumbers;
import com.neevtech.callguard.Users;
import com.neevtech.callguard.request.Paging;
import com.neevtech.callguard.response.BannedNumberResponseDTO;
import com.neevtech.callguard.response.ReportedNumberResp;
import com.neevtech.callguard.utils.Constants;
import java.util.ArrayList;
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
@Path("reportednumbers")
public class ReportedNumbersFacadeREST extends AbstractFacade<ReportedNumbers> {

//    @Inject
//    private ReportedNumberResp resp;
    @Inject
    private BannedNumberResponseDTO bnrdto;
    @PersistenceContext(unitName = "com.neevtech.callguard_CallGuard_war_1.0SNAPSHOTPU")
    private EntityManager em;

    public ReportedNumbersFacadeREST() {
        super(ReportedNumbers.class);
    }

//    @POST
//    @Override
//    @Consumes({"application/xml", "application/json"})
    public void create(ReportedNumbers entity) {
        super.create(entity);
    }
//
//    @PUT
//    @Override

    @Consumes({"application/xml", "application/json"})
    public void edit(ReportedNumbers entity) {
        super.edit(entity);
    }

//    @DELETE
//    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

//    @GET
//    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public ReportedNumbers find(@PathParam("id") Integer id) {
        return super.find(id);
    }

//    @GET
//    @Override
    @Produces({"application/xml", "application/json"})
    public List<ReportedNumbers> findAll() {
        return super.findAll();
    }

//    @GET
//    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<ReportedNumbers> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

//    @GET
//    @Path("count")
//    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReportedNumbers findByNumber(String number) {
        Query q = em.createNamedQuery("ReportedNumbers.findByNumber", ReportedNumbers.class);
        q.setParameter("number", number);
        if (q.getResultList().size() == 1) {
            ReportedNumbers u = (ReportedNumbers) q.getResultList().get(0);
            return u;
        } else {
            return null;
        }
    }

    @POST
    @Path("getBannedNumbers")
    @Consumes("application/json")
    @Produces("application/json")
    public Response getBannedNumbers(Paging p) {
        try {
            int from = Integer.parseInt(p.getFrom());
            int to = Integer.parseInt(p.getTo());
            List<ReportedNumbers> response = super.findRange(new int[]{from, to});
            Users u = getUserObject(p.getToken());
            if (u != null) {
                List<ReportedNumberResp> respList = processCallResponse(response);
                System.err.println("List" + respList.toString());
                if (respList.size() > 0) {
                    bnrdto.setStatus(Constants.STATUS_SUCCESS);
                    bnrdto.setMessage(Constants.RESPONSE_SUCCESS_MSG);
                    bnrdto.setList(respList);
                    return Response.status(200).entity(bnrdto).build();
                } else {
                    bnrdto.setStatus(Constants.STATUS_FAILURE);
                    bnrdto.setMessage(Constants.RESPONSE_FAILURE_MSG);
                    bnrdto.setList(respList);
                    return Response.status(200).entity(bnrdto).build();
                }
            } else {
                bnrdto.setStatus(Constants.STATUS_FAILURE);
                bnrdto.setMessage(Constants.TOKEN_EXPIRED);
                bnrdto.setList(null);
                return Response.status(403).entity(bnrdto).build();
            }
        } catch (Exception e) {
            bnrdto.setStatus(Constants.STATUS_FAILURE);
            bnrdto.setMessage(Constants.RESPONSE_FAILURE_MSG);
            bnrdto.setList(null);
            return Response.status(503).entity(bnrdto).build();
        }
    }

    private List<ReportedNumberResp> processCallResponse(List<ReportedNumbers> list) {
        List<ReportedNumberResp> reportResponses = new ArrayList<ReportedNumberResp>();
        for (ReportedNumbers cr : list) {
            ReportedNumberResp resp = new ReportedNumberResp();
            resp.setBanned(cr.getBanned());
            resp.setPhoneNumber(cr.getNumber());
            System.err.println("Returning item" + resp.toString());
            reportResponses.add(resp);
        }
        System.err.println("Process response item" + reportResponses.toString());
        return reportResponses;
    }

    private Users getUserObject(String token) {
        Query q = getEntityManager().createNamedQuery("Users.findByToken", Users.class);
        q.setParameter("token", token);
        if (q.getResultList().size() == 1) {
            Users u = (Users) q.getResultList().get(0);
            System.err.println("Found User Object in reported Number" + u.getEmailId());
            return u;
        } else {
            System.err.println("Reported Number User Object null");
            return null;
        }
    }
}
