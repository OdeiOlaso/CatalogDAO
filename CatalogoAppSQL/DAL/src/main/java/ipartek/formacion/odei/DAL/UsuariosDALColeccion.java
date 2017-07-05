package ipartek.formacion.odei.DAL;

import ipartek.formacion.odei.Tipos.Usuario;

import java.util.HashMap;
import java.util.Map;

public class UsuariosDALColeccion implements UsuariosDAL {

	private Map<String, Usuario> usuarios = new HashMap<String, Usuario>();

	public void alta(Usuario usuario) {
		if (usuarios.containsKey(usuario.getNombre()))
			throw new UsuarioYaExistenteDALException("Ya existe el usuario " + usuario.getNombre());
		usuarios.put(usuario.getNombre(), usuario);

	}

	public boolean validar(Usuario usuario) {

		return usuarios.containsValue(usuario);
	}

	public void modificar(Usuario usuario) {
		if (!usuarios.containsKey(usuario.getNombre()))
			throw new DALException("Intento de modificar usuario no existe " + usuario);

		usuarios.put(usuario.getNombre(), usuario);
	}

	public void borrar(Usuario usuario) {
		usuarios.remove(usuario.getNombre());
	}

	public Usuario buscarPorId(String id) {
		return usuarios.get(id);
	}

	public Usuario[] buscarTodosLosUsuarios() {
		// Usuario[] arr = new Usuario[usuarios.size()];
		//
		// int i = 0;
		//
		// for (Usuario usuario : usuarios.values()) {
		// arr[i++] = usuario;
		// }
		//
		// return arr;

		return usuarios.values().toArray(new Usuario[usuarios.size()]);
	}

}
