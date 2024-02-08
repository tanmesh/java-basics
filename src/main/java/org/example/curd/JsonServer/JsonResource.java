package org.example.curd.JsonServer;

import jakarta.ws.rs.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Path("/data")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class
JsonResource {
    private List<Product> dataList = new ArrayList<>();

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData() {
        return Response.status(Response.Status.ACCEPTED).entity(dataList).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addData(Product data) {
        dataList.add(data);
        return Response.status(Response.Status.ACCEPTED).entity(dataList).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateData(Product data) {
        for (Product dataItem: dataList) {
            if(dataItem.getId().equals(data.getId())) {
                dataItem.setMessage(data.getMessage());
                break;
            }
        }
        return Response.status(Response.Status.ACCEPTED).entity(dataList).build();
    }

    @PATCH
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void partialUpdateData(String data) {

    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteData(@PathParam("id") String id) {
        Iterator<Product> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Product dataItem = iterator.next();
            String _id = dataItem.getId().toString();
            if (_id.equals(id)) {
                iterator.remove();
                break;
            }
        }
        return Response.status(Response.Status.ACCEPTED).entity(dataList).build();
    }
}

