package compilation;

public class CompilerUtils {
	public static final String ADD = "iadd";
	public static final String MINUS = "isub";
	public static final String MUL = "imul";
	public static final String DIV = "idiv";
	public static final String NEG = "ineg";
	public static final String PUSH = "sipush";

	public static final String DUPLICATE = "dup";

	public static final String EMPTY_CONSTRUCTOR =
			"""
					.method public <init>()V
					aload_0
					invokenonvirtual java/lang/Object/<init>()V
					return
					.end method""";

	public static final String OBJECT = "java/lang/Object";
	public static final String PUSH_NULL = "aconst_null";


	private static final String CLASS_HEADER =
			"""
					.class public %s
					.super java/lang/Object""";

	private static final String CLASS_TYPE = "L%s;";

	private static final String GET_FIELD = "getfield %s/%s %s";
	private static final String PUT_FIELD = "putfield %s/%s %s";

	private static final String DEF_FIELD = ".field public %s %s";

	private static final String LOAD_VAR = "aload_%s";
	private static final String STORE_VAR = "astore_%s";
	private static final String INIT_EMPTY_CLASS =
			"""
					new %s
					dup
					invokespecial %s/<init>()V""";

	public static String toReferenceType(String className) {
		return String.format(CLASS_TYPE, className);
	}

	public static String classHeader(String className) {
		return String.format(CLASS_HEADER, className);
	}

	public static String getField(String className, String fieldName, String fieldType) {
		return String.format(GET_FIELD, className, fieldName, fieldType);
	}

	public static String setField(String className, String fieldName, String fieldType) {
		return String.format(PUT_FIELD, className, fieldName, fieldType);
	}

	public static String defineField(String name, String type) {
		return String.format(DEF_FIELD, name, type);
	}

	public static String loadLocalVariable(int id) {
		return String.format(LOAD_VAR, id);
	}

	public static String storeLocalVariable(int id) {
		return String.format(STORE_VAR, id);
	}

	public static String initClass(String name) {
		return String.format(INIT_EMPTY_CLASS, name, name);
	}
}
