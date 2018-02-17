package model;

import conn.MysqlConn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.HelperDAO;

public class AlunoDAO {

    private Connection connection;

    private String SQL_GET_ADDRESS = "SELECT * FROM `enderecos` WHERE id = ? LIMIT 1";
    private String SQL_LIST_ALL = "SELECT * FROM `alunos` ORDER BY nome ASC";
    private String SQL_GET_STUDENT = "SELECT * FROM `alunos` WHERE matricula = ? LIMIT 1";
    private String SQL_INSERT_ADDRESS = "INSERT INTO `enderecos` (`id`, `logradouro`, `numero`, `complemento`, `bairro`, `cep`, `cidade`, `estado`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
    private String SQL_INSERT_STUDENT = "INSERT INTO `alunos` (`id`, `matricula`, `cpf`, `nome`, `idade`, `endereco_id`) VALUES (NULL, ?, ?, ?, ?, ?)";
    private String SQL_UPDATE_ADDRESS = "UPDATE `enderecos` SET `logradouro` = ?, `numero` = ?, `complemento` = ?, `bairro` = ?, `cep` = ?, `cidade` = ?, `estado` = ? WHERE `enderecos`.`id` = ?";
    private String SQL_UPDATE_STUDENT = "UPDATE `alunos` SET `cpf` = ?, `nome` = ?, `idade` = ? WHERE `alunos`.`matricula` = ?";
    private String SQL_DELETE_STUDENT = "DELETE FROM `alunos` WHERE matricula = ? LIMIT 1";
    
    public AlunoDAO() {
        connection = MysqlConn.connect();
    }

    /*
     * Lista de alunos
     */
    public List<Aluno> listAll() {
        List<Aluno> alunos = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(SQL_LIST_ALL);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PreparedStatement stmtAdd = connection.prepareStatement(SQL_GET_ADDRESS);
                stmtAdd.setInt(1, rs.getInt("endereco_id"));
                ResultSet rsAdd = stmtAdd.executeQuery();

                Aluno obj = new Aluno();
                Endereco address = new Endereco();

                obj.setId(rs.getString("matricula"));
                obj.setCpf(rs.getString("cpf"));
                obj.setNome(rs.getString("nome"));
                obj.setIdade(rs.getInt("idade"));

                if (rsAdd.last()) {
                    if (rsAdd.isFirst()) {
                        address.setId(rsAdd.getInt("id"));
                        address.setLogradouro(rsAdd.getString("logradouro"));
                        address.setNumero(rsAdd.getInt("numero"));
                        address.setComplemento(rsAdd.getString("complemento"));
                        address.setBairro(rsAdd.getString("bairro"));
                        address.setCep(rsAdd.getString("cep"));
                        address.setCidade(rsAdd.getString("cidade"));
                        address.setEstado(rsAdd.getString("estado"));
                    }
                }

                obj.setEndereco(address);

                alunos.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }

    /*
     * Listar aluno específico
     * @param matricula
     */
    public Aluno read(String matricula) {
        Aluno obj = new Aluno();

        try {
            PreparedStatement stmt = connection.prepareStatement(SQL_GET_STUDENT);
            stmt.setString(1, matricula);
            ResultSet rs = stmt.executeQuery();

            if (rs.last()) {
                if (rs.isFirst()) {
                    PreparedStatement stmtAdd = connection.prepareStatement(SQL_GET_ADDRESS);
                    stmtAdd.setInt(1, rs.getInt("endereco_id"));
                    ResultSet rsAdd = stmtAdd.executeQuery();

                    Endereco address = new Endereco();

                    obj.setId(rs.getString("matricula"));
                    obj.setCpf(rs.getString("cpf"));
                    obj.setNome(rs.getString("nome"));
                    obj.setIdade(rs.getInt("idade"));

                    if (rsAdd.last()) {
                        if (rsAdd.isFirst()) {
                            address.setId(rsAdd.getInt("id"));
                            address.setLogradouro(rsAdd.getString("logradouro"));
                            address.setNumero(rsAdd.getInt("numero"));
                            address.setComplemento(rsAdd.getString("complemento"));
                            address.setBairro(rsAdd.getString("bairro"));
                            address.setCep(rsAdd.getString("cep"));
                            address.setCidade(rsAdd.getString("cidade"));
                            address.setEstado(rsAdd.getString("estado"));
                        }
                    }
                    obj.setEndereco(address);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return obj;
    }

    /*
     * Cadastrar um aluno
     */
    public Aluno create(Aluno aluno) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_ADDRESS, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, aluno.getEndereco().getLogradouro());
            preparedStatement.setInt(2, aluno.getEndereco().getNumero());
            preparedStatement.setString(3, aluno.getEndereco().getComplemento());
            preparedStatement.setString(4, aluno.getEndereco().getBairro());
            preparedStatement.setString(5, aluno.getEndereco().getCep());
            preparedStatement.setString(6, aluno.getEndereco().getCidade());
            preparedStatement.setString(7, aluno.getEndereco().getEstado());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao cadastrar endereco.");
            }

            try (ResultSet generatedCodigos = preparedStatement.getGeneratedKeys()) {
                if (generatedCodigos.next()) {
                    int endereco_id = generatedCodigos.getInt(1);

                    aluno.getEndereco().setId(endereco_id);

                    HelperDAO helper = new HelperDAO();
                    aluno.setId(helper.generateId(aluno.getCpf()));

                    PreparedStatement ps = connection.prepareStatement(SQL_INSERT_STUDENT, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, aluno.getId());
                    ps.setString(2, aluno.getCpf());
                    ps.setString(3, aluno.getNome());
                    ps.setInt(4, aluno.getIdade());
                    ps.setInt(5, endereco_id);
                    int res = ps.executeUpdate();

                } else {
                    throw new SQLException("Criação falhou, nenhum aluno foi gerado.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aluno;
    }

    /*
     * Atualizar um aluno
     */
    public Aluno update(String matricula, Aluno aluno) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_ADDRESS, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, aluno.getEndereco().getLogradouro());
            preparedStatement.setInt(2, aluno.getEndereco().getNumero());
            preparedStatement.setString(3, aluno.getEndereco().getComplemento());
            preparedStatement.setString(4, aluno.getEndereco().getBairro());
            preparedStatement.setString(5, aluno.getEndereco().getCep());
            preparedStatement.setString(6, aluno.getEndereco().getCidade());
            preparedStatement.setString(7, aluno.getEndereco().getEstado());
            preparedStatement.setInt(8, aluno.getEndereco().getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao atualizar endereco.");
            }

            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_STUDENT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, aluno.getCpf());
            ps.setString(2, aluno.getNome());
            ps.setInt(3, aluno.getIdade());
            ps.setString(4, matricula);
            int res = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return aluno;
    }

    /*
     * Atualizar um aluno
     */
    public void delete(String matricula) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_STUDENT);
            preparedStatement.setString(1, matricula);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
