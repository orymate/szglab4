/**
 * $ javac -classpath /usr/lib/jvm/java-6-sun/lib/tools.jar latex.java 
 * $javadoc -private -doclet latex ../src/{controller,view}/*.java | \
 *   iconv -t latin2 | sed -e '1,/==== CUT/ d' -e '/==== END/,$ d' >doc.tex
 */

import com.sun.javadoc.*;
import java.util.Arrays;

public class latex extends Doclet {

	public static boolean start(RootDoc root) {
		System.out.println("==== CUT HERE =====");

		ClassDoc[] classes = root.classes();
		Arrays.sort(classes);
		for (ClassDoc cd : classes) {
			System.out.println("\\oszt{" + cd.name() + "}");
			if (cd.isInterface()) {
				System.out.println("Interfész.");
			} else if (cd.isAbstract()) {
				System.out.println("Absztrakt osztály.");
			}

			System.out.println("\\begin{description}");
			System.out.println("\\item[Felelősség]");
			System.out.println(cd.commentText().replace('\n', ' '));
			if (cd.commentText().length() < 5) {
				System.out.println(" % TODO");
			}
			System.out.println("\\item[Ősosztályok] " + getSupers(cd) + ".");
			System.out.println("\\item[Interfészek] " + getIfaces(cd));
			if (!cd.isInterface()) {
				System.out.println("\\item[Attribútumok]$\\ $\n\\begin{description}");
				printFields(cd.fields());
				System.out.println("\\end{description}");
			}
			System.out.println("\\item[Metódusok]$\\ $\n\\begin{description}");
			printMembers(cd.methods());
			System.out.println("\\end{description}");
			System.out.println("\\end{description}");
			System.out.println();
		}
		System.out.println("==== END CUT HERE =====");
		return true;
	}

	private static String getIfaces(ClassDoc cd) {
		ClassDoc[] ifs = cd.interfaces();
		String s = null;
		Arrays.sort(ifs);
		for (ClassDoc iface : ifs) {
			s = (s == null ? "" : s + ", ") + iface.name();
		}
		return s == null ? "(nincs)" : (s + ".");
	}

	private static String getSupers(ClassDoc cl) {
		ClassDoc sup = cl.superclass();
		if (sup != null) {
			return getSupers(sup) + " $\\rightarrow{}$ " + cl.name();
		} else {
			return cl.name();
		}

	}

	private static void printFields(FieldDoc[] mems) {
		Arrays.sort(mems);
		for (FieldDoc mem : mems) {
			System.out.print("\t\\item[\\texttt{" + mem.modifiers() + " "
				+ getTypeName(mem.type()) + " " + mem.name()
				+ "}] ");
			System.out.println(mem.commentText().replace('\n', ' '));
			if (mem.commentText().length() < 5) {
				System.out.println(" % TODO");
			}
		}
		if (mems.length == 0) {
			System.out.println("(nincs)");
		}
	}

	static void printMembers(MethodDoc[] mems) {
		Arrays.sort(mems);
		for (MethodDoc mem : mems) {
			if (mem.name().compareTo("toString") != 0) {
				System.out.print("\t\\item[\\texttt{" + mem.modifiers() + " "
					+ getTypeName(mem.returnType()) + " "
					+ mem.name() + "(" + getParams(mem) + ")}] ");
				System.out.println(mem.commentText().replace('\n', ' '));
				if (mem.commentText().length() < 5) {
					System.out.println(" % TODO");
				}
			}
		}
		if (mems.length == 0) {
			System.out.println("(nincs)");
		}
	}

	static private String getTypeName(Type t) {
		String name = t.simpleTypeName();
		String par = "";
		try {
			TypeVariable[] args = t.asClassDoc().typeParameters();
			for (TypeVariable arg : args) {
				if (par.length() > 0) {
					par += ", ";
				}
				par += getTypeName(arg);
			}
		} catch (Exception e) {
			par = "";
		}
		if (par.length() > 0) {
			name += "<" + par + ">";
		}
        name += t.dimension();
		return name;
	}

	private static String getParams(MethodDoc mem) {
		Parameter[] pars = mem.parameters();
		String ret = "";
		for (Parameter p : pars) {
			if (ret.length() > 0) {
				ret += ", ";
			}
			ret += getTypeName(p.type()) + " " + p.name();
		}
		return ret;
	}
}
