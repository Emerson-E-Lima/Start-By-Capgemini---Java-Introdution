package controller;

/**
 *
 * @author major
 */

import model.Project;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.ConnectionFactory;

public class ProjectController {
    
    public void save(Project project){
        
        String sql = "INSET INTO projects (name,"
                + "description,"
                + "createdAt,"
                + "updatedAt)"
                + "VALUES (?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
    
        try {
            //Estabelecendo a conex�o com o banco de dados
            connection = (Connection) ConnectionFactory.getConnection();
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("ERRO AO ATUALIZAR PROJETO", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        } 
    }
    
    public void update(Project project){
        
        String  sql = "UPDATE projects SET"
                + "name = ?,"
                + "description = ?,"               
                + "createdAt = ?,"
                + "updatedAt = ?"
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Estabelecendo a conex�o com o banco de dados
            connection = (Connection) ConnectionFactory.getConnection();
            //Preparando a query
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("ERRO AO ATUALIZAR PROJETO", ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Project> gelAll(){
        
        String sql = "SELECT * FROM projects";
        
        List<Project> projects = new ArrayList<>();
                
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        try {
            //Estabelecendo a conex�o com o banco de dados
            connection = (Connection) ConnectionFactory.getConnection();
            //Preparando a query
            statement = connection.prepareStatement(sql);
            //Valor retornado pela execu��o da query
            resultSet = statement.executeQuery();  
            
            //Enquanot houver valores no resultSet ele excuta
            while (resultSet.next()) {                
                
                Project project = new Project();
                
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("description"));
                project.setCreatedAt(resultSet.getDate("createdAt"));
                project.setUpdatedAt(resultSet.getDate("updatedAt"));                
                projects.add(project);
            }
        } catch (SQLException ex) {
            throw new RuntimeException("ERRO AO INSERIR PROJETO", ex);
        } finally{
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        //Lista de terafas que foi criada e carregada do banco de dados.
        return projects;    
    }

    public void removeById(int idProject){
        String sql = "DELETE FROM projects WERE id = ?";
        
        Connection connection = null;        
        PreparedStatement statement = null;
        
        try {            
            //Estabelecendo a conex�o com o banco de dados
            connection = (Connection) ConnectionFactory.getConnection();
            //Preparando a query
            statement = connection.prepareStatement(sql);
            //Setando os valores
            statement.setInt(1, idProject);
            //Executando a query
            statement.execute();            
        } catch (SQLException ex) {            
            throw new RuntimeException("ERRO AO DELETAR TAREFA", ex);
        } finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
}
