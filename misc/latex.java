/**
 * @file Doclet osztály, ami LaTeX kimenetet gyárt.
 *
 * javac -classpath /usr/lib/jvm/java-6-sun-1.6.0.15/lib/tools.jar latex.java
 * 
 * 
 * repos/src$ mv build/latex.class . ; javadoc -doclet latex -classpath
 * /usr/lib/jvm/java-6-sun-1.6.0.15/lib/tools.jar -private `ls *.java |grep -v
 * Skel|gerp -v latex` | iconv -t latin2 | sed -e '1,/==== CUT/ d' >
 * ../doc/jdoc.tex && ( cd ../doc/; make ) || ( cd ../doc/; make )
 * 
 */
import com.sun.javadoc.*;

public class latex extends Doclet {

	public static boolean start(RootDoc root) {
		System.out.println("==== CUT HERE =====");

		ClassDoc[] classes = root.classes();
		for (ClassDoc cd : classes) {
			System.out.println("\\oszt{" + cd.name() + "}");
			if (cd.isInterface())
				System.out.println("Interfész.");
			else if (cd.isAbstract())
				System.out.println("Absztrakt osztály.");

			System.out.println("\\begin{description}");
			System.out.println("\\item[Felelősség]");
			System.out.println(cd.commentText());
			if (cd.commentText().length() < 5)
				System.out.println(" % TODO");
			System.out.println("\\item[Ősosztályok] " + getSupers(cd) + ".");
			System.out.println("\\item[Interfészek] " + getIfaces(cd));
			if (!cd.isInterface()) {
				System.out
						.println("\\item[Attribútumok]$\\ $\n\\begin{description}");
				printFields(cd.fields());
				System.out.println("\\end{description}");
			}
			System.out.println("\\item[Metódusok]$\\ $\n\\begin{description}");
			printMembers(cd.methods());
			System.out.println("\\end{description}");
			System.out.println("\\end{description}");
			System.out.println();
		}
		return true;
	}

	private static String getIfaces(ClassDoc cd) {
		ClassDoc[] ifs = cd.interfaces();
		String s = null;
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
		for (FieldDoc mem : mems) {
			System.out.print("\t\\item[\\texttt{" + mem.modifiers() + " "
					+ mem.type().simpleTypeName() + " " + mem.qualifiedName()
					+ "}] ");
			if (mem.commentText().length() < 5)
				System.out.println(" % TODO");
			System.out.println(mem.commentText());
		}
		if (mems.length == 0)
			System.out.println("(nincs)");
	}

	static void printMembers(MethodDoc[] mems) {
		for (MethodDoc mem : mems) {
			if (mem.name().compareTo("toString") != 0) {
				System.out.print("\t\\item[\\texttt{" + mem.modifiers() + " "
						+ mem.returnType().simpleTypeName() + " "
						+ mem.toString() + "}] ");
				if (mem.commentText().length() < 5)
					System.out.println(" % TODO");
				System.out.println(mem.commentText());
			}
		}
		if (mems.length == 0)
			System.out.println("(nincs)");
	}
}
