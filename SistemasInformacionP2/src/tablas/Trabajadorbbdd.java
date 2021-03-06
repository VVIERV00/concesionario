package tablas;
// Generated 28-may-2018 22:29:48 by Hibernate Tools 5.1.0.Alpha1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Trabajadorbbdd generated by hbm2java
 */
@Entity
@Table(name = "trabajadorbbdd", catalog = "nominas")
public class Trabajadorbbdd implements java.io.Serializable {

	private Integer idTrabajador;
	private Categorias categorias;
	private Empresas empresas;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String nifnie;
	private String email;
	private Date fechaAlta;
	private String codigoCuenta;
	private String iban;
	private Set nominas = new HashSet(0);

	public Trabajadorbbdd() {
	}

	public Trabajadorbbdd(Categorias categorias, Empresas empresas, String nombre, String apellido1, String nifnie) {
		this.categorias = categorias;
		this.empresas = empresas;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.nifnie = nifnie;
	}

	public Trabajadorbbdd(Categorias categorias, Empresas empresas, String nombre, String apellido1, String apellido2,
			String nifnie, String email, Date fechaAlta, String codigoCuenta, String iban) {
		this.categorias = categorias;
		this.empresas = empresas;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.nifnie = nifnie;
		this.email = email;
		this.fechaAlta = fechaAlta;
		this.codigoCuenta = codigoCuenta;
		this.iban = iban;
		//this.nominas = nominas;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idTrabajador", unique = true, nullable = false)
	public Integer getIdTrabajador() {
		return this.idTrabajador;
	}

	public void setIdTrabajador(Integer idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdCategoria", nullable = false)
	public Categorias getCategorias() {
		return this.categorias;
	}

	public void setCategorias(Categorias categorias) {
		this.categorias = categorias;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IdEmpresa", nullable = false)
	public Empresas getEmpresas() {
		return this.empresas;
	}

	public void setEmpresas(Empresas empresas) {
		this.empresas = empresas;
	}

	@Column(name = "Nombre", nullable = false, length = 50)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "Apellido1", nullable = false, length = 75)
	public String getApellido1() {
		return this.apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	@Column(name = "Apellido2", length = 75)
	public String getApellido2() {
		return this.apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	@Column(name = "NIFNIE", nullable = false, length = 10)
	public String getNifnie() {
		return this.nifnie;
	}

	public void setNifnie(String nifnie) {
		this.nifnie = nifnie;
	}

	@Column(name = "email", length = 75)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FechaAlta", length = 10)
	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	@Column(name = "CodigoCuenta", length = 20)
	public String getCodigoCuenta() {
		return this.codigoCuenta;
	}

	public void setCodigoCuenta(String codigoCuenta) {
		this.codigoCuenta = codigoCuenta;
	}

	@Column(name = "IBAN", length = 24)
	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trabajadorbbdd")
	public Set getNominas() {
		return this.nominas;
	}

	public void setNominas(Set nominas) {
		this.nominas = nominas;
	}

}
