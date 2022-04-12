package controller;

/**
 *
 * @author major
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

public class TaskController {
    
    public void save(Task task){
        String sql = "INSERT INTO tasks (idProject,"
                + "name,"
                + "description,"
                + "completed,"
                + "notes,"
                + "deadline,"
                + "createdAt,"
                + "updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = (Connection) ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.execute();            
        } catch (Exception ex) {
            throw new RuntimeException("ERRO AO SALVAR TAREFA" 
                + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
        
    }
    
    public void update(Task task){
        
        String  sql = "UPDATE tasks SET"
                + "idProject = ?"
                + "name = ?,"
                + "description = ?,"
                + "completed = ?,"
                + "notes = ?,"
                + "deadline = ?,"
                + "createdAt = ?,"
                + "updatedAt = ?"
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Estabelecendo a conexão com o banco de dados
            connection = (Connection) ConnectionFactory.getConnection();
            //Preparando a query
            statement = connection.prepareStatement(sql);
            //Setando os valores no statement            
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("ERRO AO ATUALIZAR TAREFA" 
                + ex.getMessage(), ex);
        }
    }
    
    public void removeById(int taskId){
        
        String sql = "DELETE FROM tasks WERE id = ?";        
        
        Connection connection = null;        
        PreparedStatement statement = null;
        
        try {            
            //Estabelecendo a conexão com o banco de dados
            connection = (Connection) ConnectionFactory.getConnection();
            //Preparando a query
            statement = connection.prepareStatement(sql);
            //Setando os valores
            statement.setInt(1, taskId);
            //Executando a query
            statement.execute();            
        } catch (Exception ex) {            
            throw new RuntimeException("ERRO AO DELETAR TAREFA" 
                + ex.getMessage(), ex);
        } finally{
            ConnectionFactory.closeConnection(connection, statement);
        }
        
    }
    
    public List<Task> getAll(int idProject){        
        
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        List <Task> tasks = new ArrayList<Task>();
        
        try {
            //Estabelecendo a conexão com o banco de dados
            connection = (Connection) ConnectionFactory.getConnection();
            //Preparando a query
            statement = connection.prepareStatement(sql);
            //Setando o valor que corresponde ao filtro de busca
            statement.setInt(1, idProject);
            //Valor retornado pela execução da query
            resultSet = statement.executeQuery();  
            
            //Enquanot houver valores no resultSet ele excuta
            while (resultSet.next()) {                
                
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setNotes(resultSet.getString("notes"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));                
                tasks.add(task);
            }
        } catch (Exception ex) {
            throw new RuntimeException("ERRO AO INSERIR TAREFA" 
               + ex.getMessage(), ex);
        } finally{
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        //Lista de terafas que foi criada e carregada do banco de dados.
        return tasks;
    }
    
}
