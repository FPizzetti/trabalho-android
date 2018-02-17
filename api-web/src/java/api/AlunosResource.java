/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import model.Aluno;
import model.AlunoDAO;

@Path("alunos")
public class AlunosResource {

    AlunoDAO dao = new AlunoDAO();

    public AlunosResource() {
        // construtor
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Aluno> getAll() {
        List<Aluno> list = dao.listAll();
        return list;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Aluno read(@PathParam("id") String id) {
        Aluno aluno = dao.read(id);
        return aluno;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Aluno create(Aluno temp) {
        Aluno novo = dao.create(temp);
        return novo;
    }

    @PUT
    @Path("/{matricula}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Aluno update(Aluno aluno, @PathParam("matricula") String matricula) {
        Aluno atualizado = dao.update(matricula, aluno);
        return atualizado;
    }

    @DELETE
    @Path("/{matricula}")
    public void delete(@PathParam("matricula") String matricula) {
        dao.delete(matricula);
    }
}
