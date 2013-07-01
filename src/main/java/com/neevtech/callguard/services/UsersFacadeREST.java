/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neevtech.callguard.services;

import com.neevtech.callguard.Users;
import com.neevtech.callguard.response.LoginResponseDTO;
import com.neevtech.callguard.response.ResponseDTO;
import com.neevtech.callguard.utils.Constants;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
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
@Path("users")
public class UsersFacadeREST extends AbstractFacade<Users> {

    @Inject
    ResponseDTO dTO;
    @Inject
    LoginResponseDTO loginResponseDTO;
    private static final Logger logger = Logger.getLogger("UsersFacadeREST");
    @PersistenceContext(unitName = "com.neevtech.callguard_CallGuard_war_1.0SNAPSHOTPU")
    private EntityManager em;

    public UsersFacadeREST() {
        super(Users.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Users entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Users entity) {
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
    public Users find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Users> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Users> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
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
    @Path("createUser")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createUser(Users entity) {
//        logger.info("User phoneNumber" + entity.getPhoneNumber());
//        logger.info("Registered" + isAlreadyRegistered(entity));
        if (!isAlreadyRegistered(entity)) {
            try {
                entity.setPassword(com.neevtech.callguard.utils.PwdHash.getHash(entity.getPassword()));
                String token = com.neevtech.callguard.utils.PwdHash.getHash(entity.getPhoneNumber() + new Date().getTime());
                entity.setToken(token);
                entity.setDateCreated(new Date());
                entity.setLastUpdated(new Date());
                getEntityManager().persist(entity);
                dTO.setStatus(Constants.STATUS_SUCCESS);
                dTO.setMessage(Constants.REGISTERED_SUCCESS_MSG);
                return Response.status(200).entity(dTO).build();
            } catch (Exception e) {
                e.printStackTrace();
                dTO.setStatus(Constants.STATUS_FAILURE);
                dTO.setMessage(Constants.REGISTERED_FAILURE_MSG);
                return Response.status(503).entity(dTO).build();
            }
        } else {
            dTO.setStatus(Constants.STATUS_FAILURE);
            dTO.setMessage(Constants.REGISTERED_USER_ALREADY_REGISTERED);
            return Response.status(200).entity(dTO).build();
        }
    }

    @POST
    @Path("login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(Users u) {
        String token = checkLogin(u);
        if (token != null) {
            try {
                loginResponseDTO.setStatus(Constants.STATUS_SUCCESS);
                loginResponseDTO.setMessage(Constants.LOGIN_SUCCESS);
                loginResponseDTO.setToken(token);
                return Response.status(200).entity(loginResponseDTO).build();
            } catch (Exception e) {
                loginResponseDTO.setStatus(Constants.STATUS_SUCCESS);
                loginResponseDTO.setMessage(Constants.LOGIN_FAILURE);
                loginResponseDTO.setToken(null);
                return Response.status(403).entity(loginResponseDTO).build();
            }
        } else {
            loginResponseDTO.setStatus(Constants.STATUS_FAILURE);
            loginResponseDTO.setMessage(Constants.LOGIN_FAILURE);
            loginResponseDTO.setToken(null);
            return Response.status(403).entity(loginResponseDTO).build();
        }
    }

    public boolean isAlreadyRegistered(Users user) {
        Query q = getEntityManager().createNamedQuery("Users.findByPhoneNumber", Users.class);
        q.setParameter("phoneNumber", user.getPhoneNumber());
        if (q.getResultList().size() > 0) {
            System.err.print(">0");
            return true;
        } else {
            System.err.print("0");
            return false;
        }
    }

    public String checkLogin(Users user) {
        Query q = getEntityManager().createNamedQuery("Users.findByPhoneNumberAndPassword", Users.class);
        q.setParameter("phoneNumber", user.getPhoneNumber());
        try {
            System.err.println("hash :: " + com.neevtech.callguard.utils.PwdHash.getHash(user.getPassword()));
            q.setParameter("password", com.neevtech.callguard.utils.PwdHash.getHash(user.getPassword()));
            if (q.getResultList().size() == 1) {
                System.err.println("result is one");
                Users u = (Users) q.getResultList().get(0);
                return u.getToken();
            } else {
                System.err.println("result is false");
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
