package org.DAO;

import org.TIposSQL.Factura;

public interface FacturaDAO extends IpartekDAO {

	public Factura[] findAll();

	public Factura finById(int id);

	public int findLastNumFact();

	public int insert(Factura factura);

	public void update(Factura factura);

}
