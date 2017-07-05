package org.DAO;

import org.TIposSQL.Usuario;

public interface UsuarioDAO extends IpartekDAO {

	public Usuario[] findAll();

	public Usuario finById(int id);

	public int insert(Usuario usuario);

	public void update(Usuario usuario);

	public void delete(Usuario usuario);

	public void delete(int id);

	public boolean validar(Usuario usuario);
}
