package ud.prog3.varios;

// TODO: Acabar de hacer lo de los arrays

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/** Utilidad de acceso a atributos internos de una clase con reflectividad. Incluso aunque sean privados.
 * Suministra información de uso (teórico) de memoria.
 * @author Andoni Eguíluz Morán
 * Facultad de Ingeniería - Universidad de Deusto
 */
public class AccesoAAtributos {
	private static String SEPR = "  "; // "\t";
	private static String MARCA_OBJETO_YA_VISITADO = "<#> ";
	private static int NUM_MAX_LLAMADAS = 100;  // Número de llamadas recursivas que se permiten - más se truncan
	private static int NUM_MAX_SEPRS = 10; // Número de llamadas recursivas que se visualizan con separador - más se mantiene la separación y se indica "...(n) " al principio
	private static int TAM_REF_EN_BYTES = 4;
	private static HashMap<String, Integer> TAM_EN_BYTES;
	static {
		TAM_EN_BYTES = new HashMap<>();
		TAM_EN_BYTES.put( "byte", 1 );
		TAM_EN_BYTES.put( "short", 2 );
		TAM_EN_BYTES.put( "int", 4 );
		TAM_EN_BYTES.put( "long", 8 );
		TAM_EN_BYTES.put( "float", 4 );
		TAM_EN_BYTES.put( "double", 8 );
		TAM_EN_BYTES.put( "char", 2 );
		TAM_EN_BYTES.put( "boolean", 1 );   // Es un bit pero la unidad mínima de asignación es un byte
	}

	private static int numLlamadas = 0;  // Para truncar la recursividad
	private static HashSet<Object> objetosYaRecorridos = new HashSet<>();
	private static int tamObjeto = 0;
	private static String camposAMostrar = "";
	private static String ret = "";
	private static String atsInstancia = "";
	private static String atsStatic = "";
	private static String atsErr = "";
	private static boolean hayErr = false;
	
	/** Calcula un string para visualización de atributos (y sus valores, y sus tamaños en memoria) del objeto indicado
	 * @param mens	Mensaje inicial que se muestra en consola en la primera línea
	 * @param mens	Separador (de tabulación) que se usa en cada línea de atributo
	 * @param o	Objeto que se quiere analizar
	 * @param tbStatic	true si se quieren visualizar también los atributos estáticos
	 * @param mostrarTam true si se quiere mostrar el tamaño ocupado por el objeto y sus atributos
	 * @return	String maquetado con toda la información
	 */
	public static String atributosYValoresToString( String mens, String sep, Object o, boolean tbStatic, boolean mostrarTam ) {
		ret = (mens.equals("")) ? "" : (mens + "\n");
		atsInstancia = !tbStatic ? "" : "Atributos de instancia:\n";
		atsStatic = "Atributos de clase (static):\n";
		atsErr = "Errores de acceso a atributos:\n";
		numLlamadas = 0;
		tamObjeto = 0;
		hayErr = false;
		// objetosYaRecorridos = new HashSet<>();  // Hecho ya una vez en inicialización
		atributosYValoresToStringRec( mens, sep, o, tbStatic, mostrarTam );
		objetosYaRecorridos.clear();
		ret += atsInstancia;
		if (tbStatic) ret += atsStatic;
		if (hayErr) ret += atsErr;
		if (mostrarTam) {
			ret += "{" + tamObjeto + "}\n";
		}
		camposAMostrar = "";  // Prepara para la siguiente ejecución
		return ret;
	}
	/** Devuelve un despliegue de los atributos (y sus valores, y sus tamaños en memoria) de un objeto,
	 * explorando en profundidad todos los objetos contenidos en él.
	 * @param mens	Mensaje inicial que se muestra en consola en la primera línea
	 * @param mens	Separador (de tabulación) que se usa en cada línea de atributo
	 * @param o	Objeto que se quiere analizar
	 * @param tbStatic	true si se quieren visualizar también los atributos estáticos
	 * @param mostrarTam true si se quiere mostrar el tamaño ocupado por el objeto y sus atributos
	 * @param camposAMostrar String que incluye los nombres de campos que queremos mostrar (el resto no aparecen en el resultado).<p>
	 * Por ejemplo, si queremos los atributos root y left podríamos pasar "root#left" a este parámetro.
	 * @return	String maquetado con toda la información
	 */
	public static String atributosYValoresToString( String mens, String sep, Object o, boolean tbStatic, boolean mostrarTam, String camposAMostrar ) {
		AccesoAAtributos.camposAMostrar = camposAMostrar;
		return atributosYValoresToString(mens, sep, o, tbStatic, mostrarTam);
	}
	private static void atributosYValoresToStringRec( String mens, String sep, Object o, boolean tbStatic, boolean mostrarTam ) {
		String miSep = sep+SEPR;
		String sepSgte = miSep;
		if (numLlamadas > NUM_MAX_SEPRS) {
			miSep = sep + "...(" + numLlamadas + ") ";
			sepSgte = sep;
		}
		Class<?> c = o.getClass();
		Field fs[] = c.getDeclaredFields();
		if (c.getName().startsWith("[")) {  // El objeto es un array. Ojo entonces no tiene atributos - es un objeto especial
			String tipoArr = tipoArray(o);
			int tamArr = tamArray(o);
			int tamanyo = 0;
			if (TAM_EN_BYTES.containsKey(tipoArr))
				tamanyo = tamArr * TAM_EN_BYTES.get( tipoArr );
			else
				tamanyo = tamArr * TAM_REF_EN_BYTES;
			String tam = "{" + tamanyo + "} ";
			tamObjeto += tamanyo;
			if (camposAMostrar.equals(""))
				atsInstancia += (miSep + tam + " <" + tamArr + ">*" + aString(o, o.getClass()) + "\n");
			if (!TAM_EN_BYTES.containsKey(tipoArr)) {
				// for de proceso de elementos de array si son objetos (y no son nulls)
				numLlamadas++;
				for (int i=0; i<tamArr; i++) {
					Object oArr = valorArray(o,i);
					if (oArr!=null) {
						if (objetosYaRecorridos.contains( oArr )) {  // Si ya está recorrido, no insistimos :-)
							if (camposAMostrar.equals(""))
								atsInstancia += (miSep + SEPR + "{} [" + i + "] = " + MARCA_OBJETO_YA_VISITADO + aString(oArr, oArr.getClass()) + "\n" );
						} else {
							if (camposAMostrar.equals(""))
								atsInstancia += (miSep + SEPR+ "{} [" + i + "] = " + aString(oArr, oArr.getClass()) + "\n" );
							objetosYaRecorridos.add( oArr );
							atributosYValoresToStringRec("", sepSgte+SEPR, oArr, tbStatic, mostrarTam );
						}
					}
				}
				numLlamadas--;
			}
		} else {  // Objeto normal - con atributos
			for (Field f : fs) {
				try {
					f.setAccessible(true);
					if (Modifier.isStatic(f.getModifiers())) {
						if (tbStatic)
							if (camposAMostrar.equals("") || camposAMostrar.contains(f.getName()))
								atsStatic += (miSep + f.getName() + " = " + aString(f.get( null ), f.getType()) + "\n" );
					} else {
						// atsInstancia += ( "#" + f.getType().toString().substring(6) );
						// atsInstancia += ( "# - #" + c.getName().toString() + "#\n" );
						String tam = "";
						if (mostrarTam) {  // Cálculo tamaño
							int tamanyo = 0;
							if (f.getType().toString().startsWith("class [") && f.get(o)!=null) {
								tamanyo = TAM_REF_EN_BYTES;  // Un array es una referencia
							} else if (f.getType().toString().startsWith("class ")) {
								tamanyo = TAM_REF_EN_BYTES;
							} else if (f.getType().toString().startsWith("interface [") && f.get(o)!=null) {
								tamanyo = TAM_REF_EN_BYTES;  // Un array es una referencia
							} else if (f.getType().toString().startsWith("interface "))
								tamanyo = TAM_REF_EN_BYTES;
							else if (TAM_EN_BYTES.containsKey(f.getType().toString()))
								tamanyo = TAM_EN_BYTES.get( f.getType().toString() );
							else {
								if (f.get(o)!=null) {
									System.err.println( "TAMAÑO DESCONOCIDO!!!");
									System.err.println( "Tipo -> " + f.getType().toString() );
									System.err.println( "Objeto -> " + f.get(o) );
									System.exit(0);
								}
								tamanyo = 4;
							}
							tam = "{" + tamanyo + "} ";
							tamObjeto += tamanyo;
						}
						numLlamadas++;
						if (f.getType().toString().startsWith("class ") && f.get(o)!=null ||
						    f.getType().toString().startsWith("interface ") && f.get(o)!=null) {
							//	Si quisiéramos diferenciar el objeto con definición recursiva directa
							//	if (f.getType().toString().substring(6).equals( c.getName().toString() )) {
							if (objetosYaRecorridos.contains( f.get(o) )) {  // Si ya está recorrido, no insistimos :-)
								if (camposAMostrar.equals("") || camposAMostrar.contains(f.getName()))
									atsInstancia += (miSep + tam + f.getName() + " = " + MARCA_OBJETO_YA_VISITADO + aString(f.get( o ), f.getType()) + "\n" );
							} else {
								if (camposAMostrar.equals("") || camposAMostrar.contains(f.getName()))
									atsInstancia += (miSep + tam + f.getName() + " = " + aString(f.get( o ), f.getType()) + "\n" );
								objetosYaRecorridos.add( f.get(o) );
								if (numLlamadas > NUM_MAX_LLAMADAS) {  
									atsInstancia += (miSep + "TRUNCADO POR EXCESO DE RECURSIVIDAD\n");
								} else {
									atributosYValoresToStringRec("", sepSgte, f.get(o), tbStatic, mostrarTam );
								}
							}
						} else {
							if (camposAMostrar.equals("") || camposAMostrar.contains(f.getName()))
								atsInstancia += (miSep + tam + f.getName() + " = " + aString(f.get( o ), f.getType()) + "\n" );
						}
						numLlamadas--;
					}
	//				Otra manera de hacerlo:
	//				try {
	//					atsStatic += ("  " + f.getName() + " = " + aString(f.get( null )) + "\n" );
	//				} catch (NullPointerException e2) {
	//					atsInstancia += ("  " + f.getName() + " = " + aString(f.get( o )) + "\n" );
	//				}
				} catch (Exception e) {
					if (camposAMostrar.equals("") || camposAMostrar.contains(f.getName()))
						atsErr += ( miSep + f.getName() + " (Error " + e.getClass().getName() + " / " + e.getMessage() + ")\n");
					hayErr = true;
					e.printStackTrace();
				}
			}
		}
	}

		// Devuelve el tipo base del array o
		private static String tipoArray( Object o ) {
			// o.getClass().getSimpleName() es "tipo[]"
			return o.getClass().getSimpleName().substring(0, o.getClass().getSimpleName().length()-2 );
		}
		// Devuelve el número de elementos del array o
		private static int tamArray( Object o ) {
			if (o instanceof char[]) {
				char[] arr = (char[]) o;
				return arr.length;
			} else if (o instanceof boolean[]) {
				boolean[] arr = (boolean[]) o;
				return arr.length;
			} else if (o instanceof int[]) {
				int[] arr = (int[]) o;
				return arr.length;
			} else if (o instanceof long[]) {
				long[] arr = (long[]) o;
				return arr.length;
			} else if (o instanceof float[]) {
				float[] arr = (float[]) o;
				return arr.length;
			} else if (o instanceof double[]) {
				double[] arr = (double[]) o;
				return arr.length;
			} else if (o instanceof byte[]) {
				byte[] arr = (byte[]) o;
				return arr.length;
			} else if (o instanceof short[]) {
				short[] arr = (short[]) o;
				return arr.length;
			} else if (o instanceof Object[]) {
				Object[] arr = (Object[]) o;
				return arr.length;
			} 
			return 0;
		}
		// Devuelve el valor del elemento i del array o
		private static Object valorArray( Object o, int i ) {
			Object[] arr = (Object[]) o;
			return arr[i];
		}
	
		// Devuelve la representación (toString()) del objeto. Si es un array,
		// en su lugar lo devuelve maquetado [ valor1 valor2 valor3 ... ]
		private static String aString( Object o, Class<?> tipo ) {
			String ret;
			if (o==null) {
				ret = "(" + tipo.getName().toString() + ") null";
			} else if (!tipo.toString().startsWith("class")) {
				ret = "(" + tipo.getName().toString() + ") " + o.toString();
			} else if (o instanceof Object[]) {
				Object[] arr = (Object[]) o;
				ret = "(" + o.getClass().getTypeName() + ") [";
				for (int i=0; i<arr.length; i++) {
					ret += arr[i];
					if (i+1<arr.length) ret += " ";
				}
				ret += "]";
			} else if (o instanceof char[]) {
				char[] arr = (char[]) o;
				ret = "(char[]) [";
				for (int i=0; i<arr.length; i++) {
					ret += arr[i];
					if (i+1<arr.length) ret += " ";
				}
				ret += "]";
			} else if (o instanceof boolean[]) {
				boolean[] arr = (boolean[]) o;
				ret = "(boolean[]) [";
				for (int i=0; i<arr.length; i++) {
					ret += arr[i];
					if (i+1<arr.length) ret += " ";
				}
				ret += "]";
			} else if (o instanceof int[]) {
				int[] arr = (int[]) o;
				ret = "(int[]) [";
				for (int i=0; i<arr.length; i++) {
					ret += arr[i];
					if (i+1<arr.length) ret += " ";
				}
				ret += "]";
			} else if (o instanceof long[]) {
				long[] arr = (long[]) o;
				ret = "(long[]) [";
				for (int i=0; i<arr.length; i++) {
					ret += arr[i];
					if (i+1<arr.length) ret += " ";
				}
				ret += "]";
			} else if (o instanceof float[]) {
				float[] arr = (float[]) o;
				ret = "(float[]) [";
				for (int i=0; i<arr.length; i++) {
					ret += arr[i];
					if (i+1<arr.length) ret += " ";
				}
				ret += "]";
			} else if (o instanceof double[]) {
				double[] arr = (double[]) o;
				ret = "(double[]) [";
				for (int i=0; i<arr.length; i++) {
					ret += arr[i];
					if (i+1<arr.length) ret += " ";
				}
				ret += "]";
			} else if (o instanceof byte[]) {
				byte[] arr = (byte[]) o;
				ret = "(byte[]) [";
				for (int i=0; i<arr.length; i++) {
					ret += arr[i];
					if (i+1<arr.length) ret += " ";
				}
				ret += "]";
			} else if (o instanceof short[]) {
				short[] arr = (short[]) o;
				ret = "(byte[]) [";
				for (int i=0; i<arr.length; i++) {
					ret += arr[i];
					if (i+1<arr.length) ret += " ";
				}
				ret += "]";
			} else {
				ret = "(" + o.getClass().getTypeName() + ") " + o.toString();
			}
			return ret;
		}

		class ClaseInterna {
			int val;
		}
		
	/** Prueba de la clase de acceso a atributos
	 * @param args
	 */
	public static void main(String[] args) {
		Integer o = new Integer(7);
		System.out.println( atributosYValoresToString( "INTEGER 7", "", o, true, false ) );
		
		
		ClaseInterna intt = (new AccesoAAtributos()).new ClaseInterna();
		System.out.println( atributosYValoresToString( "Clase interna", "", intt, true, false ) );
		
		String tit = "ARRAY DE ENTEROS CON 7";
		int[] ar = new int[10];
		ar[0] = 7;
		System.out.println( atributosYValoresToString( tit, "", ar, false, true ) );

		tit = "ARRAYLIST DE ENTEROS CON 7";
		ArrayList<Integer> al = new ArrayList<>();
		al.add( new Integer(7) );
		System.out.println( atributosYValoresToString( tit, "", al, false, true ) );
		
		tit = "LINKEDLIST DE ENTEROS CON 7,2,3";
		LinkedList<Integer> ll = new LinkedList<>();
		ll.add( new Integer(7) );
		ll.add( new Integer(2) );
		ll.add( new Integer(3) );
		System.out.println( atributosYValoresToString( tit, "", ll, false, true ) );
		
		tit = "LINKEDLIST DE ENTEROS CON 150 VALORES";
		ll = new LinkedList<>();
		for (int i=1; i<=150; i++) ll.add( new Integer(i) );
		System.out.println( atributosYValoresToString( tit, "", ll, false, true ) );

		tit = "ARRAY DE STRINGS";
		String[] sts = { "Hola", "Adiós", "Vale" };
		System.out.println( atributosYValoresToString( tit, "", sts, false, true ) );
		
		tit = "HASHMAP DE STRINGS-ENTEROS CON 2 VALORES";
		HashMap<String,Integer> hm = new HashMap<>();
		hm.put( "Clave1", 1 );
		hm.put( "Clave2", 2 );
		System.out.println( atributosYValoresToString( tit, "", hm, false, true ) );

		tit = "HASHSET DE STRINGS (vacío, cap. inicial 2, con 1, 2, 3 y 4 elementos)";
		HashSet<String> hs = new HashSet<>(2);
		System.out.println( atributosYValoresToString( tit, "", hs, false, true ) );
		hs.add( "Andoni" );
		System.out.println( atributosYValoresToString( tit, "", hs, false, true ) );
		hs.add( "Elena" );
		System.out.println( atributosYValoresToString( tit, "", hs, false, true ) );
		hs.add( "Rosa" );
		System.out.println( atributosYValoresToString( tit, "", hs, false, true ) );
		hs.add( "Asier" );
		System.out.println( atributosYValoresToString( tit, "", hs, false, true ) );

		tit = "TREESET DE STRINGS";
		TreeSet<String> ts = new TreeSet<>();
		ts.add( "Andoni" );
		ts.add( "Elena" );
		ts.add( "Asier" );
		ts.add( "Rosa" );
		System.out.println( atributosYValoresToString( tit, "", ts, false, true ) );
		tit = "TREESET DE STRINGS SOLO CON LA ESTRUCTURA";
		System.out.println( atributosYValoresToString( tit, "", ts, false, false, 
				"root#key#left#right" ) );

	}
	
}
